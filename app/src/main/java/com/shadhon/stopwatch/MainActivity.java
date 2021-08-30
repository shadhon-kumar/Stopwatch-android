package com.shadhon.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton startBtn, stopBtn;
    Chronometer chronometer;

    private boolean isResume;
    Handler handler;
    Long tMilliSec, tStart, tBuff, tUpdate = 0L;
    int sec, min, milliSec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = findViewById(R.id.Btn_start);
        stopBtn = findViewById(R.id.Btn_stop);
//        pauseBtn = findViewById(R.id.Btn_pause);

        chronometer = findViewById(R.id.chronometerId);

        handler = new Handler();

        tMilliSec = 0L;
        tStart = 0L;
        tBuff = 0L;
        tUpdate = 0L;
        sec = 0;
        min =0;
        milliSec= 0;

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResume){
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    chronometer.start();
                    isResume = true;
                    stopBtn.setVisibility(View.GONE);
                    startBtn.setImageDrawable(getResources().getDrawable(R.drawable.baseline_pause));
                }else {
                    tBuff += tMilliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    isResume = false;
                    stopBtn.setVisibility(View.VISIBLE);
                    startBtn.setImageDrawable(getResources().getDrawable(R.drawable.baseline_play));
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResume){
                    startBtn.setImageDrawable(getResources().getDrawable(R.drawable.baseline_play));
                    tMilliSec = 0L;
                    tStart = 0L;
                    tBuff = 0L;
                    tUpdate = 0L;
                    sec = 0;
                    min =0;
                    milliSec= 0;
                    chronometer.setText("00:00:00");
                }
            }
        });

    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int) (tUpdate/1000);
            min = sec/60;
            sec = sec%60;
            milliSec = (int) (tUpdate%100);
            chronometer.setText(String.format("%02d",min)+":" +
                    String.format("%02d",sec)+ ":" +
                    String.format("%02d",milliSec));

            handler.postDelayed(this, 60);

        }
    };
}