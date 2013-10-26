package com.NapierDevSoc.TheDrunkenLeprechaun;

import java.util.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import com.badlogic.gdx.utils.Array;

public class DrunkenLeprechaun implements ApplicationListener {
	
	public static final int GAME_STATE_PLAY = 0;
	public static final int GAME_STATE_PAUSE = 1;
	public static final int GAME_STATE_ANIMATE = 2;
	
	
	ArrayList<ArrayList<Rectangle>> pavement = new ArrayList<ArrayList<Rectangle>>();
	
	public Screen screen;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle pavementSlab;
	
	private ShapeRenderer shapeRenderer;
	
	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		
		
		for (int yy=0; yy <= h/100; yy++) {
			ArrayList<Rectangle> pavementX = new ArrayList<Rectangle>();
			
			for (int xx=0; xx <= w/100; xx++) {
				pavementSlab = new Rectangle();
				pavementSlab.width = 100;
				pavementSlab.height = 100;
				pavementSlab.x = 100 * xx + (1 * xx);
				pavementSlab.y = 100 * yy + (1 * yy);

				pavementX.add(xx, pavementSlab);
			}
			pavement.add(yy, pavementX);
		}
		
		
		camera = new OrthographicCamera(1, h/w);
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		shapeRenderer.begin(ShapeType.FilledRectangle);
        shapeRenderer.setColor(Color.RED);
        
        for (int yy=0; yy < pavement.size(); yy++) {
        	for (int xx=0; xx < pavement.get(yy).size(); xx++) {
        		shapeRenderer.filledRect(
        				pavement.get(yy).get(xx).x,
        				pavement.get(yy).get(xx).y,
        				pavement.get(yy).get(xx).width,
        				pavement.get(yy).get(xx).height);
        	}
        }
        
        shapeRenderer.end();
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
