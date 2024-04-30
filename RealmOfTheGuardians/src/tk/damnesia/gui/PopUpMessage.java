// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PopUpMessage.java

package tk.damnesia.gui;

import java.awt.*;
import maths.Vector2f;
import resource.ResourceManager;
import tk.damnesia.entity.Entity;

public class PopUpMessage extends Entity {

	public PopUpMessage(String message, Vector2f location) {
		super(location, new Vector2f(message.length() * 6, 30D));
		isDone = false;
		f = new Font("sansserif", 0, 24);
		time = Long.valueOf(System.currentTimeMillis());
		opacity = 1.0F;
		this.message = message;
	}

	public PopUpMessage(String message, Vector2f location, float opacity) {
		super(location, new Vector2f(message.length() * 6, 30D));
		isDone = false;
		f = new Font("sansserif", 0, 24);
		time = Long.valueOf(System.currentTimeMillis());
		this.opacity = opacity;
		this.message = message;
	}

	public boolean isDone() {
		return isDone;
	}

	public void update() {
		if (opacity > 0.0F
				&& System.currentTimeMillis() - time.longValue() > 1000L)
			opacity -= (float) (System.currentTimeMillis() - time.longValue()) / 30000F;
		if (opacity < 0.0F) {
			isDone = true;
			opacity = 0.0F;
		}
	}

	public boolean intersects(Entity other) {
		return false;
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		setRadius(new Vector2f(g.getFontMetrics().stringWidth(message) + 10,
				30D));
		g.setFont(f);
		g2d.setComposite(AlphaComposite.getInstance(3, opacity));
		g2d.fillRoundRect((int) getX(), (int) getY(), (int) getWidth(),
				(int) getHeight(), 10, 10);
		g.setFont(f);
		g.setColor(Color.WHITE);
		g.drawString(message, (int) getX() + 10, (int) getY() + 40);
		g.setColor(Color.BLACK);
		g2d.setComposite(AlphaComposite.getInstance(3, 1.0F));
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

	private Long time;
	private boolean isDone;
	float opacity;
	String message;
	Font f;
}
