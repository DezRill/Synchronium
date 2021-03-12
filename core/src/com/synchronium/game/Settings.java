package com.synchronium.game;

import java.io.Serializable;

/**
 * Created by DezRill on 30.04.2017.
 */

public class Settings implements Serializable {
    private boolean enablesounds, enablevolume;
    private float volumelevel;

    public void setEnablesounds(boolean value){
        enablesounds=value;
    }
    public void setEnablevolume(boolean value){
        enablevolume=value;
    }
    public void setVolumelevel(float value){
        volumelevel=value;
    }
    public boolean getEnablesounds(){
        return enablesounds;
    }
    public boolean getEnablevolume(){
        return enablevolume;
    }
    public float getVolumelevel(){
        return volumelevel;
    }
}
