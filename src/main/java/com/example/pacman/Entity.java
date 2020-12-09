package com.example.pacman;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.animation.Animation;

import java.util.List;
import java.util.Random;

public class Entity extends Sprite{

    private double speed = 8;
    private boolean moving = false;

    private SpriteAnimation animation;

    private int targetX;
    private int targetY;

    enum direction {
        UP,
        LEFT,
        RIGHT,
        DOWN;

        public static direction getRandomDirection() {
            return values()[(int) (Math.random() * values().length)];
        }
    }

    private direction dir = direction.LEFT;
    private direction dirBuffer = direction.LEFT;

    public Entity(PacmanView view, Bitmap bmp, int x, int y, int width, int height, SpriteType type) {
        super(view, bmp, x, y, width, height, type);
    }

    public void setAnimation(List<Bitmap> bitmaps, double updateTime){
        this.animation = new SpriteAnimation(bitmaps, updateTime);
    }

    @Override
    public void update(double delta) {

        this.animation.update(delta);

        if(!moving) return;

        int tolerance = (int)speed;

        if(Math.abs(this.x - this.targetX) < tolerance && Math.abs(this.y - this.targetY) < tolerance) {
            this.x = this.targetX;
            this.y = this.targetY;

            moving = false;
            return;
        }

        switch (this.dir) {
            case LEFT:
                this.x -= this.speed;
                break;
            case RIGHT:
                this.x += this.speed;
                break;
            case UP:
                this.y -= this.speed;
                break;
            case DOWN:
                this.y += this.speed;
                break;
        }
    }

    public void setDirection(direction dir){
        this.dir = dir;
    }

    public void setDirectionBuffer(direction dir){
        this.dirBuffer = dir;
    }

    public direction getDirection(){
        return dir;
    }

    public direction getDirectionBuffer(){
        return dirBuffer;
    }

    public boolean isMoving(){
        return moving;
    }

    public void move(Sprite destTile){
        if(moving) return;

        moving = true;
        targetX = destTile.x;
        targetY = destTile.y;
    }

    @Override
    public void draw(Canvas canvas){

        if(this.type == SpriteType.PLAYER){

            Bitmap b = animation.getCurrent();

            if(dir == direction.LEFT){
                b = rotateBitmap(animation.getCurrent(), 270);
            }
            else if( dir == direction.DOWN){
                b = rotateBitmap(animation.getCurrent(), 180);
            }
            else if(dir == direction.RIGHT){
                b = rotateBitmap(animation.getCurrent(), 90);
            }

            canvas.drawBitmap(b, null, new Rect(x, y, x + width, y + height), null);
        }
        else{
            canvas.drawBitmap(animation.getCurrent(), x, y,null);
        }
    }

    public Bitmap rotateBitmap(Bitmap original, float degrees) {
        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);
        return Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
    }
}
