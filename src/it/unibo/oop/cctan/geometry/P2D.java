package it.unibo.oop.cctan.geometry;

public class P2D {

	private final double x;
	private final double y;
	
	public P2D(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	@Override
	public String toString() {
		return "P2D [x=" + x + ", y=" + y + "]";
	}
	
}
