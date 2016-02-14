package com.ml2.client;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ml2.client.UI.MapEditor;
import com.ml2.client.utils.CameraHelper;
import com.ml2.shared.resources.Assets;
import com.ml2.shared.resources.Constants;
import com.ml2.shared.world.World;

public class ClientController extends InputAdapter implements Disposable {
	private InputMultiplexer multiplexer;
	protected CameraHelper camera;
	protected Skin skin;
	protected Stage stage;
	private MapEditor mapEditor;
	private int prevTouchX;
	private int prevTouchY;
	
	ClientController() {
		camera = new CameraHelper(Constants.CHUNK_WIDTH*Constants.TILE_SIZE, Constants.CHUNK_HEIGHT*Constants.TILE_SIZE);
		camera.apply(true);
		skin = new Skin(Gdx.files.internal("gui/uiskin.json"));
		stage = new Stage(new ScreenViewport());
		Label fpsLabel = new Label("FPS:", skin) {
			@Override
			public void act(final float delta) {
				this.setText("FPS:" + Gdx.graphics.getFramesPerSecond());
				super.act(delta);
			}
		};
		fpsLabel.setY(Gdx.graphics.getHeight()-skin.getFont("default-font").getLineHeight());
		stage.addActor(fpsLabel);
		stage.setDebugAll(true);
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	@Override
	public void dispose() {
		skin.dispose();
		stage.dispose();
	}
	private void doRepeatingInput(float deltaTime) {
		if(Gdx.input.isTouched()) {
			int screenX, screenY;
			if(Gdx.app.getType() == ApplicationType.Android) {
				screenX = Gdx.input.getX(0);
				screenY = Gdx.input.getY(0);
			}
			else {
				screenX = Gdx.input.getX();
				screenY = Gdx.input.getY();
			}
			if(camera != null) {
				if(screenY < 64) camera.translate(0, 2);
				else if(screenY > Gdx.graphics.getHeight()-64) camera.translate(0, -2);
				if(screenX > Gdx.graphics.getWidth()-64) camera.translate(2, 0);
				else if(screenX < 64) camera.translate(-2, 0);
			}
		}
		if(Gdx.app.getType() == ApplicationType.Desktop) {
			if(Gdx.input.isKeyPressed(Keys.UP)) {
				camera.translate(0, 1);
				camera.update();
			}
			else if(Gdx.input.isKeyPressed(Keys.DOWN)) {
				camera.translate(0, -1);
				camera.update();
			}
			if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
				camera.translate(1, 0);
				camera.update();
			}
			else if(Gdx.input.isKeyPressed(Keys.LEFT)) {
				camera.translate(-1, 0);
				camera.update();
			}
		}
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.ESCAPE) {
			//TODO: are you sure you want to quit?
			Gdx.app.exit();
		}
		else if(keycode == Keys.HOME)
			camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		else if(keycode == Keys.F1) {
			//TODO: send message to server requesting confirmation that this is admin.
			//TODO: have the server send FullTiles by default to admins.
			if(mapEditor != null) {
				mapEditor.remove();
				mapEditor = null;
			}
			else {
				mapEditor = new MapEditor(skin);
				stage.addActor(mapEditor);
			}
		}
		else return super.keyUp(keycode);
		return true;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return super.mouseMoved(screenX, screenY);
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		prevTouchX = screenX;
		prevTouchY = screenY;
		if(pointer == 3) {
			//TODO: check if admin
			if(mapEditor != null) {
				mapEditor.remove();
				mapEditor = null;
			}
			else {
				mapEditor = new MapEditor(skin);
				stage.addActor(mapEditor);
			}
		}
		return super.touchDown(screenX, screenY, pointer, button);
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		prevTouchX = screenX;
		prevTouchY = screenY;
		return super.touchDragged(screenX, screenY, pointer);
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return super.touchUp(screenX, screenY, pointer, button);
	}
	/**This method will run even if the InputMultiplexer has returned true after processing UI, so use booleans or whatever to make sure it is necessary.
	 * <br>Also calls {@link #doRepeatingInput}.*/
	public void update(float deltaTime) {
		if(stage != null)
			stage.act(deltaTime);
		doRepeatingInput(deltaTime);
	}
}
