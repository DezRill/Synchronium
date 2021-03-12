package com.synchronium.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Synchronium extends Game {
	SpriteBatch batch;
	Texture background;
	OrthographicCamera camera;
	Sound press;
	Music music;
	Settings settings;
	Profile profile, activeprofile;
	boolean fromchooseprofilescreen, fromprofilescreen, agree;
	ArrayList<Profile> profiles;
	ArrayList<int[][]> levels;
	int deletenum, choosenlevel;

	@Override
	public void create () {
		settings=new Settings();
		activeprofile=new Profile();
		profiles=new ArrayList<Profile>();
		levels=new ArrayList<int[][]>();
		background=new Texture("Textures/background.png");
		press=Gdx.audio.newSound(Gdx.files.internal("Sounds/press.mp3"));
		music=Gdx.audio.newMusic(Gdx.files.internal("Sounds/music.mp3"));

		try
		{
			FileInputStream fis=new FileInputStream("data/data/com.synchronium.game/files/settings");
			ObjectInputStream is=new ObjectInputStream(fis);
			settings=(Settings)is.readObject();
			fis.close();
			is.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		try
		{
			FileInputStream fis=new FileInputStream("data/data/com.synchronium.game/files/profiles");
			ObjectInputStream is=new ObjectInputStream(fis);
			profiles=(ArrayList<Profile>) is.readObject();
			fis.close();
			is.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		if (settings.getEnablevolume())
		{
			music.setVolume(settings.getVolumelevel());
			music.setLooping(true);
			music.play();
		}

		activeprofile=null;
		fromchooseprofilescreen=false;
		fromprofilescreen=false;
		agree=false;
		choosenlevel=-1;

		Gdx.input.setCatchBackKey(true);

		this.setScreen(new ChooseProfileScreen(this));
	}

	public TextureRegionDrawable imageButtonConverter(Texture img){
		TextureRegion img1=new TextureRegion(img);
		TextureRegionDrawable img2=new TextureRegionDrawable(img1);
		return img2;
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
