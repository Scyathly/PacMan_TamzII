package com.example.pacman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PacLevel {

    private final PacmanView view;
    private final Bitmap[] bmp;

    private int levelNumber;

    private final int MapRows = 20;
    private final int MapCols = 10;

    private final int tileWidth;
    private final int tileHeight;

    private final Sprite[] mapTiles = new Sprite[MapCols*MapRows];

    private ArrayList<Entity> ghosts;
    private ArrayList<Food> food;
    private Entity player;

    public PacLevel(PacmanView view, int tileWidth, int tileHeight, int levelNumber){
        this.view = view;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.bmp = new Bitmap[20];
        this.levelNumber = levelNumber;

        constructMap();
    }

    public void loadBitmaps(){
        bmp[0] = Bitmap.createBitmap(tileWidth, tileHeight, Bitmap.Config.ARGB_8888);
        bmp[0] = Bitmap.createScaledBitmap(bmp[0], tileWidth, tileHeight, false);
        bmp[1] = BitmapFactory.decodeResource(view.getResources(), R.drawable.wall01);
        bmp[1] = Bitmap.createScaledBitmap(bmp[1], tileWidth, tileHeight, false);

        bmp[2] = BitmapFactory.decodeResource(view.getResources(), R.drawable.green_1);
        bmp[2] = Bitmap.createScaledBitmap(bmp[2], tileWidth, tileHeight, false);
        bmp[3] = BitmapFactory.decodeResource(view.getResources(), R.drawable.green_2);
        bmp[3] = Bitmap.createScaledBitmap(bmp[3], tileWidth, tileHeight, false);

        bmp[4] = BitmapFactory.decodeResource(view.getResources(), R.drawable.blue_1);
        bmp[4] = Bitmap.createScaledBitmap(bmp[4], tileWidth, tileHeight, false);
        bmp[5] = BitmapFactory.decodeResource(view.getResources(), R.drawable.blue_2);
        bmp[5] = Bitmap.createScaledBitmap(bmp[5], tileWidth, tileHeight, false);

        bmp[6] = BitmapFactory.decodeResource(view.getResources(), R.drawable.red_1);
        bmp[6] = Bitmap.createScaledBitmap(bmp[6], tileWidth, tileHeight, false);
        bmp[7] = BitmapFactory.decodeResource(view.getResources(), R.drawable.red_2);
        bmp[7] = Bitmap.createScaledBitmap(bmp[7], tileWidth, tileHeight, false);

        bmp[8] = BitmapFactory.decodeResource(view.getResources(), R.drawable.purple_1);
        bmp[8] = Bitmap.createScaledBitmap(bmp[8], tileWidth, tileHeight, false);
        bmp[9] = BitmapFactory.decodeResource(view.getResources(), R.drawable.purple_2);
        bmp[9] = Bitmap.createScaledBitmap(bmp[9], tileWidth, tileHeight, false);


        bmp[10] = BitmapFactory.decodeResource(view.getResources(), R.drawable.pacman_1);
        bmp[10] = Bitmap.createScaledBitmap(bmp[10], tileWidth, tileHeight, false);
        bmp[11] = BitmapFactory.decodeResource(view.getResources(), R.drawable.pacman_2);
        bmp[11] = Bitmap.createScaledBitmap(bmp[11], tileWidth, tileHeight, false);
        bmp[12] = BitmapFactory.decodeResource(view.getResources(), R.drawable.pacman_3);
        bmp[12] = Bitmap.createScaledBitmap(bmp[12], tileWidth, tileHeight, false);

        bmp[13] = BitmapFactory.decodeResource(view.getResources(), R.drawable.fruit_cherry);
        bmp[13] = Bitmap.createScaledBitmap(bmp[13], tileWidth, tileHeight, false);
        bmp[14] = BitmapFactory.decodeResource(view.getResources(), R.drawable.fruit_lemon);
        bmp[14] = Bitmap.createScaledBitmap(bmp[14], tileWidth, tileHeight, false);
        bmp[15] = BitmapFactory.decodeResource(view.getResources(), R.drawable.fruit_pear);
        bmp[15] = Bitmap.createScaledBitmap(bmp[15], tileWidth, tileHeight, false);
        bmp[16] = BitmapFactory.decodeResource(view.getResources(), R.drawable.fruit_strawberry);
        bmp[16] = Bitmap.createScaledBitmap(bmp[16], tileWidth, tileHeight, false);
        bmp[17] = BitmapFactory.decodeResource(view.getResources(), R.drawable.fruit);
        bmp[17] = Bitmap.createScaledBitmap(bmp[17], tileWidth, tileHeight, false);
    }

    public void constructMap(){
        loadBitmaps();

        int[] level = Levels.getLevel(levelNumber);

        ghosts = new ArrayList<>();
        food = new ArrayList<>();

        List<Bitmap> ghostRed = new ArrayList<>(List.of(bmp[6],bmp[7]));
        List<Bitmap> ghostBlue = new ArrayList<>(List.of(bmp[4],bmp[5]));
        List<Bitmap> ghostPurple = new ArrayList<>(List.of(bmp[8],bmp[9]));
        List<Bitmap> ghostGreen = new ArrayList<>(List.of(bmp[2],bmp[3]));
        List<Bitmap> pacman = new ArrayList<>(List.of(bmp[10],bmp[11],bmp[12]));

        for(int i = 0; i < MapRows; i++){
            for(int j = 0; j < MapCols; j++){

                    int num = i * MapCols + j;

                    if(level[num] == 0){
                        Sprite s = new Sprite(view, bmp[0], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.FLOOR);
                        mapTiles[num] = s;
                    }
                    else if(level[num]== 1){
                        Sprite s = new Sprite(view, bmp[1], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.SOLID);
                        mapTiles[num] = s;
                    }
                    else
                    {
                        Sprite s = new Sprite(view, bmp[0], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.FLOOR);
                        mapTiles[num] = s;

                        if(level[num] == 2){
                            Entity e = new Entity(view, bmp[2], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.PLAYER);
                            e.setAnimation(pacman,300);
                            player = e;
                        }
                        else if(level[num] == 3){
                            Entity e = new Entity(view, bmp[0], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.GHOST);
                            e.setAnimation(ghostRed,1000);
                            ghosts.add(e);
                        }
                        else if(level[num] == 4){
                            Entity e = new Entity(view, bmp[0], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.GHOST);
                            e.setAnimation(ghostBlue,1000);
                            ghosts.add(e);
                        }
                        else if(level[num] == 5){
                            Entity e = new Entity(view, bmp[0], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.GHOST);
                            e.setAnimation(ghostGreen,1000);
                            ghosts.add(e);
                        }
                        else if(level[num] == 6){
                            Entity e = new Entity(view, bmp[0], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.GHOST);
                            e.setAnimation(ghostPurple,1000);
                            ghosts.add(e);
                        }
                        else if(level[num] == 7){
                            Food f = new Food(view, bmp[17], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.FOOD, 1);
                            food.add(f);
                        }
                        else if(level[num] == 8){
                            Food f = new Food(view, bmp[new Random().nextInt(16 - 13 + 1) + 13], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.FOOD, 5);
                            food.add(f);
                        }
                    }
            }
        }
    }

    public ArrayList<Entity> getGhosts(){ return ghosts; }

    public Entity getPlayer(){ return player; }

    public ArrayList<Food> getFood() { return food; }

    public void drawMap(Canvas canvas){
        for (Sprite tile : mapTiles) {
            tile.draw(canvas);
        }
    }

    public Sprite getTile(int x, int y){
        int x1 = x / tileWidth;
        int y1 = y / tileHeight;

        return mapTiles[x1 + MapCols * y1];
    }

}
