package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.TimedMetaData;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
//.......................................................
    TextView possition_play,durection_play, newText;

    LinearLayout layout;
    SeekBar seekBar;
    ImageView rew, play,stop,ff_next;


    MediaPlayer player;
    Handler handler = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.layoutNewId);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.setBackgroundColor(Color.parseColor("#B42F3A"));
            }
        },3000);

        possition_play = findViewById(R.id.player_position);
        durection_play = findViewById(R.id.player_durection);
        seekBar = findViewById(R.id.seek_id);
        rew = findViewById(R.id.btt_rew);
        play = findViewById(R.id.btt_play);
        stop = findViewById(R.id.btt_pause);
        ff_next = findViewById(R.id.btt_ff);
        newText = findViewById(R.id.textViewLayoutId);

        player = MediaPlayer.create(this,R.raw.music);
        runnable = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(player.getCurrentPosition());
                handler.postDelayed(this,500);
            }
        };

        int durection = player.getDuration();
        String sDurection = convertFormat(durection);
        durection_play.setText(sDurection);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setVisibility(View.GONE);
                stop.setVisibility(View.VISIBLE);
                player.start();
                seekBar.setMax(player.getDuration());
                handler.postDelayed(runnable, 0);
                layout.setBackgroundColor(Color.parseColor("#28353B"));
                newText.setText("Play");

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
                player.pause();
                handler.removeCallbacks(runnable);
                layout.setBackgroundColor(Color.parseColor("#B42F3A"));
                newText.setText("Stop");


            }
        });
        ff_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = player.getCurrentPosition();
                int durection = player.getDuration();
                if (player.isPlaying() && durection != currentPosition){
                    currentPosition = currentPosition + 15000;
                    possition_play.setText(convertFormat(currentPosition));
                    player.seekTo(currentPosition);
                }
            }
        });
        rew.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                int currentPosition = player.getCurrentPosition();
                if (player.isPlaying() && currentPosition > 5000){
                    currentPosition = currentPosition - 10000;
                    possition_play.setText(convertFormat(currentPosition));
                    player.seekTo(currentPosition);
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    player.seekTo(progress);

                }
                possition_play.setText(convertFormat(player.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
                player.seekTo(0);
            }
        });
    }

    private String convertFormat(int durection) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(durection),
                TimeUnit.MILLISECONDS.toSeconds(durection)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(durection)));


    }

}