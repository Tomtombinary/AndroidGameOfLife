package com.example.thomas.androidgame.affichage;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.example.thomas.androidgame.model.GameObject;
import com.example.thomas.androidgame.model.LifeGrid;

/**
 * Created by thomas on 09/02/16.
 */
public class HUD extends GameObject implements ButtonListener{

    private int width,height;
    private SpinBar progressBar;
    private Button btnPause;
    private LifeGrid grid;

    public HUD(LifeGrid grid,int width,int height) {
        super(0, 0);
        this.grid = grid;
        this.width = width;
        this.height = height;
        this.progressBar = new SpinBar(100,this.height - LifeGrid.SIZE,this.width - 100);
        this.btnPause = new Button(50,this.height - LifeGrid.SIZE,"Pause");
        this.btnPause.setButtonListener(this);
        this.progressBar.setSpinnerListener(this.grid);
    }

    @Override
    public void update(){
        this.progressBar.update();
    }

    public void onTouchEvent(MotionEvent motionEvent){
        this.progressBar.onTouchEvent(motionEvent);
        this.btnPause.onTouchEvent(motionEvent);
    }

    @Override
    public void draw(Canvas canvas){
        this.progressBar.draw(canvas);
        this.btnPause.draw(canvas);
    }

    @Override
    public void onClick(Button btn) {
        if(btn == btnPause){
            if(btn.getClicked()) {
                btn.setText("Start");
                this.grid.pause();
            }
            else {
                btn.setText("Pause");
                this.grid.start();
            }
        }
    }
}
