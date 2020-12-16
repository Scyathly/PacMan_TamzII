package com.example.pacman;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PacLevel {

    private final PacmanView view;
    private final Bitmap[] bmp;

    private String levelName;

    private final int MapRows = 20;
    private final int MapCols = 10;

    private final int tileWidth;
    private final int tileHeight;

    private final Sprite[] mapTiles = new Sprite[MapCols*MapRows];

    private ArrayList<Entity> ghosts;
    private ArrayList<Food> food;
    private Entity player;

    enum LevelTileTypes {
        SOLID, FOOD, FOOD_B, PLAYER, FLOOR, GHOST_R, GHOST_G, GHOST_B, GHOST_P
    }

    public PacLevel(PacmanView view, int tileWidth, int tileHeight, String levelName){
        this.view = view;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.bmp = new Bitmap[20];
        this.levelName = levelName;

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


    private String getXmlString(String levelName) {
        String xmlString = null;
        AssetManager am = view.getContext().getAssets();
        try {
            InputStream is = am.open("levels/" + levelName + ".xml");
            int length = is.available();
            byte[] data = new byte[length];
            is.read(data);
            xmlString = new String(data);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return xmlString;
    }

    public static List<String> getLevelNames(Context ctx){
        ArrayList<String> levelNames = new ArrayList<String>();
        AssetManager am = ctx.getAssets();
        try {
            for (String level : am.list("levels")) {
                if (level.endsWith(".xml"))
                    levelNames.add(level.replaceAll(".xml$",""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return levelNames;
    }

    public String[] parseXMLLevel(String levelName){
        String xmlData = getXmlString(levelName);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        InputSource is = new InputSource(new StringReader(xmlData));

        Document document = null;
        try {
            document = builder.parse(is);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }

        NodeList rows = document.getElementsByTagName("row");

        String[] lst = new String[MapRows * MapCols];

        for (int i = 0; i < rows.getLength(); i++) {
            Node child = rows.item(i);
            String contents = child.getTextContent();
            contents = contents.replaceAll("\\s+","");
            String[] split = contents.split(",");

            for (int j = 0; j < MapCols; j++){
                lst[i * MapCols + j] = split[j];
            }

        }
        return lst;
    }

    public void constructMap(){
        loadBitmaps();

        ghosts = new ArrayList<>();
        food = new ArrayList<>();

        String[] level = parseXMLLevel(levelName);

        List<Bitmap> ghostRed = new ArrayList<>(List.of(bmp[6],bmp[7]));
        List<Bitmap> ghostBlue = new ArrayList<>(List.of(bmp[4],bmp[5]));
        List<Bitmap> ghostPurple = new ArrayList<>(List.of(bmp[8],bmp[9]));
        List<Bitmap> ghostGreen = new ArrayList<>(List.of(bmp[2],bmp[3]));
        List<Bitmap> pacman = new ArrayList<>(List.of(bmp[10],bmp[11],bmp[12]));

        for(int i = 0; i < MapRows; i++){
            for(int j = 0; j < MapCols; j++){
                    int num = i * MapCols + j;
                    boolean addFloor = false;
                    Sprite s;
                    Entity e;
                    Food f;
                    switch (LevelTileTypes.valueOf(level[num])){
                        case FLOOR:
                            addFloor = true;
                            break;
                        case SOLID:
                            s = new Sprite(view, bmp[1], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.SOLID);
                            mapTiles[num] = s;
                            break;
                        case PLAYER:
                            addFloor = true;
                            e = new Entity(view, bmp[2], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.PLAYER);
                            e.setAnimation(pacman,300);
                            player = e;
                            break;
                        case GHOST_B:
                            addFloor = true;
                            e = new Entity(view, bmp[0], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.GHOST);
                            e.setAnimation(ghostBlue,1000);
                            ghosts.add(e);
                            break;
                        case GHOST_G:
                            addFloor = true;
                            e = new Entity(view, bmp[0], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.GHOST);
                            e.setAnimation(ghostGreen,1000);
                            ghosts.add(e);
                            break;
                        case GHOST_P:
                            addFloor = true;
                            e = new Entity(view, bmp[0], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.GHOST);
                            e.setAnimation(ghostPurple,1000);
                            ghosts.add(e);
                            break;
                        case GHOST_R:
                            addFloor = true;
                            e = new Entity(view, bmp[0], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.GHOST);
                            e.setAnimation(ghostRed,1000);
                            ghosts.add(e);
                            break;
                        case FOOD:
                            addFloor = true;
                            f = new Food(view, bmp[17], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.FOOD, 1);
                            food.add(f);
                            break;
                        case FOOD_B:
                            addFloor = true;
                            f = new Food(view, bmp[new Random().nextInt(16 - 13 + 1) + 13], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.FOOD, 5);
                            food.add(f);
                            break;
                    }
                    if(addFloor){
                        s = new Sprite(view, bmp[0], j*tileWidth,i*tileHeight,tileWidth, tileHeight, Sprite.SpriteType.FLOOR);
                        mapTiles[num] = s;
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

    public Bitmap loadPreview(){
        Bitmap bmp = Bitmap.createBitmap(tileWidth * MapCols, tileHeight * MapRows, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        canvas.drawColor(Color.BLACK);

        for (Sprite tile : mapTiles) {
            tile.draw(canvas);
        }

        for(Entity ghost : ghosts){
            ghost.draw(canvas);
        }

        for(Food food : food){
            food.draw(canvas);
        }

        player.draw(canvas);

        return bmp;
    }

}
