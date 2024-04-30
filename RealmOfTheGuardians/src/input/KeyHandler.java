// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KeyHandler.java

package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

// Referenced classes of package input:
//            Key

public class KeyHandler implements KeyListener {

	public KeyHandler() {
		keys = new ArrayList<Key>();
		keys.add(new Key("escape", 27));
		keys.add(new Key("up", 87));
		keys.add(new Key("down", 83));
		keys.add(new Key("left", 65));
		keys.add(new Key("right", 68));
		keys.add(new Key("space", 32));
		keys.add(new Key("enter", 10));
		keys.add(new Key("backspace", 8));
		keys.add(new Key("up", 38));
		keys.add(new Key("down", 40));
		keys.add(new Key("left", 37));
		keys.add(new Key("right", 39));
		keys.add(new Key("Q", 81));
	}

	public ArrayList<Key> getKeys() {
		return keys;
	}

	public void keyPressed(KeyEvent e) {
		for (int i = 0; i < keys.size(); i++)
			if (((Key) keys.get(i)).getKeyCode() == e.getKeyCode()) {
				System.out.println(keys.get(i).getName()+" is pressed_____");
				((Key) keys.get(i)).setPressed(true);
				((Key) keys.get(i)).setDown(true);
			}

	}

	public void keyReleased(KeyEvent e) {
		for (int i = 0; i < keys.size(); i++)
			if (((Key) keys.get(i)).getKeyCode() == e.getKeyCode()) {
				((Key) keys.get(i)).setPressed(false);
				((Key) keys.get(i)).setDown(false);
			}

	}

	public void keyTyped(KeyEvent e) {
		for (int i = 0; i < keys.size(); i++)
			if (((Key) keys.get(i)).getKeyCode() == e.getKeyCode())
				((Key) keys.get(i)).setDown(false);

	}

	public void printkeypress() {
		for (int i = 0; i < keys.size(); i++)
			if (keys.get(i).isPressed){
				System.out.println(keys.get(i).getName()+" is pressed");
			}

	}

	ArrayList<Key> keys;
}
