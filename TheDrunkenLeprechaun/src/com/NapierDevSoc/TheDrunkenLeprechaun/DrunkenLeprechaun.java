package com.NapierDevSoc.TheDrunkenLeprechaun;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class DrunkenLeprechaun implements ApplicationListener {
	
	public static final int GAME_STATE_PLAY = 0;
	public static final int GAME_STATE_PAUSE = 1;
	public static final int GAME_STATE_ANIMATE = 2;
	
	private Rectangle[][] pavement;
	private Texture pavementTexture;
	
	private Rectangle leprechaun;
	private Texture leprechaunTexture;
	
	public Screen screen;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	
	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		// Leprechaun Variable
		leprechaun = new Rectangle();
		leprechaun.width = 80;
		leprechaun.height = 80;
		leprechaun.x = w/2 - leprechaun.width/2;
		leprechaun.y = 150 - leprechaun.height/2;
		leprechaunTexture = new Texture(Gdx.files.internal("data/Hat.png"));
		
		// Pavement variables
		int pavementSlabSize = 100;
		pavement = new Rectangle[3][(int)(w/pavementSlabSize + 1)];
		pavementTexture = new Texture(Gdx.files.internal("data/sidewalk_block_128x128.png"));
		
		for (int y=0; y < 3; y++) {
			for (int x=0; x < pavement[y].length; x++) {
				pavement[y][x] = new Rectangle();
				pavement[y][x].width = pavementSlabSize;
				pavement[y][x].height = pavementSlabSize;
				pavement[y][x].x = pavementSlabSize * x;
				pavement[y][x].y = pavementSlabSize * y;
			}
		}
		
		camera = new OrthographicCamera(1, h/w);
		
		batch = new SpriteBatch();
		
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) animatePavement(100 * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) animatePavement(-100 * Gdx.graphics.getDeltaTime());
		
		if (Gdx.input.isKeyPressed(Keys.UP)) animateLeprechaun(100 * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.DOWN)) animateLeprechaun(-100 * Gdx.graphics.getDeltaTime());
		
		drawPavement();
		drawLeprechaun();
		
		batch.end();
	}
	
	private void animateLeprechaun(float y_offset) {
		leprechaun.y += y_offset;
		
		if (leprechaun.y <= 0)
			leprechaun.y = 0;
		if (leprechaun.y + leprechaun.height >= pavement[pavement.length-1][0].y + pavement[pavement.length-1][0].height)
			leprechaun.y = pavement[pavement.length-1][0].y + pavement[pavement.length-1][0].height - leprechaun.height;
		
	}
	
	private void drawLeprechaun() {
		batch.draw(leprechaunTexture,
				leprechaun.x,
				leprechaun.y,
				leprechaun.width,
				leprechaun.height);
	}
	
	private void animatePavement(float x_offset) {
		float w = Gdx.graphics.getWidth();
		
		for (int y=0; y < pavement.length; y++) {
			for (int x=0; x < pavement[y].length; x++) {
				// Move pavement slabs
				pavement[y][x].x += x_offset;
				
				//For right to left
				if (x_offset < 0 && pavement[y][x].x + pavement[y][x].width <= 0) {
					int x_max = 0;
					for (int xx=0; xx <  pavement[y].length; xx++) {
						if (pavement[y][x_max].x < pavement[y][xx].x)
							x_max = xx;
					}
					
					pavement[y][x].x = pavement[y][x_max].x + pavement[y][x_max].width + (x == 0 ? -2 : 0);
				}
				
				// For left to right
				if (x_offset > 0 && pavement[y][x].x >= w) {
					int x_min = 0;
					for (int xx=0; xx <  pavement[y].length; xx++) {
						if (pavement[y][x_min].x > pavement[y][xx].x)
							x_min = xx;
					}
					
					pavement[y][x].x = pavement[y][x_min].x - pavement[y][x].width + (x == pavement[y].length-1 ? 0 : 2);
				}
			}
		}
	}
	
	private void drawPavement() {
		for (int y=0; y < pavement.length; y++) {
			for (int x=0; x < pavement[y].length; x++) {
				// Render the pavement slabs
        		batch.draw(pavementTexture,
        				pavement[y][x].x,
        				pavement[y][x].y,
        				pavement[y][x].width,
        				pavement[y][x].height);
			}
		}
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