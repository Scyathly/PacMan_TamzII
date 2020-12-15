package com.example.pacman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.goFullscreen(this);

        Intent intent = getIntent();
        String levelName = intent.getStringExtra("levelName");
        if(levelName == null){
            levelName = "Level 1";
        }

        setContentView(new PacmanView(this, levelName));
    }
}