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
		
		// Pavement variables
		int pavementSlabSize = 100;
		float pavementOffset = w/2;
		pavement = new Rectangle[(int) h/pavementSlabSize + 2][3];
		pavementTexture = new Texture(Gdx.files.internal("data/sidewalk_block_128x128.png"));
		
		for (int y=0; y < pavement.length; y++) {
			for (int x=0; x < pavement[y].length; x++) {
				pavement[y][x] = new Rectangle();
				pavement[y][x].width = pavementSlabSize;
				pavement[y][x].height = pavementSlabSize;
				pavement[y][x].x = (pavementOffset - pavement[y].length * pavementSlabSize / 2) + pavementSlabSize * x;
				pavement[y][x].y = pavementSlabSize * y;
			}
		}

		// Leprechaun Variable
		leprechaun = new Rectangle();
		leprechaun.width = 80;
		leprechaun.height = 80;
		leprechaun.x = pavement[0][0].x + (pavement[0].length * pavementSlabSize / 2) - leprechaun.width/2;
		leprechaun.y = 150 - leprechaun.height/2;
		leprechaunTexture = new Texture(Gdx.files.internal("data/Hat.png"));
		
		camera = new OrthographicCamera(1, h/w);
		
		batch = new SpriteBatch();
		
	}

	@Override
	public void dispose() {
		pavementTexture.dispose();
		leprechaunTexture.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		if (Gdx.input.isKeyPressed(Keys.DOWN)) animatePavement(100 * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.UP)) animatePavement(-100 * Gdx.graphics.getDeltaTime());
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) animateLeprechaun(-100 * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) animateLeprechaun(100 * Gdx.graphics.getDeltaTime());
		
		drawPavement();
		drawLeprechaun();
		
		batch.end();
	}
	
	private void animateLeprechaun(float x_offset) {
		leprechaun.x += x_offset;
		
		if (leprechaun.x <= pavement[0][0].x)
			leprechaun.x = pavement[0][0].x;
		if (leprechaun.x + leprechaun.width >= pavement[0][pavement[0].length-1].x + pavement[0][pavement[0].length-1].width)
			leprechaun.x = pavement[0][pavement[0].length-1].x + pavement[0][pavement[0].length-1].width - leprechaun.width;
		
	}
	
	private void drawLeprechaun() {
		batch.draw(leprechaunTexture,
				leprechaun.x,
				leprechaun.y,
				leprechaun.width,
				leprechaun.height);
	}
	
	private void animatePavement(float y_offset) {
		float h = Gdx.graphics.getHeight();
		
		for (int y=0; y < pavement.length; y++) {
			for (int x=0; x < pavement[y].length; x++) {
				// Move pavement slabs
				pavement[y][x].y += y_offset;
				
				// For walking forward
				if (y_offset < 0 && pavement[y][x].y + pavement[y][x].height <= 0) {
					int y_max = 0;
					for (int yy=0; yy <  pavement.length; yy++) {
						if (pavement[y_max][x].y < pavement[yy][x].y)
							y_max = yy;
					}
					
					pavement[y][x].y = pavement[y_max][x].y + pavement[y_max][x].height + (y == 0 ? -2 : 0);
				}
				
				// For walking backward
				if (y_offset > 0 && pavement[y][x].y >= h) {
					int y_min = 0;
					for (int yy=0; yy < pavement.length; yy++) {
						if (pavement[y_min][x].y > pavement[yy][x].y)
							y_min = yy;
					}
					
					pavement[y][x].y = pavement[y_min][x].y - pavement[y][x].height + (y == pavement.length-1 ? 0 : 2);
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