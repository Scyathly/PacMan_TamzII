package com.example.pacman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class HighScoreListAdapter extends ArrayAdapter<HighScore> {

    private Context ctx;
    private int resourceLayout;
    private List<String> levelNames;

    public HighScoreListAdapter(Context ctx, int resourceLayout){
        super(ctx, R.layout.high_score_list_item);
        this.ctx = ctx;
        this.resourceLayout = resourceLayout;
        this.levelNames = PacLevel.getLevelNames(ctx);
    }

    public String getItemName(int position){
        return levelNames.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(ctx);
            v = vi.inflate(resourceLayout, null);
        }

        HighScore h = getItem(position);

        if (h != null) {
            TextView name = v.findViewById(R.id.HighScoreName);
            TextView points = v.findViewById(R.id.HighScorePoints);
            TextView time = v.findViewById(R.id.HighScoreTime);

            String nameStr = h.getLevelName();
            String pointsStr;
            String timeStr;

            if(h.getLevelName() == null || h.getLevelName() == ""){
                nameStr = levelNames.get(position);
            }
            if(h.getPoints() == -1){
                pointsStr = "0";
            }
            else{
                pointsStr = Integer.toString(h.getPoints());
            }

            if(h.getTime() == -1){
                timeStr = "0";
            }
            else{
                timeStr = Double.toString(h.getTime()) + " s";
            }

            name.setText(nameStr);
            points.setText("Points: " + pointsStr);
            time.setText(  "Time:   " + timeStr);

        }
        return v;
    }
}
