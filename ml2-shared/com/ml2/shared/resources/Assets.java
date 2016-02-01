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

/**Use the String->asset maps here to store frequently used assets,
 * because lookups from the manager are expensive.
 * (See related get() methods below - setting cache to true means it will store in one of these, and that should almost always be done) <p>
 * Also note: anything requiring a manager.load will be unable to return a result immediately <br>
 * (because manager.load is asynchronous). Thus, make sure the result isn't null before attempting to use it.
 * @author Alexander Edgar
 */
public class Assets implements Disposable, AssetErrorListener {
	private static Assets instance;
	/** The main ClientLoop::render looks at this to decide if it needs a loading screen or not.
	 * <br> Set it to true with {@link #finishLoading()} if it's something important that you think needs a loading screen.
	 */
	private boolean showLoadingScreen;
	private final AssetManager manager;
	private final ObjectMap<String, TextureAtlas> atlasRefs;
	private final ObjectMap<String, NinePatch> patchRefs;
	private final int test;
	
	private Assets() {
		showLoadingScreen=false;
		manager = new AssetManager();
		manager.setErrorListener(this);
		TextureLoader.TextureParameter pref = new TextureLoader.TextureParameter();
		pref.genMipMaps = true;
		pref.minFilter = TextureFilter.MipMapLinearLinear;
		pref.magFilter = TextureFilter.Nearest;
		atlasRefs = new ObjectMap<String, TextureAtlas>(1, 0.9f);
		patchRefs = new ObjectMap<String, NinePatch>(1, 0.9f);
		test = (int)(Math.random()*100);
		Gdx.app.log("Assets", "test returned " + test);
	}
	/** Will create a new Assets and set it as the instance regardless of whether an instance already exists or not.
	 * <br><pre>    Note: this should (and is) probably only be called by the main application class' create() method.</pre> 
	 */
	public static Assets makeInstance() {
		if(instance != null) instance.dispose();
		instance = new Assets();
		return instance;
	}
	public static Assets getInstance() {
		if(instance == null) instance = new Assets();
		return instance;
	}
	/** A {@link com.badlogic.gdx.graphics.g2d.TextureAtlas} won't be usable immediately - it will have to be loaded first. <p>
	 * I've made this always start a loading screen if the atlas has to be created, because they will be very large on average.
	 */
	public TextureAtlas getAtlas(String name) {
		TextureAtlas result = atlasRefs.get(name);
		if(result == null) {
			if(manager.isLoaded(name)) {
				result = manager.get(name);
				if(result != null) //This should never not be the case
					atlasRefs.put(name, result);
			}
			else {
				manager.load(name, TextureAtlas.class);
				finishLoading();
			}
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
		Gdx.app.error("ML2 Assets", "Couldn't load asset '" + ad.fileName + "'", (Exception)e);
	}
	/** See {@link #showLoadingScreen}.*/
	public void finishLoading() { showLoadingScreen = true; }
	/** @return Boolean: whether the main client loop should be displaying a loading screen or not.*/
	public boolean showLoadingScreen() { return showLoadingScreen; }
	public float getProgress() { return manager.getProgress(); }
	/**Calls manager.update() to continue loading of assets.*/
	public boolean update() {
		boolean result = manager.update();
		if(result) showLoadingScreen = false;
		return result;
	}
}
