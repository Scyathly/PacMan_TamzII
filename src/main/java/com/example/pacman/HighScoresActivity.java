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

        List<String> names = PacLevel.getLevelNames(getApplicationContext());

        ScoreUpdater su = new ScoreUpdater(getApplicationContext());

        HighScoreListAdapter highScoresListAdapter = new HighScoreListAdapter(this, R.layout.high_score_list_item);
        highScoresList.setAdapter(highScoresListAdapter);

        for(int i = 0; i < names.size();i++){
            HighScore h = su.getScore(names.get(i));
            highScoresListAdapter.add(h);
        }

    }
}