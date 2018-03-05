package rp.assignments.team.warehouse.server;

public class Location {

    public final static int xMin = 0;
    public final static int xMax = 11;
    public final static int yMin = 0;
    public final static int yMax = 7;

	private final int x;
	private final int y;

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Get the x coordinate.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the y coordinate.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Indicates if another location is equal to this one.
	 *
	 * @param other
	 *            The location to compare with
	 * @return true if other is the same location
	 */
	public boolean equals(Location other) {
		return other != null && other.getX() == this.x && other.getY() == this.y;
	}

    /**
     * Checks if the location is a valid one in the warehouse
     *
     * @return
     */
	public boolean isValidLocation() {
        return getX() >= xMin && getX() <= xMax && getY() >= yMin && getY() <= yMax;
    }

	@Override
	public String toString() {
		return String.format("(%d, %d)", this.x, this.y);
	}

}
