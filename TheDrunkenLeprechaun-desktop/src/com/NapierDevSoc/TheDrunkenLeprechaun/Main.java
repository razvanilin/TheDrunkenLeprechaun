package com.NapierDevSoc.TheDrunkenLeprechaun;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "The Drunken Leprechaun";
		cfg.useGL20 = false;
		cfg.width = 820;
		cfg.height = 480;
		
		new LwjglApplication(new DrunkenLeprechaun(), cfg);
	}
}
