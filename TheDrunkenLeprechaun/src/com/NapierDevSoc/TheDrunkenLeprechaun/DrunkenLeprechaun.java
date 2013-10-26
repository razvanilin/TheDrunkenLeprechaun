package com.NapierDevSoc.TheDrunkenLeprechaun;

import java.util.Date;

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
	
	private int[] leprechaunSpeed;
	private int[] obstacleSpeed;
	
	private int level;
	
	public int currentLevel() {
		return this.level;
	}
	public boolean nextLevel() {
		if (this.level <= 6) {
			this.level += 1;
			return true;
		}
			return false;
	}
	
	public Screen screen;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private long DELAY_IN_MILI_SIDES = 200;
    private long LAST_RANDOM_MOVE_TIME_SIDES = 0;
    private long RANDOM_MOVE_DIRECTION_SIDES = 0;
    
    private long DELAY_IN_MILI_FORWAR_BACK = 300;
    private long LAST_RANDOM_MOVE_TIME_FORWAR_BACK = 0;
    private long RANDOM_MOVE_DIRECTION_FORWAR_BACK = 0;
	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		level = 1;
		
		// Speeds
		leprechaunSpeed = new int[] {200, 100};
		obstacleSpeed = new int[] {200, 100};
		
		// Pavement variables
		int pavementSlabSize = 80;
		float pavementOffset = w/2;
		pavement = new Rectangle[(int) h/pavementSlabSize + 2][4];
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
		leprechaun.width = 60;
		leprechaun.height = 60;
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
		
		long curentTime = new Date().getTime();
        
        if (LAST_RANDOM_MOVE_TIME_SIDES < curentTime - DELAY_IN_MILI_SIDES) {
                RANDOM_MOVE_DIRECTION_SIDES = getRandomMovementDirection();
                LAST_RANDOM_MOVE_TIME_SIDES = curentTime;
        } else
                animateLeprechaun(RANDOM_MOVE_DIRECTION_SIDES * 100 * Gdx.graphics.getDeltaTime());
        
        if (LAST_RANDOM_MOVE_TIME_FORWAR_BACK < curentTime - DELAY_IN_MILI_FORWAR_BACK) {
                RANDOM_MOVE_DIRECTION_FORWAR_BACK = getRandomMovementDirection();
                LAST_RANDOM_MOVE_TIME_FORWAR_BACK = curentTime;
        } else
                animatePavement(RANDOM_MOVE_DIRECTION_FORWAR_BACK * 100 * Gdx.graphics.getDeltaTime());
		
		if (Gdx.input.isKeyPressed(Keys.DOWN)) animatePavement(100 * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.UP)) animatePavement(-100 * Gdx.graphics.getDeltaTime());
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) animateLeprechaun(-leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) animateLeprechaun(leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
		
		drawPavement();
		drawLeprechaun();
		
		batch.end();
	}
	
	private int getRandomMovementDirection(){
		int Min = -1;
		int Max = 1;
		return Min + (int)(Math.random() * ((Max - Min) + 1));
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