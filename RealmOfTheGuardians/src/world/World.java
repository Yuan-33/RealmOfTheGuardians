// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   World.java

package world;

import java.awt.Graphics;
import java.util.ArrayList;
import maths.Vector2f;

// Referenced classes of package world:
//            WallTile, FloorTile, Tile

public class World {

	public World(int w, int h) {
		entities = new ArrayList();
		this.w = w;
		this.h = h;
		tiles = new Tile[w][h];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++)
				if (x == 0 || y == 0 || x == w - 1 || y == h - 1)
					tiles[x][y] = new WallTile(new Vector2f(x * 16, y * 16),
							new Vector2f(8D, 8D));
				else
					tiles[x][y] = new FloorTile(new Vector2f(x * 16, y * 16),
							new Vector2f(8D, 8D));

		}

		tiles[15][10] = new WallTile(new Vector2f(240D, 160D), new Vector2f(8D,
				8D));
	}

	public void render(Graphics g) {
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++)
				tiles[x][y].render(g);

		}

	}

	public void update() {
	}

	public ArrayList entities;
	public Tile tiles[][];
	int w;
	int h;
}
