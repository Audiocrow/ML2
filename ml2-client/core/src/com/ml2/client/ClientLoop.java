package com.ml2.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ml2.shared.resources.Assets;

public class ClientLoop extends ApplicationAdapter {
	private SpriteBatch batch;
	private InputMultiplexer multiplexer;
	private ClientController clientController;
	private Stage stage;
	
	@Override
	public void create () {
		Assets.makeInstance();
		batch = new SpriteBatch();
		clientController = new ClientController();
		stage = new Stage(new ScreenViewport());
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(clientController);
	}

	@Override
	public void dispose() {
		stage.dispose();
		Assets.getInstance().dispose();
		super.dispose();
	}
	
	@Override
	public void pause() { }
	@Override
	public void resume() {
		/*Since the OpenGL context has to be re-established, I'm assuming a loading screen is necessary on a resume.
		 * if it isn't, the loading screen will be very quick anyway so there is no problem.*/
		Assets.getInstance().finishLoading();
	}
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void render () {
		stage.act(Gdx.graphics.getDeltaTime());
		clientController.update();
		Assets assets = Assets.getInstance();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(assets.showLoadingScreen()) {
			assets.update();
			//TODO: Loading screen code here
		}
		else {
			batch.begin();
			batch.end();
			stage.draw();
			assets.update();
		}
	}
	
}
