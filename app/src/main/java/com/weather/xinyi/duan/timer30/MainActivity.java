package com.weather.xinyi.duan.timer30;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Chronometer ch;
    private Button start;
    private Button stop;
    private EditText edit;
    private MediaPlayer mediaPlayer;
    private int delayTime = 30;//响铃间隔时间，默认值30S。

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ch = (Chronometer) findViewById(R.id.timer);
        start = (Button) findViewById(R.id.start_button);
        stop = (Button) findViewById(R.id.stop_button);
        edit = (EditText) findViewById(R.id.set_time);
        mediaPlayer = MediaPlayer.create(this, R.raw.supermariobro);
        //mediaPlayer.setLooping(true);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit.getText().toString().equals("")) {
                    delayTime = Integer.parseInt(edit.getText().toString());
                }
                ch.setBase(SystemClock.elapsedRealtime());
                ch.start();
                start.setEnabled(false);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
                ch.stop();
                start.setEnabled(true);
                ch.setBase(SystemClock.elapsedRealtime());
            }
        });

        ch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (SystemClock.elapsedRealtime() - ch.getBase() > delayTime * 1000) {
                    ch.stop();
                    mediaPlayer.start();
                    start.setEnabled(true);
                    ch.setBase(SystemClock.elapsedRealtime());
                    ch.start();
                    start.setEnabled(false);
                }
            }
        });

    }

}
