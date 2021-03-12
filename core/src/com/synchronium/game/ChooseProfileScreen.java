package com.synchronium.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by DezRill on 22.04.2017.
 */

public class ChooseProfileScreen implements Screen {
    final Synchronium game;
    Texture accept, newprofile, exit, checkboxon, checkboxoff;
    ImageButton imageButton1, imageButton2, imageButton3;
    BitmapFont font, chooseprofile;
    Array<CheckBox> checkBoxes;
    int check;
    Stage stage;
    int array[];
    long deltatime;

    public ChooseProfileScreen(final Synchronium game){
        this.game=game;

        font=new BitmapFont();
        font.getData().setScale(4,5);
        font.setColor(new Color(Color.FOREST));

        array=new int[game.profiles.size()];
        deltatime=-1;
        check=-1;

        game.camera=new OrthographicCamera();
        game.camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.batch = new SpriteBatch();

        checkBoxes=new Array<CheckBox>();
        accept=new Texture("Textures/acceptbutton.png");
        newprofile=new Texture("Textures/newprofilebutton.png");
        exit=new Texture("Textures/exitbutton.png");
        checkboxon=new Texture("Textures/active.png");
        checkboxoff=new Texture("Textures/checkboxon.png");

        CheckBox.CheckBoxStyle cbs=new CheckBox.CheckBoxStyle();
        cbs.font=new BitmapFont();
        cbs.fontColor=new Color(Color.GOLD);
        cbs.checkboxOn=new TextureRegionDrawable(new TextureRegion(checkboxon));
        cbs.checkboxOff=new TextureRegionDrawable(new TextureRegion(checkboxoff));

        stage=new Stage();
        imageButton1=new ImageButton(game.imageButtonConverter(accept));
        imageButton2=new ImageButton(game.imageButtonConverter(newprofile));
        imageButton3=new ImageButton(game.imageButtonConverter(exit));

        imageButton1.setSize(accept.getWidth(),accept.getHeight());
        imageButton2.setSize(newprofile.getWidth(),newprofile.getHeight());
        imageButton3.setSize(exit.getWidth(), exit.getHeight());

        imageButton1.setPosition(Gdx.graphics.getWidth()/2-accept.getWidth()/2, 300);
        imageButton2.setPosition(Gdx.graphics.getWidth()/2-newprofile.getWidth()/2,300-accept.getHeight()-50);
        imageButton3.setPosition(Gdx.graphics.getWidth()/2-exit.getWidth()/2,300-accept.getHeight()-newprofile.getHeight()-100);

        stage.addActor(imageButton1);
        stage.addActor(imageButton2);
        stage.addActor(imageButton3);

        Gdx.input.setInputProcessor(stage);

        for (int i=0;i<game.profiles.size();i++)
        {
            array[i]=i;
            CheckBox checkBox=new CheckBox("",cbs);
            checkBox.setChecked(false);
            checkBox.setPosition(600,1270-(140*i)-checkboxon.getHeight());
            checkBoxes.add(checkBox);
            stage.addActor(checkBox);
        }

        imageButton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                if (game.activeprofile!=null)
                {
                    game.setScreen(new MainMenuScreen(game));
                    dispose();
                }
                else deltatime= TimeUtils.millis();
            };
        });
        imageButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                game.setScreen(new NewProfileScreen(game));
                game.fromchooseprofilescreen=true;
                dispose();
            };
        });
        imageButton3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                game.setScreen(new ExitScreen(game));
                game.fromchooseprofilescreen=true;
                dispose();
            };
        });
        for (int i=0;i<checkBoxes.size;i++)
        {
            final int finalI = i;
            checkBoxes.get(i).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (game.settings.getEnablesounds()) game.press.play();
                    check=finalI;
                };
            });
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(game.background,0,0);
        for (int i=0;i<game.profiles.size();i++)
        {
            font.draw(game.batch, game.profiles.get(i).getName(), 30, 1250 - (140 * array[i]));
        }
        if (deltatime!=-1 && TimeUtils.millis()-deltatime<1000)
        {
            chooseprofile=new BitmapFont();
            chooseprofile.setColor(new Color(Color.RED));
            chooseprofile.getData().setScale(2,2);
            chooseprofile.draw(game.batch,"Choose profile!",Gdx.graphics.getWidth()/2-100,0+imageButton1.getHeight()+imageButton2.getHeight()+imageButton3.getHeight()+170);
        }
        game.batch.end();
        stage.draw();

        for (int i=0;i<checkBoxes.size;i++)
        {
            if (i!=check) checkBoxes.get(i).setChecked(false);
            else if (check==i) checkBoxes.get(i).setChecked(true);
            if (checkBoxes.get(i).isChecked()) game.activeprofile=game.profiles.get(i);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            if (game.settings.getEnablesounds()) game.press.play();
            game.setScreen(new ExitScreen(game));
            game.fromchooseprofilescreen=true;
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
        accept.dispose();
        newprofile.dispose();
        exit.dispose();
        checkboxon.dispose();
        checkboxoff.dispose();
        font.dispose();
        stage.dispose();
    }
}
