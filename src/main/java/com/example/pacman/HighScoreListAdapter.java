package com.example.pacman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HighScoreListAdapter extends ArrayAdapter<HighScore> {

    private Context ctx;
    private int resourceLayout;

    public HighScoreListAdapter(Context ctx, int resourceLayout){
        super(ctx, R.layout.high_score_list_item);
        this.ctx = ctx;
        this.resourceLayout = resourceLayout;
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

            if(h.getPoints() == -1){
                pointsStr = "Not Played";
            }
            else{
                pointsStr = Integer.toString(h.getPoints());
            }

            if(h.getTime() == -1){
                timeStr = "Not Played";
            }
            else{
                timeStr = Double.toString(h.getTime()) + " s";
            }

            name.setText(nameStr);
            points.setText("Points: " + pointsStr);
            time.setText("Time: " + timeStr);

        }
        return v;
    }
}
