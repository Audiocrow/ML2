package com.ml2.shared.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.ml2.shared.resources.Assets;
import com.ml2.shared.resources.Constants;
import com.ml2.shared.utils.Dimensions;

public class GUIManager implements Disposable {
	public final static String TAG = GUIManager.class.getName();
	
	private final Assets assets;
	private final Array<GUIObject> guiObjs;
	private GUIObject selection;
	
	public GUIManager() {
		assets = new Assets();
		assets.getAtlas(Constants.GUI_ATLAS);
		guiObjs = new Array<GUIObject>(true, 16);
		selection = null;
	}
	
	public void draw(SpriteBatch batch) {
		for(GUIObject obj : guiObjs) {
			if(obj == null) continue; //Shouldn't happen 
			GUIDrawer.draw(assets, batch, obj);
		}
		//Testing:
		GUIDrawer.drawFrame(assets, batch, new Dimensions(0, 0, 128, 128));
	}
	
	public void loadUpdate() { assets.update(); }
	@Override
	public void dispose() { assets.dispose(); }
}
