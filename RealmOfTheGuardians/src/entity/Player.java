package entity;

import java.awt.Graphics;
import java.util.ArrayList;

import input.Key;
import maths.Vector;
import resource.ResourceHandle;


public class Player extends Entity {

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Player(Vector location, Vector radius) {
		super(location, radius);
		speed = 3;
		bullets = new ArrayList();
		time = System.currentTimeMillis();
		currentAnimation = new Animation();
		currentAnimation.addImage(ResourceHandle.resize(
				ResourceHandle.cropImage(ResourceHandle.player, 0, 0, 8, 8),
				32, 32));
		currentAnimation.addImage(ResourceHandle.resize(
				ResourceHandle.cropImage(ResourceHandle.player, 8, 0, 8, 8),
				32, 32));
	}

	@Override
	public void update() {
		currentAnimation.update();
		for (int i = 0; i < bullets.size(); i++)
			((Bullet) bullets.get(i)).update();

	}

	public void update(ArrayList<Key> keys) {
		update();
		move(keys);
		// check shoots
		// checkShoot(keys);
		shoot(keys);
		for (int i = 0; i < bullets.size(); i++)
			if (((Bullet) bullets.get(i)).getX() < -2D
					* ((Bullet) bullets.get(i)).getWidth()
					|| ((Bullet) bullets.get(i)).getX() > 900D
					|| ((Bullet) bullets.get(i)).getY() > 550D
					|| ((Bullet) bullets.get(i)).getY() < -50D)
				bullets.remove(i);

	}

	public void shoot(ArrayList<Key> keys) {
		for (Key key : keys) {
			if (key.getName().equals("K") && key.isDown()) {
				int mx = (int) (getX()+1000); 
				int my = (int) getY();
				addBullet(mx, my);
			}
		}
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
					setX(getX() + speed);
				right = true;
			}
			if (((Key) keys.get(i)).getName().equals("left")
					&& ((Key) keys.get(i)).isDown() && getX() > 0.0D) {
				if (!left)
					setX(getX() - speed);
				left = true;
			}
			if (((Key) keys.get(i)).getName().equals("up")
					&& ((Key) keys.get(i)).isDown() && getY() > 0.0D) {
				if (!up)
					setY(getY() - speed);
				up = true;
			}
			if (((Key) keys.get(i)).getName().equals("down")
					&& ((Key) keys.get(i)).isDown()
					&& getY() + getHeight() < 450D) {
				if (!down)
					setY(getY() + speed);
				down = true;
			}
		}

	}

	public void addBullet(int mx, int my) {
		bullets.add(new Bullet(new Vector(getX() + getWidth() / 2D, getY()
				+ getHeight() / 2D), new Vector(16D, 2D), (int) Math
				.toDegrees(Math.atan2(my - getY() - getHeight() / 2D,
						mx - getX() - getWidth() / 2D))));
	}

	@Override
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
