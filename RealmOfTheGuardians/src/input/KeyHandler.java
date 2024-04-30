package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


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

	@Override
	public void keyPressed(KeyEvent e) {
		for (int i = 0; i < keys.size(); i++)
			if (keys.get(i).getKeyCode() == e.getKeyCode()) {
//				System.out.println(keys.get(i).getName()+" is pressed_____");
				keys.get(i).setPressed(true);
				keys.get(i).setDown(true);
			}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		for (int i = 0; i < keys.size(); i++)
			if (keys.get(i).getKeyCode() == e.getKeyCode()) {
				keys.get(i).setPressed(false);
				keys.get(i).setDown(false);
			}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		for (int i = 0; i < keys.size(); i++)
			if (keys.get(i).getKeyCode() == e.getKeyCode())
				keys.get(i).setDown(false);

	}

	public void printkeypress() {
		for (int i = 0; i < keys.size(); i++)
			if (keys.get(i).isPressed){
//				System.out.println(keys.get(i).getName()+" is pressed");
			}

	}

	ArrayList<Key> keys;
}
