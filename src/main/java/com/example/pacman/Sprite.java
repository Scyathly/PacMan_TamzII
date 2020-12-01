package com.example.pacman;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

    protected int width;
    protected int height;
    protected int x;
    protected int y;

    protected PacmanView view;
    protected Bitmap bmp;

    enum SpriteType {FLOOR, SOLID, FOOD, BIG_FOOD, PLAYER, GHOST}

    protected SpriteType type;

    public Sprite(PacmanView view, Bitmap bmp, int x, int y, int width, int height, SpriteType type){
        this.view = view;
        this.bmp = bmp;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getWidth(){return width;}
    public int getHeight(){return height;}

    public void update(double delta){
        return;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bmp, null, new Rect(x, y, width, height), null);
    }
}