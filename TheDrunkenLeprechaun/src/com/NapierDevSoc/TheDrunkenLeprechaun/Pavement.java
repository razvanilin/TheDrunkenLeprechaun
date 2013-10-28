package com.NapierDevSoc.TheDrunkenLeprechaun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Pavement {
	
	private Rectangle[][] pavement;
	private Texture texture;
	private int pavementSlabSize;
	private float pavementOffset;
	
	public Pavement() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		pavementSlabSize = 80;
		pavementOffset = w/2;
		
		pavement = new Rectangle[(int) h/pavementSlabSize + 2][4];
		texture = new Texture(Gdx.files.internal("data/sidewalk_block_128x128.png"));
		
		for (int y=0; y < pavement.length; y++) {
			for (int x=0; x < pavement[y].length; x++) {
				pavement[y][x] = new Rectangle();
				pavement[y][x].width = pavementSlabSize;
				pavement[y][x].height = pavementSlabSize;
				pavement[y][x].x = (pavementOffset - pavement[y].length * pavementSlabSize / 2) + pavementSlabSize * x;
				pavement[y][x].y = pavementSlabSize * y;
			}
		}
	}
	
	public float[] getCoordinates() {
		return new float[] {pavement[0][0].x, pavement[0][pavement[0].length-1].x + pavement[0][pavement[0].length-1].width};
	}
	
	public void animate(float y_offset) {
		float h = Gdx.graphics.getHeight();
		
		for (int y=0; y < pavement.length; y++) {
			for (int x=0; x < pavement[y].length; x++) {
				// Move pavement slabs
				pavement[y][x].y += y_offset;
				
				// For walking forward
				if (y_offset < 0 && pavement[y][x].y + pavement[y][x].height < 0) {
					int y_max = 0;
					for (int yy=0; yy <  pavement.length; yy++) {
						if (pavement[y_max][x].y < pavement[yy][x].y)
							y_max = yy;
					}
					
					pavement[y][x].y = pavement[y_max][x].y + pavement[y_max][x].height + (y == 0 ? -2 : 0);
				}
				
				// For walking backward
				if (y_offset > 0 && pavement[y][x].y > h) {
					int y_min = 0;
					for (int yy=0; yy < pavement.length; yy++) {
						if (pavement[y_min][x].y > pavement[yy][x].y)
							y_min = yy;
					}
					
					pavement[y][x].y = pavement[y_min][x].y - pavement[y][x].height + (y == pavement.length-1 ? 0 : 2);
				}
			}
		}
	}
	
	public void draw(SpriteBatch batch) {
		for (int y=0; y < pavement.length; y++) {
			for (int x=0; x < pavement[y].length; x++) {
				// Render the pavement slabs
        		batch.draw(texture,
        				pavement[y][x].x,
        				pavement[y][x].y,
        				pavement[y][x].width,
        				pavement[y][x].height);
			}
		}
	}
	
	public float getOffset()
	{
		return pavementOffset;
	}
	
	public int getSlabSize()
	{
		return pavementSlabSize;
	}
	
	public void textureDispose() {
		this.texture.dispose();
	}
}
