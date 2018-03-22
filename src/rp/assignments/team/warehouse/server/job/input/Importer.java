package rp.assignments.team.warehouse.server.job.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.job.Item;
import rp.assignments.team.warehouse.server.job.Job;
import rp.assignments.team.warehouse.server.job.JobItem;

public class Importer {

    private File jobsFile;
    private File cancellationsFile;
    private File trainingFile;
    private File locationsFile;
    private File itemsFile;
    private File dropsFile;

    private Map<Integer, Job> jobs;
    private Map<Integer, Job> trainingJobs;
    private Map<Integer, Location> locations;
    private Map<Integer, Item> items;
    private Set<Location> drops;

    private boolean doneParsing;

    private static final Logger logger = LogManager.getLogger(Importer.class);

    /**
     * @param jobsFile The file to import the jobs from.
     * @param cancellationsFile The file to import the cancellation history from.
     * @param locationsFile The file to import the item locations from.
     * @param itemsFile The file to import the items from.
     * @param dropsFile The file to import the drop locations from.
     */
    public Importer(File jobsFile, File cancellationsFile, File trainingFile, File locationsFile, File itemsFile, File dropsFile) {
        assert jobsFile.exists() : "jobs file must exist";
        assert cancellationsFile.exists() : "cancellations file must exist";
        assert trainingFile.exists() : "training file must exist";
        assert locationsFile.exists() : "locations file must exist";
        assert itemsFile.exists() : "items file must exist";
        assert dropsFile.exists() : "drops file must exist";

        this.jobsFile = jobsFile;
        this.cancellationsFile = cancellationsFile;
        this.trainingFile = trainingFile;
        this.locationsFile = locationsFile;
        this.itemsFile = itemsFile;
        this.dropsFile = dropsFile;


        this.doneParsing = false;
    }

    /**
     * Parse the job input CSVs
     *
     * @return True if successful.
     */
    public boolean parse() {
        try (BufferedReader jobsReader = new BufferedReader(new FileReader(jobsFile));
                BufferedReader cancellationsReader = new BufferedReader(new FileReader(cancellationsFile));
                BufferedReader trainingReader = new BufferedReader(new FileReader(trainingFile));
                BufferedReader locationsReader = new BufferedReader(new FileReader(locationsFile));
                BufferedReader itemsReader = new BufferedReader(new FileReader(itemsFile));
                BufferedReader dropsReader = new BufferedReader(new FileReader(dropsFile))) {
            logger.info("Parsing locations");
            this.parseLocations(locationsReader);
            logger.info("Parsing items");
            this.parseItems(itemsReader);
            logger.info("Parsing jobs");
            this.parseJobs(jobsReader, false);
            logger.info("Parsing training");
            this.parseJobs(trainingReader, true);
            logger.info("Parsing cancellations");
            this.parseCancellations(cancellationsReader);
            logger.info("Parsing drops");
            this.parseDrops(dropsReader);
            logger.info("Done parsing");
            this.doneParsing = true;
        } catch (IOException e) {
            logger.fatal(e.getMessage());
        }

        return this.doneParsing;
    }

    private void parseLocations(BufferedReader locationsReader) throws IOException {
        this.locations = new HashMap<>();

        Pattern locationPattern = Pattern.compile("^([0-9]+)\\s*,\\s*([0-9]+)\\s*,\\s*([a-z]+)$");

        String line;
        while ((line = locationsReader.readLine()) != null) {
            Matcher m = locationPattern.matcher(line);
            if (isInvalidLine("locations", line, m)) {
                continue;
            }

            int x = Integer.parseInt(m.group(1));
            int y = Integer.parseInt(m.group(2));
            int id = Item.parseId(m.group(3));

            if (!Location.isValidLocation(x, y)) {
                logger.info("Invalid coordinates in locations file ({}, {})", x, y);
                continue;
            }

            Location l = new Location(x, y);

            if (!this.locations.containsKey(id)) {
                this.locations.put(id, l);
            } else {
                logger.info("locations file referenced item id {} in a new location ({}, {})", id, x, y);
            }
        }
    }

    private void parseItems(BufferedReader itemsReader) throws IOException {
        this.items = new HashMap<>();

        Pattern itemPattern = Pattern
                .compile("^([a-z]+)\\s*,\\s*([0-9]+(?:\\.[0-9]+)?)\\s*,\\s*([0-9]+(?:\\.[0-9]+)?)$");

        String line;
        while ((line = itemsReader.readLine()) != null) {
            Matcher m = itemPattern.matcher(line);
            if (isInvalidLine("items", line, m)) {
                continue;
            }

            String idString = m.group(1);
            int id = Item.parseId(idString);
            float reward = Float.parseFloat(m.group(2));
            float weight = Float.parseFloat(m.group(3));
            Location location = this.locations.get(id);

            if (location != null) {
                if (!this.items.containsKey(id)) {
                    this.items.put(id, new Item(id, reward, weight, location));
                } else {
                    logger.info("items file referenced item id {} multiple times", idString);
                }
            } else {
                logger.info("items file referenced item id {} with unmapped location", idString);
            }
        }
    }

    private void parseJobs(BufferedReader jobsReader, boolean isTraining) throws IOException {


        // Standard regex: "^([0-9]+)(\\s*,\\s*([a-z]+)\\s*,\\s*([1-9][0-9]*))+$"
        // Use a better one that ensures items aren't referenced twice
        Pattern jobPattern = Pattern.compile("^([0-9]+)(\\s*,\\s*([a-z]+)(?!.+\\3)\\s*,\\s*([1-9][0-9]*))+$");

        String line;
        if(isTraining) {
            this.trainingJobs = new HashMap<>();
        } else {
        	this.jobs = new HashMap<>();
        }
        while ((line = jobsReader.readLine()) != null) {
            Matcher m = jobPattern.matcher(line);

            if (isInvalidLine("jobs", line, m)) {
                continue;
            }

            int id = Integer.parseInt(m.group(1));
            String[] parts = line.split(",");

            List<JobItem> jobItems = new ArrayList<JobItem>();


            int i = 1;
            while (i < parts.length - 1) {
                String itemIdString = parts[i];
                int itemId = Item.parseId(itemIdString);
                int count = Integer.parseInt(parts[i + 1]);

                if (this.items.containsKey(itemId)) {
                    jobItems.add(new JobItem(this.items.get(itemId), count));
                } else {
                    logger.info("{} file referenced unknown item id {} in job {}", (isTraining ? "training" : "jobs"), itemIdString, id);
                }

                i += 2;
            }

            if (isTraining) {
                this.trainingJobs.put(id, new Job(id, jobItems));
            } else {
                this.jobs.put(id, new Job(id, jobItems));
            }
        }
    }

    private void parseCancellations(BufferedReader cancellationsReader) throws IOException {
        Pattern cancellationsPattern = Pattern.compile("^([0-9]+)\\s*,\\s*([0-1])$");

        String line;
        while ((line = cancellationsReader.readLine()) != null) {
            Matcher m = cancellationsPattern.matcher(line);
            if (isInvalidLine("cancellations", line, m)) {
                continue;
            }

            int id = Integer.parseInt(m.group(1));
            boolean cancelled = Integer.parseInt(m.group(2)) == 1;

            if (this.trainingJobs.containsKey(id)) {
                if (cancelled) {
                    this.trainingJobs.get(id).setPreviouslyCancelled();
                }
            } else if(this.jobs.containsKey(id)) {
                if (cancelled) {
                    this.jobs.get(id).setCancelled();
                }
            } else {
                logger.info("cancellations file referenced unknown job id {}", id);
            }
        }
    }

    private void parseDrops(BufferedReader dropsReader) throws IOException {
        this.drops = new HashSet<>();

        Pattern dropPattern = Pattern.compile("^([0-9]+)\\s*,\\s*([0-9]+)$");

        String line;
        while ((line = dropsReader.readLine()) != null) {
            Matcher m = dropPattern.matcher(line);
            if (isInvalidLine("drops", line, m)) {
                continue;
            }

            int x = Integer.parseInt(m.group(1));
            int y = Integer.parseInt(m.group(2));

            Location l = new Location(x, y);

            if (!this.drops.add(l)) {
                logger.info("drops file referenced location {} multiple times", l);
            }
        }
    }

    private boolean isInvalidLine(String file, String line, Matcher m) {
        if (!m.matches()) {
            logger.warn("Invalid line in {} file. Saw: {}", file, line);
            return true;
        }
        return false;
    }

    public boolean isDoneParsing() {
        return this.doneParsing;
    }

    public Set<Job> getJobs() throws ImportNotFinishedException {
        if (!this.doneParsing) {
            throw new ImportNotFinishedException();
        }

        return new HashSet<>(this.jobs.values());
    }

    public Map<Integer, Job> getJobsMap() throws ImportNotFinishedException {
        if (!this.doneParsing) {
            throw new ImportNotFinishedException();
        }

        return this.jobs;
    }

    public Set<Job> getTrainingJobs() throws ImportNotFinishedException {
        if (!this.doneParsing) {
            throw new ImportNotFinishedException();
        }

        return new HashSet<>(this.trainingJobs.values());
    }

    public Set<Item> getItems() throws ImportNotFinishedException {
        if (!this.doneParsing) {
            throw new ImportNotFinishedException();
        }

        return new HashSet<>(this.items.values());
    }

    public Set<Location> getDrops() throws ImportNotFinishedException {
        if (!this.doneParsing) {
            throw new ImportNotFinishedException();
        }

        return this.drops;
    }

    @Override
    public String toString() {
        return String.format("jobs: %s\nlocations: %s\nitems: %s",
                this.jobs.values().stream().map(Job::toString).collect(Collectors.joining(", ")),
                this.locations.values().stream().map(Location::toString).collect(Collectors.joining(", ")),
                this.items.values().stream().map(Item::toString).collect(Collectors.joining(", ")));
    }
}
