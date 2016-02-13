package com.ml2.client;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.ml2.client.utils.CameraHelper;
import com.ml2.shared.resources.Constants;

public class ClientController extends InputAdapter {
	protected CameraHelper camera;
	
	ClientController() {
		camera = new CameraHelper(Constants.CHUNK_WIDTH*Constants.TILE_SIZE, Constants.CHUNK_HEIGHT*Constants.TILE_SIZE);
		//camera.setToOrtho(true);
		camera.apply(true);
		//camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
	}
	
	private void doRepeatingInput(float deltaTime) {
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
		else return super.keyUp(keycode);
		return true;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return super.mouseMoved(screenX, screenY);
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return super.touchDown(screenX, screenY, pointer, button);
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return super.touchDragged(screenX, screenY, pointer);
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return super.touchUp(screenX, screenY, pointer, button);
	}
	/**This method will run even if the InputMultiplexer has returned true after processing UI, so use booleans or whatever to make sure it is necessary.
	 * <br>Also calls {@link #doRepeatingInput}.*/
	public void update(float deltaTime) {
		doRepeatingInput(deltaTime);
	}
}
