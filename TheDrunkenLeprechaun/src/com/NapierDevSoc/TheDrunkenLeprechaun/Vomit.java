package com.NapierDevSoc.TheDrunkenLeprechaun;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Vomit {
	private ArrayList<Rectangle> vomits;
	private Texture texture;
	
	public Vomit(){
		vomits = new ArrayList<Rectangle>();
		texture = new Texture(Gdx.files.internal("data/rainbow_vomit.png"));		
	}
	
	public  ArrayList<Rectangle> getRactangles(){
		return vomits;
	}
	
	public void add(float LeprechaunX, float LeprechaunY, int size){
		Rectangle newVomit = new Rectangle();
		newVomit.width = (16 / 7 * (size + 1)) * 20;
		newVomit.height = 2;
		newVomit.x = LeprechaunX + 30;//half of leprechaun width
		newVomit.y = LeprechaunY + 60;//half of leprechaun height
		
		vomits.add(newVomit);//add new vomit at vomit start position
	}
	
	public void animate(float y_offset) {
		for(Rectangle vomit : vomits){
			vomit.y += y_offset;
		}
		
	}
	
	public void draw(SpriteBatch batch) {
		float h = Gdx.graphics.getHeight();
		for(int i = 0; i < vomits.size(); i++){
			if(vomits.get(i).y  <= h){
			batch.draw(texture,
					vomits.get(i).x,
					vomits.get(i).y,
					vomits.get(i).width,
					vomits.get(i).height);
			}
			else{
				vomits.remove(i);
				i--;
			}
		}
	}
	
	public void remove(Rectangle object)
	{
		vomits.remove(object);
	}
}
