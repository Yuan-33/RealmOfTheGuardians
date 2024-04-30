package world;

import java.awt.Graphics;
import java.awt.Image;

import maths.Vector2f;
import resource.ResourceManager;

// Referenced classes of package world:
//            Tile

public class FloorTile extends Tile {

	public FloorTile(Vector2f location, Vector2f radius) {
		super(location, radius);
		img = ResourceManager.resize(ResourceManager.cropImage(
				ResourceManager.imagesheet, 240, 240, 8, 8), 16, 16);
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
