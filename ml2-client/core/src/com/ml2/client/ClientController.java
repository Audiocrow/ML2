package com.ml2.client;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class ClientController extends InputAdapter {
	
	ClientController() {
		Gdx.input.setInputProcessor(this);
	}

	public void update() {
		doRepeatingInput(Gdx.graphics.getDeltaTime());
		//Camera update goes here when/if we use one
	}
	private void doRepeatingInput(float deltaTime) {
		if(Gdx.app.getType() == ApplicationType.Desktop) {}
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.ESCAPE) {
			//TODO: are you sure you want to quit?
			Gdx.app.exit();
			return true;
		}
		else return super.keyUp(keycode);
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return super.mouseMoved(screenX, screenY);
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return super.touchUp(screenX, screenY, pointer, button);
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return super.touchDragged(screenX, screenY, pointer);
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return super.touchDown(screenX, screenY, pointer, button);
	}
}
