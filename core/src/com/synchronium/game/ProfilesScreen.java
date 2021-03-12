package com.synchronium.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
 * Created by lenovo on 25.03.2017.
 */

public class ProfilesScreen implements Screen {
    final Synchronium game;
    Stage stage;
    Texture back, checkboxon, checkboxoff, delete, newprofile;
    ImageButton imageButton1, imageButton2, deletebutton;
    CheckBox checkBox;
    BitmapFont font;
    int check;
    Array<ImageButton> deletebuttons;
    Array<CheckBox> checkBoxes;
    int array[];
    long deltatime;

    public ProfilesScreen(final Synchronium game) {
        this.game = game;
        back = new Texture("Textures/backbutton.png");
        checkboxon=new Texture("Textures/active.png");
        checkboxoff=new Texture("Textures/checkboxon.png");
        delete=new Texture("Textures/delete.png");
        newprofile=new Texture("Textures/newprofilebutton.png");

        imageButton1 = new ImageButton(game.imageButtonConverter(back));
        imageButton2=new ImageButton(game.imageButtonConverter(newprofile));

        checkBoxes=new Array<CheckBox>();
        deletebuttons=new Array<ImageButton>();

        check=-1;
        deltatime=-1;

        font=new BitmapFont();
        font.getData().setScale(4,5);
        font.setColor(new Color(Color.FOREST));

        stage=new Stage();

        array=new int[game.profiles.size()];

        imageButton1.setSize(back.getWidth(),back.getHeight());
        imageButton2.setSize(newprofile.getWidth(), newprofile.getHeight());

        imageButton1.setPosition(Gdx.graphics.getWidth()/2-back.getWidth()/2,50);
        imageButton2.setPosition(Gdx.graphics.getWidth()/2-newprofile.getWidth()/2,70+back.getHeight());

        stage.addActor(imageButton1);
        stage.addActor(imageButton2);

        Gdx.input.setInputProcessor(stage);

        CheckBox.CheckBoxStyle cbs=new CheckBox.CheckBoxStyle();
        cbs.font=new BitmapFont();
        cbs.fontColor=new Color(Color.GOLD);
        cbs.checkboxOn=new TextureRegionDrawable(new TextureRegion(checkboxon));
        cbs.checkboxOff=new TextureRegionDrawable(new TextureRegion(checkboxoff));

        if (game.agree)
        {
            if (game.profiles.get(game.deletenum)==game.activeprofile) game.activeprofile=game.profiles.get(0);
            game.profiles.remove(game.deletenum);
            game.agree=false;
        }

        for (int i=0;i<game.profiles.size();i++)
        {
            array[i]=i;
            checkBox=new CheckBox("",cbs);
            checkBox.setChecked(false);
            checkBox.setPosition(480,1270-(140*i)-checkboxon.getHeight());
            checkBoxes.add(checkBox);
            stage.addActor(checkBox);

            deletebutton=new ImageButton(game.imageButtonConverter(delete));
            deletebutton.setPosition(600, 1270-(140*i)-delete.getHeight());
            deletebutton.setSize(delete.getWidth(),delete.getHeight());
            deletebuttons.add(deletebutton);
            stage.addActor(deletebutton);
        }

        for (int i=0;i<game.profiles.size();i++)
        {
            if (game.profiles.get(i)==game.activeprofile)
            {
                checkBoxes.get(i).setChecked(true);
                check=i;
            }
        }

        imageButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                if (!control())
                {
                    game.setScreen(new MainMenuScreen(game));
                    dispose();
                }
                else deltatime=TimeUtils.millis();
            }
        });
        imageButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    if (game.settings.getEnablesounds()) game.press.play();
                    game.setScreen(new NewProfileScreen(game));
                    dispose();
            }
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
        for (int i=0;i<deletebuttons.size;i++)
        {
            final int finalI = i;
            deletebuttons.get(i).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (game.settings.getEnablesounds()) game.press.play();
                    game.fromprofilescreen=true;
                    game.setScreen(new AUSureScreen(game));
                    game.deletenum=finalI;
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

        if (control() && deltatime!=-1 && TimeUtils.millis()-deltatime<1000)
        {
            BitmapFont checknotnull=new BitmapFont();
            checknotnull.setColor(new Color(Color.RED));
            checknotnull.getData().setScale(2,2);
            checknotnull.draw(game.batch,"Create profile!",Gdx.graphics.getWidth()/2-100,0+imageButton1.getHeight()+imageButton2.getHeight()+130);
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
            if (!control())
            {
                if (game.settings.getEnablesounds()) game.press.play();
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
            else deltatime=TimeUtils.millis();
        }
    }

    private boolean control(){
        if (game.profiles.size()==0) return true;
        else return false;
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
        checkboxon.dispose();
        checkboxoff.dispose();
        delete.dispose();
        newprofile.dispose();
        font.dispose();
        stage.dispose();
    }
}
