package tk.damnesia.entity;

import java.awt.Graphics;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import maths.Vector2f;
import resource.ResourceManager;

// Referenced classes of package tk.damnesia.entity:
//            Entity

public class Obstacle extends Entity {
	private int health;
	// Class.forName("com.mysql.cj.jdbc.Driver");
	
	private static final String URL = "jdbc:mysql://localhost:3306/realm?useSSL=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD = "szy11408"; // mysql pwd
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
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("未能成功加载数据库驱动程序！");
		}
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT health FROM mytable LIMIT 1");
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
            pstmt = conn.prepareStatement("UPDATE mytable SET health = ?");
//            System.out.println("(*******");
            pstmt.setInt(1, this.originHealth+1);
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


	public boolean takeDamage(int damage) {
		this.health -= damage;
		if (this.health <= 0) {
			updateHealthInDB();
			this.isDestroyed = true;
			destroy();
			return true;
		}
		return false;
	}

	private void destroy() {
//		GameCanvas.delObstacle(this);
//		System.out.println("Obstacle destroyed");
	}

	@Override
	public void update() {
		if (dir == 0) {
			if (getX() + getWidth() > 0.0D) {
				setX(getX() - speed);
			} else {
				setX(840D);
				col = (byte) r.nextInt(8);
				setY(r.nextInt(332));
				dir = (byte) r.nextInt(4);
			}
		} else if (dir == 1) {
			if (getX() + getWidth() < 800D) {
				setX(getX() + speed);
			} else {
				setX(-70D);
				col = (byte) r.nextInt(8);
				setY(r.nextInt(380) + 50);
				dir = (byte) r.nextInt(4);
			}
		} else if (dir == 2) {
			if (getY() + getHeight() < 450D) {
				setY(getY() + speed);
			} else {
				setY(-70D);
				col = (byte) r.nextInt(8);
				setX(r.nextInt(770));
				dir = (byte) r.nextInt(4);
			}
		} else if (dir == 3)
			if (getY() + getWidth() > 0.0D) {
				setY(getY() - speed);
			} else {
				setY(470D);
				col = (byte) r.nextInt(8);
				setX(r.nextInt(770));
				dir = (byte) r.nextInt(4);
			}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(ResourceManager.Wall[col], (int) getX(), (int) getY(), null);
	}

	Random r;
	byte col;
	byte dir;
	int speed;
}
