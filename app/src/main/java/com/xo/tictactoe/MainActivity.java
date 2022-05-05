package com.xo.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private LinearLayout item_ai, item_multi, item_friend;

    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        this.item_ai = (LinearLayout) findViewById(R.id.item_ai);
        this.item_multi = (LinearLayout) findViewById(R.id.item_multi);
        this.item_friend = (LinearLayout) findViewById(R.id.item_friend);

        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();*/

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
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                //startActivity(signInIntent,RC_SIGN_IN);

                Intent intent = new Intent(getApplicationContext(),PlayActivity.class);

                intent.putExtra("play","item_friend");

                //startActivities(new Intent[]{intent});
            }
        });
    }
}