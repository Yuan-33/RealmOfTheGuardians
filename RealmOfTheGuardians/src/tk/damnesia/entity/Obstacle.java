// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Obstacle.java

package tk.damnesia.entity;

import java.awt.Graphics;
import java.util.Random;
import maths.Vector2f;
import resource.ResourceManager;

// Referenced classes of package tk.damnesia.entity:
//            Entity

public class Obstacle extends Entity {

	public Obstacle(Vector2f location, Vector2f radius, byte dir) {
		super(location, radius);
		r = new Random();
		col = (byte) r.nextInt(8);
		this.dir = (byte) r.nextInt(4);
		speed = 3;
		this.dir = dir;
		update();
	}

	public Obstacle() {
		super(new Vector2f(-500D, -500D), new Vector2f(16D, 16D));
		r = new Random();
		col = (byte) r.nextInt(8);
		dir = (byte) r.nextInt(4);
		speed = 3;
		dir = 0;
		update();
	}

	public void update() {
		if (dir == 0) {
			if (getX() + getWidth() > 0.0D) {
				setX(getX() - (double) speed);
			} else {
				setX(840D);
				col = (byte) r.nextInt(8);
				setY(r.nextInt(332));
				dir = (byte) r.nextInt(4);
			}
		} else if (dir == 1) {
			if (getX() + getWidth() < 800D) {
				setX(getX() + (double) speed);
			} else {
				setX(-70D);
				col = (byte) r.nextInt(8);
				setY(r.nextInt(380) + 50);
				dir = (byte) r.nextInt(4);
			}
		} else if (dir == 2) {
			if (getY() + getHeight() < 450D) {
				setY(getY() + (double) speed);
			} else {
				setY(-70D);
				col = (byte) r.nextInt(8);
				setX(r.nextInt(770));
				dir = (byte) r.nextInt(4);
			}
		} else if (dir == 3)
			if (getY() + getWidth() > 0.0D) {
				setY(getY() - (double) speed);
			} else {
				setY(470D);
				col = (byte) r.nextInt(8);
				setX(r.nextInt(770));
				dir = (byte) r.nextInt(4);
			}
	}

	public void render(Graphics g) {
		g.drawImage(ResourceManager.Wall[col], (int) getX(), (int) getY(), null);
	}

	Random r;
	byte col;
	byte dir;
	int speed;
}
