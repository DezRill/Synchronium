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
 * Created by DezRill on 03.05.2017.
 */

public class GameOverScreen implements Screen {
    final Synchronium game;
    Texture totalwin, mainmenubutton;
    ImageButton imageButton;
    Music win;
    long deltatime;
    Stage stage;

    public GameOverScreen(final Synchronium game){
        this.game=game;

        deltatime= TimeUtils.millis();

        totalwin=new Texture("Textures/gameovermessage.png");
        mainmenubutton=new Texture("Textures/mainmenubutton.png");

        game.choosenlevel=-1;

        if (game.settings.getEnablevolume())
        {
            game.music.pause();
            win=Gdx.audio.newMusic(Gdx.files.internal("Sounds/winsound.mp3"));
            win.setVolume(game.settings.getVolumelevel());
            win.play();
        }

        int sum=0;
        for (int i=0;i<5;i++)
        {
            sum+=game.activeprofile.getLevelcounts(i);
        }
        game.activeprofile.setTotalcount(sum);
        for (int i=0;i<game.profiles.size();i++)
        {
            if (game.profiles.get(i).getName().trim().equals(game.activeprofile.getName().trim())) game.profiles.set(i,game.activeprofile);
        }

        imageButton=new ImageButton(game.imageButtonConverter(mainmenubutton));

        imageButton.setPosition(Gdx.graphics.getWidth()/2-mainmenubutton.getWidth()/2,550);

        stage=new Stage();

        stage.addActor(imageButton);

        Gdx.input.setInputProcessor(stage);

        imageButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                if (game.settings.getEnablevolume())
                {
                    win.stop();
                    game.music.play();
                }
                game.setScreen(new MainMenuScreen(game));
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
        game.batch.draw(totalwin,Gdx.graphics.getWidth()/2-totalwin.getWidth()/2,750);
        game.batch.end();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            if (game.settings.getEnablesounds()) game.press.play();
            if (game.settings.getEnablevolume())
            {
                win.stop();
                game.music.play();
            }
            game.setScreen(new MainMenuScreen(game));
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
        totalwin.dispose();
        mainmenubutton.dispose();
        stage.dispose();
    }
}
