package com.synchronium.game;

import java.io.Serializable;

/**
 * Created by DezRill on 22.04.2017.
 */

public class Profile implements Serializable, Comparable{
    private String name;
    private int totalcount, currentlevel;
    private int levelcounts[]=new int[5];

    public void setName(String value){
        name=value;
    }
    public void setTotalcount(int value){
        totalcount=value;
    }
    public void setCurrentlevel(int value){
        currentlevel=value;
    }
    public void setLevelcounts(int value, int levelnum) {levelcounts[levelnum]=value;}
    public String getName(){
        return name;
    }
    public int getTotalcount(){
        return totalcount;
    }
    public int getCurrentlevel(){
        return currentlevel;
    }
    public int getLevelcounts(int levelnum){return levelcounts[levelnum];}

    @Override
    public int compareTo(Object p) {
        int compareTotalcount=((Profile)p).totalcount;
        return (this.totalcount-compareTotalcount);
    }
}
