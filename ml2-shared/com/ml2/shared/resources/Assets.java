package com.ml2.shared.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {
	public final String TAG = Assets.class.getName();
	
	private final AssetManager manager;
	
	public Assets() {
		manager = new AssetManager();
		manager.setErrorListener(this);
	}
	@Override public void error(AssetDescriptor ad, Throwable e) {
		Gdx.app.error(TAG, "Couldn't load asset '" + ad.fileName + "'", (Exception)e);
	}
	public boolean update() { return manager.update(); }
	@Override public void dispose() {
		manager.dispose(); //Reminder: this also unloads/disposes everything the manager is managing
	}
}
