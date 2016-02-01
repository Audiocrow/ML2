package com.ml2.shared.gui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ml2.shared.resources.Assets;
import com.ml2.shared.resources.Constants;
import com.ml2.shared.utils.Dimensions;

public class GUIDrawer {
	public static void draw(SpriteBatch batch, GUIObject obj) {
		if(batch == null || obj == null) return;
		Assets assets = Assets.getInstance();
		if(obj instanceof Frame) {
			Frame frame = (Frame)obj;
			NinePatch tex = assets.getPatch(frame.ninePatch, assets.getAtlas(Constants.GUI_ATLAS), true);
			if(tex == null) return;
			Dimensions dims = frame.getDims();
			tex.draw(batch, dims.getX(), dims.getY(), dims.getWidth(), dims.getHeight());
		}
	}
}
