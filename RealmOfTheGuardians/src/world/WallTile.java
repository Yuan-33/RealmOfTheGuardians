package world;

import java.awt.Graphics;
import java.awt.Image;

import maths.Vector;
import resource.ResourceHandle;


public class WallTile extends Tile {

	public WallTile(Vector location, Vector radius) {
		super(location, radius);
		img = ResourceHandle.resize(ResourceHandle.cropImage(
				ResourceHandle.imagesheet, 8, 112, 8, 8), 16, 16);
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
		return true;
	}

	Image img;
}
