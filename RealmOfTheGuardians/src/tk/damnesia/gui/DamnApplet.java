package tk.damnesia.gui;

import java.applet.Applet;
import java.awt.BorderLayout;

import tk.damnesia.main.GameCanvas;

public class DamnApplet extends Applet {

	public DamnApplet() {
	}

	@Override
	public void init() {
		setSize(800, 450);
		GameCanvas.applet = true;
		game = new GameCanvas();
		setLayout(new BorderLayout());
		add(game, "Center");
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
		game.stop();
	}

	private static final long serialVersionUID = 1L;
	private GameCanvas game;
}
