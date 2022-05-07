package com.xogamer.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private LinearLayout item_ai, item_multi, item_friend;

    private CircleImageView circleImageView;
    private ImageView quit;
    private TextView textView2, textView2_1;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    String userImg = "https://firebasestorage.googleapis.com/v0/b/tic-tac-toe-ce9a6.appspot.com/o/icons8_circled_user_male_skin_type_7_48px_2.png?alt=media&token=1ac9602a-9432-4d04-97a8-23f14b652fd0";
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Players");
    DatabaseReference rqs = FirebaseDatabase.getInstance().getReference("Request");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        this.item_ai = (LinearLayout) findViewById(R.id.item_ai);
        this.item_multi = (LinearLayout) findViewById(R.id.item_multi);
        this.item_friend = (LinearLayout) findViewById(R.id.item_friend);
        this.textView2 = (TextView) findViewById(R.id.textView2);
        this.textView2_1 = (TextView) findViewById(R.id.textView2_1);
        this.circleImageView = (CircleImageView) findViewById(R.id.circleImageView);
        this.quit = (ImageView) findViewById(R.id.quit);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            if (account.getPhotoUrl() != null) {
                Glide.with(MainActivity.this).load(account.getPhotoUrl()).into(circleImageView);
            } else {
                Glide.with(MainActivity.this).load(userImg).into(circleImageView);
            }
            textView2_1.setText(account.getDisplayName());
            textView2.setText(account.getId());

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", account.getId());
            hashMap.put("name", account.getDisplayName());
            try {
                hashMap.put("photoUrl", account.getPhotoUrl().toString());
            } catch (Exception e) {
                hashMap.put("photoUrl", userImg);
            }

            ref.push().setValue(hashMap);

            rqs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Request player = dataSnapshot.getValue(Request.class);

                        if (player.getId().equals(account.getId()) && player.isPlay().equals("false")) {

                            try {
                                DialogAcceptFriend acceptFriend = new DialogAcceptFriend(MainActivity.this);
                                acceptFriend.getAccept_player_name().setText(player.getFriendName());
                                try {
                                    Glide.with(MainActivity.this).load(player.getPhotoUrl()).into(acceptFriend.getAccept_player_image());
                                } catch (Exception e) {
                                    try {
                                        Glide.with(MainActivity.this).load(userImg).into(acceptFriend.getAccept_player_image());
                                    } catch (Exception e0) {
                                    }
                                }
                                acceptFriend.getAccept_player_button().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "Start", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getApplicationContext(), PlayActivity.class);

                                        intent.putExtra("play", "item_friend");
                                        intent.putExtra("FriendId", player.getFriendId());
                                        intent.putExtra("id", player.getId());

                                        startActivities(new Intent[]{intent});

                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("play", "true");
                                        dataSnapshot.getRef().updateChildren(hashMap);

                                        acceptFriend.dismiss();
                                    }
                                });
                                acceptFriend.getRefuse_player_button().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("play", "");
                                        dataSnapshot.getRef().updateChildren(hashMap);

                                        acceptFriend.dismiss();
                                    }
                                });
                                acceptFriend.build();
                            } catch (Exception e) {
                            }

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        //checkGameRequest(account.getId());

        item_ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);

                intent.putExtra("play", "item_ai");

                startActivities(new Intent[]{intent});
            }
        });

        item_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);

                intent.putExtra("play", "item_multi");

                startActivities(new Intent[]{intent});
            }
        });

        item_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (account == null) {
                    SignIn();
                } else {
                    DialogWithFriend friend = new DialogWithFriend(MainActivity.this);
                    friend.getAccept().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            friend.dismiss();
                            //search for player
                            DialogSearch search = new DialogSearch(MainActivity.this);

                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        Players player = dataSnapshot.getValue(Players.class);

                                        if (player.getId().equals(friend.getEdttext().getText().toString())) {

                                            Glide.with(MainActivity.this).load(player.getPhotoUrl()).into(search.getPlayer_image());
                                            search.getPlayer_name().setText(player.getName());
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            search.getPlayer().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (search.getPlayer_name().getText().toString().equals(getString(R.string.no_player))) {
                                        Toast.makeText(MainActivity.this, getString(R.string.no_player), Toast.LENGTH_SHORT).show();
                                        search.dismiss();
                                    } else {
                                        //Send And Wait (open popup charged dialogue)
                                        //progress Dialog
                                        try {

                                        } catch (Exception e) {
                                            Toast.makeText(MainActivity.this, "Exception", Toast.LENGTH_SHORT).show();
                                        }

                                        HashMap<String, Object> hashMap1 = new HashMap<>();
                                        hashMap1.put("id", friend.getEdttext().getText().toString());
                                        hashMap1.put("name", search.getPlayer_name().getText().toString());
                                        try {
                                            hashMap1.put("photoUrl", account.getPhotoUrl().toString());
                                        } catch (Exception e) {
                                            hashMap1.put("photoUrl", userImg);
                                        }
                                        hashMap1.put("friendName", account.getDisplayName());
                                        hashMap1.put("FriendId", account.getId());
                                        hashMap1.put("play", "false");

                                        rqs.push().setValue(hashMap1);


                                        Toast.makeText(MainActivity.this, "Request: to " + search.getPlayer_name().getText().toString(), Toast.LENGTH_SHORT).show();
                                        search.dismiss();

                                        //start waiting accept of player friend
                                        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                                        progressDialog.show();
                                        progressDialog.setContentView(R.layout.dialog_chargement);
                                        progressDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

                                        rqs.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                    Request request = dataSnapshot.getValue(Request.class);
                                                    if (request.getFriendId().equals(account.getId()) && request.getId().equals(friend.getEdttext().getText().toString())
                                                            && request.isPlay().equals("true")) {

                                                        finish();
                                                        progressDialog.dismiss();
                                                        Toast.makeText(MainActivity.this, "Start", Toast.LENGTH_SHORT).show();

                                                        Intent intent = new Intent(getApplicationContext(), PlayActivity.class);

                                                        intent.putExtra("play", "item_friend");
                                                        intent.putExtra("FriendId", request.getFriendId());
                                                        intent.putExtra("id", request.getId());

                                                        startActivities(new Intent[]{intent});

                                                        HashMap<String, Object> hashMap = new HashMap<>();
                                                        hashMap.put("play", "");
                                                        dataSnapshot.getRef().updateChildren(hashMap);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }
                            });

                            search.build();
                        }
                    });
                    friend.getCopy().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Email Subject");
                            intent.putExtra(Intent.EXTRA_TEXT, account.getId());
                            startActivity(Intent.createChooser(intent, getString(R.string.app_name)));
                        }
                    });
                    friend.build();
                }
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
                    }
                });
            }
        });
    }

    private void SignIn() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);

                finish();
                startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }
}