package com.ml2.client.UI;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ml2.shared.resources.Assets;
import com.ml2.shared.resources.Constants;

public class MapEditor extends Window {
	private final Widget tileset;
	private final ScrollPane tilesetPane;
	
	public MapEditor(Skin skin) {
		super("Map Editor", skin);
		Assets assets = Assets.getInstance();
		final Texture tiledata = assets != null ? assets.getTiledata() : null;
		if(assets == null || tiledata == null) {
			//TODO: notify user that tiledata needs to load first
			tilesetPane = null;
			tileset = null;
			return;
		}
		setSize(Constants.TILE_SIZE*5, Constants.TILE_SIZE*5);
		setResizable(true);
		setKeepWithinStage(true);
		tileset = new Widget() {
			@Override
			public float getMinWidth() { return Constants.TILE_SIZE*getScaleX(); }
			@Override
			public float getMinHeight() { return Constants.TILE_SIZE*getScaleY(); }
			@Override
			public float getPrefWidth() { return tiledata.getWidth()*getScaleX(); }
			@Override
			public float getPrefHeight() { return tiledata.getHeight()*getScaleY(); }
			@Override
			public float getMaxWidth() { return tiledata.getWidth()*getScaleX(); }
			@Override
			public float getMaxHeight() { return tiledata.getHeight()*getScaleY(); }
			@Override
			public void draw(Batch batch, float parentAlpha) {
				batch.draw(tiledata, getX(), getY(), getWidth(), getHeight());
			}
		};
		tileset.setSize(tiledata.getWidth(), tiledata.getHeight());
		tilesetPane = new ScrollPane(tileset, skin);
		tilesetPane.setScrollbarsOnTop(true);
		if(Gdx.app.getType() == ApplicationType.Desktop) {
			tilesetPane.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if(getTapCount() >= 2) {
						int tx = (int)((x + tilesetPane.getScrollX())/(Constants.TILE_SIZE*tileset.getScaleX()));
						int ty = (int)((tilesetPane.getTop() - y + tilesetPane.getScrollY())/(Constants.TILE_SIZE*tileset.getScaleY()));
						Gdx.app.debug("MapEditor", String.format("Click(%.2f,%.2f):Tile(%d,%d)", x, y, tx, ty));
					}
				}
			});
		}
		else {
			tilesetPane.addListener(new ActorGestureListener() {
				@Override
				public boolean longPress(Actor actor, float x, float y) {
					int tx = (int)((x + tilesetPane.getScrollX())/(Constants.TILE_SIZE*tileset.getScaleX()));
					int ty = (int)((tilesetPane.getTop() - y + tilesetPane.getScrollY())/(Constants.TILE_SIZE*tileset.getScaleY()));
					Gdx.app.debug("MapEditor", String.format("Click(%.2f,%.2f):Tile(%d,%d)", x, y, tx, ty));
					return true;
				}
				@Override
				public void zoom(InputEvent event, float initialDistance, float distance) {
					float scale = tileset.getScaleX() + (distance * 0.1f);
					if(scale < 0.5f) scale = 0.5f;
					else if(scale > 2.0f) scale = 2.0f;
					if(tileset.getScaleX() != scale) {
						tileset.setScale(scale);
						tilesetPane.layout();
					}
				}
			});
		}
		add(tilesetPane);
		if(Gdx.app.getType() == ApplicationType.Desktop) {
			addCaptureListener(new InputListener() {
				@Override
				public boolean scrolled(InputEvent event, float x, float y, int amount) {
					Actor hit = hit(x, y, false);
					if(hit == tileset || hit == tilesetPane) {
						if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)) {
							float scale = tileset.getScaleX() + (amount * 0.1f);
							if(scale < 0.5f) scale = 0.5f;
							else if(scale > 2.0f) scale = 2.0f;
							if(tileset.getScaleX() != scale) {
								tileset.setScale(scale);
								tilesetPane.layout();
							}
							event.stop();
							return true;
						}
					}
					return false;
				}
			});
		}
	}

}
