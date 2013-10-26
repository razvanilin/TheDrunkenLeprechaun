package com.NapierDevSoc.TheDrunkenLeprechaun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GrassSides {
	
	private final int HEIGHT = 480;
	private final int WIDTH = 250;
	
	private Texture[] grassTexture;
	private Rectangle[] grassRectangle;
	
	public GrassSides(){
		
		grassRectangle = new Rectangle[2];
		grassTexture = new Texture[3];
		
		for (int i=0;i<grassRectangle.length;i++)
		{
			grassRectangle[i] = new Rectangle();
			grassRectangle[i].width = WIDTH;
			grassRectangle[i].height = HEIGHT;
			grassRectangle[i].x = 0;
			grassRectangle[i].y = HEIGHT*i;
		}
		
		for (int i=0; i<grassTexture.length;i++)
		{
			grassTexture[i] = new Texture(Gdx.files.internal("data/sidesGrass"+i+".png"));
		}
	}
	
	public void drawGrass(SpriteBatch batch) {
		for (int i=0; i < grassRectangle.length; i++) {
        		batch.draw(grassTexture[i],
        				grassRectangle[i].x,
        				grassRectangle[i].y,
        				grassRectangle[i].width,
        				grassRectangle[i].height);
		}
	}
	
	public void animateGrass(float y_offset) {
		float h = Gdx.graphics.getHeight();
		
		for (int i=0; i < grassRectangle.length; i++) {
			
				grassRectangle[i].y += y_offset;
				
				// For walking forward
				if (y_offset < 0 && grassRectangle[i].y + grassRectangle[i].height <= 0) {
					int y_max = 0;
					for (int ii=0; ii <  grassRectangle.length; ii++) {
						if (grassRectangle[y_max].y < grassRectangle[ii].y)
							y_max = ii;
					}
					
					grassRectangle[i].y = grassRectangle[y_max].y + grassRectangle[y_max].height + (i == 0 ? -2 : 0);
				}
				
				// For walking backward
				if (y_offset > 0 && grassRectangle[i].y >= h) {
					int y_min = 0;
					for (int ii=0; ii < grassRectangle.length; ii++) {
						if (grassRectangle[y_min].y > grassRectangle[ii].y)
							y_min = ii;
					}
					
					grassRectangle[i].y = grassRectangle[y_min].y - grassRectangle[i].height + (i == grassRectangle.length-1 ? 0 : 2);
				}
			}
		}
}

