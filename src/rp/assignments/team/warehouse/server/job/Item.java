package rp.assignments.team.warehouse.server.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rp.assignments.team.warehouse.server.Location;

public class Item implements IIDed, IRewardable {

    /** The number of places {@link #numericIdToString(int)} should output */
    public static final int PAD_TO = 2;

    /** The ID of the item */
    private final int id;

    /** The reward given by delivering the item */
    private final float reward;

    /** The weight of the item */
    private final float weight;

    /** The location of the item */
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
        assert location != null;

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
            numeric += (int) Math.pow(26, i) * (c - 0x61);
            i++;
        }

        return numeric;
    }

    /**
     * Convert a numeric id to a string.
     *
     * @param id The numeric id.
     * @return The string equivalent.
     * @see #PAD_TO
     * @see #numericIdToString(int, int)
     */
    public static String numericIdToString(int id) {
        return numericIdToString(id, PAD_TO);
    }

    /**
     * Convert a numeric id to a string.
     *
     * @param id The numeric id
     * @param padTo The minimum length of the output string (will be front-padded with "a"s)
     * @return The string equivalent.
     */
    public static String numericIdToString(int id, int padTo) {
        assert id >= 0;

        String string = toStringRadix26(id);

        if (string.length() < padTo) {
            string = String.join("", Collections.nCopies(padTo - string.length(), "a")) + string;
        }

        return string;
    }

    /**
     * Get the string representation of n in radix 26.
     *
     * @param n The integer to be converted.
     * @return Base 26 equivalent of n.
     */
    private static String toStringRadix26(int n) {
        final String conv = "abcdefghijklmnopqrstuvwxyz";

        if (n < 26) { return String.valueOf(conv.charAt(n)); }

        return toStringRadix26((int) n / 26) + String.valueOf(conv.charAt(n % 26));
    }

    /**
     * @return the id
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * @return the id as a string
     */
    public String getIdString() {
        return Item.numericIdToString(this.id);
    }

    /**
     * Get the reward for dropping of one of this item.
     *
     * @return the reward
     */
    @Override
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
