package com.ml2.shared.world;

import com.ml2.shared.utils.IntIntMap;

/** This is the base Tile class that <b>only includes information a non-admin Client needs</b>.
 * The server and admin clients should be using {@link FullTile} instead.*/ 
public class Tile {
	protected IntIntMap layers;
	
	public Tile() {
		layers = new IntIntMap();
	}
}
