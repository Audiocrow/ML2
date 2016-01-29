package com.ml2.shared.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

public class Assets implements Disposable, AssetErrorListener {
	public final String TAG = Assets.class.getName();
	
	private final AssetManager manager;
	/*Use these String->asset maps to store frequently used assets,
	 * because lookups from the manager are expensive.
	 * (See related methods below - setting cache to true means it will store in one of these)
	 * Also note: anything requiring a manager.load will be unable to return a result immediately
	 * (because manager.load is asynchronous). Thus, make sure the result isn't null before attempting to use it.
	 */
	private final ObjectMap<String, TextureAtlas> atlasRefs;
	private final ObjectMap<String, NinePatch> patchRefs;
	
	public Assets() {
		manager = new AssetManager();
		manager.setErrorListener(this);
		TextureLoader.TextureParameter pref = new TextureLoader.TextureParameter();
		pref.genMipMaps = true;
		pref.minFilter = TextureFilter.MipMapLinearLinear;
		pref.magFilter = TextureFilter.Nearest;
		atlasRefs = new ObjectMap<String, TextureAtlas>(1, 0.9f);
		patchRefs = new ObjectMap<String, NinePatch>(1, 0.9f);
	}
	/** An {@link Atlas} won't be usable immediately - it will have to be loaded first.
	 */
	public TextureAtlas getAtlas(String name) {
		TextureAtlas result = atlasRefs.get(name);
		if(result == null) {
			if(manager.isLoaded(name)) {
				result = manager.get(name);
				if(result != null)
					atlasRefs.put(name, result);
			}
			else
				manager.load(name, TextureAtlas.class);
		}
		return result;
	}
	public NinePatch getPatch(String name, TextureAtlas atlas, boolean cacheResult) {
		NinePatch result = patchRefs.get(name);
		if(result == null && atlas != null) {
			result = atlas.createPatch(name);
			if(result != null && cacheResult) patchRefs.put(name, result);
		}
		return result;
	}
	@Override
	public void dispose() {
		manager.dispose(); //Reminder: this also unloads/disposes everything the manager is managing
	}
	@Override
	public void error(AssetDescriptor ad, Throwable e) {
		Gdx.app.error(TAG, "Couldn't load asset '" + ad.fileName + "'", (Exception)e);
	}
	/**Reminder: finishLoading() halts everything until the queue has all been loaded - recommended just for loading GUI.*/
	public void finishLoading() { manager.finishLoading(); }
	public float getProgress() { return manager.getProgress(); }
	/**Calls manager.update() to continue loading of assets.*/
	public boolean update() { return manager.update(); }
}
