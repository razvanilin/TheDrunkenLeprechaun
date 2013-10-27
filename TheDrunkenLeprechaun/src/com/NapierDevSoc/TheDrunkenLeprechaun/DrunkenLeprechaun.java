package com.NapierDevSoc.TheDrunkenLeprechaun;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DrunkenLeprechaun implements Screen {
	
	public static final int GAME_STATE_PLAY = 0;
	public static final int GAME_STATE_PAUSE = 1;
	public static final int GAME_STATE_ANIMATE = 2;
	
	private Leprechaun leprechaun;
	private Pavement pavement;
	private GrassSides grassSide;
    private GrassSides streetSide;
    private Obstacles obstacles;
    private Vomit vomits;
    
    private float last_obsticle_created = 0f;
    private static final float OBSTICLES_DELAY = 5000f;

	public Screen screen;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Random rand;
	private float current_time = 0f;
	
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
	public void show() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		pavement = new Pavement();
		leprechaun = new Leprechaun(pavement.getCoordinates()[0], pavement.getCoordinates()[1], 150);
        grassSide = new GrassSides("sidesGrass",0f);
        streetSide = new GrassSides("street", Gdx.graphics.getWidth()-250);
        obstacles = new Obstacles();
        
        vomits = new Vomit();
        rand = new Random();
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
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		current_time += Gdx.graphics.getDeltaTime();
		System.out.println(""+current_time);
		if(current_time > 3.0f){
			current_time = 0f;//
			// 
			obstacles.add(
					pavement.getCoordinates()[0] + (float)(Math.random() * ((pavement.getCoordinates()[1] - 80 - pavement.getCoordinates()[0]) + 1)),
					Gdx.graphics.getHeight() + 80
			);
		}
		
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			pavement.animate(100 * Gdx.graphics.getDeltaTime());
			grassSide.animate(100 * Gdx.graphics.getDeltaTime());
			streetSide.animate(100 * Gdx.graphics.getDeltaTime());
			obstacles.animate(100 * Gdx.graphics.getDeltaTime());
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			pavement.animate(-100 * Gdx.graphics.getDeltaTime());
			grassSide.animate(-100 * Gdx.graphics.getDeltaTime());
			streetSide.animate(-100 * Gdx.graphics.getDeltaTime());
			obstacles.animate(-100 * Gdx.graphics.getDeltaTime());
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT)) leprechaun.animate(-leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) leprechaun.animate(leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
		
		if (Gdx.input.isKeyPressed(Keys.SPACE)){
			vomits.add(leprechaun.x, leprechaun.y, level);			
		}
		
		
		vomits.animate(200 * Gdx.graphics.getDeltaTime());
		
		leprechaun.animate(drunkHorizontalMovement() * Gdx.graphics.getDeltaTime());
		pavement.animate(drunkVerticalMovement() * Gdx.graphics.getDeltaTime());
		grassSide.animate(drunkVerticalMovement() * Gdx.graphics.getDeltaTime());
		streetSide.animate(drunkVerticalMovement() * Gdx.graphics.getDeltaTime());

		obstacles.animate(drunkVerticalMovement() * Gdx.graphics.getDeltaTime());


		batch.begin();

		pavement.draw(batch);
		grassSide.draw(batch);
		streetSide.draw(batch);
		leprechaun.draw(batch);
		obstacles.draw(batch);
		vomits.draw(batch);
		
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
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	
}