package com.example.mymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.os.Handler;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaplayer;
    private Button b1;
    private ImageButton implay, imloop;
    private SeekBar skb;
    private TextView ctime, ftime;
    Boolean press = true;
    int lp1 = 1;
    private double startTime = 0;
    private double finalTime = 0;
    Handler handler= new Handler();

    Runnable runnable;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaplayer.stop();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        implay = (ImageButton) findViewById(R.id.image1);
        imloop = (ImageButton) findViewById(R.id.image2);
        skb = (SeekBar) findViewById(R.id.seekBar);
        ctime = (TextView) findViewById(R.id.current);
        ftime = (TextView) findViewById(R.id.final1);


        mediaplayer = MediaPlayer.create(this, R.raw.aabaad);
       // finalTime = mediaplayer.getDuration();
        //startTime = mediaplayer.getCurrentPosition();
       /* runnable= new Runnable() {
            @Override
            public void run() {
                long currentDuration = mediaplayer.getCurrentPosition();
                long elapsedDuration = mediaplayer.getDuration() - currentDuration;

                //Displaying current song progress
                // playing
                //tvProgressLeft.setText("" + utils.milliSecondsToTimer(currentDuration));
                // Displaying remaining time
                //tvProgressRight.setText("" + utils.milliSecondsToTimer(elapsedDuration));

                // Updating progress bar
                //int progress = (int) (utils.getProgressPercentage(currentDuration,
                        elapsedDuration));
                // Log.d("Progress", ""+progress);
                //skb.setProgress(progress);

                // Running this thread after 100
                // milliseconds
                handler.postDelayed(this, 100);
            }
        };*/
        skb.setMax(mediaplayer.getDuration());
        skb.setProgress(mediaplayer.getCurrentPosition());


        //myHandler.postDelayed(UpdateSongTime,100);
        //b2.setEnabled(true);
        //b3.setEnabled(false);
        skb.setMax(mediaplayer.getDuration());
        skb.setProgress(mediaplayer.getCurrentPosition());
        double maxtime= mediaplayer.getDuration();
        String maxt= convertf(maxtime);
        ftime.setText(maxt);
        skb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaplayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        implay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (press) {
                    mediaplayer.start();
                    upseek();

                    implay.setBackgroundResource(R.drawable.pause);
                    press = false;
                } else {
                    mediaplayer.pause();
                    implay.setBackgroundResource(R.drawable.play);
                    press = true;
                }
            }
        });
        imloop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lp1 == 1) {
                    imloop.setBackgroundResource(R.drawable.shuffle);
                    lp1 = 2;
                } else if (lp1 == 2) {
                    imloop.setBackgroundResource(R.drawable.loop1);
                    lp1 = 3;
                } else {
                    imloop.setBackgroundResource(R.drawable.loop);
                    lp1 = 1;
                }
            }
        });


    }

    private String convertf(double maxtime) {
        return String.format("%02d:%02d",TimeUnit.MILLISECONDS.toMinutes((long) maxtime),TimeUnit.MILLISECONDS.toSeconds((long)maxtime)
                -TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)maxtime)));
    }


    public void upseek()
    {
        int curpos= mediaplayer.getCurrentPosition();
        skb.setProgress(curpos);
        String curtime= convertcur(curpos);
        ctime.setText(curtime);
        runnable= new Runnable() {
            @Override
            public void run() {
                upseek();
            }
        };
        handler.postDelayed(runnable,200);
    }

    private String convertcur(int curpos) {
        return String.format("%02d:%02d",TimeUnit.MILLISECONDS.toMinutes((long) curpos),TimeUnit.MILLISECONDS.toSeconds((long)curpos)
                -TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)curpos)));
    }

}




        //mediaplayer.start();

