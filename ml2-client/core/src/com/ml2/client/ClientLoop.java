package com.ml2.client;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ml2.client.UI.MapEditor;
import com.ml2.client.utils.CameraHelper;
import com.ml2.shared.resources.Assets;
import com.ml2.shared.resources.Constants;
import com.ml2.shared.world.Chunk;
import com.ml2.shared.world.World;

public class ClientLoop extends ApplicationAdapter {
	private SpriteBatch batch;
	private ShapeRenderer shaper;
	private InputMultiplexer multiplexer;
	private ClientController clientController;
	private Stage stage;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.makeInstance().load(Constants.TILEDATA_ASSET, Texture.class, Assets.getInstance().getPrefs());
		batch = new SpriteBatch();
		shaper = new ShapeRenderer();
		clientController = new ClientController();
		stage = new Stage(new ScreenViewport());
		stage.addActor(new MapEditor("Map Editor", new Skin(Gdx.files.internal("gui/uiskin.json"))));
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(clientController);
		World.makeInstance().addChunk(new GridPoint2(0, 0), new Chunk(false));
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
		clientController.camera.resize(width, height);
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void render () {
		stage.act(Gdx.graphics.getDeltaTime());
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
			if(tiledata != null) {
				camera.apply(false);
				batch.setProjectionMatrix(camera.combined);
				batch.begin();
				batch.draw(tiledata, 256, 256, 32, 32, 128, 0, 32, 32, false, true);
				batch.end();
			}
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
			stage.getViewport().apply();
			stage.draw();
			assets.update();
		}
	}
	
}
