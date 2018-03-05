package rp.assignments.team.warehouse.server.job.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.job.*;

public class Importer {
	private File jobsFile;
	private File cancellationsFile;
	private File locationsFile;
	private File itemsFile;
	private File dropsFile;

	private Map<Integer, Job> jobs;
	private Map<Integer, Location> locations;
	private Map<Integer, Item> items;
	private Set<Location> drops;

	private boolean doneParsing;

	/**
	 * @param jobsFile
	 *            The file to import the jobs from.
	 * @param cancellationsFile
	 *            The file to import the cancellation history from.
	 * @param locationsFile
	 *            The file to import the item locations from.
	 * @param itemsFile
	 *            The file to import the items from.
	 * @param dropsFile
	 *            The file to import the drop locations from.
	 */
	public Importer(File jobsFile, File cancellationsFile, File locationsFile, File itemsFile, File dropsFile) {
		assert jobsFile.exists() : "jobs file must exist";
		assert cancellationsFile.exists() : "cancellations file must exist";
		assert locationsFile.exists() : "locations file must exist";
		assert itemsFile.exists() : "items file must exist";
		assert dropsFile.exists() : "drops file must exist";

		this.jobsFile = jobsFile;
		this.cancellationsFile = cancellationsFile;
		this.locationsFile = locationsFile;
		this.itemsFile = itemsFile;
		this.dropsFile = dropsFile;

		this.doneParsing = false;
	}

	/**
	 * Parse the job input CSVs
	 *
	 * @throws IOException
	 */
	public void parse() throws IOException {
		try (BufferedReader jobsReader = new BufferedReader(new FileReader(jobsFile));
				BufferedReader cancellationsReader = new BufferedReader(new FileReader(cancellationsFile));
				BufferedReader locationsReader = new BufferedReader(new FileReader(locationsFile));
				BufferedReader itemsReader = new BufferedReader(new FileReader(itemsFile));
				BufferedReader dropsReader = new BufferedReader(new FileReader(dropsFile))) {
			this.parseLocations(locationsReader);
			this.parseItems(itemsReader);
			this.parseJobs(jobsReader);
			this.parseCancellations(cancellationsReader);
			this.parseDrops(dropsReader);
		} catch (IOException e) {
			throw e;
		}

		this.doneParsing = true;
	}

	private void parseLocations(BufferedReader locationsReader) throws IOException {
		this.locations = new HashMap<Integer, Location>();

		Pattern locationPattern = Pattern.compile("^([0-9]+)\\s*,\\s*([0-9]+)\\s*,\\s*([a-z]+)$");

		String line;
		while ((line = locationsReader.readLine()) != null) {
			Matcher m = locationPattern.matcher(line);
			if (isInvalidLine("locations", line, m))
				continue;

			int x = Integer.parseInt(m.group(1));
			int y = Integer.parseInt(m.group(2));
			int id = Item.parseId(m.group(3));

			if (!Location.isValidLocation(x, y)) {
				trace(String.format("Invalid coordinates in locations file (%d, %d)", x, y));
				continue;
			}

			Location l = new Location(x, y);

			if (!this.locations.containsKey(id)) {
				this.locations.put(id, l);
			} else {
				trace(String.format("locations file referenced item id %c in a new location (%d, %d)", id, x, y));
			}
		}
	}

	private void parseItems(BufferedReader itemsReader) throws IOException {
		this.items = new HashMap<Integer, Item>();

		Pattern itemPattern = Pattern
				.compile("^([a-z]+)\\s*,\\s*([0-9]+(?:\\.[0-9]+)?)\\s*,\\s*([0-9]+(?:\\.[0-9]+)?)$");

		String line;
		while ((line = itemsReader.readLine()) != null) {
			Matcher m = itemPattern.matcher(line);
			if (isInvalidLine("items", line, m))
				continue;

			String idString = m.group(1);
			int id = Item.parseId(idString);
			float reward = Float.parseFloat(m.group(2));
			float weight = Float.parseFloat(m.group(3));
			Location location = this.locations.get(id);

			if (location != null) {
				if (!items.containsKey(id)) {
					items.put(id, new Item(id, reward, weight, location));
				} else {
					trace(String.format("items file referenced item id %s multiple times", idString));
				}
			} else {
				log(String.format("items file referenced item id %s with unmapped location", idString));
			}
		}
	}

	private void parseJobs(BufferedReader jobsReader) throws IOException {
		this.jobs = new HashMap<Integer, Job>();

		// Standard regex: "^([0-9]+)(\\s*,\\s*([a-z]+)\\s*,\\s*([1-9][0-9]*))+$"
		// Use a better one that ensures items aren't referenced twice
		Pattern jobPattern = Pattern.compile("^([0-9]+)(\\s*,\\s*([a-z]+)(?!.+\\3)\\s*,\\s*([1-9][0-9]*))+$");

		String line;
		while ((line = jobsReader.readLine()) != null) {
			Matcher m = jobPattern.matcher(line);

			if (isInvalidLine("jobs", line, m))
				continue;

			int id = Integer.parseInt(m.group(1));
			String[] parts = line.split(",");

			List<JobItem> jobItems = new ArrayList<JobItem>();

			int i = 1;
			while (i < parts.length - 1) {
				String itemIdString = parts[i];
				int itemId = Item.parseId(itemIdString);
				int count = Integer.parseInt(parts[i + 1]);

				if (items.containsKey(itemId)) {
					jobItems.add(new JobItem(items.get(itemId), count));
				} else {
					log(String.format("jobs file referenced unknown item id %s in job %d", itemIdString, id));
				}

				i += 2;
			}

			jobs.put(id, new Job(id, jobItems));
		}
	}

	private void parseCancellations(BufferedReader cancellationsReader) throws IOException {
		Pattern cancellationsPattern = Pattern.compile("^([0-9]+)\\s*,\\s*([0-1])$");

		String line;
		while ((line = cancellationsReader.readLine()) != null) {
			Matcher m = cancellationsPattern.matcher(line);
			if (isInvalidLine("cancellations", line, m))
				continue;

			int id = Integer.parseInt(m.group(1));
			boolean cancelled = Integer.parseInt(m.group(1)) == 1;
			Job job = this.jobs.get(id);

			if (job != null) {
				if (cancelled) {
					job.setPreviouslyCancelled();
				}
			} else {
				trace(String.format("cancellations file referenced unknown job id %d", id));
			}
		}
	}

	private void parseDrops(BufferedReader dropsReader) throws IOException {
		this.drops = new HashSet<Location>();

		Pattern dropPattern = Pattern.compile("^([0-9]+)\\s*,\\s*([0-9]+)$");

		String line;
		while ((line = dropsReader.readLine()) != null) {
			Matcher m = dropPattern.matcher(line);
			if (isInvalidLine("drops", line, m))
				continue;

			int x = Integer.parseInt(m.group(1));
			int y = Integer.parseInt(m.group(2));

			Location l = new Location(x, y);

			if (!drops.add(l)) {
				trace(String.format("drops file referenced location %s multiple times", l));
			}
		}
	}

	private boolean isInvalidLine(String file, String line, Matcher m) {
		if (!m.matches()) {
			trace(String.format("Invalid line in %s file. Saw: %s", file, line));
			return true;
		}
		return false;
	}

	public List<Job> getJobs() throws ImportNotFinishedException {
		if (!this.doneParsing) {
			throw new ImportNotFinishedException();
		}

		return new ArrayList<Job>(this.jobs.values());
	}

	public List<Item> getItems() throws ImportNotFinishedException {
		if (!this.doneParsing) {
			throw new ImportNotFinishedException();
		}

		return new ArrayList<Item>(this.items.values());
	}

	public Set<Location> getDrops() throws ImportNotFinishedException {
		if (!this.doneParsing) {
			throw new ImportNotFinishedException();
		}

		return this.drops;
	}

	private void log(String x) {
		System.out.println("log: " + x);
	}

	private void trace(String x) {
		// System.out.println("trace: " + x);
	}

	public String toString() {
		return String.format("jobs: %s\nlocations: %s\nitems: %s",
				this.jobs.values().stream().map(Job::toString).collect(Collectors.joining(", ")),
				this.locations.values().stream().map(Location::toString).collect(Collectors.joining(", ")),
				this.items.values().stream().map(Item::toString).collect(Collectors.joining(", ")));
	}

}