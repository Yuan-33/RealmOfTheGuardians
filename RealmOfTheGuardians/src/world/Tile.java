// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Tile.java

package world;

import maths.Vector2f;
import tk.damnesia.entity.Entity;

public abstract class Tile extends Entity {

	public Tile(Vector2f location, Vector2f radius) {
		super(location, radius);
	}

	public abstract boolean isBlocked();
}
