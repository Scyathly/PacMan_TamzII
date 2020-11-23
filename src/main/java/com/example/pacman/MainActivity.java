package com.example.pacman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button clickButton1 = (Button) findViewById(R.id.btn1);
        clickButton1.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        Button clickButton2 = (Button) findViewById(R.id.btn2);
        clickButton2.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });

        Button clickButton3 = (Button) findViewById(R.id.btn3);
        clickButton3.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });

    }
}