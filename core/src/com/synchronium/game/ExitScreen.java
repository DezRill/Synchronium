package com.synchronium.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by DezRill on 22.04.2017.
 */

public class ExitScreen implements Screen {
    final Synchronium game;
    Texture yes,no,question;
    ImageButton imageButton1, imageButton2;
    Stage stage;

    public ExitScreen (final Synchronium game){
        this.game=game;
        yes=new Texture("Textures/yesbutton.png");
        no=new Texture("Textures/nobutton.png");
        question=new Texture("Textures/question.png");
        stage=new Stage();
        imageButton1=new ImageButton(game.imageButtonConverter(yes));
        imageButton2=new ImageButton(game.imageButtonConverter(no));

        imageButton1.setSize(yes.getWidth(),yes.getHeight());
        imageButton2.setSize(no.getWidth(),no.getHeight());

        imageButton1.setPosition(Gdx.graphics.getWidth()/3-yes.getWidth()/2,650-question.getHeight());
        imageButton2.setPosition(Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/3-no.getWidth()/2,650-question.getHeight());

        stage.addActor(imageButton1);
        stage.addActor(imageButton2);

        Gdx.input.setInputProcessor(stage);

        imageButton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.background.dispose();
                game.press.dispose();
                game.music.dispose();

                try
                {
                    FileOutputStream fos=new FileOutputStream(new File("data/data/com.synchronium.game/files/settings"));
                    ObjectOutputStream os=new ObjectOutputStream(fos);
                    os.writeObject(game.settings);
                    os.close();
                    fos.close();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                try
                {
                    FileOutputStream fos=new FileOutputStream(new File("data/data/com.synchronium.game/files/profiles"));
                    ObjectOutputStream os=new ObjectOutputStream(fos);
                    os.writeObject(game.profiles);
                    os.close();
                    fos.close();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                Gdx.app.exit();
                dispose();
            };
        });
        imageButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                if (game.fromchooseprofilescreen){
                    game.setScreen(new ChooseProfileScreen(game));
                    game.fromchooseprofilescreen=false;
                }
                else game.setScreen(new MainMenuScreen(game));
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
        game.batch.draw(question,Gdx.graphics.getWidth()/2-question.getWidth()/2,650);
        game.batch.end();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK))
        {
            if (game.settings.getEnablesounds()) game.press.play();
            if (game.fromchooseprofilescreen)
            {
                game.setScreen(new ChooseProfileScreen(game));
                game.fromchooseprofilescreen=false;
            }
            else game.setScreen(new MainMenuScreen(game));
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
        question.dispose();
        stage.dispose();
    }
}
