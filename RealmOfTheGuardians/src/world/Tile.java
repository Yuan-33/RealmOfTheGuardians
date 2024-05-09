package world;

import entity.Entity;
import maths.Vector;

public abstract class Tile extends Entity {

	public Tile(Vector location, Vector radius) {
		super(location, radius);
	}

	public abstract boolean isBlocked();
}
