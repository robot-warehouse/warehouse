package rp.assignments.team.warehouse.server.job.selection;

public class Location {
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

	@Override
	public String toString() {
		return String.format("(%d, %d)", this.x, this.y);
	}

}
