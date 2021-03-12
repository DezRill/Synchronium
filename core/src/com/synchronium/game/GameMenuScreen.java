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
 * Created by DezRill on 22.04.2017.
 */

public class GameMenuScreen implements Screen {
    final Synchronium game;
    Stage stage;
    Texture chooselevel, newgame, back;
    ImageButton imageButton1, imageButton2, imageButton3;

    public GameMenuScreen (final Synchronium game){
        this.game=game;
        stage=new Stage();
        if (game.activeprofile.getCurrentlevel()!=-1) chooselevel=new Texture("Textures/chooselevelonbutton.png");
        else chooselevel=new Texture("Textures/chooseleveloffbutton.png");
        newgame=new Texture("Textures/newgamebutton.png");
        back=new Texture("Textures/backbutton.png");

        imageButton1=new ImageButton(game.imageButtonConverter(chooselevel));
        imageButton2=new ImageButton(game.imageButtonConverter(newgame));
        imageButton3=new ImageButton(game.imageButtonConverter(back));

        imageButton1.setSize(chooselevel.getWidth(),chooselevel.getHeight());
        imageButton2.setSize(newgame.getWidth(),newgame.getHeight());
        imageButton3.setSize(back.getWidth(),back.getHeight());

        imageButton1.setPosition(Gdx.graphics.getWidth()/2-chooselevel.getWidth()/2,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/4-150);
        imageButton2.setPosition(Gdx.graphics.getWidth()/2-newgame.getWidth()/2,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/4-150*2);
        imageButton3.setPosition(Gdx.graphics.getWidth()/2-back.getWidth()/2,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/4-150*3);

        stage.addActor(imageButton1);
        stage.addActor(imageButton2);
        stage.addActor(imageButton3);

        Gdx.input.setInputProcessor(stage);

        imageButton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.activeprofile.getCurrentlevel()!=-1)
                {
                    if (game.settings.getEnablesounds()) game.press.play();
                    game.setScreen(new ChooseLevelScreen(game));
                    dispose();
                }
            };
        });
        imageButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                if (game.activeprofile.getCurrentlevel()!=-1) game.setScreen(new AUSureScreen(game));
                else
                {
                    game.choosenlevel=0;
                    game.activeprofile.setCurrentlevel(game.choosenlevel);
                    game.setScreen(new GameScreen(game));
                }
                dispose();
            };
        });
        imageButton3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
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
        game.batch.end();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            if (game.settings.getEnablesounds()) game.press.play();
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
        chooselevel.dispose();
        newgame.dispose();
        back.dispose();
        stage.dispose();
    }
}
