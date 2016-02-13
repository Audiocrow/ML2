package com.ml2.client.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.ml2.shared.resources.Assets;
import com.ml2.shared.resources.Constants;

public class MapEditor extends Window {

	public MapEditor(String title, Skin skin) {
		super(title, skin);
	}
	public MapEditor(String title, WindowStyle style) {
		super(title, style);
	}
	public MapEditor(String title, Skin skin, String styleName) {
		super(title, skin, styleName);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Assets assets = Assets.getInstance();
		Texture tiledata = assets.getTiledata();
		if(tiledata != null) {
			int oy = (int)(getTop() - Constants.TILE_SIZE - getPadTop());
			int ox = (int)(getX());
			for(int x=ox; x<=getRight()-Constants.TILE_SIZE; x+=Constants.TILE_SIZE)
				for(int y=oy; y>=getY()+Constants.TILE_SIZE; y-=Constants.TILE_SIZE)
				{
					int tx = (x-ox);
					int ty = (oy-y);
					batch.draw(tiledata, x, y, tx, ty, Constants.TILE_SIZE, Constants.TILE_SIZE);
				}
		}
	}
}
