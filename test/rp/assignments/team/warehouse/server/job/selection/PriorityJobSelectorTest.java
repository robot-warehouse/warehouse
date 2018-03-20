package rp.assignments.team.warehouse.server.job.selection;

import rp.assignments.team.warehouse.server.job.Job;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PriorityJobSelectorTest {

	private static Job job0, job1, job2, job3;

	private static Set<Job> jobs;

	private PriorityJobSelector selector;

	@BeforeClass
	public static void setupBeforeClass() {
		job0 = mock(Job.class);
		when(job0.getPriority()).thenReturn(10.0f);
		job1 = mock(Job.class);
		when(job1.getPriority()).thenReturn(5.0f);
		job2 = mock(Job.class);
		when(job2.getPriority()).thenReturn(50.0f);
		job3 = mock(Job.class);
		when(job3.getPriority()).thenReturn(15.0f);

		jobs = new HashSet<Job>();
		jobs.add(job0);
		jobs.add(job1);
		jobs.add(job2);
		jobs.add(job3);
	}

	@Before
	public void beforeEach() {
		selector = new PriorityJobSelector(jobs);
	}

	@Test
	public void nextShouldGiveJobsInOrderOfPriority() {
		Assert.assertEquals(job2, selector.next());
		Assert.assertEquals(job3, selector.next());
		Assert.assertEquals(job0, selector.next());
		Assert.assertEquals(job1, selector.next());
	}

	@Test
	public void previewShouldPreviewNextJob() {
		Job taken = selector.next();
		Job previewed = selector.preview();
		Assert.assertNotEquals(taken, previewed);
		Assert.assertEquals(previewed, selector.next());
	}

	@Test
	public void previewShouldNotRemoveJob() {
		Job previewed = selector.preview();
		Assert.assertEquals(previewed, selector.preview());
	}

	@Test
	public void nextShouldRemoveJob() {
		Job taken = selector.next();
		Assert.assertNotEquals(taken, selector.next());
	}

	@Test
	public void hasNextShouldBeTrueWhenThereAreJobs() {
		Assert.assertTrue(selector.hasNext());
	}

	@Test
	public void hasNextShouldBeFalseWhenThereAreNoJobs() {
		selector.next();
		selector.next();
		selector.next();
		Assert.assertTrue(selector.hasNext());
		selector.next();
		Assert.assertFalse(selector.hasNext());
	}

}
