package com.example.thomas.androidgame.affichage;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.example.thomas.androidgame.model.GameObject;

/**
 * Created by thomas on 09/02/16.
 */
public class Button extends GameObject
{

    private String text;
    private Paint p;
    private boolean clicked;
    private ButtonListener listener;

    public Button(float x, float y,String text) {
        super(x, y);
        this.text = text;
        this.p = new Paint();
        this.p.setColor(Color.WHITE);
        this.p.setTextAlign(Paint.Align.CENTER);
    }

    public void setButtonListener(ButtonListener listener){
        this.listener = listener;
    }

    public boolean getClicked(){
        return this.clicked;
    }

    @Override
    public void update(){

    }

    public void onTouchEvent(MotionEvent motionEvent){
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            Rect bounds = new Rect();
            float width = this.p.measureText(this.text);
            float height = this.p.getTextSize();
            bounds.set((int) (this.x - width / 2), (int) (this.y - height / 2), (int) (this.x + width), (int) (this.y + height));
            if (bounds.contains((int)motionEvent.getX(),(int)motionEvent.getY())) {
                this.clicked = !this.clicked;
                if(this.listener != null)this.listener.onClick(this);
            }
        }
    }

    public void setText(String text){
        this.text = text;
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawText(text,this.x,this.y,p);
    }
}
