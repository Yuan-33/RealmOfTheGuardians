// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GameCanvas.java

package tk.damnesia.main;

import input.Key;
import input.KeyHandler;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import maths.Vector2f;
import resource.ResourceManager;
import tk.damnesia.entity.*;
import tk.damnesia.gui.Frame;
import tk.damnesia.gui.PopUpMessage;

// Referenced classes of package tk.damnesia.main:
//            HighscoreHelper

public class GameCanvas extends Canvas implements Runnable, MouseListener,
		KeyListener {

	public GameCanvas() {
		ResourceManager.inits();
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
		Canvas canvas = new GameCanvas();
		canvas.setMinimumSize(new Dimension(frame.WIDTH, frame.HEIGHT - 2));
		canvas.setMaximumSize(new Dimension(frame.WIDTH, frame.HEIGHT - 2));
		canvas.setPreferredSize(new Dimension(frame.WIDTH, frame.HEIGHT - 2));
		frame.add(canvas);
		frame.pack();
	}

	public void initialize() {
		if (!gameOver) {
			handler = new KeyHandler();
			addKeyListener(handler);
			addKeyListener(this);
			addMouseListener(this);
			setMinimumSize(new Dimension(800, 448));
			setMaximumSize(new Dimension(800, 448));
			setPreferredSize(new Dimension(800, 448));
		}
		entities = new ArrayList();
		p1 = new Player(new Vector2f(392D, 217D), new Vector2f(16D, 16D));
		entities.add(p1);
		entities.add(new PopUpMessage("STARTED THE GAME!", new Vector2f(20D,
				80D), 0.0F));
		entities.add(new Obstacle(new Vector2f(1810D, 60D), new Vector2f(16D,
				16D), (byte) 0));
		entities.add(new Obstacle(new Vector2f(1310D, 150D), new Vector2f(16D,
				16D), (byte) 0));
		entities.add(new Obstacle(new Vector2f(5210D, 260D), new Vector2f(16D,
				16D), (byte) 0));
		gameOver = false;
		score = 0;
		blockspeed = -0.1F;
		prepareImages();
		requestFocus();
	}

	private void prepareImages() {
		for (int i = 0; i < ResourceManager.alphabet.length; i++)
			prepareImage(ResourceManager.alphabet[i], null);

		for (int i = 0; i < ResourceManager.Wall.length; i++) {
			prepareImage(ResourceManager.Wall[i], null);
			prepareImage(ResourceManager.WallDownLeft[i], null);
			prepareImage(ResourceManager.WallDownRight[i], null);
			prepareImage(ResourceManager.WallTopRight[i], null);
			prepareImage(ResourceManager.WallTopLeft[i], null);
		}

		for (int i = 0; i < ResourceManager.bg.length; i++)
			prepareImage(ResourceManager.bg[i], null);

		prepareImage(ResourceManager.bullet, null);
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
				unprocessed += (double) (now - lastTime) / nsPerTick;
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
		if (!menu) {
			if (!gameOver) {
				score++;
				for (Iterator iterator = entities.iterator(); iterator
						.hasNext();) {
					Entity e = (Entity) iterator.next();
					if (e instanceof Obstacle) {
						if (e.getX() < ((Entity) entities.get(0)).getX())
							e.setX(e.getX() + (double) blockspeed);
						else
							e.setX(e.getX() - (double) blockspeed);
						if (e.getY() < ((Entity) entities.get(0)).getY())
							e.setY(e.getY() + (double) blockspeed);
						else
							e.setY(e.getY() - (double) blockspeed);
					}
					if (e instanceof Player)
						((Player) e).update(keys);
					else
						e.update();
					if (e instanceof PopUpMessage)
						((PopUpMessage) e).isDone();
				}

				checkCollisions();
			} else if (((Key) keys.get(0)).isDown())
				initialize();
			if (score > highscore)
				highscore = score;
			if (score == 20) {
				entities.add(new Obstacle());
				entities.set(
						1,
						new PopUpMessage((new StringBuilder(String
								.valueOf(name))).append(" STARTED THE GAME!")
								.toString(), new Vector2f(20D, 80D)));
			}
			if (score % 1000 == 0)
				blockspeed += 0.1F;
			if (score == 500) {
				entities.set(1, new PopUpMessage("Awesome!", new Vector2f(20D,
						80D)));
				entities.add(new Obstacle());
				entities.add(new Obstacle());
				entities.add(new Obstacle());
				blockspeed += 0.1F;
			}
			if (score == 1000) {
				entities.set(1, new PopUpMessage("Dodging like a boss!",
						new Vector2f(20D, 80D)));
				entities.add(new Obstacle());
				entities.add(new Obstacle());
				entities.add(new Obstacle());
			}
			if (score == 2000) {
				entities.set(1, new PopUpMessage("wcfs96 is a cheater!",
						new Vector2f(20D, 80D)));
				entities.add(new Obstacle());
				entities.add(new Obstacle());
				entities.add(new Obstacle());
			}
			if (score == 3000) {
				entities.set(1, new PopUpMessage("I c what you did there...",
						new Vector2f(20D, 80D)));
				entities.add(new Obstacle());
				entities.add(new Obstacle());
				entities.add(new Obstacle());
			}
			if (score == 4000) {
				entities.set(1, new PopUpMessage("Needs MOAR obstacles!",
						new Vector2f(20D, 80D)));
				entities.add(new Obstacle());
				entities.add(new Obstacle());
				entities.add(new Obstacle());
				entities.add(new Obstacle());
			}
			if (score == 5000) {
				entities.set(1, new PopUpMessage("You dead yet?", new Vector2f(
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
			if (((Entity) entities.get(0)).intersects((Entity) entities
					.get(i + 1)) && i != 0) {
				entities.set(1, new PopUpMessage((new StringBuilder(
						"GAME OVER! Score: ")).append(score).toString(),
						new Vector2f(20D, 80D), 0.8F));
				Thread t = new Thread(new HighscoreHelper(name, score));
				t.start();
				gameOver = true;
			}
			if (entities.get(0) instanceof Player) {
				for (int j = 0; j < ((Player) entities.get(0)).bullets.size(); j++)
					if (i != 0
							&& ((Bullet) ((Player) entities.get(0)).bullets
									.get(j)).intersects((Entity) entities
									.get(i))) {
						((Player) entities.get(0)).bullets.remove(j);
						entities.set(i, new Obstacle());
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
							ResourceManager.bg[drawX / 8],
							(int) ((double) (i * 64) - ((Entity) entities
									.get(0)).getX() % 2D) - drawX, j * 64, null);

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
				((Entity) entities.get(1)).update();
				((Entity) entities.get(1)).render(g);
			}
		} else {
			g.setColor(Color.blue);
			g.fillRect(0, 0, 800, 450);
			drawString(g, "Please enter your name", 20, 20, 32);
			drawString(g, name, 20, 70, 24);
			drawString(g, "WASD or arrow keys to move.", 20, 200, 16);
			drawString(g, "Try to avoid the boxes and beat the highscore!", 20,
					230, 16);
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
				g.drawImage(ResourceManager.resize(ResourceManager.alphabet[j],
						size, size), x + i * size, y, null);
				break;
			}

		}

	}

	public void mouseClicked(MouseEvent mouseevent) {
	}

	public void mouseEntered(MouseEvent mouseevent) {
	}

	public void mouseExited(MouseEvent mouseevent) {
	}

	public void mousePressed(MouseEvent mouseevent) {
	}

	public void mouseReleased(MouseEvent mouseevent) {
	}

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

	public void keyReleased(KeyEvent keyevent) {
	}

	public void keyTyped(KeyEvent keyevent) {
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
	public KeyHandler handler;
	ArrayList keys;
	ArrayList entities;
	int drawX;
	int score;
	int highscore;
	float blockspeed;

}
