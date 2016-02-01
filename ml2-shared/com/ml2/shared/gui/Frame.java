package com.ml2.shared.gui;

/** Draws a skinned-box but does not increment space in a GUIObject container
 * @author Alexander Edgar
 */
public class Frame extends GUIObject {
	/** The name of the NinePatch to be used by this frame*/
	protected String ninePatch;
	
	public Frame(Frame other) {
		super((GUIObject)other);
	}
	/** Will assume the default frame skin */
	public Frame(short x, short y, short width, short height) {
		super(x, y, width, height);
		ninePatch = "frame";
	}
	/** @param ninePatch the name of the NinePatch to be used by this frame*/
	public Frame(String ninePatch, short x, short y, short width, short height) {
		super(x, y, width, height);
		this.ninePatch = ninePatch;
	}
}
