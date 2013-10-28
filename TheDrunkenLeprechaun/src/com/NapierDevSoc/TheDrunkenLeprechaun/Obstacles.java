package com.NapierDevSoc.TheDrunkenLeprechaun;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Obstacles {
	
	private static final int SIZE = 80;
	
	public ArrayList<Rectangle> obstacles;
	private Texture texture;
	
	
	public Obstacles(){
		obstacles = new ArrayList<Rectangle>();
		texture = new Texture(Gdx.files.internal("data/orc.png"));		
	}
	
	public void add(float x, float y){
		Rectangle newObstacle = new Rectangle();
		newObstacle.width = SIZE - 20;
		newObstacle.height = SIZE - 20;
		newObstacle.x = x + 10;
		newObstacle.y = y + 10;
		
		obstacles.add(newObstacle);//add new vomit at vomit start position
	}
	
	public void animate(float y_offset) {
		for(Rectangle obstacle : obstacles){
			obstacle.y += y_offset;
		}
		
	}
	
	public void draw(SpriteBatch batch) {
		
		for(int i = 0; i < obstacles.size(); i++){
				batch.draw(texture,
						obstacles.get(i).x - 10,
						obstacles.get(i).y - 10,
						obstacles.get(i).width + 20,
						obstacles.get(i).height + 20);
		}
	}
}
