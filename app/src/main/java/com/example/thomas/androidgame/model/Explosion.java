package com.example.thomas.androidgame.model;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by thomas on 08/02/16.
 */
public class Explosion extends GameObject {

    private Particle[] particles;
    private int count;

    public Explosion(float x,float y){
        super(x,y);
        Random rnd = new Random();
        this.count = rnd.nextInt(50);
        this.particles = new Particle[count];
        for(int i = 0; i < this.count; i++)
            this.particles[i] = new Particle(x,y);
    }

    @Override
    public void draw(Canvas canvas) {
        for(int i = 0;i < count;i++)
            this.particles[i].draw(canvas);
    }

    @Override
    public void update(){
        if(state == STATE_ALIVE) {
            int i = 0;
            while (i < this.particles.length && particles[i] != null) {
                particles[i].update();
                if (this.particles[i].getState() == STATE_DEAD) {
                    for (int j = i; j < particles.length - 1; j++)
                        particles[j] = particles[j + 1];
                    this.count--;
                }
                i++;
            }
            if (this.count <= 0)
                this.state = STATE_DEAD;
        }
    }
}
