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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by DezRill on 22.04.2017.
 */

public class NewProfileScreen implements Screen {
    final Synchronium game;
    Texture text, accept, back, linedit;
    ImageButton imageButton1, imageButton2;
    Stage stage;
    BitmapFont font, empty;
    TextField textField;
    long deltatime;

    public NewProfileScreen(final Synchronium game){
        this.game=game;
        text=new Texture("Textures/enterthename.png");
        accept=new Texture("Textures/acceptbutton.png");
        back=new Texture("Textures/backbutton.png");
        linedit=new Texture("Textures/linedit.png");

        deltatime=-1;

        stage=new Stage();

        font=new BitmapFont();
        font.getData().setScale(3,4);

        imageButton1=new ImageButton(game.imageButtonConverter(accept));
        imageButton2=new ImageButton(game.imageButtonConverter(back));

        imageButton1.setSize(accept.getWidth(),accept.getHeight());
        imageButton2.setSize(back.getWidth(),back.getHeight());

        imageButton1.setPosition(Gdx.graphics.getWidth()/2-accept.getWidth()/2,150);
        imageButton2.setPosition(Gdx.graphics.getWidth()/2-back.getWidth()/2,150-accept.getHeight()-50);

        TextField.TextFieldStyle tfs=new TextField.TextFieldStyle();
        tfs.background=new TextureRegionDrawable(new TextureRegion(linedit));
        tfs.font=font;
        tfs.fontColor=new Color(Color.FOREST);

        textField=new TextField("",tfs);
        textField.setPosition(30,770-text.getHeight()-50);
        textField.setSize(660,80);

        stage.addActor(imageButton1);
        stage.addActor(imageButton2);
        stage.addActor(textField);

        Gdx.input.setInputProcessor(stage);

        imageButton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                if (textField.getText()=="")
                {
                    empty=new BitmapFont();
                    empty.setColor(Color.RED);
                    empty.getData().setScale(2,2);
                    deltatime= TimeUtils.millis();
                }
                else
                {
                    game.profile=new Profile();
                    game.profile.setName(textField.getText());
                    game.profile.setCurrentlevel(-1);
                    game.profile.setTotalcount(-1);
                    for (int i=0;i<5;i++)
                    {
                        game.profile.setLevelcounts(0,i);
                    }

                    if (game.profiles.size()==0) game.activeprofile=game.profile;
                    game.profiles.add(game.profile);
                    if (game.fromchooseprofilescreen){
                        game.setScreen(new ChooseProfileScreen(game));
                        game.fromchooseprofilescreen=false;
                    }
                    else game.setScreen(new ProfilesScreen(game));
                    dispose();
                }
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
                else game.setScreen(new ProfilesScreen(game));
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
        game.batch.draw(text,30,770);
        if (TimeUtils.millis()-deltatime<1000 && deltatime!=-1)
        {
            empty.draw(game.batch,"Enter the name!",Gdx.graphics.getWidth()/2-122,430);
        }
        if (TimeUtils.millis()-deltatime>1000 && deltatime!=-1) empty.dispose();
        game.batch.end();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            if (game.settings.getEnablesounds()) game.press.play();
            if (game.fromchooseprofilescreen)
            {
                game.setScreen(new ChooseProfileScreen(game));
                game.fromchooseprofilescreen=false;
            }
            else game.setScreen(new ProfilesScreen(game));
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
        text.dispose();
        accept.dispose();
        back.dispose();
        linedit.dispose();
        font.dispose();
        stage.dispose();
    }
}
