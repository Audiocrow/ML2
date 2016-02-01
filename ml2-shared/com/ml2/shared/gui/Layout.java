package com.ml2.shared.gui;

import com.badlogic.gdx.utils.Array;
import com.ml2.shared.utils.Dimensions;

/** Stores an insert-ordered array of children; arranges them within a bounded area.
 * <p> Call {@link GUIDrawer#draw} directly on the layout to have it drawn. 
 * @author Alexander Edgar
 */
public class Layout extends GUIObject {
	private static enum LayoutType { Vertical, Horizontal };
	/** See {@see LayoutType}. Defaults to Vertical*/
	private LayoutType type;
	private byte padding;
	private byte spacing;
	private Array<GUIObject> children;
	
	public Layout(Layout other) {
		super((GUIObject)other);
		type = other.type;
		padding = other.padding;
		spacing = other.spacing;
		children = new Array<GUIObject>(true, other.children.items.length, GUIObject.class);
		for(GUIObject obj : children)
			obj.setParent(this);
		compute();
	}
	/**@param type {@link Vertical} or {@link Horizontal}*/
	public Layout(LayoutType type, byte padding, byte spacing, short x, short y, short width, short height) {
		super(x, y, width, height);
		this.type = type;
		this.padding = padding;
		this.spacing = spacing;
		children = new Array<GUIObject>(true, 4, GUIObject.class);
		compute();
	}
	public void compute() {
		if(parent != null) {
			dims.setMaxWidth((short)(parent.getDims().getWidth() - padding));
			dims.setMaxHeight((short)(parent.getDims().getHeight() - padding));
		}
		short width = (short)(padding*2), height = (short)(padding*2);
		//Calculate current total width&height of this layout
		for(GUIObject obj : children) {
			Dimensions oDims = obj.getDims();
			if(type == LayoutType.Horizontal) {
				width += oDims.getWidth();
				height = (short)Math.max(oDims.getHeight(), height);
			}
			else {
				height += oDims.getHeight();
				width = (short)Math.max(oDims.getWidth(), width);
			}
		}
		if(type == LayoutType.Horizontal)
			width += (children.size-1)*spacing;
		else
			height += (children.size-1)*spacing;
		//Next, fix being over the maximum if its happened
		if(dims.getMaxWidth() > 0 && width > dims.getMaxWidth()) {
			//Attempt to remove padding first
			if(width - padding*2 <= dims.getMaxWidth()) {
				while(width > dims.getMaxWidth() && padding > 0) {
					width -= 2;
					padding--;
				}
			}
			else {
				width -= padding*2;
				padding = 0;
			}
			//Attempt to reduce spacing before shrinking children
			if(type == LayoutType.Horizontal && width-((children.size-1)*spacing) <= dims.getMaxWidth()) {
				while(width > dims.getMaxWidth() && spacing > 0) {
					width -= (children.size-1);
					spacing--;
				}
			}
			//Reducing spacing alone isn't enough - shrink children
			else if(type == LayoutType.Horizontal) {
				width -= (children.size-1)*spacing;
				spacing=0;
				//This is the amount each child needs to shrink, but this might infringe upon their minimum sizes
				short reduction = (short)Math.round((float)(dims.getMaxWidth() - width)/(float)children.size);
				short remainder = 0;
				for(GUIObject obj : children) {
					Dimensions oDims = obj.getDims();
					if(oDims.getWidth() - reduction < oDims.getMinWidth()) {
						remainder += reduction - (oDims.getWidth() - oDims.getMinWidth());
						oDims.setWidth(oDims.getMinWidth());
					}
					else oDims.setWidth((short)(oDims.getWidth()-reduction));
				}
			}
		}
	}
}
