package com.ml2.shared.gui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ml2.shared.resources.Assets;
import com.ml2.shared.resources.Constants;
import com.ml2.shared.utils.Dimensions;

public class GUIDrawer {
	public static void draw(Assets assets, SpriteBatch batch, GUIObject obj) {
	}
	public static void drawFrame(Assets assets, SpriteBatch batch, Dimensions dims) {
		NinePatch frame = assets.getPatch("frame", assets.getAtlas(Constants.GUI_ATLAS), true);
		if(frame == null) return;
		frame.draw(batch, dims.getX(), dims.getY(), dims.getWidth(), dims.getHeight());
	}
}
