package com.ml2.shared.gui;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.ml2.shared.resources.Assets;

public class GUIManager implements Disposable {
	public final static String TAG = GUIManager.class.getName();
	
	private Assets assets;
	private final Array guiObjs;
	
	public GUIManager() {
		assets = new Assets();
		guiObjs = new Array(true, 16, GUIObject.class);
	}
	
	@Override
	public void dispose() { assets.dispose(); }
}
