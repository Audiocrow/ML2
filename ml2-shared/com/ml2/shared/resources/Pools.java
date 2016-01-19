package com.ml2.shared.resources;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Pool;

/*
 *	REMEMBER: FREE THE POOLABLE OBJECT ONCE YOU ARE DONE WITH IT!
 *	FURTHERMORE, NULL YOUR REFERENCE TO IT AFTERWARDS (BECAUSE THIS IS NOT DONE AUTOMATICALLY)!
 *
 *	E.G.:	GridPoint2 pos = Pools.getGP2();
 *			//Stuff done with pos here
 *			Pools.freeGP2(pos);
 *			pos = null;
 */

public class Pools {
	private Pool<GridPoint2> gp2pool;
	
	public GridPoint2 getGP2() {
		if(gp2pool == null) gp2pool = com.badlogic.gdx.utils.Pools.get(GridPoint2.class);
		return gp2pool.obtain();
	}
	public void freeGP2(GridPoint2 gp2) {
		if(gp2pool != null) gp2pool.free(gp2);
	}
}
