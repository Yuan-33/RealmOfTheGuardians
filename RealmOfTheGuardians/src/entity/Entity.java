package entity;

import java.awt.Graphics;

import maths.Vector;

public abstract class Entity {

	public Entity(Vector location, Vector radius) {
		this.location = location;
		this.radius = radius;
	}

	public void setRadius(Vector radius) {
		this.radius = radius;
	}

	public void setLocation(Vector location) {
		this.location = location;
	}

	public double getWidth() {
		return radius.getX() * 2D;
	}

	public double getHeight() {
		return radius.getY() * 2D;
	}

	public double getX() {
		return location.getX();
	}

	public double getY() {
		return location.getY();
	}

	public void setX(double x) {
		location.set(x, getY());
	}

	public void setY(double y) {
		location.set(getX(), y);
	}

	public Vector getRadius() {
		return radius;
	}

	public Vector getLocation() {
		return location;
	}

	public boolean intersects(Entity other) {
		if (other.getX() >= getX() && other.getX() <= getX() + getWidth()
				&& other.getY() >= getY()
				&& other.getY() <= getY() + getHeight())
			return true;
		if (other.getX() + other.getWidth() >= getX()
				&& other.getX() + other.getWidth() <= getX() + getWidth()
				&& other.getY() >= getY()
				&& other.getY() <= getY() + getHeight())
			return true;
		if (other.getX() >= getX() && other.getX() <= getX() + getWidth()
				&& other.getY() + other.getHeight() >= getY()
				&& other.getY() + other.getHeight() <= getY() + getHeight())
			return true;
		return other.getX() + other.getWidth() >= getX()
				&& other.getX() + other.getWidth() <= getX() + getWidth()
				&& other.getY() + other.getHeight() >= getY()
				&& other.getY() + other.getHeight() <= getY() + getHeight();
	}

	public double distance(Entity other) {
		return Math.sqrt(Math.pow(getX() - other.getX(), 2D)
				+ Math.pow(getY() - other.getY(), 2D));
	}

	public double distance(int x0, int x1, int y0, int y1) {
		return Math.sqrt(Math.pow(x0 - x1, 2D) + Math.pow(y0 - y1, 2D));
	}

	public void printStats() {
		System.out.println((new StringBuilder("X: ")).append(getX())
				.append(" Y: ").append(getY()).append(" W: ")
				.append(getWidth()).append(" H: ").append(getHeight())
				.toString());
	}

	public void printStats(Entity e) {
		System.out.println((new StringBuilder("X: ")).append(e.getX())
				.append(" Y: ").append(e.getY()).append(" W: ")
				.append(e.getWidth()).append(" H: ").append(e.getHeight())
				.toString());
	}

	public abstract void update();

	public abstract void render(Graphics g);

	Vector location;
	Vector radius;
}
