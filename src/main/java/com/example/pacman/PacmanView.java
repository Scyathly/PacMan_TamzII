package com.example.pacman;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class PacmanView extends SurfaceView {

    private SurfaceHolder holder;
    private GameThread mainThread;

    private int MapRows = 20;
    private int MapCols = 10;

    private int colWidth;
    private int colHeight;

    private PacLevel map;

    private Entity.direction dir = Entity.direction.LEFT;

    private ArrayList<Entity> ghosts = new ArrayList<Entity>();

    PacmanView p;

    @SuppressLint("ClickableViewAccessibility")
    public PacmanView(Context context) {
        super(context);

        mainThread = new GameThread(this);

        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {

                map = new PacLevel(p, colWidth, colHeight);
                ghosts = map.getGhosts();

                mainThread.setRunning(true);
                mainThread.start();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) { }

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

        this.setOnTouchListener(new SwipeListener(context) {
            @Override
            public void onSwipeTop() {
                dir = Entity.direction.UP;
                Toast.makeText(context, "Swipe UP", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSwipeRight() {
                dir = Entity.direction.RIGHT;
                Toast.makeText(context, "Swipe RIGHT", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSwipeLeft() {
                dir = Entity.direction.LEFT;
                Toast.makeText(context, "Swipe LEFT", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSwipeBottom() {
                dir = Entity.direction.DOWN;
                Toast.makeText(context, "Swipe DOWN", Toast.LENGTH_SHORT).show();
            }

        });

        p = this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        colWidth = w / MapCols;
        colHeight = h / MapRows;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(canvas != null){
            canvas.drawColor(Color.BLACK);

            map.drawMap(canvas);

            for(Sprite s : ghosts){
                s.draw(canvas);
            }

        }
    }

    public void update(double delta){
        for(Entity g : ghosts){

            if(!g.isMoving()){
                switch (dir){
                    case LEFT:{
                        if(map.getTile(g.x - colWidth, g.y).type != Sprite.SpriteType.SOLID){
                            g.move(Entity.direction.LEFT, map.getTile(g.x -colWidth,g.y));
                        }
                        break;
                    }
                    case UP:{
                        if(map.getTile(g.x, g.y - colHeight).type != Sprite.SpriteType.SOLID){
                            g.move(Entity.direction.UP, map.getTile(g.x, g.y - colHeight));
                        }
                        break;
                    }
                    case DOWN:{
                        if(map.getTile(g.x , g.y + colHeight).type != Sprite.SpriteType.SOLID){
                            g.move(Entity.direction.DOWN, map.getTile(g.x, g.y + colHeight));
                        }
                        break;
                    }
                    case RIGHT:{
                        if(map.getTile(g.x + colWidth, g.y).type != Sprite.SpriteType.SOLID){
                            g.move(Entity.direction.RIGHT, map.getTile(g.x + colWidth, g.y));
                        }
                        break;
                    }
                }


            }

            g.update(delta);
        }

    }

}
