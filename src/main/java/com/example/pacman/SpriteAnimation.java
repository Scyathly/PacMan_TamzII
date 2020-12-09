package com.example.pacman;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class SpriteAnimation {

    private double updateTime;
    private double currentTime;
    private int currentFrame;
    private List<Bitmap> bmp;

    public SpriteAnimation(List<Bitmap> bitmaps, double updateTime){
        this.updateTime = updateTime;
        this.currentTime = 0;
        this.currentFrame = 0;
        this.bmp = new ArrayList<>(bitmaps);
    }

    public void update(double delta){
        currentTime += delta;

        if(currentTime >= updateTime){
            if(currentFrame == bmp.size() - 1){
                currentFrame = 0;
            }
            else{
                currentFrame++;
            }
            currentTime = 0;
        }
    }

    public Bitmap getCurrent(){
        return bmp.get(currentFrame);
    }
}
