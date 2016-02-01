package com.ml2.shared.gui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.ml2.shared.resources.Constants;

/** Draws a skinned-box but does not increment space in a GUIObject container
 * @author Alexander Edgar
 */
public class Frame extends GUIObject {
	protected NinePatch texture;
	
	public Frame(Frame other) {
		super((GUIObject)other);
	}
	/** Will assume the default frame skin */
	public Frame(short x, short y, short width, short height) {
		super(x, y, width, height);
		texture = assets.getPatch("frame", assets.getAtlas(Constants.GUI_ATLAS), true);
	}
}
