package main;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;

import entity.Bullet;
import entity.Entity;
import entity.Obstacle;
import entity.Player;
import gui.Frame;
import gui.Messagehandle;
import input.Key;
import input.KeyHandle;
import maths.Vector;
import resource.ResourceHandle;


public class Main extends Canvas implements Runnable, MouseListener,
		KeyListener {
	private Image backgroundImage;

	public Main() {
		ResourceHandle.inits();
		backgroundImage = new ImageIcon("res/cover.png").getImage();
		framerate = 60D;
		running = true;
		gameOver = false;
		menu = true;
		name = "Enter a username";
		drawX = 0;
		blockspeed = 0.2F;
		start();

	}

	public static void main(String args[]) {
		Frame frame = new Frame();
		Canvas canvas = new Main();
		canvas.setMinimumSize(new Dimension(frame.WIDTH, frame.HEIGHT - 2));
		canvas.setMaximumSize(new Dimension(frame.WIDTH, frame.HEIGHT - 2));
		canvas.setPreferredSize(new Dimension(frame.WIDTH, frame.HEIGHT - 2));
		frame.add(canvas);
		frame.pack();
	}

	public void initialize() {
		final String URL = "jdbc:mysql://localhost:3306/?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
		final String USER = "root"; // mysql database username
		final String PASSWORD = "szy11408"; // mysql database password
		if (!gameOver) {
			handler = new KeyHandle();
			addKeyListener(handler);
			addKeyListener(this);
			addMouseListener(this);
			setMinimumSize(new Dimension(800, 448));
			setMaximumSize(new Dimension(800, 448));
			setPreferredSize(new Dimension(800, 448));
		}
		Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();

            // mysql 
            // create database realm
            stmt.execute("CREATE DATABASE IF NOT EXISTS realm");
            stmt.execute("USE realm");
            // create table mytable 
            stmt.execute("CREATE TABLE IF NOT EXISTS mytable (health INT DEFAULT 1)");
            // insert health into table
            stmt.execute("INSERT INTO mytable (health) VALUES (DEFAULT)");
            // update the value, set health of obstacle to 1
            stmt.execute("UPDATE mytable SET health = 1");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // close connection
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                	System.out.println("***");
                    System.out.println(e.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                	System.out.println("***");
                    System.out.println(e.getMessage());
                }
            }
		}
		entities = new ArrayList();
		p1 = new Player(new Vector(392D, 217D), new Vector(16D, 16D));
		entities.add(p1);
		entities.add(new Messagehandle("STARTED THE GAME!", new Vector(20D,
				80D), 0.0F));
		entities.add(new Obstacle(new Vector(1810D, 60D), new Vector(16D,
				16D), (byte) 0));
		entities.add(new Obstacle(new Vector(1310D, 150D), new Vector(16D,
				16D), (byte) 0));
		entities.add(new Obstacle(new Vector(5210D, 260D), new Vector(16D,
				16D), (byte) 0));
		gameOver = false;
		score = 0;
		blockspeed = -0.1F;
		prepareImages();
		requestFocus();
	}

	private void prepareImages() {
		for (int i = 0; i < ResourceHandle.alphabet.length; i++)
			prepareImage(ResourceHandle.alphabet[i], null);

		for (int i = 0; i < ResourceHandle.Wall.length; i++) {
			prepareImage(ResourceHandle.Wall[i], null);
			prepareImage(ResourceHandle.WallDownLeft[i], null);
			prepareImage(ResourceHandle.WallDownRight[i], null);
			prepareImage(ResourceHandle.WallTopRight[i], null);
			prepareImage(ResourceHandle.WallTopLeft[i], null);
		}

		for (int i = 0; i < ResourceHandle.bg.length; i++)
			prepareImage(ResourceHandle.bg[i], null);

		prepareImage(ResourceHandle.bullet, null);
	}

	public void start() {
		running = true;
		Thread thread = new Thread(this);
		thread.setPriority(10);
		thread.start();
	}

	public void stop() {
		running = false;
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double unprocessed = 0.0D;
		int frames = 0;
		long lastTimer1 = System.currentTimeMillis();
		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		int toTick = 0;
		long lastRenderTime = System.nanoTime();
		int min = 0x3b9ac9ff;
		int max = 0;
		while (running) {
			double nsPerTick = 1000000000D / framerate;
			boolean shouldRender = false;
			for (; unprocessed >= 1.0D; unprocessed--)
				toTick++;

			int tickCount = toTick;
			if (toTick > 0 && toTick < 3)
				tickCount = 1;
			if (toTick > 20)
				toTick = 20;
			for (int i = 0; i < tickCount; i++) {
				toTick--;
				update();
				shouldRender = true;
			}

			BufferStrategy bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(3);
			} else {
				if (shouldRender) {
					frames++;
					Graphics g = bs.getDrawGraphics();
					render(g);
					long renderTime = System.nanoTime();
					int timePassed = (int) (renderTime - lastRenderTime);
					if (timePassed < min)
						min = timePassed;
					if (timePassed > max)
						max = timePassed;
					lastRenderTime = renderTime;
				}
				long now = System.nanoTime();
				unprocessed += (now - lastTime) / nsPerTick;
				lastTime = now;
				try {
					Thread.sleep(1L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (shouldRender && bs != null)
					bs.show();
				if (System.currentTimeMillis() - lastTimer1 > 1000L) {
					lastTimer1 += 1000L;
					fps = frames;
					frames = 0;
				}
			}
		}
	}

	private void update() {
		keys = handler.getKeys();
		for (Key key : keys) {
			if (key.getName().equals("Q") && key.isDown()) {
				for (Entity e : entities) {
					if (e instanceof Player) {
						((Player)e).shoot(keys);  // shoot
					}
				}
			}
		}

		for (Entity entity : entities) {
            entity.update();
        }

		if (!menu) {
			if (!gameOver) {
				score++;
				for (Iterator iterator = entities.iterator(); iterator
						.hasNext();) {
					Entity e = (Entity) iterator.next();
					if (e instanceof Obstacle) {
						if (e.getX() < entities.get(0).getX())
							e.setX(e.getX() + blockspeed);
						else
							e.setX(e.getX() - blockspeed);
						if (e.getY() < entities.get(0).getY())
							e.setY(e.getY() + blockspeed);
						else
							e.setY(e.getY() - blockspeed);
					}
					if (e instanceof Player)
						((Player) e).update(keys);
					else
						e.update();
					if (e instanceof Messagehandle)
						((Messagehandle) e).isDone();
				}

				checkCollisions();
			} else if (keys.get(0).isDown())
				initialize();
			if (score > highscore)
				highscore = score;
			if (score == 20) {
				entities.add(new Obstacle());
				entities.set(
						1,
						new Messagehandle((new StringBuilder(String
								.valueOf(name))).append(" STARTED THE GAME!")
								.toString(), new Vector(20D, 80D)));
			}
			if (score % 1000 == 0)
				blockspeed += 0.1F;
			if (score == 500) {
				entities.set(1, new Messagehandle("COOL!", new Vector(20D,
						80D)));
				entities.add(new Obstacle());
				entities.add(new Obstacle());
				entities.add(new Obstacle());
				blockspeed += 0.1F;
			}
			if (score == 1000) {
				entities.set(1, new Messagehandle("YOU ARE GREAT!",
						new Vector(20D, 80D)));
				entities.add(new Obstacle());
				entities.add(new Obstacle());
				entities.add(new Obstacle());
			}
			if (score == 2000) {
				entities.set(1, new Messagehandle("AWESOME!",
						new Vector(20D, 80D)));
				entities.add(new Obstacle());
				entities.add(new Obstacle());
				entities.add(new Obstacle());
			}
			if (score == 3000) {
				entities.set(1, new Messagehandle("YOU ARE AMAZING!",
						new Vector(20D, 80D)));
				entities.add(new Obstacle());
				entities.add(new Obstacle());
				entities.add(new Obstacle());
			}
			if (score == 4000) {
				entities.set(1, new Messagehandle("YOU CAN NOT BE BETTER!",
						new Vector(20D, 80D)));
				entities.add(new Obstacle());
				entities.add(new Obstacle());
				entities.add(new Obstacle());
				entities.add(new Obstacle());
			}
			if (score == 5000) {
				entities.set(1, new Messagehandle("YOU GUARD THE REALM!", new Vector(
						20D, 80D)));
				entities.add(new Obstacle());
				entities.add(new Obstacle());
				entities.add(new Obstacle());
				entities.add(new Obstacle());
			}
		}
	}

	private void checkCollisions() {
		for (int i = 0; i < entities.size() - 1; i++) {
			if (entities.get(0).intersects(entities
					.get(i + 1)) && i != 0) {
				entities.set(1, new Messagehandle((new StringBuilder(
						"GAME OVER! Score: ")).append(score).toString(),
						new Vector(20D, 80D), 0.8F));
				gameOver = true;
			}
			if (entities.get(0) instanceof Player) {
				for (int j = 0; j < ((Player) entities.get(0)).bullets.size(); j++)
					if (i != 0
							&& ((Bullet) ((Player) entities.get(0)).bullets
									.get(j)).intersects(entities
											.get(i))) 
					{
						((Player) entities.get(0)).bullets.remove(j);
						if (entities.get(i) instanceof Obstacle) {
							Obstacle obstacle = (Obstacle) entities.get(i);
							// bullet damage = 1
							boolean flag = obstacle.takeDamage(1);
							if (flag) {
								entities.set(i, new Obstacle());
							}
						}
					}
			}
		}

	}

	private void render(Graphics g) {
		g.clearRect(0, 0, 800, 450);
		if (!menu) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setComposite(AlphaComposite.getInstance(3, 0.2F));
			drawX++;
			if (drawX >= 64)
				drawX = 0;
			for (int i = 0; i < 76; i++) {
				for (int j = 0; j < 71; j++)
					g2d.drawImage(
							ResourceHandle.bg[drawX / 8],
							(int) (i * 64 - entities
									.get(0).getX() % 2D) - drawX,
							j * 64, null);

			}

			g2d.setComposite(AlphaComposite.getInstance(3, 1.0F));
			if (!gameOver) {
				Entity e;
				for (Iterator iterator = entities.iterator(); iterator
						.hasNext(); e.render(g))
					e = (Entity) iterator.next();

			}
			g2d.setColor(Color.BLUE);
			g2d.setComposite(AlphaComposite.getInstance(3, 0.3F));
			g.fillRect(0, 0, 800, 60);
			g2d.setComposite(AlphaComposite.getInstance(3, 1.0F));
			g2d.setColor(Color.BLACK);
			drawString(g, (new StringBuilder("FPS: ")).append(fps).toString(),
					550, 20, 32);
			drawString(g, (new StringBuilder("Score: ")).append(score)
					.toString(), 20, 20, 32);
			drawString(g, (new StringBuilder("Highscore: ")).append(highscore)
					.toString(), 20, 410, 32);
			if (gameOver) {
				drawString(g, "GAME OVER!", 260, 220, 32);
				drawString(g, "escape to try again!", 60, 280, 32);
				entities.get(1).update();
				entities.get(1).render(g);
			}
		} else {
		     Color silver = new Color(192, 192, 192); // 银色的RGB值
		     g.setColor(silver);
		     g.fillRect(0, 0, 800, 450);
//		     g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
			drawString(g, "Please enter your name", 20, 20, 32);
			drawString(g, name, 20, 70, 24);
			drawString(g, "WASD to move and K to shoot.", 20, 340, 16);
			drawString(g, "Fight and Guard the Realm!", 20,
					370, 16);
			drawString(g, "Press Enter to start...", 20, 400, 16);
		}
	}

	private void drawString(Graphics g, String s, int x, int y, int size) {
		char alphabet[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z', '!', '?', '.', ':', '1', '2', '3', '4',
				'5', '6', '7', '8', '9', '0' };
		for (int i = 0; i < s.length(); i++) {
			for (int j = 0; j < alphabet.length; j++) {
				if (s.toLowerCase().charAt(i) != alphabet[j])
					continue;
				g.drawImage(ResourceHandle.resize(ResourceHandle.alphabet[j],
						size, size), x + i * size, y, null);
				break;
			}

		}

	}

	@Override
	public void mouseClicked(MouseEvent mouseevent) {
	}

	@Override
	public void mouseEntered(MouseEvent mouseevent) {
	}

	@Override
	public void mouseExited(MouseEvent mouseevent) {
	}

	@Override
	public void mousePressed(MouseEvent mouseevent) {
	}

	@Override
	public void mouseReleased(MouseEvent mouseevent) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (menu)
			if (e.getKeyCode() != 10) {
				if (!e.isActionKey() && !e.isShiftDown() && !e.isMetaDown()) {
					if (name.equals("Enter a username"))
						name = "";
					if (e.getKeyCode() == 8 && name.length() > 0)
						name = name.substring(0, name.length() - 1).trim();
					else if (name.length() < 25)
						name = (new StringBuilder(String.valueOf(name)))
								.append(e.getKeyChar()).toString().trim();
				}
			} else {
				menu = false;
			}
	}

	@Override
	public void keyReleased(KeyEvent keyevent) {
	}

	@Override
	public void keyTyped(KeyEvent keyevent) {
	}

	public static void delObstacle(Obstacle obstacle) {
		entities.remove(obstacle);
	}

	private static final long serialVersionUID = 1L;
	Frame frame;
	private double framerate;
	private int fps;
	private boolean running;
	private boolean gameOver;
	public static boolean applet = false;
	public boolean menu;
	Player p1;
	String name;
	public KeyHandle handler;

	ArrayList<Key> keys;
	static ArrayList<Entity> entities;
// =======
// 	ArrayList keys;
// 	static ArrayList entities;

	int drawX;
	int score;
	int highscore;
	float blockspeed;

}
