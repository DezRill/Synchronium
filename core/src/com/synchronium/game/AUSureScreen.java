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
 * Created by DezRill on 01.05.2017.
 */

public class AUSureScreen implements Screen {
    final Synchronium game;
    Texture yes, no, ausure;
    ImageButton imageButton1, imageButton2;
    Stage stage;

    public AUSureScreen(final Synchronium game){
        this.game=game;
        yes=new Texture("Textures/yesbutton.png");
        no=new Texture("Textures/nobutton.png");
        ausure=new Texture("Textures/ausure.png");

        stage=new Stage();

        imageButton1=new ImageButton(game.imageButtonConverter(yes));
        imageButton2=new ImageButton(game.imageButtonConverter(no));

        imageButton1.setPosition(Gdx.graphics.getWidth()/3-yes.getWidth()/2,650-ausure.getHeight());
        imageButton2.setPosition(Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/3-no.getWidth()/2,650-ausure.getHeight());

        stage.addActor(imageButton1);
        stage.addActor(imageButton2);

        Gdx.input.setInputProcessor(stage);

        imageButton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                game.agree=true;
                if (game.fromprofilescreen)
                {
                    game.setScreen(new ProfilesScreen(game));
                    game.fromprofilescreen=false;
                }
                else
                {
                    game.activeprofile.setCurrentlevel(-1);
                    for (int i=0;i<5;i++)
                    {
                        game.activeprofile.setLevelcounts(0,i);
                    }
                    for (int i=0;i<game.profiles.size();i++)
                    {
                        if (game.profiles.get(i).getName().trim().equals(game.activeprofile.getName().trim())) game.profiles.set(i,game.activeprofile);
                    }
                    game.choosenlevel=0;
                    game.setScreen(new GameScreen(game));
                    game.agree=false;
                }
                dispose();
            };
        });
        imageButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                if (game.fromprofilescreen)
                {
                    game.setScreen(new ProfilesScreen(game));
                    game.fromprofilescreen=false;
                }
                else game.setScreen(new GameMenuScreen(game));
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
        game.batch.draw(ausure, Gdx.graphics.getWidth()/2-ausure.getWidth()/2,650);
        game.batch.end();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            if (game.settings.getEnablesounds()) game.press.play();
            if (game.fromprofilescreen)
            {
                game.setScreen(new ProfilesScreen(game));
                game.fromprofilescreen=false;
            }
            else game.setScreen(new GameMenuScreen(game));
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
        yes.dispose();
        no.dispose();
        ausure.dispose();
        stage.dispose();
    }
}
