package com.NapierDevSoc.TheDrunkenLeprechaun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameOver implements Screen{
	
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonPlay, buttonExit;
	private BitmapFont white, black;
	private Label heading, gameOver;
	
	private static float BUTTON_WIDTH = 400f;
	
	private Pavement pavement;
	private GrassSides grassSide;
    private GrassSides streetSide;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Texture logoTexture;
	
	private Sound intro;
	
	private Game game;
	
	public GameOver(Game game){
		this.game = game;
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Table.drawDebug(stage);
		batch.begin();
		pavement.draw(batch);
		grassSide.draw(batch);
        streetSide.draw(batch);
        batch.draw(logoTexture, Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/2, 512, 256);

		batch.end();
		
		stage.act(delta);
		stage.draw();
	}
	@Override
	public void resize(int width, int height) {
		
	}
	@Override
	public void show() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		//I think that the main menu background could look the same as the game
		//environment just it would move.
		pavement = new Pavement();
		grassSide = new GrassSides("sidesGrass",0f);
        streetSide = new GrassSides("street", Gdx.graphics.getWidth()-250);
		
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		//load the sound
		intro = Gdx.audio.newSound(Gdx.files.internal("data/intro1.mp3"));
		intro.loop();
		
		//loading button.pack - forgot to make w and h with the power of 2s... dumb
		atlas = new TextureAtlas("data/ui/button.pack");
		skin = new Skin(atlas);
		
		//load the logo
		logoTexture = new Texture(Gdx.files.internal("data/logo.png"));
		
		table = new Table();
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//fonts white for heading, black for buttons
		white = new BitmapFont(Gdx.files.internal("data/fonts/white.fnt"), false);
		black = new BitmapFont(Gdx.files.internal("data/fonts/black.fnt"), false);
		
		//button style. For button the button.pack is used (will have to be changed
		// because the buttons look like they just came from 90s..
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.up");//from button.pack
		textButtonStyle.down = skin.getDrawable("button.down");//from button.pack
		textButtonStyle.pressedOffsetX = 1;//when pressed mooves to the right by 1px
		textButtonStyle.pressedOffsetY = - 1;//when pressed mooves down by 1px
		textButtonStyle.font = black;
		
		//button exit
		buttonExit = new TextButton("EXIT", textButtonStyle);
		buttonExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				Gdx.app.exit();
			}
		});
		buttonExit.pad(20);
		buttonExit.setWidth(BUTTON_WIDTH);
		
		//button play
		buttonPlay = new TextButton("PLAY AGAIN", textButtonStyle);
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new DrunkenLeprechaun(game));
				intro.dispose();
			}
		});
		buttonPlay.pad(20);
		buttonPlay.setWidth(BUTTON_WIDTH);
		
		//making the heading
		LabelStyle labelStyle = new LabelStyle(white, Color.WHITE);		
		heading = new Label("", labelStyle);	
		gameOver = new Label("Game Over", labelStyle);
		gameOver.scale(2);
		
		//adding the heading and the buttons to the table
		table.add(heading).spaceBottom(70);
		table.row();
		table.add(gameOver).spaceBottom(30);
		table.row();
		table.add(buttonPlay);
		table.row();
		table.add(buttonExit);
		table.debug();
		stage.addActor(table);
		
		camera = new OrthographicCamera(1, h/w);
		
		batch = new SpriteBatch();
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		white.dispose();
		black.dispose();
	}
}