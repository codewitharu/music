package com.example.mymusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class ActivityVideo extends AppCompatActivity {
    private MediaPlayer videoplayer;
    private SurfaceView surfaceView;
   private SeekBar seekBar;
   private ImageButton playpause;
   private TextView startview, endview;
   private Boolean f= true;
   Handler handler= new Handler();
   Runnable runnable;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        videoplayer.stop();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        surfaceView= (SurfaceView) findViewById(R.id.surfaceVideo);
        seekBar= (SeekBar) findViewById(R.id.seekBar4);
        playpause= (ImageButton) findViewById(R.id.playButton);
        startview= (TextView) findViewById(R.id.starttime);
        endview= (TextView) findViewById(R.id.lasttime);
        videoplayer= MediaPlayer.create(this,R.raw.us);
        seekBar.setMax(videoplayer.getDuration());
        seekBar.setProgress(videoplayer.getCurrentPosition());
        double mxtm= videoplayer.getDuration();
        String endtime= cnvt(mxtm);
        endview.setText(endtime);

        surfaceView.setKeepScreenOn(true);
        SurfaceHolder surfaceHolder= surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                videoplayer.setDisplay(surfaceHolder);

            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    videoplayer.seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f)
                {
                    videoplayer.start();
                    playpause.setBackgroundResource(R.drawable.pause);
                    f=false;
                    seekup();
                }
                else
                {
                    videoplayer.pause();
                    playpause.setBackgroundResource(R.drawable.play);
                    f=true;
                }

            }
        });

    }

    private String cnvt(double mxtm) {
        return String.format("%02d:%02d",TimeUnit.MILLISECONDS.toMinutes((long) mxtm),TimeUnit.MILLISECONDS.toSeconds((long)mxtm)
                -TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)mxtm)));
    }
    public void seekup()
    {
        int curpos= videoplayer.getCurrentPosition();
        seekBar.setProgress(curpos);
        String curtime= cnvt(curpos);
        startview.setText(curtime);
        runnable= new Runnable() {
            @Override
            public void run() {
                seekup();
            }
        };
        handler.postDelayed(runnable,200);
    }
}