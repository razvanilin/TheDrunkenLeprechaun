package com.NapierDevSoc.TheDrunkenLeprechaun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private Alcohol alcohol;

	public Screen screen;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private float current_time = 0f;
	private float obstacle_time = 1.5f;
	private float obstacleChangeRate;
	
	private int level = 0;
	private int levelIncrease = 10;
	private int score;
	private int lastScore;
	private int alcoholCounter = 0;
	private int pukePower = 100;
	
	private int[] leprechaunSpeed;
	private int[] obstacleSpeed;
	
	private BitmapFont white;
	
	private Sound hit;
	private Music gameOn;
	private Sound destroy;

	private Game game;

	public DrunkenLeprechaun(Game game){
		this.game = game;
	}

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
		alcohol = new Alcohol();
        
        camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		
		//On Screen Text
		white = new BitmapFont(Gdx.files.internal("data/fonts/white.fnt"), false);
		
		// Speeds
		leprechaunSpeed = new int[100];
		for (int i=0;i<100;i++)
			leprechaunSpeed[i] = 150 + levelIncrease; 	
		//leprechaunSpeed = new int[] {150, 140, 130, 120, 110, 100, 90};
		obstacleSpeed = new int[100];
		for (int i=0;i<100;i++)
			obstacleSpeed[i] = 100 + levelIncrease;
		//obstacleSpeed = new int[] {100, 110, 120, 130, 140, 150, 160};
		
		//Sounds
		hit = Gdx.audio.newSound(Gdx.files.internal("data/hit.mp3"));
		
		gameOn = Gdx.audio.newMusic(Gdx.files.internal("data/game.ogg"));
		gameOn.setVolume(0.3f);
		gameOn.play();
		gameOn.isLooping();
		
		destroy = Gdx.audio.newSound(Gdx.files.internal("data/destroy.mp3"));
	}

	@Override
	public void dispose() {
		pavement.textureDispose();
		leprechaun.textureDispose();
		gameOn.dispose();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		current_time += Gdx.graphics.getDeltaTime();
		if(current_time > obstacle_time){
			current_time = 0f;
			obstacles.add(
					pavement.getCoordinates()[0] + (float)(Math.random() * ((pavement.getCoordinates()[1] - 80 - pavement.getCoordinates()[0]) + 1)),
					Gdx.graphics.getHeight() + 80
			);
		}
		
		if (alcoholCounter > 5){
			alcoholCounter = 0;
			alcohol.add(
					pavement.getCoordinates()[0] + (float)(Math.random() * ((pavement.getCoordinates()[1] - 80 - pavement.getCoordinates()[0]) + 1)),
					Gdx.graphics.getHeight() + 80
					);
		}
		
		//Backward movement
		/*if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			pavement.animate(leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
			grassSide.animate(leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
			streetSide.animate(leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
			obstacles.animate(leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
			alcohol.animate(leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
		}*/
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			pavement.animate(-leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
			grassSide.animate(-leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
			streetSide.animate(-leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
			obstacles.animate(-leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
			alcohol.animate(-leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
		}

		obstacles.animate(-leprechaunSpeed[level] / 2 * Gdx.graphics.getDeltaTime());
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) leprechaun.animate(-leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) leprechaun.animate(leprechaunSpeed[level] * Gdx.graphics.getDeltaTime());
		
		if (Gdx.input.isKeyPressed(Keys.SPACE)){
			vomits.add(leprechaun.x, leprechaun.y, 0);
			pukePower--;
			if (pukePower == 0){
				game.setScreen(new GameOver(game));
				gameOn.stop();
				gameOn.dispose();
			}
		}
		
		
		vomits.animate(200 * Gdx.graphics.getDeltaTime());
		
		leprechaun.animate(drunkHorizontalMovement() * Gdx.graphics.getDeltaTime());
		pavement.animate(drunkVerticalMovement() * Gdx.graphics.getDeltaTime());
		grassSide.animate(drunkVerticalMovement() * Gdx.graphics.getDeltaTime());
		streetSide.animate(drunkVerticalMovement() * Gdx.graphics.getDeltaTime());
		alcohol.animate(drunkVerticalMovement() * Gdx.graphics.getDeltaTime());

		obstacles.animate(drunkVerticalMovement() * Gdx.graphics.getDeltaTime());

		for(int i = 0; i < obstacles.obstacles.size(); i++){

			if(obstacles.obstacles.get(i).overlaps(leprechaun.getRectangle())){
				game.setScreen(new GameOver(game));
				gameOn.stop();
				gameOn.dispose();
			}
			for(int j = 0; j < vomits.vomits.size(); j++){
				if( obstacles.obstacles.get(i).overlaps(vomits.vomits.get(j))){
					hit.play(1.0f);
					System.out.println("i = " + i + " obstacles.lives = " + obstacles.lives);
					int lives = obstacles.lives.get(i) - 1;
					//System.out.println("lives = " + lives + " obstacles.lives.get(i) = " + obstacles.lives.get(i));
					obstacles.lives.remove(i);
					obstacles.lives.add(i, lives);
					vomits.vomits.remove(j);
					j--;
					if (lives <= 0){
						level();
						alcoholCounter++;
						obstacles.obstacles.remove(i);
						obstacles.lives.remove(i);
						destroy.play(1.0f);
						i--;
						break;
					}
				}
			}
		}

		for (int i = 0;i<alcohol.bottles.size();i++)
		{
			if (alcohol.bottles.get(i).overlaps(leprechaun.getRectangle())){
				alcohol.bottles.remove(i);
				i--;
				pukePower += 100;
			}
		}

		batch.begin();

		pavement.draw(batch);
		grassSide.draw(batch);
		streetSide.draw(batch);
		leprechaun.draw(batch);
		obstacles.draw(batch);
		alcohol.draw(batch);
		vomits.draw(batch);
		white.draw(batch, "Score: "+score, 20, Gdx.graphics.getHeight()-20);
		white.draw(batch, "Level: "+level, 20, Gdx.graphics.getHeight()-60);
		white.draw(batch, "Alcohol Level: "+pukePower, 20, Gdx.graphics.getHeight()/3);
		
		batch.end();
	}
	

	@Override
	public void resize(int width, int height) { }

	@Override
	public void pause() { }

	@Override
	public void resume() { }
	
	
	
	//METHODS & Variables
	
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
	
	private void level()
	{
		score++;
		System.out.println(""+score);
		if (score == level+1 * level+1 + lastScore){
			level++;
			System.out.println("level="+level);
			lastScore = score;
			
			if (level>0){
				obstacleChangeRate = (float)level/(level*10f);
				obstacle_time = obstacle_time - obstacleChangeRate;
				System.out.println("obstacle_time="+obstacle_time);
			}
		}
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	
}