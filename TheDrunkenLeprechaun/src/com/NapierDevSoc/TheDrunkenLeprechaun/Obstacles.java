package com.NapierDevSoc.TheDrunkenLeprechaun;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Obstacles {
	
	private static final int SIZE = 80;
	
	private ArrayList<Rectangle> obstacles;
	private Texture texture;
	
	
	public Obstacles(){
		obstacles = new ArrayList<Rectangle>();
		texture = new Texture(Gdx.files.internal("data/orc.png"));		
	}
	
	public void add(float x, float y){
		Rectangle newObstacle = new Rectangle();
		newObstacle.width = SIZE;
		newObstacle.height = SIZE;
		newObstacle.x = x;
		newObstacle.y = y;
		
		obstacles.add(newObstacle);//add new vomit at vomit start position
		System.out.println("" + obstacles.size());
	}
	
	public void animate(float y_offset) {
		for(Rectangle obstacle : obstacles){
			obstacle.y += y_offset;
		}
		
	}
	
	public void draw(SpriteBatch batch) {
		
		for(int i = 0; i < obstacles.size(); i++){
			//System.out.println(obstacles.get(i).x);
			//System.out.println(obstacles.get(i).y);
//			if(obstacles.get(i).y  < 0){
				batch.draw(texture,
						obstacles.get(i).x,
						obstacles.get(i).y,
						obstacles.get(i).width,
						obstacles.get(i).height);
//			}
//			else{
				//obstacles.remove(i);
				//i--;
//			}
		}
	}
}
