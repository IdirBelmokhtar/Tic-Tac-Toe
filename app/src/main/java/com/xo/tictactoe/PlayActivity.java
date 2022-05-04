package com.xo.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {

    private String s;
    private ImageView item1_, item2_, item3_, item4_, item5_, item6_, item7_, item8_, item9_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        getSupportActionBar().hide();

        findItems();

        s = getIntent().getStringExtra("play");

        play();
    }

    private void findItems() {
        this.item1_ = (ImageView) findViewById(R.id.item1_);
        this.item2_ = (ImageView) findViewById(R.id.item2_);
        this.item3_ = (ImageView) findViewById(R.id.item3_);
        this.item4_ = (ImageView) findViewById(R.id.item4_);
        this.item5_ = (ImageView) findViewById(R.id.item5_);
        this.item6_ = (ImageView) findViewById(R.id.item6_);
        this.item7_ = (ImageView) findViewById(R.id.item7_);
        this.item8_ = (ImageView) findViewById(R.id.item8_);
        this.item9_ = (ImageView) findViewById(R.id.item9_);
    }

    private void play() {
        if (s.equals("item_ai")) {
            AiPlay();
        }
        if (s.equals("item_multi")) {
            MultiPlay();
        }
        if (s.equals("item_friend")) {
            FriendPlay();
        }
    }

    private void AiPlay() {

    }

    private void MultiPlay() {
        item1_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item1_.getVisibility() == View.INVISIBLE) {
                    item1_.setImageResource(R.drawable.ic_x);
                    item1_.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(PlayActivity.this, "visible", Toast.LENGTH_SHORT).show();
                }
            }
        });
        item2_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item2_.getVisibility() == View.INVISIBLE) {
                    item2_.setImageResource(R.drawable.ic_o);
                    item2_.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(PlayActivity.this, "visible", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void FriendPlay() {

    }


}