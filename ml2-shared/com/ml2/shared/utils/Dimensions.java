package com.ml2.shared.utils;

public class Dimensions {
	/**(x,y): top left corner of the bounding box*/
	private int x,y;
	private int width;
	private int height;
	private int minWidth;
	private int minHeight;
	private int maxWidth;
	private int maxHeight;
	
	public Dimensions(Dimensions b) {
		x = b.x;
		y = b.y;
		width = b.width;
		height = b.height;
	}
	public Dimensions(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
	public int getWidth() { return width; }
	public void setWidth(int width) { this.width = width > 0 ? width : this.width; }
	public int getHeight() { return height; }
	public void setHeight(int height) { this.height = height > 0 ? height : this.height; }
	public void set(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width > 0 ? width : this.width;
		this.height = height > 0 ? height : this.height;
	}
}
