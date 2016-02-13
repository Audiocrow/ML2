package com.ml2.client.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.ml2.shared.resources.Assets;
import com.ml2.shared.resources.Constants;

public class EditorTileset extends Widget {
	private int prefWidth;
	private int prefHeight;
	
	public EditorTileset(int width, int height) {
		setSize(width, height);
		prefWidth = width;
		prefHeight = height;
	}
	
	@Override
	public float getMinWidth() { return Constants.TILE_SIZE; }
	@Override
	public float getMinHeight() { return Constants.TILE_SIZE; }
	@Override
	public float getPrefWidth() { return prefWidth; }
	@Override
	public float getPrefHeight() { return prefHeight; }
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		float scrollX = 0, scrollY = 0;
		if(getParent() instanceof ScrollPane) {
			ScrollPane scrollPane = (ScrollPane)getParent();
			scrollX = scrollPane.getScrollX();
			scrollY = scrollPane.getScrollY();
		}
		Assets assets = Assets.getInstance();
		Texture tiledata = assets.getTiledata();
		if(tiledata != null) {
			int oy = (int)(getTop() - Constants.TILE_SIZE);
			int ox = (int)(getX());
			int right = (int)(getRight());
			int bottom = (int)(getY());
			int prevY = oy+Constants.TILE_SIZE;
			for(int x=ox; x<right; x+=Constants.TILE_SIZE) {
				if(x-ox >= tiledata.getWidth()) break;
				prevY = oy+Constants.TILE_SIZE;
				for(int y=oy; y>bottom-Constants.TILE_SIZE; y-=Constants.TILE_SIZE)
				{
					if(y-oy >= tiledata.getHeight()) break;
					int tx = (x-ox);
					int ty = (oy-y);
					int w = right-x < Constants.TILE_SIZE ? right-x : Constants.TILE_SIZE;
					if(y < bottom) y = bottom;
					int h = prevY - y < Constants.TILE_SIZE ? prevY-y : Constants.TILE_SIZE;
					batch.draw(tiledata, x, y, tx, ty, w, h);
					prevY = y;
				}
			}
		}
	}
}
