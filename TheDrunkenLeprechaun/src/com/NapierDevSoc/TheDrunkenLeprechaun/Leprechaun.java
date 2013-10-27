package com.NapierDevSoc.TheDrunkenLeprechaun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Leprechaun {
	
	private Rectangle leprechaun;
	private Texture texture;
	private float x1, x2;
	
	public float x = 0f;
	public float y = 0f;
	
	public Leprechaun(float x1, float x2, float y) {
		this.x1 = x1;
		this.x2 = x2;
		
		leprechaun = new Rectangle();
		leprechaun.width = 60;
		leprechaun.height = 60;
		leprechaun.x = x1 + (x2 - x1)/2 - leprechaun.width/2;
		leprechaun.y = y - leprechaun.height/2;
		texture = new Texture(Gdx.files.internal("data/Hat.png"));
		
		this.x = leprechaun.x;
		this.y = leprechaun.y;
	}
	
	public void animate(float x_offset) {
		leprechaun.x += x_offset;
		
		if (leprechaun.x <= x1)
			leprechaun.x = x1;
		if (leprechaun.x + leprechaun.width >= x2)
			leprechaun.x = x2 - leprechaun.width;
		
		x = leprechaun.x;
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(texture,
				leprechaun.x,
				leprechaun.y,
				leprechaun.width,
				leprechaun.height);
	}
	
	public void textureDispose() {
		this.texture.dispose();
	}
}
