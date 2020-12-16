package com.example.pacman;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyDialog extends Dialog implements View.OnClickListener {

    private Activity activity;
    private Button confirm, cancel;
    private String name;
    private ScoreUpdater su;
    private TextView tw;

    public MyDialog(Activity a, String name) {
        super(a);
        this.activity = a;
        this.su = new ScoreUpdater(activity);
        this.name = name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_dialog_highscore);

        this.tw = findViewById(R.id.highscore_dialog_level_name);
        tw.setText(name);

        confirm = findViewById(R.id.btn_yes);
        cancel = findViewById(R.id.btn_no);
        confirm.setOnClickListener(this::onClick);
        cancel.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                su.deleteScore(name);
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
