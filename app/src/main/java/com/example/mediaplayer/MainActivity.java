package com.example.mediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Button next, previous, pause, stop;
    private MediaPlayer mediaPlayer;
    private double fullTime, startTime=0, finalTime=0;
    private Handler handler = new Handler();
    private int forwardTime = 5000, backwardTime = 5000;
    private SeekBar seekbar;
    private TextView currenttime, ftime, songName;
    private Button option;
    int id;

    public static int oneTimeOnly = 0;

public static int [] songs = {R.raw.queen, R.raw.back, R.raw.hell, R.raw.bell, R.raw.thunder};
public String [] nameSong = {
        "Queen We will rock you",
        "AC DC Back in Black",
        "AC DC Highway to hell",
        "AC DC Hells Bell",
        "AC DC Thunderstruck"
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        //String name = i.getStringExtra("name");
        id = i.getIntExtra("id", 1);

        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);

        currenttime = findViewById(R.id.currentlyTime);
        ftime = findViewById(R.id.fullTime);
        songName = findViewById(R.id.songName);
        seekbar = findViewById(R.id.seekbar);
        songName.setText(nameSong[id]);
        mediaPlayer = MediaPlayer.create(this, songs[id]);
        seekbar.setClickable(false);
        pause.setBackgroundResource(R.drawable.play1);
        stop.setEnabled(false);


        Button list = findViewById(R.id.list);

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, list.class);
                oneTimeOnly = 0;
                mediaPlayer.seekTo(0);
                mediaPlayer.pause();
                startActivity(i);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pause.getBackground().getConstantState() == getResources().getDrawable(R.drawable.play1).getConstantState()) {
                    mediaPlayer.start();
                    stop.setEnabled(true);
                    pause.setBackgroundResource(R.drawable.pause);
                    fullTime = mediaPlayer.getDuration();
                    startTime = mediaPlayer.getCurrentPosition();

                    if (oneTimeOnly == 0) {
                        seekbar.setMax((int) fullTime);
                        oneTimeOnly = 1;
                    }

                    currenttime.setText(String.format("%d M %d s", TimeUnit.MILLISECONDS.toMinutes((long) startTime), (TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))));
                    ftime.setText(String.format("%d M %d s", TimeUnit.MILLISECONDS.toMinutes((long) fullTime), (TimeUnit.MILLISECONDS.toSeconds((long) fullTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) fullTime)))));

                    seekbar.setProgress((int) startTime);
                    handler.postDelayed(UpdateSongTime, 0);
                }
                else
                {
                    mediaPlayer.pause();
                    pause.setBackgroundResource(R.drawable.play1);
                }

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop.setEnabled(false);
                pause.setBackgroundResource(R.drawable.play1);
                mediaPlayer.seekTo(0);
                mediaPlayer.pause();
                startTime = 0;
                currenttime.setText(String.format("%d M %d s", TimeUnit.MILLISECONDS.toMinutes((long) startTime), (TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))));
            }
        });
        seekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                seekbar.setEnabled(true);
                return false;
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser)
                {

                    mediaPlayer.seekTo(progress);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                id++;
                id = id%5;
                changeMusic(id);
            }
        });
    }
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            currenttime.setText(String.format("%d M %d s",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);


            handler.postDelayed(this, 100);

        }
    };
    public void next(View view)
    {
        id++;
        id = id%5;
        changeMusic(id);
    }
    public void previous(View view)
    {
        id--;
        if(id < 0) id = songs.length - 1;
        changeMusic(id);

    }
    public void changeMusic(int id)
    {
        oneTimeOnly = 0;
        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
        startTime = 0;
        currenttime.setText(String.format("%d M %d s", TimeUnit.MILLISECONDS.toMinutes((long) startTime), (TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))));
        mediaPlayer = MediaPlayer.create(this, songs[id]);
        songName.setText(nameSong[id]);
        mediaPlayer.start();
        fullTime = mediaPlayer.getDuration();
        seekbar.setMax((int) fullTime);
        ftime.setText(String.format("%d M %d s", TimeUnit.MILLISECONDS.toMinutes((long) fullTime), (TimeUnit.MILLISECONDS.toSeconds((long) fullTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) fullTime)))));
    }
}
