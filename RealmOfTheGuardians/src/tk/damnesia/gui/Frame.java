// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Frame.java

package tk.damnesia.gui;

import javax.swing.JFrame;

public class Frame extends JFrame {

	public Frame() {
		super("Damnesia's Game");
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
