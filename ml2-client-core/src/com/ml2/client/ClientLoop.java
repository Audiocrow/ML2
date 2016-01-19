package com.ml2.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ml2.shared.resources.Assets;

public class ClientLoop extends ApplicationAdapter {
	private Assets assets;
	private SpriteBatch batch;
	private boolean paused;
	
	@Override
	public void create () {
		assets = new Assets();
		batch = new SpriteBatch();
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
		if(!paused) { } //Control(ler) goes here
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!paused) { assets.update(); }
	}
	
}
