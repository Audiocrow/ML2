package com.ml2.shared.utils;

/** Assumes every use of this will be for sceen-sized based calculations, thus everything is a short.
 * @author Alexander Edgar
 */
public class Dimensions {
	private short x;
	private short y;
	private short width;
	private short height;
	private short minWidth;
	private short minHeight;
	/** Assume no maximum width if <= 0 */
	private short maxWidth;
	/** Assume no maximum height if <= 0 */
	private short maxHeight;
	
	public Dimensions(Dimensions b) {
		x = b.x;
		y = b.y;
		width = b.width;
		height = b.height;
		minWidth = b.minWidth;
		minHeight = b.minHeight;
		maxWidth = b.maxWidth;
		maxHeight = b.maxHeight;
	}
	public Dimensions(short x, short y, short width, short height) {
		set(x, y, width, height);
	}
	public Dimensions(short x, short y, short width, short height, short minWidth, short minHeight) {
		set(x, y, width, height, minWidth, minHeight);
	}
	public Dimensions(short x, short y, short width, short height, short minWidth, short minHeight, short maxWidth, short maxHeight) {
		set(x, y, width, height, minWidth, minHeight, maxWidth, maxHeight);
	}
	
	public short getX() { return x; }
	public void setX(short x) { this.x = x; }
	public short getY() { return y; }
	public void setY(short y) { this.y = y; }
	public short getWidth() { return width; }
	public void setWidth(short width) { this.width = width > 0 ? width : this.width; }
	public short getHeight() { return height; }
	public void setHeight(short height) { this.height = height > 0 ? height : this.height; }
	public short getMinWidth() { return minWidth; }
	public void setMinWidth(short minWidth) { this.minWidth = minWidth < 0 ? 0 : minWidth; }
	public short getMinHeight() { return minHeight; }
	public void setMinHeight(short minHeight) { this.minHeight = minHeight < 0 ? 0 : minHeight; }
	public short getMaxWidth() { return maxWidth; }
	public void setMaxWidth(short maxWidth) { this.maxWidth = maxWidth; }
	public short getMaxHeight() { return maxHeight; }
	public void setMaxHeight(short maxHeight) { this.maxHeight = maxHeight; }
	public void set(short x, short y, short width, short height) {
		this.x = x;
		this.y = y;
		this.width = width > 0 ? width : this.width;
		this.height = height > 0 ? height : this.height;
	}
	public void set(short x, short y, short width, short height, short minWidth, short minHeight) {
		set(x, y, width, height);
		this.minWidth = minWidth;
		this.minHeight = minHeight;
	}
	public void set(short x, short y, short width, short height, short minWidth, short minHeight, short maxWidth, short maxHeight) {
		set(x, y, width, height, minWidth, minHeight);
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
	}
}
