package com.ml2.client;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ml2.client.utils.CameraHelper;
import com.ml2.shared.resources.Assets;
import com.ml2.shared.resources.Constants;
import com.ml2.shared.world.Chunk;
import com.ml2.shared.world.World;

public class ClientLoop extends ApplicationAdapter {
	private SpriteBatch batch;
	private ShapeRenderer shaper;
	private ClientController clientController;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.makeInstance().load(Constants.TILEDATA_ASSET, Texture.class, Assets.getInstance().getPrefs());
		batch = new SpriteBatch();
		shaper = new ShapeRenderer();
		clientController = new ClientController();
		World.makeInstance().addChunk(new GridPoint2(0, 0), new Chunk(false));
	}

	@Override
	public void dispose() {
		clientController.dispose();
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
		if(clientController == null) return;
		if(clientController.camera != null)
			clientController.camera.resize(width, height);
		if(clientController.stage != null && clientController.stage.getViewport() != null)
			clientController.stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void render () {
		clientController.update(Gdx.graphics.getDeltaTime());
		Assets assets = Assets.getInstance();
		Gdx.gl.glClearColor(0.05f, 0, 0.05f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		CameraHelper camera = clientController.camera;
		if(assets.showLoadingScreen()) {
			assets.update();
			//TODO: Loading screen code here
		}
		else {
			Texture tiledata = assets.getTiledata();
			//Test
			camera.apply(false);
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			if(tiledata != null)
				batch.draw(tiledata, 256, 256, 128, 0, 32, 32);
			batch.end();
			shaper.setProjectionMatrix(camera.combined);
			shaper.begin(ShapeRenderer.ShapeType.Filled);
			shaper.rect(Constants.TILE_SIZE, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
			shaper.end();
			shaper.begin(ShapeRenderer.ShapeType.Line);
			for(int x=0; x<=camera.viewportWidth; x+=Constants.TILE_SIZE)
				shaper.line(x, 0, x, camera.viewportHeight);
			for(int y=0; y<=camera.viewportHeight; y+=Constants.TILE_SIZE)
				shaper.line(0, y, camera.viewportWidth, y);
			shaper.end();
			clientController.stage.getViewport().apply();
			clientController.stage.draw();
			assets.update();
		}
	}
	
}
