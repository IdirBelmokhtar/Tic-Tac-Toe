package com.xo.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout item_ai, item_multi, item_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        this.item_ai = (LinearLayout) findViewById(R.id.item_ai);
        this.item_multi = (LinearLayout) findViewById(R.id.item_multi);
        this.item_friend = (LinearLayout) findViewById(R.id.item_friend);

        item_ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PlayActivity.class);

                intent.putExtra("play","item_ai");

                startActivities(new Intent[]{intent});
            }
        });

        item_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PlayActivity.class);

                intent.putExtra("play","item_multi");

                startActivities(new Intent[]{intent});
            }
        });

        item_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PlayActivity.class);

                intent.putExtra("play","item_friend");

                startActivities(new Intent[]{intent});
            }
        });
    }
}