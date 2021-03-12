package com.synchronium.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by lenovo on 25.03.2017.
 */

public class MainMenuScreen implements Screen{
    final Synchronium game;
    Stage stage;
    Texture gamebutton, profilesbutton, recordsbutton, exitbutton, optionsbutton, logo;
    ImageButton imageButton1, imageButton2, imageButton3, imageButton4, imageButton5;

    public MainMenuScreen(final Synchronium game) {
        this.game=game;
        gamebutton=new Texture("Textures/gamebutton.png");
        profilesbutton=new Texture("Textures/profilesbutton.png");
        recordsbutton=new Texture("Textures/recordsbutton.png");
        exitbutton=new Texture("Textures/exitbutton.png");
        optionsbutton=new Texture("Textures/optionsbutton.png");
        logo=new Texture("Textures/logo.png");
        stage=new Stage();

        imageButton1=new ImageButton(game.imageButtonConverter(gamebutton));
        imageButton2=new ImageButton(game.imageButtonConverter(profilesbutton));
        imageButton3=new ImageButton(game.imageButtonConverter(recordsbutton));
        imageButton4=new ImageButton(game.imageButtonConverter(optionsbutton));
        imageButton5=new ImageButton(game.imageButtonConverter(exitbutton));


        imageButton1.setSize(gamebutton.getWidth(),gamebutton.getHeight());
        imageButton2.setSize(profilesbutton.getWidth(),profilesbutton.getHeight());
        imageButton3.setSize(recordsbutton.getWidth(),recordsbutton.getHeight());
        imageButton4.setSize(optionsbutton.getWidth(),optionsbutton.getHeight());
        imageButton5.setSize(exitbutton.getWidth(),exitbutton.getHeight());

        imageButton1.setPosition(Gdx.graphics.getWidth()/2-gamebutton.getWidth()/2,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/4-130-50);
        imageButton2.setPosition(Gdx.graphics.getWidth()/2-profilesbutton.getWidth()/2,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/4-130*2-50);
        imageButton3.setPosition(Gdx.graphics.getWidth()/2-recordsbutton.getWidth()/2,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/4-130*3-50);
        imageButton4.setPosition(Gdx.graphics.getWidth()/2-optionsbutton.getWidth()/2,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/4-130*4-50);
        imageButton5.setPosition(Gdx.graphics.getWidth()/2-exitbutton.getWidth()/2,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/4-130*5-50);

        stage.addActor(imageButton1);
        stage.addActor(imageButton2);
        stage.addActor(imageButton3);
        stage.addActor(imageButton4);
        stage.addActor(imageButton5);

        Gdx.input.setInputProcessor(stage);

        imageButton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                game.setScreen(new GameMenuScreen(game));
                dispose();
            };
        });
        imageButton2.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                game.setScreen(new ProfilesScreen(game));
                dispose();
            };
        });
        imageButton3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                game.setScreen(new RecordsScreen(game));
                dispose();
            };
        });
        imageButton4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                game.setScreen(new OptionsScreen(game));
                dispose();
            };
        });
        imageButton5.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                game.setScreen(new ExitScreen(game));
                dispose();
            };
        });

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.setProjectionMatrix(game.camera.combined);
        game.camera.update();
        game.batch.begin();
        game.batch.draw(game.background,0,0);
        game.batch.draw(logo,Gdx.graphics.getWidth()/2-logo.getWidth()/2,950);
        game.batch.end();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            if (game.settings.getEnablesounds()) game.press.play();
            game.setScreen(new ExitScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gamebutton.dispose();
        recordsbutton.dispose();
        profilesbutton.dispose();
        optionsbutton.dispose();
        exitbutton.dispose();
        stage.dispose();
        logo.dispose();
    }
}
