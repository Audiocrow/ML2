package com.ml2.shared.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.ml2.shared.resources.Assets;
import com.ml2.shared.resources.Constants;
import com.ml2.shared.utils.Math;

public class GUIManager implements Disposable {
	private final Array<GUIObject> guiObjs;
	private GUIObject selection;
	
	public GUIManager() {
		//TODO: Queue the actual loading screen assets for loading, which will require a true manager.finishLoading() block.
		//Queue the GUI Atlas for loading - will cause a loading screen.
		Assets.getInstance().getAtlas(Constants.GUI_ATLAS);
		guiObjs = new Array<GUIObject>(true, 4);
		selection = null;
		//Testing:
		guiObjs.add(new Frame((short)Math.center(0, Gdx.graphics.getWidth(), 128), (short)Math.center(0, Gdx.graphics.getHeight(), 128), (short)128, (short)128));
	}
	
	public void draw(SpriteBatch batch) {
		int i=0;
		for(GUIObject obj : guiObjs) {
			//This should never happen
			if(obj == null) {
				guiObjs.removeIndex(i);
				continue; 
			}
			GUIDrawer.draw(batch, obj);
			i++;
		}
	}
	
	@Override
	public void dispose() { }
}
