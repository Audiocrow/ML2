package com.ml2.shared.world;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.ObjectMap;
import com.ml2.shared.resources.Constants;

public class World {
	private static World instance;
	private ObjectMap<GridPoint2, Chunk> chunks;
	
	private World() {
		chunks = new ObjectMap<GridPoint2, Chunk>(16, 0.7f);
	}
	/** Will create a new World and set it as the instance regardless of whether the instance already exists or not. <br>
	 * This should probably only be called in the main application class' create() method.*/
	public static World makeInstance() {
		if(instance != null) instance.chunks.clear();
		instance = null;
		return getInstance();
	}
	public static World getInstance() {
		if(instance == null) instance = new World();
		return instance;
	}
	public void addChunk(GridPoint2 pos, Chunk chunk) {
		if(chunk != null)
			chunks.put(pos, chunk);
	}
	public Tile getTile(int x, int y) {
		Chunk chunk = chunks.get(new GridPoint2(x/Constants.CHUNK_WIDTH, y/Constants.CHUNK_HEIGHT));
		if(chunk == null) return null;
		return chunk.getTile(x % Constants.CHUNK_WIDTH, y % Constants.CHUNK_HEIGHT);
	}
}
