package com.example.pacman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.goFullscreen(this);

        Intent intent = getIntent();
        int levelNumber = intent.getIntExtra("LevelNumber", 0);

        setContentView(new PacmanView(this, levelNumber));
    }
}