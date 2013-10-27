package com.NapierDevSoc.TheDrunkenLeprechaun;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DrunkenLeprechaun implements ApplicationListener {
	
	public static final int GAME_STATE_PLAY = 0;
	public static final int GAME_STATE_PAUSE = 1;
	public static final int GAME_STATE_ANIMATE = 2;
	
	private Leprechaun leprechaun;
	private Pavement pavement;
	private GrassSides grassSide;
	private GrassSides streetSide;

	public Screen screen;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private int level;
	
	public int getLevel() {
		return this.level;
	}
	public int currentLevel() {
		return this.level + 1;
	}
	public boolean nextLevel() {
		if (this.level <= 6) {
			this.level += 1;
			return true;
		}
			return false;
	}
	
	
	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
<<<<<<< HEAD
		pavement = new Pavement();
		leprechaun = new Leprechaun(pavement.getCoordinates()[0], pavement.getCoordinates()[1], 150);
		grassSide = new GrassSides();
=======
		level = 6;
		
		// Speeds
		leprechaunSpeed = new int[] {150, 140, 130, 120, 110, 100, 90};
		obstacleSpeed = new int[] {100, 110, 120, 130, 140, 150, 160};
		
		//grass side variables
		grassSide = new GrassSides("sidesGrass",0f);
		streetSide = new GrassSides("street", Gdx.graphics.getWidth()-250);
		
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
>>>>>>> pr/12
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		
		level = 0;
		
		// Speeds
		leprechaunSpeed = new int[] {150, 140, 130, 120, 110, 100, 90};
		obstacleSpeed = new int[] {100, 110, 120, 130, 140, 150, 160};
		
	}

	@Override
	public void dispose() {
		pavement.textureDispose();
		leprechaun.textureDispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
<<<<<<< HEAD
			
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			pavement.animate(100 * Gdx.graphics.getDeltaTime());
			grassSide.animateGrass(100 * Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			pavement.animate(-100 * Gdx.graphics.getDeltaTime());
			grassSide.animateGrass(-100 * Gdx.graphics.getDeltaTime());
		}
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) leprechaun.animate(-leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) leprechaun.animate(leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
		
		
		leprechaun.animate(drunkHorizontalMovement() * Gdx.graphics.getDeltaTime());
		pavement.animate(drunkVerticalMovement() * Gdx.graphics.getDeltaTime());
		grassSide.animateGrass(drunkVerticalMovement() * Gdx.graphics.getDeltaTime());
		
		
		batch.begin();
		
		pavement.draw(batch);
		grassSide.drawGrass(batch);
		leprechaun.draw(batch);
=======
		

			
		if (Gdx.input.isKeyPressed(Keys.DOWN)) 
		{
			animatePavement(100 * Gdx.graphics.getDeltaTime());
			grassSide.animate(100 * Gdx.graphics.getDeltaTime());
			streetSide.animate(100 * Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyPressed(Keys.UP))
		{
			animatePavement(-100 * Gdx.graphics.getDeltaTime());
			grassSide.animate(-100 * Gdx.graphics.getDeltaTime());
			streetSide.animate(-100 * Gdx.graphics.getDeltaTime());
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT)) animateLeprechaun(-leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) animateLeprechaun(leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
		
		grassSide.animate(drunkVerticalMovement() * Gdx.graphics.getDeltaTime());
		streetSide.animate(drunkVerticalMovement() * Gdx.graphics.getDeltaTime());
		animateLeprechaun(drunkHorizontalMovement() * Gdx.graphics.getDeltaTime());
		animatePavement(drunkVerticalMovement() * Gdx.graphics.getDeltaTime());
		
		batch.begin();
		
		grassSide.draw(batch);
		streetSide.draw(batch);
		drawPavement();
		drawLeprechaun();
>>>>>>> pr/12
		
		batch.end();
	}
	

	@Override
	public void resize(int width, int height) { }

	@Override
	public void pause() { }

	@Override
	public void resume() { }
	
	
	
	//METHODS & Variables
	private int[] leprechaunSpeed;
	private int[] obstacleSpeed;
	
	private float drunkVerticalPosition = 0;
	private double drunkVerticalDirection = .5;
	private float drunkHorizontalPosition = 0;
	private double drunkHorizontalDirection = .5;
	
	private float drunkVerticalMovement() {
		drunkVerticalPosition += drunkVerticalDirection;
		if (drunkVerticalPosition > obstacleSpeed[level]/5)
			drunkVerticalDirection = -1;
		if (drunkVerticalPosition < -obstacleSpeed[level]/5)
			drunkVerticalDirection = 1;
		return drunkVerticalPosition;
	}
	private float drunkHorizontalMovement() {
		drunkHorizontalPosition += drunkHorizontalDirection;
		if (drunkHorizontalPosition > obstacleSpeed[level]/3)
			drunkHorizontalDirection = -1;
		if (drunkHorizontalPosition < -obstacleSpeed[level]/3)
			drunkHorizontalDirection = 1;
		return drunkHorizontalPosition;
	}
	
}