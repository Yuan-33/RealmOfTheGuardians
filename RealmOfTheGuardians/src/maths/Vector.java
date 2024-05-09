package maths;

public class Vector {

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	double x;
	double y;
}
