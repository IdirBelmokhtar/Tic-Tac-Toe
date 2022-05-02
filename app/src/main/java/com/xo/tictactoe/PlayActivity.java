package com.xo.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {

    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        getSupportActionBar().hide();

        s = getIntent().getStringExtra("play");

        play();
    }

    private void play() {
        if (s.equals("item_ai")){
            AiPlay();
        }
        if (s.equals("item_multi")){
            MultiPlay();
        }
        if (s.equals("item_friend")){
            FriendPlay();
        }
    }

    private void AiPlay() {

    }

    private void MultiPlay() {

    }

    private void FriendPlay() {

    }


}