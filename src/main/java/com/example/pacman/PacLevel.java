package com.example.pacman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class PacLevel {

    private PacmanView view;
    private Bitmap bmp[];

    private int MapRows = 20;
    private int MapCols = 10;

    private int tileWidth;
    private int tileHeight;

    private Sprite[] mapTiles = new Sprite[MapCols*MapRows];

    private ArrayList<Entity> ghosts;
    private Sprite pacman;

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
            1,0,0,0,0,0,0,0,2,1,
            1,1,1,1,1,1,1,1,1,1
    };

    public PacLevel(PacmanView view, int tileWidth, int tileHeight){
        this.view = view;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.bmp = new Bitmap[6];

        constructMap();
    }

    public void constructMap(){
        bmp[0] = BitmapFactory.decodeResource(view.getResources(), R.drawable.empty);
        bmp[1] = BitmapFactory.decodeResource(view.getResources(), R.drawable.wall01);
        bmp[2] = BitmapFactory.decodeResource(view.getResources(), R.drawable.ghost_green);
        bmp[2].setHasAlpha(true);

        ghosts = new ArrayList<Entity>();

        for(int i = 0; i < MapRows; i++){
            for(int j = 0; j < MapCols; j++){

                    if(level[i*MapCols + j] == 0){
                        Sprite s = new Sprite(view, bmp[level[i*MapCols + j]], j*tileWidth,i*tileHeight,(j+1)*tileWidth, (i+1)*tileHeight, Sprite.SpriteType.FLOOR);
                        mapTiles[i*MapCols + j] = s;
                    }
                    else if(level[i*MapCols + j] == 1){
                        Sprite s = new Sprite(view, bmp[level[i*MapCols + j]], j*tileWidth,i*tileHeight,(j+1)*tileWidth, (i+1)*tileHeight, Sprite.SpriteType.SOLID);
                        mapTiles[i*MapCols + j] = s;
                    }
                    else if(level[i*MapCols + j] == 2){
                        Sprite s = new Entity(view, bmp[0], j*tileWidth,i*tileHeight,(j+1)*tileWidth, (i+1)*tileHeight, Sprite.SpriteType.FLOOR);
                        mapTiles[i*MapCols + j] = s;

                        Entity e = new Entity(view, bmp[level[i*MapCols + j]], j*tileWidth,i*tileHeight,(j+1)*tileWidth, (i+1)*tileHeight, Sprite.SpriteType.GHOST);
                        ghosts.add(e);
                    }
            }
        }

    }

    public ArrayList<Entity> getGhosts(){
        return ghosts;
    }

    public void drawMap(Canvas canvas){
        for (Sprite tile : mapTiles) {
            tile.draw(canvas);
        }
    }

    public Sprite getTile(int x, int y){
        int x1 = x / tileWidth;
        int y1 = y / tileHeight;

        Log.d("GHOST", "BLOCK x, y " + x1 + " " + y1 + " width: " + tileWidth + " height : " + tileHeight);

        return mapTiles[x1 + MapCols * y1];
    }

}
