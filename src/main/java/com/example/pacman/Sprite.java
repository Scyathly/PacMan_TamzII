package com.example.pacman;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Sprite {

    private double x = 0;
    private double y = 0;
    private double xVel = 0;
    private double yVel = 0;

    private PacmanView view;
    private Bitmap bmp;

    public Sprite(PacmanView view, Bitmap bmp){
        this.view = view;
        this.bmp = bmp;
    }

    public void Update(){
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void Draw(Canvas canvas){
        canvas.drawBitmap(bmp, (int)x, (int)y, null);
    }

}