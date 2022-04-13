package com.example.studytimer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private String display;
    private String course ;
    private int data;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0 ;
    private boolean runningstate = false;
    private boolean wasrunning = false;
    TextView type;
    TextView lasttime;
    TextView lasttime2;


    ImageButton start;
    ImageButton pause;
    ImageButton end;




    @Override
    protected void onStop() {

        super.onStop();
        wasrunning = runningstate;
        runningstate = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_main2);
        type = findViewById(R.id.type);
        lasttime = findViewById(R.id.land);
        start = findViewById(R.id.start);
        pause = findViewById(R.id.pause);
        end = findViewById(R.id.end);
        start.setOnClickListener(new MyonClickListener());
        pause.setOnClickListener(new MyonClickListener());
        end.setOnClickListener(new MyonClickListener());
        SharedPreferences sharedPreferences = getSharedPreferences(
                "cun", Context.MODE_PRIVATE
        );
        data =sharedPreferences.getInt("data",0);
        display = sharedPreferences.getString("display","");
        if(data == 1)
        {
            lasttime.setText(display);

        }
        else {


        }







        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            runningstate = savedInstanceState.getBoolean("runningstate");
            wasrunning = savedInstanceState.getBoolean("wasrunning");
        }
        runtime();
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {

        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putInt("seconds", seconds);
        saveInstanceState.putBoolean("runningstate", runningstate);
        saveInstanceState.putBoolean("wasrunning", wasrunning);
    }


    class MyonClickListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start:
                    runningstate = true;
                    course = type.getText().toString();

                    break;
                case R.id.pause:
                    runningstate = false;
                    break;
                case R.id.end:
                    runningstate = false;

                    SharedPreferences mySharedPreferences = getSharedPreferences(
                            "cun", Context.MODE_PRIVATE
                    );

                    @SuppressLint("CommitPrefEdits")
                    SharedPreferences.Editor editor = mySharedPreferences.edit();

                    editor.putString("display","You spent "+String.format("%02d:%02d", minutes, seconds % 60)+" on "+course + " last time");
                    editor.putInt("data",1);
                    editor.commit();



                    seconds = 0;
                    type.setText(null);
            }


        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main);
            type = findViewById(R.id.type);
            lasttime = findViewById(R.id.land);
            start = findViewById(R.id.start);
            pause = findViewById(R.id.pause);
            end = findViewById(R.id.end);
            start.setOnClickListener(new MyonClickListener());
            pause.setOnClickListener(new MyonClickListener());
            end.setOnClickListener(new MyonClickListener());
            type.setText(course);
            SharedPreferences sharedPreferences = getSharedPreferences(
                    "cun", Context.MODE_PRIVATE
            );
            data =sharedPreferences.getInt("data",0);
            display = sharedPreferences.getString("display","");
            if(data == 1)
            {
                lasttime.setText(display);

            }
            else {


            }

        }
        else {

            setContentView(R.layout.activity_main2);
            type = findViewById(R.id.type);
            lasttime = findViewById(R.id.land);
            start = findViewById(R.id.start);
            pause = findViewById(R.id.pause);
            end = findViewById(R.id.end);
            type.setText(course);
            start.setOnClickListener(new MyonClickListener());
            pause.setOnClickListener(new MyonClickListener());
            end.setOnClickListener(new MyonClickListener());
            SharedPreferences sharedPreferences = getSharedPreferences(
                    "cun", Context.MODE_PRIVATE
            );
            data =sharedPreferences.getInt("data",0);
            display = sharedPreferences.getString("display","");
            if(data == 1)
            {
                lasttime.setText(display);

            }
            else {


            }
            }

        super.onConfigurationChanged(newConfig);
    }

    private void runtime() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
                         @Override
                         public void run() {
                             final TextView textView = findViewById(R.id.textView3);
                              hours = seconds / 3600 % 24;
                              minutes = seconds % 3600 / 60;
                             String time = String.format("%02d:%02d", minutes, seconds % 60);
                             textView.setText(time);
                             if (runningstate) seconds++;
                             handler.postDelayed(this, 1000);
                             if(minutes >= 1)
                             {
                                 time = String.format("%02d:%02d:%02d",hours, minutes, seconds % 60);
                                 textView.setText(time);
                             }
                         }
                     }
        );
    }
}
