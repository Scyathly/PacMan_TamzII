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

    enum SpriteType {FLOOR, SOLID, FOOD, PLAYER, GHOST}

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
        if(this.type == SpriteType.FLOOR) return;
        canvas.drawBitmap(bmp, x, y, null);
    }

    public boolean isCollided(Sprite s, boolean useCircle){
        if(useCircle){
            int tolerance = 10;
            int r1 = s.width / 2 - tolerance;
            int r2 = this.width / 2 - tolerance;

            int x1 = s.x + s.width / 2;
            int x2 = this.x + this.width / 2;

            int y1 = s.y + s.height / 2;
            int y2 = this.y + this.height / 2;

            return (x2 - x1) * (x2 - x1) + (y1 - y2) * (y1 - y2) <= (r1 + r2) * (r1 + r2);
        }
        else{
            return x < s.getX() + s.getWidth() &&
                    x + width > s.getX() &&
                    y < s.getY() + s.getHeight() &&
                    y + height > s.getY();
        }
    }
}