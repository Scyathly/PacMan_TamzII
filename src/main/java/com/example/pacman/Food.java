package com.example.pacman;

import android.graphics.Bitmap;

public class Food extends Sprite{

    private int value;

    public Food(PacmanView view, Bitmap bmp, int x, int y, int width, int height, SpriteType type, int value) {
        super(view, bmp, x, y, width, height, type);
        this.value = value;
    }

    public int getValue(){ return value; }
}
