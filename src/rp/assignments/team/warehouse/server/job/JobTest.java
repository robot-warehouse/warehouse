package rp.assignments.team.warehouse.server.job;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import rp.assignments.team.warehouse.server.Location;
import rp.assignments.team.warehouse.server.job.input.Importer;
import rp.assignments.team.warehouse.server.job.selection.CompareByPriorityComparator;
import rp.assignments.team.warehouse.server.job.selection.IJobSelector;
import rp.assignments.team.warehouse.server.job.selection.PriorityJobSelector;

public class JobTest {

    public static void main(String[] args) {
        String set = "sample";
        if (args.length > 0) {
            switch (args[0]) {
                case "example":
                    set = "sample";
                    break;
                case "mock":
                    set = "mock";
                    break;
                case "sample":
                    set = "sample";
                    break;
            }
        }

        File jobsFile = new File("input/" + set + "/jobs.csv");
        File cancellationsFile = new File("input/" + set + "/cancellations.csv");
        File locationsFile = new File("input/" + set + "/locations.csv");
        File itemsFile = new File("input/" + set + "/items.csv");
        File dropsFile = new File("input/" + set + "/drops.csv");

        Importer importer = new Importer(jobsFile, cancellationsFile, locationsFile, itemsFile, dropsFile);
        try {
            importer.parse();

            List<Job> jobs = importer.getJobs();
            List<Item> items = importer.getItems();
            Set<Location> drops = importer.getDrops();

            IJobSelector selector = new PriorityJobSelector(jobs);

            Collections.sort(jobs, new CompareByPriorityComparator(false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
