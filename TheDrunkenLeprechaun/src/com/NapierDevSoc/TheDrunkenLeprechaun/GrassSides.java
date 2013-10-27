package com.NapierDevSoc.TheDrunkenLeprechaun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GrassSides {
	
	private final int HEIGHT = 480;
	private final int WIDTH = 250;
	
	private Texture[] sideTexture;
	private Rectangle[] sideRectangle;
	
	private String sideType;
	
	public GrassSides(String sideType, float origin){
		
		sideRectangle = new Rectangle[4];
		sideTexture = new Texture[4];

		this.sideType = sideType;
		
		for (int i=0;i<sideRectangle.length;i++)
		{
			sideRectangle[i] = new Rectangle();
			sideRectangle[i].width = WIDTH;
			sideRectangle[i].height = HEIGHT;
			sideRectangle[i].x = origin;
			sideRectangle[i].y = HEIGHT*i;
		}

		for (int i=0; i<sideTexture.length;i++)
		{
			sideTexture[i] = new Texture(Gdx.files.internal("data/"+sideType+i+".png"));
		}
	}
	


	public void draw(SpriteBatch batch) {
		for (int i=0; i < sideRectangle.length; i++) {
        		batch.draw(sideTexture[i],
        				sideRectangle[i].x,
        				sideRectangle[i].y,
        				sideRectangle[i].width,
        				sideRectangle[i].height);
		}
	}
	
	public void animate(float y_offset) {
		float h = Gdx.graphics.getHeight();
		
		for (int i=0; i < sideRectangle.length; i++) {
			
				sideRectangle[i].y += y_offset;
				
				// For walking forward
				if (y_offset < 0 && sideRectangle[i].y + sideRectangle[i].height <= 0) {
					int y_max = 0;
					for (int ii=0; ii <  sideRectangle.length; ii++) {
						if (sideRectangle[y_max].y < sideRectangle[ii].y)
							y_max = ii;
					}
					
					sideRectangle[i].y = sideRectangle[y_max].y + sideRectangle[y_max].height + (i == 0 ? -2 : 0);
				}
				
				// For walking backward
				if (y_offset > 0 && sideRectangle[i].y >= h) {
					int y_min = 0;
					for (int ii=0; ii < sideRectangle.length; ii++) {
						if (sideRectangle[y_min].y > sideRectangle[ii].y)
							y_min = ii;
					}
					
					sideRectangle[i].y = sideRectangle[y_min].y - sideRectangle[i].height + (i == sideRectangle.length-1 ? 0 : 2);
				}
			}
		}
}
