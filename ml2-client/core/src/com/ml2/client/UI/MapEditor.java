package com.ml2.client.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Align;
import com.ml2.shared.resources.Assets;
import com.ml2.shared.resources.Constants;

public class MapEditor extends Window {
	private ScrollPane tilesetPane;
	private Image tileset;
	
	public MapEditor(Skin skin) {
		super("Map Editor", skin);
		Assets assets = Assets.getInstance();
		final Texture tiledata = assets != null ? assets.getTiledata() : null;
		if(assets == null || tiledata == null) {
			//TODO: notify user that tiledata needs to load first
			return;
		}
		setSize(Constants.TILE_SIZE*5, Constants.TILE_SIZE*5);
		setResizable(true);
		setKeepWithinStage(true);
		tileset = new Image(tiledata);
		tileset.setScale(1.5f);
		tileset.setAlign(Align.topLeft);
		tilesetPane = new ScrollPane(tileset, skin);
		tilesetPane.setScrollbarsOnTop(true);
		tilesetPane.addListener(new ActorGestureListener() {
			@Override
			public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(pointer == 0 || button == Buttons.LEFT) {
					int tx = (int)(x + tilesetPane.getScrollX())/Constants.TILE_SIZE;
					int ty = (int)(tilesetPane.getTop() - y + tilesetPane.getScrollY())/Constants.TILE_SIZE;
					Gdx.app.debug("MapEditor", String.format("ClickedTile(%d,%d)", tx, ty));
				}
			}
			@Override
			public void zoom(InputEvent event, float initialDistance, float distance) {
				float delta = distance > 0 ? 0.1f : -0.1f;
				//tileset.setScale(me.getScaleX()+delta, me.getScaleY()+delta);
			}
		});
		add(tilesetPane);
	}

}
