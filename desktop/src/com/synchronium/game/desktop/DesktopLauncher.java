package com.synchronium.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.synchronium.game.Synchronium;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="Synchronium";
		config.height=1280;
		config.width=720;
		new LwjglApplication(new Synchronium(), config);
	}
}
