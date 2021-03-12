package com.synchronium.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * Created by DezRill on 23.04.2017.
 */

public class GameScreen implements Screen {
    final Synchronium game;
    Texture mainpoint, wall, arrowup, arrowdown, arrowleft, arrowright, exittexture;
    ImageButton imageButton1, imageButton2, imageButton3, imageButton4;
    Actor mainpoints, walls, exit;
    Stage stage;
    BitmapFont font, moves;
    Array<Vector3> touchPos, prevTouchPos;
    Array<Actor> mp, w;
    Array<Rectangle> _mp, _w;
    Rectangle exitcollision;
    long deltatime;
    int movecounter, xstart, ystart;
    int array[][];

    public GameScreen(final Synchronium game){
        this.game=game;

        Levels l=new Levels();

        array=l.getLevel(game.choosenlevel);

        deltatime=-1;
        movecounter=0;
        xstart=0;
        ystart=1280-72;

        moves=new BitmapFont();
        moves.setColor(new Color(Color.GOLD));
        moves.getData().setScale(6,6);

        mainpoint=new Texture("Textures/mainpoint.png");
        wall=new Texture("Textures/wall.png");
        arrowup=new Texture("Textures/arrowu.png");
        arrowdown=new Texture("Textures/arrowd.png");
        arrowleft=new Texture("Textures/arrowl.png");
        arrowright=new Texture("Textures/arrowr.png");
        exittexture=new Texture("Textures/exit.png");

        imageButton1=new ImageButton(game.imageButtonConverter(arrowup));
        imageButton2=new ImageButton(game.imageButtonConverter(arrowdown));
        imageButton3=new ImageButton(game.imageButtonConverter(arrowleft));
        imageButton4=new ImageButton(game.imageButtonConverter(arrowright));

        imageButton1.setPosition(720-144+5-arrowup.getWidth(), 144*2+10);
        imageButton2.setPosition(720-144+5-arrowdown.getWidth(),10);
        imageButton3.setPosition(720-144*2+10-arrowleft.getWidth(),144);
        imageButton4.setPosition(720-5-arrowright.getWidth(),144);

        imageButton1.setSize(arrowup.getWidth(),arrowup.getHeight());
        imageButton2.setSize(arrowdown.getWidth(),arrowdown.getHeight());
        imageButton3.setSize(arrowleft.getWidth(),arrowleft.getHeight());
        imageButton4.setSize(arrowright.getWidth(),arrowright.getHeight());

        stage=new Stage();

        touchPos=new Array<Vector3>();
        prevTouchPos=new Array<Vector3>();
        mp=new Array<Actor>();
        w=new Array<Actor>();
        _mp=new Array<Rectangle>();
        _w=new Array<Rectangle>();

        for (int i=0;i<10;i++)
        {
            for (int j=0;j<10;j++)
            {
                switch (array[i][j])
                {
                    case 0: break;
                    case 1:
                    {
                        Rectangle _wt=new Rectangle();
                        walls=new Image(wall);
                        walls.setPosition(xstart,ystart);
                        _wt.set(walls.getX(),walls.getY(),wall.getWidth(),wall.getHeight());
                        _w.add(_wt);
                        w.add(walls);
                    }break;
                    case 2:
                    {
                        mainpoints=new Image(mainpoint);
                        mainpoints.setPosition(xstart,ystart);
                        mainpoints.setSize(mainpoint.getWidth(),mainpoint.getHeight());
                        Rectangle _mpt=new Rectangle();
                        _mpt.set(mainpoints.getX(),mainpoints.getY(),mainpoint.getWidth(),mainpoint.getHeight());
                        _mp.add(_mpt);
                        mp.add(mainpoints);
                        stage.addActor(mainpoints);
                    }break;
                    case 3:
                    {
                        exit=new Image(exittexture);
                        exit.setPosition(xstart,ystart);
                        exit.setSize(exittexture.getWidth(),exittexture.getHeight());
                        exitcollision=new Rectangle();
                        exitcollision.set(exit.getX(),exit.getY(),exittexture.getWidth(), exittexture.getHeight());
                        stage.addActor(exit);
                    }break;
                }
                xstart+=72;
            }
            ystart-=72;
            xstart=0;
        }

        for(Actor item: w)
        {
            stage.addActor(item);
        }
        stage.addActor(imageButton1);
        stage.addActor(imageButton2);
        stage.addActor(imageButton3);
        stage.addActor(imageButton4);

        Gdx.input.setInputProcessor(stage);

        for (Rectangle item:_mp)
        {
            Vector3 temp=new Vector3();
            temp.set(item.getX(),item.getY(),0);
            touchPos.add(temp);
            prevTouchPos.add(temp);
        }

        imageButton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                for (int i=0;i<mp.size;i++)
                {
                    Vector3 temp=new Vector3();
                    temp.set(touchPos.get(i).x, touchPos.get(i).y,0);
                    prevTouchPos.set(i,temp);
                    touchPos.get(i).y+=mainpoint.getHeight();
                }
                movecounter++;
            };
        });
        imageButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                for (int i=0;i<mp.size;i++)
                {
                    Vector3 temp=new Vector3();
                    temp.set(touchPos.get(i).x, touchPos.get(i).y,0);
                    prevTouchPos.set(i,temp);
                    touchPos.get(i).y-=mainpoint.getHeight();
                }
                movecounter++;
            };
        });
        imageButton3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                for (int i=0;i<mp.size;i++)
                {
                    Vector3 temp=new Vector3();
                    temp.set(touchPos.get(i).x, touchPos.get(i).y,0);
                    prevTouchPos.set(i,temp);
                    touchPos.get(i).x-=mainpoint.getWidth();
                }
                movecounter++;
            };
        });
        imageButton4.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.settings.getEnablesounds()) game.press.play();
                for (int i=0;i<mp.size;i++)
                {
                    Vector3 temp=new Vector3();
                    temp.set(touchPos.get(i).x, touchPos.get(i).y,0);
                    prevTouchPos.set(i,temp);
                    touchPos.get(i).x+=mainpoint.getWidth();
                }
                movecounter++;
            };
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.camera.update();
        game.batch.begin();
        game.batch.draw(game.background, 0, 0);
        moves.draw(game.batch,""+movecounter,60,500);
        game.batch.end();
        stage.draw();

        for (int i=0;i<mp.size;i++)
        {
            mp.get(i).setPosition(touchPos.get(i).x,touchPos.get(i).y);
            _mp.get(i).setPosition(touchPos.get(i).x,touchPos.get(i).y);
        }

        for (int i=0;i<mp.size;i++) {
            Iterator<Rectangle> it = _w.iterator();
            while (it.hasNext()) {
                Rectangle temp = it.next();
                if (temp.overlaps(_mp.get(i))) {
                    touchPos.get(i).x = prevTouchPos.get(i).x;
                    touchPos.get(i).y = prevTouchPos.get(i).y;
                }
            }
            if (_mp.get(i).overlaps(exitcollision))
            {
                mp.get(i).remove();
                _mp.removeIndex(i);
                mp.removeIndex(i);
                touchPos.removeIndex(i);
                prevTouchPos.removeIndex(i);
            }
        }

        for (int i=0;i<mp.size;i++)
        {
            for (int j=0;j<mp.size;j++)
            {
                if (j!=i && _mp.get(i).overlaps(_mp.get(j))) game.setScreen(new LoseScreen(game));
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            if (game.settings.getEnablesounds()) game.press.play();
            if (deltatime!=-1)
            {
                if (TimeUtils.millis()-deltatime<1000)
                {
                    game.setScreen(new GameMenuScreen(game));
                    dispose();
                }
            }
            deltatime=TimeUtils.millis();
        }
        if (deltatime!=-1)
        {
            if (TimeUtils.millis()-deltatime>1000) font.dispose();
            else
            {
                font=new BitmapFont();
                font.setColor(new Color(Color.RED));
                font.getData().setScale(2,2);
                game.batch.begin();
                font.draw(game.batch,"Press again to exit",Gdx.graphics.getWidth()/2-122,530);
                game.batch.end();
            }
        }

        if (mp.size==0)
        {
            if (game.activeprofile.getLevelcounts(game.choosenlevel)<movecounter) game.activeprofile.setLevelcounts(movecounter,game.choosenlevel);
            game.choosenlevel++;
            if (game.choosenlevel<5)
            {
                game.setScreen(new WinScreen(game));
                dispose();
            }
            else
            {
                game.setScreen(new GameOverScreen(game));
                game.activeprofile.setCurrentlevel(5);
                dispose();
            }
            if (game.activeprofile.getCurrentlevel()<game.choosenlevel)
            {
                game.activeprofile.setCurrentlevel(game.choosenlevel);
                for (int i=0;i<game.profiles.size();i++)
                {
                    if (game.profiles.get(i).getName().trim().equals(game.activeprofile.getName().trim())) game.profiles.get(i).setCurrentlevel(game.activeprofile.getCurrentlevel());
                }
            }
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
        mainpoint.dispose();
        wall.dispose();
        arrowup.dispose();
        arrowdown.dispose();
        arrowleft.dispose();
        arrowright.dispose();
        exittexture.dispose();
        moves.dispose();
    }
}
