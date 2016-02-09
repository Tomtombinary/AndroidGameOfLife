package com.example.thomas.androidgame.affichage;

import android.graphics.Canvas;
import android.provider.Telephony;
import android.view.SurfaceHolder;

import com.example.thomas.androidgame.model.Particle;

import java.util.ArrayList;

/**
 * Created by thomas on 08/02/16.
 */
public class MainThread  extends Thread{
    private final static int MAX_FPS = 10;
    private final static int MAX_FRAME_SKIPS = 5;
    private final static int FRAME_PERIOD = 1000/MAX_FPS;

    private boolean running;

    private SurfaceHolder surfaceHolder;
    private MainGamePanel gamePanel;

    public MainThread(SurfaceHolder surfaceHolder,MainGamePanel gamePanel){
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }


    public void setRunning(boolean running){
        this.running = running;
    }

    @Override
    public void run(){
        Canvas canvas;
        long beginTime;
        long timeDiff;
        int sleepTime;
        int framesSkipped;

        sleepTime = 0;

        while(running){
            canvas = null;
            try {

                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;
                    this.gamePanel.update();
                    this.gamePanel.render(canvas);
                    timeDiff = System.currentTimeMillis() - beginTime;
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);
                    if(sleepTime > 0){
                        try{
                            Thread.sleep(sleepTime);
                        }catch(InterruptedException e){

                        }
                    }

                    while(sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS){
                        this.gamePanel.update();
                        sleepTime+= FRAME_PERIOD;
                        framesSkipped++;
                    }
                }
            }finally {
                if(canvas!=null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
