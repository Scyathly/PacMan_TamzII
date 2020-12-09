package com.example.pacman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelSelectActivity extends AppCompatActivity {

    private ListView levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.goFullscreen(this);

        setContentView(R.layout.activity_level_select);

        levels = findViewById(R.id.levelsList);

        loadListView();

    }

    private void loadListView() {

        List<String> names = new ArrayList<String>(Arrays.asList(Levels.getLevelNames()));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);

        levels.setAdapter(arrayAdapter);

        levels.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent();
            intent.putExtra("LevelNumber", position);
            setResult(RESULT_OK, intent);
            finish();
        });

    }
}