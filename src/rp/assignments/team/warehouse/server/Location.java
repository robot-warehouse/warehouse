package rp.assignments.team.warehouse.server;

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
        assert isValidLocation(x, y);

        this.x = x;
        this.y = y;
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
     * Checks if the given x and y coordinates are valid in the warehouse.
     *
     * @param x The x coordinate to check.
     * @param y The y coordinate to check.
     * @return True if the coordinates are valid.
     */
    public static boolean isValidLocation(int x, int y) {
        return x >= MIN_X && x <= MAX_X && y >= MIN_Y && y <= MAX_Y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", this.x, this.y);
    }

}
