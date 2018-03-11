package rp.assignments.team.warehouse.server.job;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PickTest {

	private Job mockedJob;
	private Item mockedItem;
	private Pick pick;

	@Before
	public void beforeEach() {
		this.mockedJob = mock(Job.class);
		this.mockedItem = mock(Item.class);
		this.pick = new Pick(mockedJob, mockedItem);
	}

	@Test
	public void canInstantiatePick() {
		Assert.assertNotNull(pick);
		Assert.assertEquals(mockedItem, pick.getItem());
	}

	@Test
	public void canGetJobOfPick() {
		Assert.assertEquals(mockedJob, pick.getJob());
	}

	@Test
	public void canGetItemOfPick() {
		Assert.assertEquals(mockedItem, pick.getItem());
	}

	@Test
	public void canGetWeightOfPick() {
		Assert.assertEquals(mockedItem.getWeight(), pick.getWeight(), 0.0f);
	}

	@Test
	public void canGetRewardOfPick() {
		Assert.assertEquals(mockedItem.getReward(), pick.getReward(), 0.0f);
	}

	@Test
	public void canGetLocationOfPick() {
		Assert.assertEquals(mockedItem.getLocation(), pick.getPickLocation());
	}

	@Test
	public void isPickedShouldInitiallyBeFalse() {
		Assert.assertFalse(pick.isPicked());
	}

	@Test
	public void canSetPickAsPicked() {
		pick.setPicked();
		Assert.assertTrue(pick.isPicked());
	}

	@Test
	public void isCompletedShouldInitiallyBeFalse() {
		Assert.assertFalse(pick.isCompleted());
	}

	@Test
	public void canSetPickAsCompleted() {
		pick.setCompleted();
		Assert.assertTrue(pick.isCompleted());
	}

	@Test
	public void setCompletedShouldNotifyJobPickCompleted() {
		pick.setCompleted();
		verify(mockedJob).pickCompleted(pick);
	}
}
