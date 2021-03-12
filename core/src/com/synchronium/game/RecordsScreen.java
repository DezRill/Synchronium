package com.synchronium.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

/**
 * Created by DezRill on 22.04.2017.
 */

public class RecordsScreen implements Screen {
    final Synchronium game;
    Texture back;
    ImageButton imageButton;
    BitmapFont font;
    Stage stage;
    Array<Profile> sortprofiles;

    public RecordsScreen(final Synchronium game){
        this.game=game;

        back=new Texture("Textures/backbutton.png");

        font=new BitmapFont();
        font.getData().setScale(4,5);
        font.setColor(new Color(Color.FOREST));

        sortprofiles=new Array<Profile>();

        for (Profile item:game.profiles)
        {
            if (item.getCurrentlevel()==5) sortprofiles.add(item);
        }

        sortprofiles.sort(new Comparator<Profile>() {
            @Override
            public int compare(Profile profile, Profile t1) {
                return profile.getTotalcount()-t1.getTotalcount();
            }
        });


        stage=new Stage();

        imageButton=new ImageButton(game.imageButtonConverter(back));

        imageButton.setSize(back.getWidth(),back.getHeight());

        imageButton.setPosition(Gdx.graphics.getWidth()/2-back.getWidth()/2,50);

        stage.addActor(imageButton);
        Gdx.input.setInputProcessor(stage);

        imageButton.addListener(new ClickListener(){
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
        for(int i=0;i<sortprofiles.size;i++)
        {
            font.draw(game.batch,sortprofiles.get(i).getName(),30, 1200 - (140 * i));
            font.draw(game.batch,String.valueOf(sortprofiles.get(i).getTotalcount()),555,1200-(140*i));
        }
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
        back.dispose();
        font.dispose();
        stage.dispose();
    }
}
