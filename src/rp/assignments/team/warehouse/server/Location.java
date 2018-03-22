package rp.assignments.team.warehouse.server;

import java.util.ArrayList;
import java.util.List;

public class Location {

    /** The minimum x coordinate of the warehouse. */
    public static final int MIN_X = 0;

    /** The maximum x coordinate of the warehouse. */
    public static final int MAX_X = 11;

    /** The minimum y coordinate of the warehouse. */
    public static final int MIN_Y = 0;

    /** The maximum y coordinate of the warehouse. */
    public static final int MAX_Y = 7;

    /** The x coordinate. */
    private final int x;

    /** The y coordinate. */
    private final int y;

    /**
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public Location(int x, int y) {
        if (!isValidLocation(x, y)) {
            throw new IllegalArgumentException("Cannot initialise Location with invalid coordinates.");
        }

        this.x = x;
        this.y = y;
    }

    /**
     * Checks if the given x and y coordinates are valid in the warehouse.
     *
     * @param x The x coordinate to check.
     * @param y The y coordinate to check.
     * @return True if the coordinates are valid.
     */
    public static boolean isValidLocation(int x, int y) {
        return x >= MIN_X && x <= MAX_X && y >= MIN_Y && y <= MAX_Y;
    }

    /**
     * Get the x coordinate.
     *
     * @return The x coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get the y coordinate.
     *
     * @return The y coordinate.
     */
    public int getY() {
        return this.y;
    }

    public List<Location> getNeighbours() {
        int[][] vectors = new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        List<Location> neighbours = new ArrayList<>();

        for (int[] vector : vectors) {
            int x = this.getX() + vector[0];
            int y = this.getY() + vector[1];

            if (Location.isValidLocation(x, y)) {
                neighbours.add(new Location(x, y));
            }
        }

        return neighbours;
    }

    /**
     * Indicates if another location is equal to this one.
     *
     * @param other The location to compare with.
     * @return True if other is the same location.
     */
    public boolean equals(Location other) {
        return other != null && other.getX() == this.x && other.getY() == this.y;
    }

    /**
     * Indicates if another location is equal to this one.
     *
     * @param other The location to compare with.
     * @return True if other is the same location.
     */
    public boolean equals(Object other) {
        if (other instanceof Location) {
            return this.equals((Location) other);
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", this.x, this.y);
    }

}
