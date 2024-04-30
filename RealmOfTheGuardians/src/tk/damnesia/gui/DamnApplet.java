// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamnApplet.java

package tk.damnesia.gui;

import java.applet.Applet;
import java.awt.BorderLayout;
import tk.damnesia.main.GameCanvas;

public class DamnApplet extends Applet {

	public DamnApplet() {
	}

	public void init() {
		setSize(800, 450);
		GameCanvas.applet = true;
		game = new GameCanvas();
		setLayout(new BorderLayout());
		add(game, "Center");
	}

	public void start() {
	}

	public void stop() {
		game.stop();
	}

	private static final long serialVersionUID = 1L;
	private GameCanvas game;
}
