package com.NapierDevSoc.TheDrunkenLeprechaun;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class DrunkenLeprechaun implements ApplicationListener {
	
	public static final int GAME_STATE_PLAY = 0;
	public static final int GAME_STATE_PAUSE = 1;
	public static final int GAME_STATE_ANIMATE = 2;
	
	public Screen screen;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle leprechaun;
	
	private ShapeRenderer shapeRenderer;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		// Create leprechaun rectangle & center it
		leprechaun = new Rectangle();
		leprechaun.width = 64;
		leprechaun.height = 64;
		leprechaun.x = w / 2 - leprechaun.width / 2;
		leprechaun.y = h / 2 - leprechaun.height / 2;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		shapeRenderer.begin(ShapeType.FilledRectangle);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.filledRect(leprechaun.x, leprechaun.y, leprechaun.width, leprechaun.height);
        shapeRenderer.end();
        // Once we have a leprechaun image, the shapeRenderer can be replaced with the below.
		//batch.draw(leprechaunImage, leprechaun.x, leprechaun.y);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
