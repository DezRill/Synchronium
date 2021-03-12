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
 * Created by lenovo on 03.05.2017.
 */

public class ChooseLevelScreen implements Screen {
    final Synchronium game;
    Texture accept, back, checkboxon, checkboxoff;
    ImageButton imageButton1, imageButton2;
    BitmapFont font, chooselevel;
    Array<CheckBox> checkBoxes;
    int check;
    long deltatime;
    Levels l;
    Stage stage;

    public ChooseLevelScreen(final Synchronium game)
    {
        this.game=game;

        font=new BitmapFont();
        font.getData().setScale(4,5);
        font.setColor(new Color(Color.FOREST));

        l=new Levels();
        checkBoxes=new Array<CheckBox>();

        deltatime=-1;
        check=-1;
        game.choosenlevel=-1;

        accept=new Texture("Textures/acceptbutton.png");
        back=new Texture("Textures/backbutton.png");
        checkboxon=new Texture("Textures/active.png");
        checkboxoff=new Texture("Textures/checkboxon.png");

        CheckBox.CheckBoxStyle cbs=new CheckBox.CheckBoxStyle();
        cbs.font=new BitmapFont();
        cbs.fontColor=new Color(Color.GOLD);
        cbs.checkboxOn=new TextureRegionDrawable(new TextureRegion(checkboxon));
        cbs.checkboxOff=new TextureRegionDrawable(new TextureRegion(checkboxoff));

        imageButton1=new ImageButton(game.imageButtonConverter(accept));
        imageButton2=new ImageButton(game.imageButtonConverter(back));

        imageButton1.setPosition(Gdx.graphics.getWidth()/2-accept.getWidth()/2,190);
        imageButton2.setPosition(Gdx.graphics.getWidth()/2-back.getWidth()/2,50);

        imageButton1.setSize(accept.getWidth(),accept.getHeight());
        imageButton2.setSize(back.getWidth(),back.getHeight());

        stage=new Stage();

        stage.addActor(imageButton1);
        stage.addActor(imageButton2);

        Gdx.input.setInputProcessor(stage);

        for (int i=0;i<l.getArray().size();i++)
        {
            if (i<=game.activeprofile.getCurrentlevel())
            {
                CheckBox checkBox=new CheckBox("",cbs);
                checkBox.setChecked(false);
                checkBox.setPosition(600,1270-(140*i)-checkboxon.getHeight());
                checkBoxes.add(checkBox);
                stage.addActor(checkBox);
            }
        }

        imageButton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                if (game.choosenlevel!=-1)
                {
                    game.setScreen(new GameScreen(game));
                    dispose();
                }
                else deltatime= TimeUtils.millis();
            };
        });
        imageButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                game.setScreen(new GameMenuScreen(game));
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
        for (int i=0;i<checkBoxes.size;i++)
        {
            font.draw(game.batch, "Level "+(i+1), 30, 1250 - (140 * i));
        }
        if (deltatime!=-1 && TimeUtils.millis()-deltatime<1000)
        {
            chooselevel=new BitmapFont();
            chooselevel.setColor(new Color(Color.RED));
            chooselevel.getData().setScale(2,2);
            chooselevel.draw(game.batch,"Choose level!",Gdx.graphics.getWidth()/2-100,0+imageButton1.getHeight()+imageButton2.getHeight());
        }
        game.batch.end();
        stage.draw();

        for (int i=0;i<checkBoxes.size;i++)
        {
            if (i!=check) checkBoxes.get(i).setChecked(false);
            else if (check==i) checkBoxes.get(i).setChecked(true);
            if (checkBoxes.get(i).isChecked()) game.choosenlevel=i;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            if (game.settings.getEnablesounds()) game.press.play();
            game.setScreen(new GameMenuScreen(game));
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
        back.dispose();
        checkboxon.dispose();
        checkboxoff.dispose();
        stage.dispose();
    }
}
