package com.example.pacman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HighScoresActivity extends AppCompatActivity {

    private ListView highScoresList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.goFullscreen(this);

        setContentView(R.layout.activity_high_scores);

        highScoresList = findViewById(R.id.highScoresList);
        loadListView();
    }

    private void loadListView() {

        List<String> names = new ArrayList<>(Arrays.asList(Levels.getLevelNames()));

        List<String> display = new ArrayList<>();

        ScoreUpdater su = new ScoreUpdater(getApplicationContext());

        for(int i = 0; i < names.size();i++){
            String text = names.get(i) + " : ";
            int score = su.getScore(i);
            double time = (double) Math.round(su.getTime(i) * 100) / 100;
            if(score == -1 || time == -1){
                text += "Not played yet";
            }
            else{
                text += "Points : " + score + ", Time: " + time;
            }
            display.add(text);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, display);

        highScoresList.setAdapter(arrayAdapter);
    }
}