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
		

		
		
		for (int i=0; i <= h/100; i++) {
			ArrayList<Rectangle> pavementX = new ArrayList<Rectangle>();
			
			for (int ii=0; ii <= w/100; ii++) {
				pavementSlab = new Rectangle();
				pavementSlab.width = 100;
				pavementSlab.height = 100;
				pavementSlab.x = 100 * ii + (1*ii);
				pavementSlab.y = 100 * i + (1*i);

				pavementX.add(ii, pavementSlab);
			}
			pavement.add(i, pavementX);
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
        
        for (int i=0; i<pavement.size(); i++) {
        	for (int ii=0; ii<pavement.get(i).size(); ii++) {
        		shapeRenderer.filledRect(pavement.get(i).get(ii).x, pavement.get(i).get(ii).y, pavement.get(i).get(ii).width, pavement.get(i).get(ii).height);
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
