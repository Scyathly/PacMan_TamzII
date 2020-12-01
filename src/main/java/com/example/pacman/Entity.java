package com.example.pacman;

import android.graphics.Bitmap;
import android.util.Log;

public class Entity extends Sprite{

    private double speed = 5;
    private boolean moving = false;

    private int targetX;
    private int targetY;

    private int spriteWidth;
    private int spriteHeight;

    enum direction {UP, LEFT, RIGHT, DOWN}

    public direction dir = direction.UP;

    public Entity(PacmanView view, Bitmap bmp, int x, int y, int width, int height, SpriteType type) {
        super(view, bmp, x, y, width, height, type);

        spriteWidth = width - x;
        spriteHeight = height - y;
    }

    @Override
    public void update(double delta) {

        if(!moving) return;

        int tolerance = (int)speed - 1;

        if(Math.abs(this.x - this.targetX) < tolerance && Math.abs(this.y - this.targetY) < tolerance) {
            this.x = this.targetX;
            this.y = this.targetY;

            this.width = x + spriteWidth;
            this.height = y + spriteHeight;

            moving = false;
            return;
        }


        switch (this.dir) {
            case LEFT:
                this.x -= this.speed;
                this.width -= this.speed;
                break;
            case RIGHT:
                this.x += this.speed;
                this.width += this.speed;
                break;
            case UP:
                this.y -= this.speed;
                this.height -= this.speed;
                break;
            case DOWN:
                this.y += this.speed;
                this.height += this.speed;
                break;
        }

        Log.d("GHOST", "X, Y" + x + " " + y + " DEST X, Y" + " " + targetX + " " + targetY);

    }

    public boolean isMoving(){
        return moving;
    }

    public void canMove(){

    }

    public void move(direction dir, Sprite destTile){
        if(moving) return;

        this.dir = dir;
        moving = true;

        targetX = destTile.x;
        targetY = destTile.y;
    }

}
