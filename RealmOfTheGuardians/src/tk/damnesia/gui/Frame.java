package tk.damnesia.gui;

import javax.swing.JFrame;

public class Frame extends JFrame {

	public Frame() {
		super("Realm Of The Guardians");
		WIDTH = 800;
		HEIGHT = 450;
		setDefaultCloseOperation(3);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private static final long serialVersionUID = 1L;
	public int WIDTH;
	public int HEIGHT;
}
