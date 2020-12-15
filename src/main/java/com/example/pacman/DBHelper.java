package com.example.pacman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "DBPacman.db";

    public static final String ITEM_COLUMN_ID = "id";
    public static final String ITEM_COLUMN_NAME = "name";
    public static final String ITEM_COLUMN_POINTS = "points";
    public static final String ITEM_COLUMN_TIME = "time";


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE highScore " + "(id INTEGER PRIMARY KEY, name TEXT, points INTEGER, time DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS highScore");
        onCreate(db);
    }

    public boolean insertScore(HighScore score)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_COLUMN_NAME, score.getLevelName());
        contentValues.put(ITEM_COLUMN_POINTS, score.getPoints());
        contentValues.put(ITEM_COLUMN_TIME, score.getTime());

        long insertedId = db.insert("highScore", null, contentValues);
        
        if (insertedId == -1) return false;
        return true;
    }

    public boolean dataExists(String levelName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from highScore where name='" + levelName + "'", null);

        if(res.getCount() <= 0){
            return false;
        }
        return true;
    }

    public boolean deleteScore (String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        if(db.delete("highScore","name = ?", new String[] {name}) > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public HighScore getScore(String levelName){
        SQLiteDatabase db = this.getReadableDatabase();

        if(!dataExists(levelName)){
            return new HighScore();
        }


        Cursor res =  db.rawQuery("select * from highScore where name='" + levelName + "'", null);
        res.moveToFirst();

        int id = res.getInt(0);
        String name = res.getString(res.getColumnIndex(ITEM_COLUMN_NAME));
        int points = res.getInt(res.getColumnIndex(ITEM_COLUMN_POINTS));
        double time = res.getDouble(res.getColumnIndex(ITEM_COLUMN_TIME));

        HighScore a = new HighScore(name, points, time);

        return a;
    }

    public boolean updateScore (HighScore score)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ITEM_COLUMN_POINTS, score.getPoints());
        cv.put(ITEM_COLUMN_TIME, score.getTime());

        db.update("highScore", cv, "name = '" + score.getLevelName() + "'", null);

        return true;
    }

    public ArrayList<HighScore> getScoreList()
    {
        ArrayList<HighScore> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select id, name, points, time from highScore", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            int id = res.getInt(res.getColumnIndex(ITEM_COLUMN_ID));
            String name = res.getString(res.getColumnIndex(ITEM_COLUMN_NAME));
            int points = res.getInt(res.getColumnIndex(ITEM_COLUMN_POINTS));
            double time = res.getDouble(res.getColumnIndex(ITEM_COLUMN_TIME));

            HighScore a = new HighScore(name, points, time);
            arrayList.add(a);
            res.moveToNext();
        }

        return arrayList;
    }

    public int removeAll()
    {
        int nRecordDeleted = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        nRecordDeleted = db.delete("highScore", null, null);
        return nRecordDeleted;
    }


}
