package com.synchronium.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by DezRill on 01.05.2017.
 */

public class LoseScreen implements Screen {
    final Synchronium game;
    Texture message, fromstart, mainmenu;
    ImageButton imageButton1, imageButton2;
    Stage stage;
    Music lose;
    long deltatime;

    public LoseScreen(final Synchronium game){
        this.game=game;
        message=new Texture("Textures/losemessage.png");
        fromstart=new Texture("Textures/fromstartbutton.png");
        mainmenu=new Texture("Textures/mainmenubutton.png");

        deltatime= TimeUtils.millis();

        if (game.settings.getEnablevolume())
        {
            game.music.pause();
            lose=Gdx.audio.newMusic(Gdx.files.internal("Sounds/losesound.mp3"));
            lose.setVolume(game.settings.getVolumelevel());
            lose.play();
        }


        stage=new Stage();

        imageButton1=new ImageButton(game.imageButtonConverter(fromstart));
        imageButton2=new ImageButton(game.imageButtonConverter(mainmenu));

        imageButton1.setPosition(Gdx.graphics.getWidth()/2-fromstart.getWidth()/2,730-fromstart.getHeight()-50);
        imageButton2.setPosition(Gdx.graphics.getWidth()/2-mainmenu.getWidth()/2,730-fromstart.getHeight()-mainmenu.getHeight()-100);

        stage.addActor(imageButton1);
        stage.addActor(imageButton2);

        Gdx.input.setInputProcessor(stage);

        imageButton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                game.setScreen(new GameScreen(game));
                if (game.settings.getEnablevolume())
                {
                    lose.stop();
                    game.music.play();
                }
                dispose();
            };
        });
        imageButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                game.setScreen(new MainMenuScreen(game));
                if (game.settings.getEnablevolume())
                {
                    lose.stop();
                    game.music.play();
                }
                dispose();
            };
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(game.background,0,0);
        game.batch.draw(message, Gdx.graphics.getWidth()/2-message.getWidth()/2,730);
        game.batch.end();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            if (game.settings.getEnablesounds()) game.press.play();
            game.setScreen(new MainMenuScreen(game));
            if (game.settings.getEnablevolume())
            {
                lose.stop();
                game.music.play();
            }
            dispose();
        }

        if (TimeUtils.millis()-deltatime>4500)
        {
            if (game.settings.getEnablevolume())
            {
                game.music.play();
                lose.dispose();
            }
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
        message.dispose();
        fromstart.dispose();
        mainmenu.dispose();
        stage.dispose();
        if (game.settings.getEnablevolume()) lose.dispose();
    }
}
