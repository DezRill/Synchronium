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
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by DezRill on 22.04.2017.
 */

public class OptionsScreen implements Screen {
    final Synchronium game;
    Texture back, volume, line, knob, checkboxon, checkboxoff, enablesoundstexture, enablevolumetexture;
    Stage stage;
    ImageButton imageButton;
    Slider vslider;
    CheckBox enablevolume, enablesounds;

    public OptionsScreen(final Synchronium game){
        this.game=game;
        back=new Texture("Textures/backbutton.png");
        volume=new Texture("Textures/volume.png");
        line=new Texture("Textures/line.png");
        knob=new Texture("Textures/point.png");
        checkboxon=new Texture("Textures/checkboxon.png");
        checkboxoff=new Texture("Textures/checkboxoff.png");
        enablesoundstexture=new Texture("Textures/enablesounds.png");
        enablevolumetexture=new Texture("Textures/enablevolume.png");
        stage=new Stage();

        Slider.SliderStyle ss=new Slider.SliderStyle();
        ss.background=new TextureRegionDrawable(new TextureRegion(line));
        ss.knob=new TextureRegionDrawable(new TextureRegion(knob));

        vslider=new Slider((float)0,(float)1,(float)0.001,false,ss);

        imageButton=new ImageButton(game.imageButtonConverter(back));

        imageButton.setSize(back.getWidth(),back.getHeight());
        imageButton.setPosition(Gdx.graphics.getWidth()/2-back.getWidth()/2,50);

        vslider.setPosition(30,850-volume.getHeight());
        vslider.setSize(660,42);
        vslider.setValue(game.settings.getVolumelevel());

        CheckBox.CheckBoxStyle cbs=new CheckBox.CheckBoxStyle();
        cbs.font=new BitmapFont();
        cbs.fontColor=new Color(Color.GOLD);
        cbs.checkboxOn=new TextureRegionDrawable(new TextureRegion(checkboxon));
        cbs.checkboxOff=new TextureRegionDrawable(new TextureRegion(checkboxoff));

        enablevolume=new CheckBox("",cbs);
        enablevolume.setChecked(game.settings.getEnablevolume());
        enablevolume.setPosition(564,638);

        enablesounds=new CheckBox("",cbs);
        enablesounds.setChecked(game.settings.getEnablesounds());
        enablesounds.setPosition(564,638-enablevolume.getHeight()-43);

        stage.addActor(imageButton);
        stage.addActor(vslider);
        stage.addActor(enablevolume);
        stage.addActor(enablesounds);

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
        game.batch.draw(volume,30,850);
        game.batch.draw(enablevolumetexture,30,850-volume.getHeight()-vslider.getHeight()-80);
        game.batch.draw(enablesoundstexture,30,850-volume.getHeight()-vslider.getHeight()-enablevolumetexture.getHeight()-80*2);
        game.batch.end();

        stage.draw();

        if (vslider.isDragging())
        {
            game.music.setVolume(vslider.getValue());
            game.settings.setVolumelevel(vslider.getValue());
        }

        if (!enablevolume.isChecked())
        {
            game.music.stop();
            game.music.setLooping(false);
            game.settings.setEnablevolume(false);
        }
        else
        {
            game.music.play();
            game.music.setVolume(vslider.getValue());
            game.music.setLooping(true);
            game.settings.setEnablevolume(true);
        }
        if (!enablesounds.isChecked())
        {
            game.settings.setEnablesounds(false);
        }
        else
        {
            game.settings.setEnablesounds(true);
        }

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
        volume.dispose();
        line.dispose();
        knob.dispose();
        checkboxon.dispose();
        checkboxoff.dispose();
        enablesoundstexture.dispose();
        enablevolumetexture.dispose();
        stage.dispose();
    }
}
