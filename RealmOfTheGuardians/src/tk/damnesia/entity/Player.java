// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Player.java

package tk.damnesia.entity;

import input.Key;
import java.awt.Graphics;
import java.util.ArrayList;
import maths.Vector2f;
import resource.ResourceManager;

// Referenced classes of package tk.damnesia.entity:
//            Entity, Animation, Bullet

public class Player extends Entity {

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Player(Vector2f location, Vector2f radius) {
		super(location, radius);
		speed = 3;
		bullets = new ArrayList();
		time = System.currentTimeMillis();
		currentAnimation = new Animation();
		currentAnimation.addImage(ResourceManager.resize(
				ResourceManager.cropImage(ResourceManager.player, 0, 0, 8, 8),
				32, 32));
		currentAnimation.addImage(ResourceManager.resize(
				ResourceManager.cropImage(ResourceManager.player, 8, 0, 8, 8),
				32, 32));
	}

	public void update() {
		currentAnimation.update();
		for (int i = 0; i < bullets.size(); i++)
			((Bullet) bullets.get(i)).update();

	}

	public void update(ArrayList keys) {
		update();
		move(keys);
		for (int i = 0; i < bullets.size(); i++)
			if (((Bullet) bullets.get(i)).getX() < -2D
					* ((Bullet) bullets.get(i)).getWidth()
					|| ((Bullet) bullets.get(i)).getX() > 900D
					|| ((Bullet) bullets.get(i)).getY() > 550D
					|| ((Bullet) bullets.get(i)).getY() < -50D)
				bullets.remove(i);

	}

	private void move(ArrayList keys) {
		boolean left = false;
		boolean right = false;
		boolean up = false;
		boolean down = false;
		for (int i = 0; i < keys.size(); i++) {
			if (((Key) keys.get(i)).getName().equals("right")
					&& ((Key) keys.get(i)).isDown()
					&& getX() + getWidth() < 800D) {
				if (!right)
					setX(getX() + (double) speed);
				right = true;
			}
			if (((Key) keys.get(i)).getName().equals("left")
					&& ((Key) keys.get(i)).isDown() && getX() > 0.0D) {
				if (!left)
					setX(getX() - (double) speed);
				left = true;
			}
			if (((Key) keys.get(i)).getName().equals("up")
					&& ((Key) keys.get(i)).isDown() && getY() > 0.0D) {
				if (!up)
					setY(getY() - (double) speed);
				up = true;
			}
			if (((Key) keys.get(i)).getName().equals("down")
					&& ((Key) keys.get(i)).isDown()
					&& getY() + getHeight() < 450D) {
				if (!down)
					setY(getY() + (double) speed);
				down = true;
			}
		}

	}

	public void addBullet(int mx, int my) {
		bullets.add(new Bullet(new Vector2f(getX() + getWidth() / 2D, getY()
				+ getHeight() / 2D), new Vector2f(16D, 2D), (int) Math
				.toDegrees(Math.atan2((double) my - getY() - getHeight() / 2D,
						(double) mx - getX() - getWidth() / 2D))));
	}

	public void render(Graphics g) {
		currentAnimation
				.render(g, (int) location.getX(), (int) location.getY());
		for (int i = 0; i < bullets.size(); i++)
			((Bullet) bullets.get(i)).render(g);

	}

	Animation currentAnimation;
	int speed;
	public ArrayList bullets;
	long time;
}
