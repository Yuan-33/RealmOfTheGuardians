package input;

public class Key {

	public Key(String name, int keyCode) {
		isDown = false;
		isPressed = false;
		this.name = name;
		this.keyCode = keyCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public boolean isDown() {
		return isDown;
	}

	public boolean isPressed() {
		return isPressed;
	}

	public void setDown(boolean down) {
		isDown = down;
	}

	public void setPressed(boolean pressed) {
		isPressed = pressed;
	}

	String name;
	int keyCode;
	public boolean isDown;
	public boolean isPressed;
}
