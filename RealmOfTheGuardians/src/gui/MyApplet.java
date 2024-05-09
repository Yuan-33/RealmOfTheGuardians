package gui;

import java.applet.Applet;
import java.awt.BorderLayout;

import main.Main;

public class MyApplet extends Applet {

	public MyApplet() {
	}

	@Override
	public void init() {
		setSize(800, 450);
		Main.applet = true;
		game = new Main();
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
	private Main game;
}
