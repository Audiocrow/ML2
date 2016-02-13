package com.ml2.shared.world;

import com.ml2.shared.resources.Constants;

/** Us ML people are used to calling these "Maps".*/
public class Chunk {
	private Tile[][] tiles;
	
	public Chunk(boolean admin) {
		if(admin) tiles = new FullTile[Constants.CHUNK_HEIGHT][Constants.CHUNK_WIDTH];
		else tiles = new Tile[Constants.CHUNK_HEIGHT][Constants.CHUNK_WIDTH];
	}
	public Tile getTile(int x, int y) {
		if(y < 0 || y >= tiles.length || x < 0 || x >= tiles[0].length) return null;
		return tiles[y][x];
	}
}
