package com.example.pacman;

import android.graphics.Canvas;
import android.util.Log;

public class GameThread extends Thread {

    private PacmanView view;
    private boolean running = false;

    private long lastLoopTime = System.nanoTime();
    private final int TARGET_FPS = 60;
    private final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    private double lastFpsTime = 0;
    private double fps = 0;

    public GameThread(PacmanView view){
        this.view = view;
    }

    public void setRunning(boolean run){
        running = run;
    }

    @Override
    public void run(){

        while (running)
        {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double)OPTIMAL_TIME);

            lastFpsTime += updateLength;
            fps++;

            if (lastFpsTime >= 1000000000)
            {
                Log.d("(FPS",fps + ")");
                lastFpsTime = 0;
                fps = 0;
            }


            view.update(delta);

            Canvas c = null;
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }



            try {
                if(((lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000) > 0) Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
