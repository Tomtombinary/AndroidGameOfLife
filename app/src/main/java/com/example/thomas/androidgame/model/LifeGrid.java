package com.example.thomas.androidgame.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.thomas.androidgame.affichage.SpinBar;
import com.example.thomas.androidgame.affichage.SpinnerListener;

/**
 * Created by thomas on 09/02/16.
 */
public class LifeGrid extends GameObject implements SpinnerListener{

    public static final int SIZE = 40;
    private int lines,columns;
    private int width,height;
    private boolean [][] matrix;
    private boolean [][] saved_matrix;
    private boolean pause;

    private Paint p;
    private Paint p2;

    private long touchTime = 0;
    private long UPDATE = 10;
    private long update_time = 0;

    public LifeGrid(int width,int height) {
        super(0,0);
        height -= 2*SIZE;
        this.lines = height / SIZE;
        this.columns = width / SIZE;
        this.width = width;
        this.height = height;
        this.matrix = new boolean[this.lines][this.columns];
        this.saved_matrix = new boolean[this.lines][this.columns];
        this.p = new Paint();
        this.p.setColor(Color.WHITE);
        this.p2 = new Paint();
        this.p2.setColor(Color.RED);
    }

    @Override
    public void update(){
        if(!pause) {
            if(update_time > UPDATE) {
                this.actualiser();
                this.update_time = 0;
            }else
                this.update_time++;
        }
        if(touchTime > 0)touchTime--;
    }

    @Override
    public void onProgess(SpinBar bar, float progress) {
        UPDATE = (long) (progress * 10);
    }

    @Override
    public void draw(Canvas canvas) {
        for(int i = 0;i < this.lines;i++){
            for(int j=0; j < this.columns; j++){
                if(this.matrix[i][j]) {
                    canvas.drawRect(j * SIZE, i * SIZE, j * SIZE + SIZE, i * SIZE + SIZE, p2);
                }
            }
        }

        for(int i = 0;i < this.lines;i++)
            canvas.drawLine(0,i * SIZE,width,i*SIZE,p);
        canvas.drawLine(0,this.lines * SIZE - 1,width,this.lines *SIZE - 1,p);

        for(int i = 0;i < this.columns + 1;i++)
            canvas.drawLine(i*SIZE,0,i*SIZE,height,p);
        canvas.drawLine(this.columns*SIZE - 1,0,this.columns*SIZE - 1,height,p);
    }

    public void pause(){
        this.pause = true;
    }

    public void start(){
        this.pause = false;
    }
    public void handleOnTouch(float x,float y){
        if(touchTime == 0) {
            int l = (int) (y / (float) SIZE);
            int c = (int) (x / (float) SIZE);
            if (l < this.lines && c < this.columns) {
                this.matrix[l][c] = !matrix[l][c];
            }
            touchTime = 5;
        }

        /*if(l >= this.lines || c >= this.columns)
            this.pause = !this.pause;*/
        //Log.d("OnTouch", "L:" + l + "C:" + c);
    }

    public void clear(){
        for(int i=0; i < this.lines; i++){
            for(int j=0;j<this.columns; j++){
                this.matrix[i][j] = false;
                this.saved_matrix[i][j] = false;
            }
        }
    }

    public int voisins(int i,int j){
        int v = 0;
        for(int k=i-1;k <i+2;k++){
            for(int l=j-1;l < j+2;l++){
                if(!(k == i && l == j)){
                    int k1 = k,c1 = l;
                    if(k < 0)k1 = this.lines - 1;
                    if(l < 0)c1 = this.columns - 1;
                    if(this.matrix[k1%this.lines][c1%this.columns])
                        v++;
                }
            }
        }
        return v;
    }

    public void actualiser(){
        this.copierMap();
        for (int i = 0; i < this.lines; i++){
            for (int j = 0; j < this.columns; j++){
                int v = voisins(i,j);
                this.saved_matrix[i][j] = ((v==3) || (this.matrix[i][j] && v==2));
            }
        }
        this.copierSave();
    }

    public void copierMap(){
        for (int i = 0; i < this.lines; i++){
            for (int j = 0; j < this.columns; j++){
                if(this.saved_matrix[i][j]!=this.matrix[i][j])
                    this.saved_matrix[i][j] = this.matrix[i][j];
            }
        }
    }

    public void copierSave(){
        for (int i = 0; i < this.lines; i++){
            for (int j = 0; j < this.columns; j++){
                if(this.matrix[i][j]!=this.saved_matrix[i][j])
                    this.matrix[i][j] = this.saved_matrix[i][j];
            }
        }
    }


}
