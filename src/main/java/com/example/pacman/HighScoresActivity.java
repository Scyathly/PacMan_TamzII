package com.example.pacman;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.List;

public class HighScoresActivity extends AppCompatActivity {

    private ListView highScoresList;
    private HighScoreListAdapter highScoresListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.goFullscreen(this);

        setContentView(R.layout.activity_high_scores);

        highScoresList = findViewById(R.id.highScoresList);
        highScoresListAdapter = new HighScoreListAdapter(this, R.layout.high_score_list_item);
        highScoresList.setAdapter(highScoresListAdapter);

        loadListView();

        highScoresList.setOnItemLongClickListener((parent, view, position, id) -> {
            HighScoreListAdapter ha = (HighScoreListAdapter)highScoresList.getAdapter();
            String name = ha.getItemName(position);

            MyDialog dialog = new MyDialog(HighScoresActivity.this, name);
            dialog.show();

            loadListView();
            return false;
        });

        highScoresList.setOnItemClickListener((parent, view, position, id) -> {
            loadListView();
        });
    }

    private void loadListView() {
        List<String> names = PacLevel.getLevelNames(getApplicationContext());
        ScoreUpdater su = new ScoreUpdater(getApplicationContext());

        highScoresListAdapter.clear();

        for(int i = 0; i < names.size();i++){
            HighScore h = su.getScore(names.get(i));
            highScoresListAdapter.add(h);
        }

        highScoresListAdapter.notifyDataSetChanged();
    }
}