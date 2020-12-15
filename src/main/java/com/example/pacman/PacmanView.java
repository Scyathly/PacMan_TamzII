package com.example.pacman;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.SoundPool;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class PacmanView extends SurfaceView {

    private SurfaceHolder holder;
    private GameThread mainThread;

    private String levelName;

    private int MapRows = 20;
    private int MapCols = 10;

    private SoundPool sp;
    private int idEating;
    private int idWin;
    private int idLose;

    private int colWidth;
    private int colHeight;

    private PacLevel map;

    private ArrayList<Entity> ghosts;
    private ArrayList<Food> food;
    private Entity player;

    private double time = 0;
    private int points = 0;


    PacmanView p;

    @SuppressLint("ClickableViewAccessibility")
    public PacmanView(Context context, String levelName) {
        super(context);

        this.levelName = levelName;

        mainThread = new GameThread(this);

        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {

                sp = new SoundPool.Builder().setMaxStreams(4).build();
                idEating = sp.load(context, R.raw.munch, 2);
                idWin = sp.load(context, R.raw.game_win, 1);
                idLose = sp.load(context, R.raw.game_over, 1);

                map = new PacLevel(p, colWidth, colHeight, levelName);
                ghosts = map.getGhosts();
                food = map.getFood();
                player = map.getPlayer();

                mainThread.setRunning(true);
                mainThread.start();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) { }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                boolean retry = true;
                mainThread.setRunning(false);
                while (retry){
                    try{
                        mainThread.join();
                        retry = false;
                    } catch (InterruptedException e){

                    }
                }
                sp.release();
            }
        });

        this.setOnTouchListener(new SwipeListener(context) {
            @Override
            public void onSwipeTop() {
                player.setDirectionBuffer(Entity.direction.UP);
            }
            @Override
            public void onSwipeRight() {
                player.setDirectionBuffer(Entity.direction.RIGHT);
            }
            @Override
            public void onSwipeLeft() {
                player.setDirectionBuffer(Entity.direction.LEFT);
            }
            @Override
            public void onSwipeBottom() {
                player.setDirectionBuffer(Entity.direction.DOWN);
            }

        });

        p = this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        colWidth = w / MapCols;
        colHeight = h / MapRows;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(canvas != null){

            canvas.drawColor(Color.BLACK);
            map.drawMap(canvas);

            player.draw(canvas);

            for(Food f: food){
               f.draw(canvas);
            }

            for(Sprite s : ghosts){
                s.draw(canvas);
            }

        }
    }

    public void endGame(Canvas canvas, boolean won){
        drawEnd(canvas, won);

        if(won){
            sp.play(idWin,0.5f,0.5f, 1,0, 1);
        }
        else{
            sp.play(idLose,0.5f,0.5f, 1,0, 1);
        }

        ScoreUpdater su = new ScoreUpdater(getContext());

        double roundedTime = (double) Math.round(time /1000 * 100) / 100;
        su.updateScore(points, roundedTime, levelName);
    }

    public void drawEnd(Canvas canvas, boolean won){
        Paint p = new Paint();

        Rect alphaRec = new Rect(0, 0, getWidth(), getHeight());
        p.setColor(Color.WHITE);
        p.setStyle(Paint.Style.FILL);
        p.setAlpha(200);
        canvas.drawRect(alphaRec, p);

        p.setTextAlign(Paint.Align.CENTER);
        p.setColor(Color.BLACK);
        p.setTextSize(90);

        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((p.descent() + p.ascent()) / 2)) - 90;

        if(won)
            canvas.drawText("You Won!", xPos, yPos, p);
        else
            canvas.drawText("You Lost!", xPos, yPos, p);

        canvas.drawText("Your score: " + points, xPos, yPos + 90, p);
        double roundedTime = (double) Math.round(time /1000 * 100) / 100;
        canvas.drawText("Time: " + roundedTime + "s", xPos, yPos + 180, p);
    }

    public int update(double delta){

        if(!player.isMoving()){
            switch (player.getDirectionBuffer()){
                case LEFT:{
                    if(map.getTile(player.x - colWidth, player.y).type != Sprite.SpriteType.SOLID){
                        player.move(map.getTile(player.x -colWidth,player.y));
                        player.setDirection(player.getDirectionBuffer());
                    }
                    break;
                }
                case UP:{
                    if(map.getTile(player.x, player.y - colHeight).type != Sprite.SpriteType.SOLID){
                        player.move(map.getTile(player.x, player.y - colHeight));
                        player.setDirection(player.getDirectionBuffer());
                    }
                    break;
                }
                case DOWN:{
                    if(map.getTile(player.x , player.y + colHeight).type != Sprite.SpriteType.SOLID){
                        player.move(map.getTile(player.x, player.y + colHeight));
                        player.setDirection(player.getDirectionBuffer());
                    }
                    break;
                }
                case RIGHT:{
                    if(map.getTile(player.x + colWidth, player.y).type != Sprite.SpriteType.SOLID){
                        player.move(map.getTile(player.x + colWidth, player.y));
                        player.setDirection(player.getDirectionBuffer());
                    }
                    break;
                }
            }


        }

        time += delta;
        player.update(delta);

        for(int i = 0; i < food.size(); i++){
            if(food.get(i).isCollided(player, false)){
                points += food.get(i).getValue();
                food.remove(i);
                sp.play(idEating,0.5f,0.5f, 1,0, 1);
                if(food.isEmpty()){
                    return 2;
                }
            }
        }

        for(Entity g : ghosts) {
            if (g.isCollided(player, true)) {
                return -1;
            }

            if(!g.isMoving()) {
                if(g.getLastDirSwitch() > 1500){
                    g.setDirectionBuffer(Entity.direction.getRandomDirection());
                    g.setLastDirSwitch(0);
                }
                else{
                    switch (g.getDirectionBuffer()) {
                        case LEFT: {
                            if (map.getTile(g.x - colWidth, g.y).type != Sprite.SpriteType.SOLID)  {
                                g.move(map.getTile(g.x - colWidth, g.y));
                                g.setDirection(g.getDirectionBuffer());
                            }
                            else{
                                g.setDirectionBuffer(Entity.direction.getRandomDirection());
                            }
                            break;
                        }
                        case UP: {
                            if (map.getTile(g.x, g.y - colHeight).type != Sprite.SpriteType.SOLID) {
                                g.move(map.getTile(g.x, g.y - colHeight));
                                g.setDirection(g.getDirectionBuffer());
                            }
                            else{
                                g.setDirectionBuffer(Entity.direction.getRandomDirection());
                            }
                            break;
                        }
                        case DOWN: {
                            if (map.getTile(g.x, g.y + colHeight).type != Sprite.SpriteType.SOLID) {
                                g.move(map.getTile(g.x, g.y + colHeight));
                                g.setDirection(g.getDirectionBuffer());
                            }
                            else{
                                g.setDirectionBuffer(Entity.direction.getRandomDirection());
                            }
                            break;
                        }
                        case RIGHT: {
                            if (map.getTile(g.x + colWidth, g.y).type != Sprite.SpriteType.SOLID) {
                                g.move(map.getTile(g.x + colWidth, g.y));
                                g.setDirection(g.getDirectionBuffer());
                            }
                            else{
                                g.setDirectionBuffer(Entity.direction.getRandomDirection());
                            }
                            break;
                        }
                    }
                }
            }
            g.update(delta);
        }
        return 1;
    }

}
