package com.example.pacman;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class ScoreUpdater {

    private Context ctx;
    private DBHelper db;

    public ScoreUpdater(Context ctx){
        this.ctx = ctx;
        this.db = new DBHelper(ctx);
    }

    public void updateScore(int score, double time, String levelName){
        HighScore hs = db.getScore(levelName);

        if(     (hs.getPoints() == -1) ||
                (hs.getPoints() < score) ||
                (hs.getPoints() == score && time < hs.getTime())  ){
            saveScore(new HighScore(levelName, score, time));
        }
    }

    private void saveScore(HighScore hs){
        if(db.dataExists(hs.getLevelName())){
            db.updateScore(hs);
        }
        else{
            db.insertScore(hs);
        }
    }

    public HighScore getScore(String levelName){
        return db.getScore(levelName);
    }

    public void deleteScore(String levelName){
        db.deleteScore(levelName);
    }

}
