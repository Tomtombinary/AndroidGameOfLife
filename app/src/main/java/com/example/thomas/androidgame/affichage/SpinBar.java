package com.example.thomas.androidgame.affichage;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import com.example.thomas.androidgame.model.GameObject;

/**
 * Created by thomas on 09/02/16.
 */
public class SpinBar extends GameObject {


    private Paint pWhite;
    private Paint pGreen;
    private Paint pGray;
    private float progress;
    private int width;
    private boolean touch;
    private SpinnerListener listener;

    public SpinBar(float x, float y,int width) {
        super(x, y);
        this.width = width;
        this.progress = 0;
        this.pWhite = new Paint();
        this.pGreen = new Paint();
        this.pGray = new Paint();
        this.pWhite.setColor(Color.GRAY);
        this.pGreen.setColor(Color.GREEN);
        this.pGray.setColor(Color.DKGRAY);
    }

    public void setSpinnerListener(SpinnerListener listener){
        this.listener = listener;
    }

    @Override
    public void update(){

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(this.x, this.y - 1, this.x + width, this.y - 1, pGray);
        canvas.drawLine(this.x, this.y, this.x + width, this.y, pWhite);
        canvas.drawLine(this.x, this.y + 1, this.x + width, this.y + 1, pGray);
        canvas.drawCircle(this.x + (float)width* progress,this.y,10,pGreen);
    }

    public void onTouchEvent(MotionEvent motionEvent){
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            double distance = Math.sqrt(Math.pow(motionEvent.getX() - (this.x + (float) width * progress), 2) + Math.pow(motionEvent.getY() - this.y, 2));
            if (distance <= 10){
                this.touch = true;
                this.progress = (motionEvent.getX() - this.x)/ width;
                if(progress < 0)progress = 0;
                if(progress > 1f)progress = 1f;
                if(this.listener != null)this.listener.onProgess(this,progress);
            }else
                this.touch = false;
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE && touch)
        {
            this.progress = (motionEvent.getX() - this.x)/ width;
            if(progress < 0)progress = 0;
            if(progress > 1f)progress = 1f;
            if(this.listener != null)this.listener.onProgess(this,progress);
        }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            Log.d("ACTION","UP");
            this.touch = false;
        }
    }
}
