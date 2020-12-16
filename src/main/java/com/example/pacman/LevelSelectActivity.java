package com.example.pacman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelSelectActivity extends AppCompatActivity implements LevelSelectAdapter.ItemClickListener {

    private RecyclerView levelsView;
    private LevelSelectAdapter levelSelectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.goFullscreen(this);

        setContentView(R.layout.activity_level_select);

        levelsView = findViewById(R.id.levels_list);

        ArrayList<Bitmap> previews = new ArrayList<>();
        ArrayList<String> names;

        names = new ArrayList<>(PacLevel.getLevelNames(getApplicationContext()));

        for(int i = 0; i < names.size(); i++){
            PacmanView pv = new PacmanView(getApplicationContext(),names.get(i));
            PacLevel pl = new PacLevel(pv, 30,30, names.get(i));
            previews.add(pl.loadPreview());
        }

        LinearLayoutManager manager = new LinearLayoutManager(LevelSelectActivity.this, LinearLayoutManager.HORIZONTAL, false);
        levelsView.setLayoutManager(manager);
        levelSelectAdapter = new LevelSelectAdapter(this, previews, names);
        levelSelectAdapter.setClickListener(this);
        levelsView.setAdapter(levelSelectAdapter);


    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent();
        intent.putExtra("levelName", levelSelectAdapter.getItemName(position));
        setResult(RESULT_OK, intent);
        finish();
    }
}