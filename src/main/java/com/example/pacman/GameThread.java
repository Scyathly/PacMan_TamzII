package com.example.pacman;

import android.graphics.Canvas;
import android.util.Log;

public class GameThread extends Thread {

    private PacmanView view;
    private boolean running = false;

    private long lastLoopTime = System.nanoTime();
    private final int TARGET_FPS = 60;
    private final long OPTIMAL_TIME = 1_000_000_000 / TARGET_FPS;
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
            double delta = updateLength;

            lastFpsTime += updateLength;
            fps++;

            if (lastFpsTime >= 1_000_000_000)
            {
                Log.d("(FPS",fps + ")   (Frame Time " + delta / 1_000_000 +"ms)");
                lastFpsTime = 0;
                fps = 0;
            }

            int state = view.update(delta / 1_000_000);

            Canvas c = null;
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);

                    if(state == -1){
                        view.endGame(c, false);
                        running = false;
                    }
                    else if(state == 2){
                        view.endGame(c, true);
                        running = false;
                    }

                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }

            try {
                if(((lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1_000_000) > 0) Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1_000_000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
