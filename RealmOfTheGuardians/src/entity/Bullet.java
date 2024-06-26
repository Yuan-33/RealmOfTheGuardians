package entity;

import java.awt.Graphics;
import java.awt.Graphics2D;

import maths.Vector;
import resource.ResourceHandle;



public class Bullet extends Entity {

	public Bullet(Vector location, Vector radius) {
		super(location, radius);
		rotation = 50;
		speed = 4;
	}

	public Bullet(Vector location, Vector radius, int rotation) {
		super(location, radius);
		this.rotation = 50;
		speed = 4;
		this.rotation = rotation;
	}

	@Override
	public void update() {
		setX(getX() + speed * Math.cos(Math.toRadians(rotation)));
		setY(getY() + speed * Math.sin(Math.toRadians(rotation)));
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(getX(), getY());
		g2d.rotate(Math.toRadians(rotation));
		g.drawImage(ResourceHandle.bullet, 0, 0, null);
		g2d.rotate(Math.toRadians(-rotation));
		g2d.translate(-getX(), -getY());
	}

	int rotation;
	int speed;
}
