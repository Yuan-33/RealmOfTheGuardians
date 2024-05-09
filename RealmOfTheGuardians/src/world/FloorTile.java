package world;

import java.awt.Graphics;
import java.awt.Image;

import maths.Vector;
import resource.ResourceHandle;


public class FloorTile extends Tile {

	public FloorTile(Vector location, Vector radius) {
		super(location, radius);
		img = ResourceHandle.resize(ResourceHandle.cropImage(
				ResourceHandle.imagesheet, 240, 240, 8, 8), 16, 16);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, (int) getX(), (int) getY(), null);
	}

	@Override
	public void update() {
	}

	@Override
	public boolean isBlocked() {
		return false;
	}

	Image img;
}
