package rp.assignments.team.warehouse.server.job;

import rp.assignments.team.warehouse.server.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Item implements IIDed, IRewardable {

	/** The number of places {@link #numericIdToString(int)} should output */
	public static final int PAD_TO = 2;

	private final int id;
	private final float reward;
	private final float weight;
	private final Location location;

	/**
	 * @param id The id of the item.
	 * @param reward The reward for dropping off one of the item.
	 * @param weight The weight of the item.
	 * @param location The location the item is picked from.
	 */
	public Item(int id, float reward, float weight, Location location) {
		assert id >= 0;
		assert !Float.isNaN(reward);
		assert !Float.isNaN(weight);
		assert Float.compare(0.0f, weight) <= 0 : "Weight cannot be negative";

		this.id = id;
		this.reward = reward;
		this.weight = weight;
		this.location = location;
	}

	/**
	 * @param id The id of the item.
	 * @param reward The reward for dropping off one of the item.
	 * @param weight The weight of the item.
	 * @param location The location the item is picked from.
	 */
	public Item(String id, float reward, float weight, Location location) {
		this(parseId(id), reward, weight, location);
	}

	/**
	 * Convert a string id into a numeric.
	 *
	 * @param id The string id.
	 * @return The numeric equivalent.
	 */
	public static int parseId(String id) {
		char[] charArray = id.toCharArray();
		List<Character> chars = new ArrayList<Character>(charArray.length);

		for (char c : charArray) {
			chars.add(c);
		}

		assert chars.stream().allMatch(c -> Character.isLetter(c) && Character.isLowerCase(c));

		Collections.reverse(chars);

		int i = 0;
		int numeric = 0;

		for (char c : chars) {
			numeric += (int) Math.pow(26, i) * ((int) c - 0x61);
			i++;
		}

		return numeric;
	}

	/**
	 * Convert a numeric id to a string.
	 *
	 * @see #PAD_TO
	 * @param id The numeric id.
	 * @return The string equivalent.
	 */
	public static String numericIdToString(int id) {
		return numericIdToString(id, PAD_TO);
	}

	/**
	 * Convert a numeric id to a string.
	 *
	 * @param id The numeric id
	 * @param padTo The minimum length of the output string (will be front-padded
	 *        with "a"s)
	 * @return The string equivalent.
	 */
	public static String numericIdToString(int id, int padTo) {
		assert id >= 0;
		String string = "";
		int r = 0;
		int n = id;
		do {
			r = n % 26;
			n = n / 26;
			string = "" + (char) (r + 0x61) + string;
		} while (n > 0 && r != 0);

		if (string.length() < padTo) {
			string = String.join("", Collections.nCopies(padTo - string.length(), "a")) + string;
		}

		return string;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Get the reward for dropping of one of this item.
	 *
	 * @return the reward
	 */
	public float getReward() {
		return this.reward;
	}

	/**
	 * Get the weight of this item.
	 *
	 * @return the weight
	 */
	public float getWeight() {
		return this.weight;
	}

	/**
	 * Get the location of where this item is picked from.
	 *
	 * @return the location
	 */
	public Location getLocation() {
		return this.location;
	}

	/**
	 * Indicates if another item is equal to this one.
	 *
	 * @param other The item to compare with.
	 * @return true if the other is the same item.
	 */
	public boolean equals(Item other) {
		if (other != null && other.getId() == this.id) {
			assert other.getLocation().equals(this.location);
			assert Float.compare(other.getReward(), this.reward) == 0;
			assert Float.compare(other.getWeight(), this.weight) == 0;
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return String.format("%s %s r%f w%f", numericIdToString(this.id), this.location, this.reward, this.weight);
	}
}
