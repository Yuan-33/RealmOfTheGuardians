// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Obstacle.java

package tk.damnesia.entity;

import java.awt.Graphics;
import java.sql.*;
import java.util.Random;
import maths.Vector2f;
import resource.ResourceManager;
import tk.damnesia.main.*;

// Referenced classes of package tk.damnesia.entity:
//            Entity

public class Obstacle extends Entity {
	private int health;
	private static final String URL = "jdbc:mysql://localhost:3306/GameDB?useSSL=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD = "091321Mty!"; // 你的数据库密码
	private int originHealth;
	private boolean isDestroyed = false;

	public Obstacle(Vector2f location, Vector2f radius, byte dir) {
		super(location, radius);
		r = new Random();
		col = (byte) r.nextInt(8);
		this.dir = (byte) r.nextInt(4);
		speed = 3;
		this.dir = dir;
		this.health = loadInitialHealth();
		this.originHealth = this.health;
		update();
	}

	public Obstacle() {
		super(new Vector2f(-500D, -500D), new Vector2f(16D, 16D));
		r = new Random();
		col = (byte) r.nextInt(8);
		dir = (byte) r.nextInt(4);
		speed = 3;
		dir = 0;
		this.health = loadInitialHealth();
		this.originHealth = this.health;
		update();
	}

	private int loadInitialHealth() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int health = 0;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT health FROM GlobalSettings LIMIT 1");
            if (rs.next()) {
                health = rs.getInt("health");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return health;
	}

	public void updateHealthInDB() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            pstmt = conn.prepareStatement("UPDATE GlobalSettings SET health = ?");
            pstmt.setInt(1, this.originHealth+5);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }


	public void takeDamage(int damage) {
		this.health -= damage;
		if (this.health <= 0) {
			updateHealthInDB();
			this.isDestroyed = true;
			destroy();
		}
	}

	private void destroy() {
		GameCanvas.delObstacle(this);
		System.out.println("Obstacle destroyed");
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
