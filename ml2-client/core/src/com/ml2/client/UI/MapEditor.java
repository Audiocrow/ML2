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
		init();
	}
	public MapEditor(String title, WindowStyle style) {
		super(title, style);
		init();
	}
	public MapEditor(String title, Skin skin, String styleName) {
		super(title, skin, styleName);
		init();
	}
	
	public void init() {
		setMovable(true);
		setResizable(true);
		setKeepWithinStage(true);
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		Assets assets = Assets.getInstance();
		Texture tiledata = assets.getTiledata();
		if(tiledata != null) {
			int oy = (int)(getTop() - Constants.TILE_SIZE - getPadTop());
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
