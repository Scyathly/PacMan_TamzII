package com.example.pacman;

import android.content.Context;
import android.content.SharedPreferences;

public class ScoreUpdater {

    private String prefName = "PacMan_Score";
    private Context ctx;
    private SharedPreferences sp;

    public ScoreUpdater(Context ctx){
        this.ctx = ctx;
        sp = ctx.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    public void updateScore(int score, double time, int levelNumber){
        int scoreSP = getScore(levelNumber);
        double timeSP = getTime(levelNumber);

        if(     (scoreSP == -1) ||
                (scoreSP < score) ||
                (scoreSP == score && time < timeSP)  ){
            saveScore(score, time, levelNumber);
        }

    }

    private void saveScore(int score, double time, int levelNumber){
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("level_" + levelNumber + "_points", score);
        editor.putFloat("level_" + levelNumber + "_time", (float)time);
        editor.apply();
    }

    public int getScore(int levelNumber){
        return sp.getInt("level_" + levelNumber + "_points", -1);
    }

    public double getTime(int levelNumber){
        return sp.getFloat("level_" + levelNumber + "_time", -1);
    }

}
