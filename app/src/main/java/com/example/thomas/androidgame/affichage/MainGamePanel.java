package com.example.thomas.androidgame.affichage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.thomas.androidgame.model.Explosion;
import com.example.thomas.androidgame.model.GameObject;
import com.example.thomas.androidgame.model.LifeGrid;
/**
 * Created by thomas on 08/02/16.
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private float x,y;
    Paint p;
    private Explosion[] explosion;
    private LifeGrid grid;
    private HUD hud;
    int index = 0;

    public MainGamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        this.p = new Paint();
        p.setColor(Color.WHITE);
        this.thread = new MainThread(getHolder(),this);
        setFocusable(true);
        this.explosion = new Explosion[1000];
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.thread.setRunning(true);
        this.thread.start();
        this.grid = new LifeGrid(getWidth(),getHeight());
        this.hud  = new HUD(this.grid,getWidth(),getHeight());
        /*Log.d("W", ":" + getWidth());
        Log.d("H", ":" + getHeight());*/
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while(retry){
            try {
                thread.join();
                retry = false;
            }catch(InterruptedException e){

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN)
        {
            this.hud.onTouchEvent(event);
                this.x = event.getX();
                this.y = event.getY();
                this.grid.handleOnTouch(x, y);
            /*
            explosion[index] = new Explosion(x,y);
            index++;
            */
        }
        return true;
    }

    @SuppressLint("WrongCall")
    public void render(Canvas canvas){
        draw(canvas);
        this.grid.draw(canvas);
        this.hud.draw(canvas);
        p.setColor(Color.RED);
        for(int i=0;i < index;i++)
            explosion[i].draw(canvas);
    }

    public void update(){
        for(int i=0;i < index;i++) {
            explosion[i].update();
            if(explosion[i].getState() == GameObject.STATE_DEAD) {
                for (int j = i; j < index - 1; j++)
                    explosion[j] = explosion[j + 1];
                index--;
            }
        }
        this.grid.update();
        this.hud.update();
    }
}
