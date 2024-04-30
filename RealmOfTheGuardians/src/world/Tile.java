package world;

import maths.Vector2f;
import tk.damnesia.entity.Entity;

public abstract class Tile extends Entity {

	public Tile(Vector2f location, Vector2f radius) {
		super(location, radius);
	}

	public abstract boolean isBlocked();
}
