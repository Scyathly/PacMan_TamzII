package com.example.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class PacmanView extends SurfaceView {

    private Bitmap bmp[];
    private SurfaceHolder holder;
    private GameThread mainThread;

    int MapRows = 20;
    int MapCols = 10;

    private int level[] = {
            1,1,1,1,1,1,1,1,1,1,
            1,0,0,0,0,0,0,0,0,1,
            1,0,1,0,0,0,0,0,1,1,
            1,0,1,0,0,0,0,0,0,1,
            1,0,1,1,1,1,1,0,0,1,
            1,0,0,0,0,0,0,0,0,1,
            1,0,1,0,0,0,0,0,0,1,
            1,0,1,1,1,1,1,0,0,1,
            1,0,1,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,1,
            1,1,1,1,1,1,1,1,0,1,
            1,0,0,0,0,0,0,0,0,1,
            1,0,0,0,0,0,0,0,0,1,
            1,0,1,1,1,1,1,0,0,1,
            1,0,0,0,0,0,1,0,0,1,
            1,0,1,1,1,1,1,0,0,1,
            1,0,1,0,0,0,0,0,0,1,
            1,0,1,1,1,1,1,0,0,1,
            1,0,0,0,0,0,0,0,0,1,
            1,1,1,1,1,1,1,1,1,1
    };

    int width = 80;
    int height = 80;

    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();

    public PacmanView(Context context) {
        super(context);

        mainThread = new GameThread(this);

        bmp = new Bitmap[6];

        bmp[0] = BitmapFactory.decodeResource(getResources(), R.drawable.empty);
        bmp[1] = BitmapFactory.decodeResource(getResources(), R.drawable.wall01);

        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                mainThread.setRunning(true);
                mainThread.start();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                boolean retry = true;
                mainThread.setRunning(false);
                while (retry){
                    try{
                        mainThread.join();
                        retry = false;
                    } catch (InterruptedException e){

                    }
                }
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / MapCols;
        height = h / MapRows;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(canvas != null){
            canvas.drawColor(Color.BLACK);

            for (int i = 0; i < MapRows; i++) {
                for (int j = 0; j < MapCols; j++) {
                    canvas.drawBitmap(bmp[level[i*10 + j]], null,
                            new Rect(j*width, i*height,(j+1)*width, (i+1)*height), null);
                }
            }

            for(Sprite s : sprites){
                s.Draw(canvas);
            }

        }
    }

    public void update(double delta){
        for(Sprite s : sprites){
            s.Update();
        }
    }
}
