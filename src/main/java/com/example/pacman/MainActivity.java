package com.example.pacman;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 1;
    private String levelName = "Level 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goFullscreen(this);

        setContentView(R.layout.activity_main);

        Button clickButton1 = findViewById(R.id.btn1);
        clickButton1.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
            myIntent.putExtra("levelName", levelName);
            startActivity(myIntent);
        });

        Button clickButton2 = findViewById(R.id.btn2);
        clickButton2.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, LevelSelectActivity.class);
            startActivityForResult(myIntent, REQUEST_CODE);
        });

        Button clickButton3 = findViewById(R.id.btn3);
        clickButton3.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, HighScoresActivity.class);
            startActivity(myIntent);
        });

        Button clickButton4 = findViewById(R.id.btn4);
        clickButton4.setOnClickListener(v -> {
            finishAffinity();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                levelName = data.getStringExtra("levelName");
                if(levelName == null){
                    levelName = "Level 1";
                }
            }
        }
    }

    public static void goFullscreen(AppCompatActivity v){
        v.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        v.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try
        {
            v.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
    }
}