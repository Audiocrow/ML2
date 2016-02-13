package com.ml2.client.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.ml2.shared.resources.Assets;
import com.ml2.shared.resources.Constants;

public class MapEditor extends Window {
	private ScrollPane tilesetPane;
	private Image tileset;
	
	public MapEditor(Skin skin) {
		super("Map Editor", skin);
		Assets assets = Assets.getInstance();
		Texture tiledata = assets != null ? assets.getTiledata() : null;
		if(assets == null || tiledata == null) {
			//TODO: notify user that tiledata needs to load first
			return;
		}
		setSize(Constants.TILE_SIZE*5, Constants.TILE_SIZE*5);
		setResizable(true);
		setKeepWithinStage(true);
		//EditorTileset tileset = new EditorTileset(tiledata.getWidth(), tiledata.getHeight());
		tileset = new Image(tiledata);
		tilesetPane = new ScrollPane(tileset, skin);
		tilesetPane.setScrollbarsOnTop(true);
		tilesetPane.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(pointer == 0 || button == Buttons.LEFT) {
					int tx = (int)(x + tilesetPane.getScrollX())/Constants.TILE_SIZE;
					int ty = (int)(tilesetPane.getTop() - y + tilesetPane.getScrollY())/Constants.TILE_SIZE;
					Gdx.app.debug("MapEditor", String.format("ClickedTile(%d,%d)", tx, ty));
					return true;
				}
				return false;
			}
		});
		add(tilesetPane);
	}

}
