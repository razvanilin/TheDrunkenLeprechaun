package com.NapierDevSoc.TheDrunkenLeprechaun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Car {

	private static final int WIDTH = 128;
	private static final int HEIGHT = 64;
	
	private Rectangle[] cars;
	private Texture[] texture;
	
	public Car()
	{
		cars = new Rectangle[4];
		for (int i=0;i<cars.length;i++){
			cars[i].height = HEIGHT;
			cars[i].width = WIDTH;
			cars[i].x = Gdx.graphics.getWidth()-128;
			cars[i].y = Gdx.graphics.getHeight()+128;
		}
		
		texture = new Texture[] {
				new Texture(Gdx.files.internal("data/car0down.png")),
				new Texture(Gdx.files.internal("data/car1down.png")),
				new Texture(Gdx.files.internal("data/car0up.png")),
				new Texture(Gdx.files.internal("data/car1up.png")),
		};
	}
	
	public void add(int type)
	{
		Rectangle newCar = new Rectangle();
		newCar.width = WIDTH;
		newCar.height = HEIGHT;
		//if (type >=0 && type <=1){
			newCar.x = Gdx.graphics.getWidth()-128;
			newCar.y = Gdx.graphics.getHeight()+128;
	}
	
	public void show(SpriteBatch batch)
	{
		
	}
	
}
