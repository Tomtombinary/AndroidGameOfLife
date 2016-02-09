package com.example.thomas.androidgame.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

/**
 * Created by thomas on 08/02/16.
 */
public class Particle extends GameObject{

    public static int LIFE_TIME=200;
    private Paint p;
    private int age;
    private float radius;

    public Particle(float x,float y){
        super(x,y);
        this.p = new Paint();
        Random rnd = new Random();
        this.p.setColor(Color.argb(255,rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255)));
        float v= rnd.nextFloat()*10.0f;
        this.vx = (float)Math.cos(rnd.nextDouble() * 2.0 * Math.PI) * v;
        this.vy = (float)Math.sin(rnd.nextDouble() * 2.0 * Math.PI) * v;
        this.radius = rnd.nextFloat()*10.0f;
        this.age = rnd.nextInt(100);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(x,y,radius,p);
    }

    @Override
    public void update(){
        if(state == STATE_ALIVE) {
            super.update();
            age++;
            if(age > LIFE_TIME)
                state = STATE_DEAD;
        }
    }
}
