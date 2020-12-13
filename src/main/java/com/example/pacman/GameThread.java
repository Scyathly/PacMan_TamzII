package com.example.pacman;

import android.graphics.Canvas;
import android.util.Log;

public class GameThread extends Thread {

    private PacmanView view;
    private boolean running = false;

    private long lastLoopTime = System.nanoTime();
    private final int target_fps = 60;
    private final long optimal_time = 1_000_000_000 / target_fps;

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
                if(((lastLoopTime-System.nanoTime() + optimal_time)/1_000_000) > 0) Thread.sleep( (lastLoopTime-System.nanoTime() + optimal_time)/1_000_000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
