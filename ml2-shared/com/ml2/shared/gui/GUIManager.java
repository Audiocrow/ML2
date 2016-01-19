package com.ml2.shared.gui;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.ml2.shared.resources.Assets;

public class GUIManager implements Disposable {
	private Assets assets;
	private final Array guiObjs;
	
	public GUIManager() {
		guiObjs = new Array(true, 16, GUIObject.class);
	}
	
	@Override
	public void dispose() { assets.dispose(); }
}
