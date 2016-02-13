package com.ml2.client.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ml2.shared.resources.Constants;

public class CameraHelper extends OrthographicCamera {
	private Viewport viewport;
	//private Actor target;
	
	public CameraHelper(int view_w, int view_h) {
		super();
		viewport = new FitViewport(view_w, view_h, this);
	}
	public void apply(boolean centerCamera) {
		if(viewport != null) viewport.apply(centerCamera);
	}
	public void adjustZoom(int delta) {
		if(delta > 0) zoom += zoom == 0.5f ? 0.5 : 1.0f;
		else zoom--;
		if(zoom > 2.0) zoom = 2.0f;
		else if(zoom < 0.5) zoom = 0.5f;
	}
	/**Returns the number of tiles on the screen in the x,y axis respectively*/
	public GridPoint2 getTilesInView() {
		GridPoint2 tiv = new GridPoint2();
		tiv.set((int)((viewportWidth*zoom)/Constants.TILE_SIZE), (int)((viewportHeight*zoom)/Constants.TILE_SIZE));
		return tiv;
	}
	public void resize(int width, int height) {
		//viewport.update(Constants.TILE_SIZE*Constants.CHUNK_WIDTH, Constants.TILE_SIZE*Constants.CHUNK_HEIGHT);
		if(viewport != null) {
			Gdx.app.debug("ch", "" + width + "," + height + " world " + viewport.getWorldWidth() + "," + viewport.getWorldHeight());
			viewport.update(width, height, true);
			update();
		}
		//position.set(viewportWidth/2, viewportHeight/2, 0);
		//position.set(width/2, height/2, 0);
	}
	public void update(float deltaTime) {
		/*if(target != null) {
			position.x = target.getX()*Constants.TILE_SIZE + target.getOX() + Constants.SPRITE_WIDTH/2;// + target.getOriginX();
			position.y = target.getY()*Constants.TILE_SIZE + target.getOY() + Constants.SPRITE_HEIGHT/2;// + target.getOriginY();
		}*/
		//super.update();
	}
	/*
	public void setTarget(Actor e) {
		target = e;
		update(0);
		//Gdx.app.debug(TAG, "Camera position:" + position);
	}
	*/
}
