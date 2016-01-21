package com.ml2.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ml2.shared.gui.GUIManager;
import com.ml2.shared.resources.Assets;

public class ClientLoop extends ApplicationAdapter {
	public final static String TAG = ClientLoop.class.getName();
	
	private Assets assets;
	private SpriteBatch batch;
	private ClientController clientController;
	private GUIManager guiManager;
	private boolean paused;
	
	@Override
	public void create () {
		assets = new Assets();
		batch = new SpriteBatch();
		clientController = new ClientController(assets);
		guiManager = new GUIManager();
		paused = false;
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	
	@Override
	public void pause() { paused = true; }
	@Override
	public void resume() { paused = false; }
	
	@Override
	public void render () {
		if(!paused) { clientController.update(); }
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		guiManager.draw(batch);
		batch.end();
		if(!paused) {
			assets.update();
			guiManager.loadUpdate();
		}
	}
	
}
