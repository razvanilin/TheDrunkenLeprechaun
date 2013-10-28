package com.NapierDevSoc.TheDrunkenLeprechaun;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Alcohol {

	private static final int SIZE = 40;

	public ArrayList<Rectangle> bottles;
	private Texture[] texture;

	public Alcohol() {
		bottles = new ArrayList<Rectangle>();
		
		texture = new Texture[4];
		for (int k=0;k<4;k++)
		texture[k] = new Texture(Gdx.files.internal("data/bot" + k + ".png"));
	}

	public void add(float x, float y) {
		Rectangle newbottle = new Rectangle();
		newbottle.x = x;
		newbottle.y = y;
		newbottle.width = SIZE;
		newbottle.height = SIZE;

		bottles.add(newbottle);
	}

	public void draw(SpriteBatch batch) {
		for (int k = 0; k < bottles.size(); k++)
			batch.draw(texture[0 + (int)(Math.random() * ((3 - 0) + 1))],
					bottles.get(k).x, bottles.get(k).y, bottles.get(k).width,
					bottles.get(k).height);
	}

	public void animate(float y_offset)
	{
		for (Rectangle bottle : bottles)
			bottle.y += y_offset;
	}
	
}
