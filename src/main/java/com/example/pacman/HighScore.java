package com.example.pacman;

public class HighScore {
    private String levelName;
    private int points;
    private double time;

    public HighScore(){
        this.levelName = "";
        this.points = -1;
        this.time = -1;
    }

    public HighScore(String levelName, int points, double time){
        this.levelName = levelName;
        this.points = points;
        this.time = time;
    }

    public String getLevelName(){ return levelName; }

    public int getPoints(){ return points; }

    public double getTime() { return time; }
}
