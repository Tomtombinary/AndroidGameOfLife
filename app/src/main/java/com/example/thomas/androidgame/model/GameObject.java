package com.example.thomas.androidgame.model;

import android.graphics.Canvas;

/**
 * Created by thomas on 08/02/16.
 */
public abstract  class GameObject {

    public static final int STATE_ALIVE=0,STATE_DEAD=1;
    protected int state;

    protected float x,y,vx,vy,ax,ay;

    public GameObject(float x,float y){
        this.x = x;
        this.y = y;
        this.state = STATE_ALIVE;
    }

    public void update(){
        this.vx+= ax;
        this.vy+= ay;
        this.x += vx;
        this.y += vy;
    }

    public int getState(){
        return this.state;
    }

    public abstract void draw(Canvas canvas);


}
