package com.xogamer.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayActivity extends AppCompatActivity {

    private String s;
    private CircleImageView circleImageView2, circle_player_1, circle_player_2;
    private LinearLayout item1, item2, item3, item4, item5, item6, item7, item8, item9;
    private ImageView item1_, item2_, item3_, item4_, item5_, item6_, item7_, item8_, item9_;
    private ImageView win_vertical_1, win_vertical_2, win_vertical_3;
    private ImageView win_horizontal_1, win_horizontal_2, win_horizontal_3;
    private ImageView win_diagonal_1, win_diagonal_2;
    private ImageView reset;

    private Boolean turn = false;
    //for X player
    private Boolean itm1, itm2, itm3, itm4, itm5, itm6, itm7, itm8, itm9;
    //for O player
    private Boolean itm1_, itm2_, itm3_, itm4_, itm5_, itm6_, itm7_, itm8_, itm9_;

    private TextView nbr_win_p1, nbr_win_p2;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Parties");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        getSupportActionBar().hide();

        findItems();

        s = getIntent().getStringExtra("play");

        itm1 = itm2 = itm3 = itm4 = itm5 = itm6 = itm7 = itm8 = itm9 = false;
        itm1_ = itm2_ = itm3_ = itm4_ = itm5_ = itm6_ = itm7_ = itm8_ = itm9_ = false;

        play();
    }

    private void findItems() {
        this.circleImageView2 = (CircleImageView) findViewById(R.id.circleImageView2);
        this.circle_player_1 = (CircleImageView) findViewById(R.id.circle_player_1);
        this.circle_player_2 = (CircleImageView) findViewById(R.id.circle_player_2);

        this.item1 = (LinearLayout) findViewById(R.id.item1);
        this.item1_ = (ImageView) findViewById(R.id.item1_);

        this.item2 = (LinearLayout) findViewById(R.id.item2);
        this.item2_ = (ImageView) findViewById(R.id.item2_);

        this.item3 = (LinearLayout) findViewById(R.id.item3);
        this.item3_ = (ImageView) findViewById(R.id.item3_);

        this.item4 = (LinearLayout) findViewById(R.id.item4);
        this.item4_ = (ImageView) findViewById(R.id.item4_);

        this.item5 = (LinearLayout) findViewById(R.id.item5);
        this.item5_ = (ImageView) findViewById(R.id.item5_);

        this.item6 = (LinearLayout) findViewById(R.id.item6);
        this.item6_ = (ImageView) findViewById(R.id.item6_);

        this.item7 = (LinearLayout) findViewById(R.id.item7);
        this.item7_ = (ImageView) findViewById(R.id.item7_);

        this.item8 = (LinearLayout) findViewById(R.id.item8);
        this.item8_ = (ImageView) findViewById(R.id.item8_);

        this.item9 = (LinearLayout) findViewById(R.id.item9);
        this.item9_ = (ImageView) findViewById(R.id.item9_);

        this.win_vertical_1 = (ImageView) findViewById(R.id.win_vertical_1);
        this.win_horizontal_1 = (ImageView) findViewById(R.id.win_horizontal_1);

        this.win_vertical_2 = (ImageView) findViewById(R.id.win_vertical_2);
        this.win_horizontal_2 = (ImageView) findViewById(R.id.win_horizontal_2);

        this.win_vertical_3 = (ImageView) findViewById(R.id.win_vertical_3);
        this.win_horizontal_3 = (ImageView) findViewById(R.id.win_horizontal_3);

        this.win_diagonal_1 = (ImageView) findViewById(R.id.win_diagonal_1);
        this.win_diagonal_2 = (ImageView) findViewById(R.id.win_diagonal_2);

        this.nbr_win_p1 = (TextView) findViewById(R.id.nbr_win_p1);
        this.nbr_win_p2 = (TextView) findViewById(R.id.nbr_win_p2);

        this.reset = (ImageView) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetGame("");
            }
        });
    }

    private void play() {
        Glide.with(PlayActivity.this).load(R.drawable.ic_icons8_bot).into(circleImageView2);
        if (s.equals("item_ai")) {
            if (turn.equals(false)) {
                circle_player_1.setVisibility(View.VISIBLE);
                circle_player_2.setVisibility(View.GONE);
                XAiPlay(getResources().getDrawable(R.drawable.ic_x));
            } else {
                circle_player_2.setVisibility(View.VISIBLE);
                circle_player_1.setVisibility(View.GONE);
                //OAiPlay(getResources().getDrawable(R.drawable.ic_o));
                //if bot is first the player
                boolean v = false;
            }
        }
        if (s.equals("item_multi")) {
            if (turn.equals(false)) {
                circle_player_1.setVisibility(View.VISIBLE);
                circle_player_2.setVisibility(View.GONE);
                Xplay_(getResources().getDrawable(R.drawable.ic_x));
            } else {
                circle_player_2.setVisibility(View.VISIBLE);
                circle_player_1.setVisibility(View.GONE);
                Oplay_(getResources().getDrawable(R.drawable.ic_o));
            }
        }
        if (s.equals("item_friend")) {

            String player1 = getIntent().getStringExtra("id");
            String player2 = getIntent().getStringExtra("FriendId");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Parties part = dataSnapshot.getValue(Parties.class);
                        if (part.getPlayer1().equals(player1) && part.getPlayer2().equals(player2) && part.getPlay().equals("true")) {

                            if (part.getItm1().equals("false") && part.getItm2().equals("false") && part.getItm3().equals("false") && part.getItm4().equals("false") && part.getItm5().equals("false") && part.getItm6().equals("false") && part.getItm7().equals("false") && part.getItm8().equals("false") && part.getItm9().equals("false")){
                                restItems();
                            }

                            if (part.getItm1().equals("x")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_x)).into(item1_);
                                item1_.setVisibility(View.VISIBLE);
                                itm1 = true;
                            }
                            if (part.getItm1().equals("o")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_o)).into(item1_);
                                item1_.setVisibility(View.VISIBLE);
                                itm1_ = true;
                            }
                            if (part.getItm2().equals("x")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_x)).into(item2_);
                                item2_.setVisibility(View.VISIBLE);
                                itm2 = true;
                            }
                            if (part.getItm2().equals("o")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_o)).into(item2_);
                                item2_.setVisibility(View.VISIBLE);
                                itm2_ = true;
                            }
                            if (part.getItm3().equals("x")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_x)).into(item3_);
                                item3_.setVisibility(View.VISIBLE);
                                itm3 = true;
                            }
                            if (part.getItm3().equals("o")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_o)).into(item1_);
                                item3_.setVisibility(View.VISIBLE);
                                itm3_ = true;
                            }
                            if (part.getItm4().equals("x")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_x)).into(item4_);
                                item4_.setVisibility(View.VISIBLE);
                                itm4 = true;
                            }
                            if (part.getItm7().equals("o")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_o)).into(item4_);
                                item4_.setVisibility(View.VISIBLE);
                                itm4_ = true;
                            }
                            if (part.getItm5().equals("x")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_x)).into(item5_);
                                item5_.setVisibility(View.VISIBLE);
                                itm5 = true;
                            }
                            if (part.getItm5().equals("o")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_o)).into(item5_);
                                item5_.setVisibility(View.VISIBLE);
                                itm5_ = true;
                            }
                            if (part.getItm6().equals("x")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_x)).into(item6_);
                                item6_.setVisibility(View.VISIBLE);
                                itm6 = true;
                            }
                            if (part.getItm6().equals("o")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_o)).into(item6_);
                                item6_.setVisibility(View.VISIBLE);
                                itm6_ = true;
                            }
                            if (part.getItm7().equals("x")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_x)).into(item7_);
                                item7_.setVisibility(View.VISIBLE);
                                itm7 = true;
                            }
                            if (part.getItm7().equals("o")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_o)).into(item7_);
                                item7_.setVisibility(View.VISIBLE);
                                itm7_ = true;
                            }
                            if (part.getItm8().equals("x")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_x)).into(item8_);
                                item8_.setVisibility(View.VISIBLE);
                                itm8 = true;
                            }
                            if (part.getItm8().equals("o")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_o)).into(item8_);
                                item8_.setVisibility(View.VISIBLE);
                                itm8_ = true;
                            }

                            if (part.getItm9().equals("x")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_x)).into(item9_);
                                item9_.setVisibility(View.VISIBLE);
                                itm9 = true;
                            }
                            if (part.getItm9().equals("o")) {
                                Glide.with(PlayActivity.this).load(getResources().getDrawable(R.drawable.ic_o)).into(item9_);
                                item9_.setVisibility(View.VISIBLE);
                                itm9_ = true;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Parties parties = dataSnapshot.getValue(Parties.class);
                        if (parties.getPlayer1().equals(player1) && parties.getPlayer2().equals(player2)
                                && parties.getPlay().equals("true")) {

                            if (parties.getTurn().equals(false)){
                                circle_player_1.setVisibility(View.VISIBLE);
                                circle_player_2.setVisibility(View.GONE);
                                XFriendPlay_(dataSnapshot,parties.getTurn());

                            }else {
                                circle_player_2.setVisibility(View.VISIBLE);
                                circle_player_1.setVisibility(View.GONE);
                                OFriendPlay_(dataSnapshot,parties.getTurn());
                            }

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            /*reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Parties parties = dataSnapshot.getValue(Parties.class);
                        if (parties.getPlayer1().equals(player1) && parties.getPlayer2().equals(player2) &&
                                parties.getTurn().equals(false) && parties.getPlay().equals("true")) {

                            circle_player_1.setVisibility(View.VISIBLE);
                            circle_player_2.setVisibility(View.GONE);
                            XFriendPlay_(getResources().getDrawable(R.drawable.ic_x), dataSnapshot, parties.getTurn()
                                    , parties.getItm1(), parties.getItm2(), parties.getItm3(), parties.getItm4(), parties.getItm5(), parties.getItm6(), parties.getItm7(), parties.getItm8(), parties.getItm9()
                                    , parties.getItm1_(), parties.getItm2_(), parties.getItm3_(), parties.getItm4_(), parties.getItm5_(), parties.getItm6_(), parties.getItm7_(), parties.getItm8_(), parties.getItm9_());

                        } else if (parties.getPlayer1().equals(player1) && parties.getPlayer2().equals(player2) &&
                                parties.getTurn().equals(true) && parties.getPlay().equals("true")) {

                            circle_player_2.setVisibility(View.VISIBLE);
                            circle_player_1.setVisibility(View.GONE);
                            OFriendPlay_(getResources().getDrawable(R.drawable.ic_o), dataSnapshot, parties.getTurn()
                                    , parties.getItm1(), parties.getItm2(), parties.getItm3(), parties.getItm4(), parties.getItm5(), parties.getItm6(), parties.getItm7(), parties.getItm8(), parties.getItm9()
                                    , parties.getItm1_(), parties.getItm2_(), parties.getItm3_(), parties.getItm4_(), parties.getItm5_(), parties.getItm6_(), parties.getItm7_(), parties.getItm8_(), parties.getItm9_());


                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });*/
        }
    }



    private void XAiPlay(Drawable drawable) {
        Drawable drawable_ = getResources().getDrawable(R.drawable.ic_o);
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithAi(item1_, drawable);
                itm1 = true;
                checkForWinner();
                OAiPlay(drawable_);
            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item2_, drawable);
                itm2 = true;
                checkForWinner();
                OAiPlay(drawable_);
            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item3_, drawable);
                itm3 = true;
                checkForWinner();
                OAiPlay(drawable_);
            }
        });
        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item4_, drawable);
                itm4 = true;
                checkForWinner();
                OAiPlay(drawable_);
            }
        });
        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item5_, drawable);
                itm5 = true;
                checkForWinner();
                OAiPlay(drawable_);
            }
        });
        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item6_, drawable);
                itm6 = true;
                checkForWinner();
                OAiPlay(drawable_);
            }
        });
        item7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item7_, drawable);
                itm7 = true;
                checkForWinner();
                OAiPlay(drawable_);
            }
        });
        item8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item8_, drawable);
                itm8 = true;
                checkForWinner();
                OAiPlay(drawable_);
            }
        });
        item9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item9_, drawable);
                itm9 = true;
                checkForWinner();
                OAiPlay(drawable_);
            }
        });
    }
    private void OAiPlay(Drawable drawable) {
        //-----!
        /*        AIFunction(   v, v, v
                , v, v, v
                , v, v, v
                , getResources().getDrawable(R.drawable.ic_o)
                , v, v, v
                , v, v, v
                , v, v, v
                , item8_, itm8_);*/

        //first start
        boolean X = true;
        boolean O = true;
        boolean v = false;
        //-------------------
        AIFunction(X, v, v
                , v, v, v
                , v, v, v
                , drawable
                , v, v, v
                , v, v, v
                , v, v, v
                , item6_, itm6_);
        AIFunction(X, X, v
                , v, v, v
                , v, v, v
                , drawable
                , v, v, v
                , v, v, O
                , v, v, v
                , item9_, itm9_);
        AIFunction(X, X, v
                , v, X, v
                , v, v, v
                , drawable
                , v, v, v
                , v, v, O
                , v, v, O
                , item7_, itm7_);

        Random random = new Random();

        boolean[] bool = {itm1, itm2, itm3, itm4, itm5, itm6, itm7, itm8, itm9};


        int i = random.nextInt(bool.length);
        Toast.makeText(PlayActivity.this, i + " : " + bool[i], Toast.LENGTH_SHORT).show();


    }

    private void AIFunction(boolean x1, boolean x2, boolean x3
            , boolean x4, boolean x5, boolean x6
            , boolean x7, boolean x8, boolean x9
            , Drawable drawable
            , boolean o1, boolean o2, boolean o3
            , boolean o4, boolean o5, boolean o6
            , boolean o7, boolean o8, boolean o9
            , ImageView item_, Boolean itm_) {
        if (
                itm1.equals(x1) && itm2.equals(x2) && itm3.equals(x3) &&
                        itm4.equals(x4) && itm5.equals(x5) && itm6.equals(x6) &&
                        itm7.equals(x7) && itm8.equals(x8) && itm9.equals(x9)
                        &&
                        itm1_.equals(o1) && itm2_.equals(o2) && itm3_.equals(o3) &&
                        itm4_.equals(o4) && itm5_.equals(o5) && itm6_.equals(o6) &&
                        itm7_.equals(o7) && itm8_.equals(o8) && itm9_.equals(o9)

        ) {
            putItemPlay(item_, drawable);
            itm_ = true;
            checkForWinner();
        }
    }

    private void Xplay_(Drawable drawable) {
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item1_, drawable);
                itm1 = true;
                checkForWinner();
            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item2_, drawable);
                itm2 = true;
                checkForWinner();
            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item3_, drawable);
                itm3 = true;
                checkForWinner();
            }
        });
        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item4_, drawable);
                itm4 = true;
                checkForWinner();
            }
        });
        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item5_, drawable);
                itm5 = true;
                checkForWinner();
            }
        });
        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item6_, drawable);
                itm6 = true;
                checkForWinner();
            }
        });
        item7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item7_, drawable);
                itm7 = true;
                checkForWinner();
            }
        });
        item8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item8_, drawable);
                itm8 = true;
                checkForWinner();
            }
        });
        item9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item9_, drawable);
                itm9 = true;
                checkForWinner();
            }
        });
    }
    private void Oplay_(Drawable drawable) {
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item1_, drawable);
                itm1_ = true;
                checkForWinner();
            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item2_, drawable);
                itm2_ = true;
                checkForWinner();
            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item3_, drawable);
                itm3_ = true;
                checkForWinner();
            }
        });
        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item4_, drawable);
                itm4_ = true;
                checkForWinner();
            }
        });
        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item5_, drawable);
                itm5_ = true;
                checkForWinner();
            }
        });
        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item6_, drawable);
                itm6_ = true;
                checkForWinner();
            }
        });
        item7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item7_, drawable);
                itm7_ = true;
                checkForWinner();
            }
        });
        item8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item8_, drawable);
                itm8_ = true;
                checkForWinner();
            }
        });
        item9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlay(item9_, drawable);
                itm9_ = true;
                checkForWinner();
            }
        });
    }

    private void XFriendPlay_(DataSnapshot dataSnapshot, Boolean turn) {
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item1_, turn, "itm1", "x");
                checkForWinnerOnline(dataSnapshot, turn.toString());

            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item2_, turn, "itm2", "x");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item3_, turn, "itm3", "x");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item4_, turn, "itm4", "x");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item5_, turn, "itm5", "x");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item6_, turn, "itm6", "x");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
        item7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item7_, turn, "itm7", "x");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
        item8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item8_, turn, "itm8", "x");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
        item9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item9_, turn, "itm9", "x");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
    }
    private void OFriendPlay_(DataSnapshot dataSnapshot, Boolean turn) {
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item1_, turn, "itm1", "o");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item2_, turn, "itm2", "o");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item3_, turn, "itm3", "o");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item4_, turn, "itm4", "o");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item5_, turn, "itm5", "o");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item6_, turn, "itm6", "o");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
        item7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item7_, turn, "itm7", "o");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
        item8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item8_, turn, "itm8", "o");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
        item9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putItemPlayWithFriend(dataSnapshot,item9_, turn, "itm9", "o");
                checkForWinnerOnline(dataSnapshot, turn.toString());
            }
        });
    }


    private void putItemPlayWithAi(ImageView item, Drawable drawable) {
        if (item.getVisibility() == View.GONE) {
            Glide.with(PlayActivity.this).load(drawable).into(item);
            item.setVisibility(View.VISIBLE);
            //passer a l'autre player

            turn = !turn;
        } else {
            //Toast.makeText(PlayActivity.this, "is visible", Toast.LENGTH_SHORT).show();

        }
    }
    private void putItemPlay(ImageView item, Drawable drawable) {
        if (item.getVisibility() == View.GONE) {
            Glide.with(PlayActivity.this).load(drawable).into(item);
            item.setVisibility(View.VISIBLE);
            //passer a l'autre player

            turn = !turn;
            play();
        } else {
            //Toast.makeText(PlayActivity.this, "is visible", Toast.LENGTH_SHORT).show();

        }
    }
    private void putItemPlayWithFriend(DataSnapshot dataSnapshot, ImageView item, Boolean turn, String itm, String s) {
        if (item.getVisibility() == View.GONE) {

            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put(itm,s);
            hashMap.put("turn",!turn);

            dataSnapshot.getRef().updateChildren(hashMap);

            play();
        } else {
            //Toast.makeText(PlayActivity.this, "is visible", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkForWinner() {
        //if X player win
        try {

            if (itm1.equals(itm2) && itm1.equals(itm3) && itm1.equals(true)) {
                win_horizontal_1.setVisibility(View.VISIBLE);
                ResetGame(String.valueOf(turn));
            }
            if (itm4.equals(itm5) && itm4.equals(itm6) && itm4.equals(true)) {
                win_horizontal_2.setVisibility(View.VISIBLE);
                ResetGame(String.valueOf(turn));
            }
            if (itm7.equals(itm8) && itm7.equals(itm9) && itm7.equals(true)) {
                win_horizontal_3.setVisibility(View.VISIBLE);
                ResetGame(String.valueOf(turn));
            }
            if (itm1.equals(itm4) && itm1.equals(itm7) && itm1.equals(true)) {
                win_vertical_1.setVisibility(View.VISIBLE);
                ResetGame(String.valueOf(turn));
            }
            if (itm2.equals(itm5) && itm2.equals(itm8) && itm2.equals(true)) {
                win_vertical_2.setVisibility(View.VISIBLE);
                ResetGame(String.valueOf(turn));
            }
            if (itm3.equals(itm6) && itm3.equals(itm9) && itm3.equals(true)) {
                win_vertical_3.setVisibility(View.VISIBLE);
                ResetGame(String.valueOf(turn));
            }
            if (itm1.equals(itm5) && itm1.equals(itm9) && itm1.equals(true)) {
                win_diagonal_1.setVisibility(View.VISIBLE);
                ResetGame(String.valueOf(turn));
            }
            if (itm3.equals(itm5) && itm3.equals(itm7) && itm3.equals(true)) {
                win_diagonal_2.setVisibility(View.VISIBLE);
                ResetGame(String.valueOf(turn));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //if O player win
        try {

            if (itm1_.equals(itm2_) && itm1_.equals(itm3_) && itm1_.equals(true)) {
                win_horizontal_1.setVisibility(View.VISIBLE);
                ResetGame(String.valueOf(turn));
            }
            if (itm4_.equals(itm5_) && itm4_.equals(itm6_) && itm4_.equals(true)) {
                win_horizontal_2.setVisibility(View.VISIBLE);
                ResetGame(String.valueOf(turn));
            }
            if (itm7_.equals(itm8_) && itm7_.equals(itm9_) && itm7_.equals(true)) {
                win_horizontal_3.setVisibility(View.VISIBLE);
                ResetGame(String.valueOf(turn));
            }
            if (itm1_.equals(itm4_) && itm1_.equals(itm7_) && itm1_.equals(true)) {
                win_vertical_1.setVisibility(View.VISIBLE);
                ResetGame(String.valueOf(turn));
            }
            if (itm2_.equals(itm5_) && itm2_.equals(itm8_) && itm2_.equals(true)) {
                win_vertical_2.setVisibility(View.VISIBLE);
                ResetGame(String.valueOf(turn));
            }
            if (itm3_.equals(itm6_) && itm3_.equals(itm9_) && itm3_.equals(true)) {
                win_vertical_3.setVisibility(View.VISIBLE);
                ResetGame(String.valueOf(turn));
            }
            if (itm1_.equals(itm5_) && itm1_.equals(itm9_) && itm1_.equals(true)) {
                win_diagonal_1.setVisibility(View.VISIBLE);
                ResetGame(String.valueOf(turn));
            }
            if (itm3_.equals(itm5_) && itm3_.equals(itm7_) && itm3_.equals(true)) {
                win_diagonal_2.setVisibility(View.VISIBLE);
                ResetGame(String.valueOf(turn));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //if draw no win
        try {
            if (win_vertical_1.getVisibility() == View.GONE && win_vertical_2.getVisibility() == View.GONE && win_vertical_3.getVisibility() == View.GONE &&
                    win_horizontal_1.getVisibility() == View.GONE && win_horizontal_2.getVisibility() == View.GONE && win_horizontal_3.getVisibility() == View.GONE &&
                    win_diagonal_1.getVisibility() == View.GONE && win_diagonal_2.getVisibility() == View.GONE
                    &&
                    item1_.getVisibility() == View.VISIBLE && item2_.getVisibility() == View.VISIBLE && item3_.getVisibility() == View.VISIBLE &&
                    item4_.getVisibility() == View.VISIBLE && item5_.getVisibility() == View.VISIBLE && item6_.getVisibility() == View.VISIBLE &&
                    item7_.getVisibility() == View.VISIBLE && item8_.getVisibility() == View.VISIBLE && item9_.getVisibility() == View.VISIBLE
            ) {
                Toast.makeText(PlayActivity.this, "Draw", Toast.LENGTH_SHORT).show();
                ResetGame("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void checkForWinnerOnline(DataSnapshot dataSnapshot, String turn) {
        //if X player win
        try {

            if (itm1.equals(itm2) && itm1.equals(itm3) && itm1.equals(true)) {
                win_horizontal_1.setVisibility(View.VISIBLE);
                ResetGameOnline(dataSnapshot, String.valueOf(turn));
            }
            if (itm4.equals(itm5) && itm4.equals(itm6) && itm4.equals(true)) {
                win_horizontal_2.setVisibility(View.VISIBLE);
                ResetGameOnline(dataSnapshot, String.valueOf(turn));
            }
            if (itm7.equals(itm8) && itm7.equals(itm9) && itm7.equals(true)) {
                win_horizontal_3.setVisibility(View.VISIBLE);
                ResetGameOnline(dataSnapshot, String.valueOf(turn));
            }
            if (itm1.equals(itm4) && itm1.equals(itm7) && itm1.equals(true)) {
                win_vertical_1.setVisibility(View.VISIBLE);
                ResetGameOnline(dataSnapshot, String.valueOf(turn));
            }
            if (itm2.equals(itm5) && itm2.equals(itm8) && itm2.equals(true)) {
                win_vertical_2.setVisibility(View.VISIBLE);
                ResetGameOnline(dataSnapshot, String.valueOf(turn));
            }
            if (itm3.equals(itm6) && itm3.equals(itm9) && itm3.equals(true)) {
                win_vertical_3.setVisibility(View.VISIBLE);
                ResetGameOnline(dataSnapshot, String.valueOf(turn));
            }
            if (itm1.equals(itm5) && itm1.equals(itm9) && itm1.equals(true)) {
                win_diagonal_1.setVisibility(View.VISIBLE);
                ResetGameOnline(dataSnapshot, String.valueOf(turn));
            }
            if (itm3.equals(itm5) && itm3.equals(itm7) && itm3.equals(true)) {
                win_diagonal_2.setVisibility(View.VISIBLE);
                ResetGameOnline(dataSnapshot, String.valueOf(turn));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //if O player win
        try {

            if (itm1_.equals(itm2_) && itm1_.equals(itm3_) && itm1_.equals(true)) {
                win_horizontal_1.setVisibility(View.VISIBLE);
                ResetGameOnline(dataSnapshot, String.valueOf(turn));
            }
            if (itm4_.equals(itm5_) && itm4_.equals(itm6_) && itm4_.equals(true)) {
                win_horizontal_2.setVisibility(View.VISIBLE);
                ResetGameOnline(dataSnapshot, String.valueOf(turn));
            }
            if (itm7_.equals(itm8_) && itm7_.equals(itm9_) && itm7_.equals(true)) {
                win_horizontal_3.setVisibility(View.VISIBLE);
                ResetGameOnline(dataSnapshot, String.valueOf(turn));
            }
            if (itm1_.equals(itm4_) && itm1_.equals(itm7_) && itm1_.equals(true)) {
                win_vertical_1.setVisibility(View.VISIBLE);
                ResetGameOnline(dataSnapshot, String.valueOf(turn));
            }
            if (itm2_.equals(itm5_) && itm2_.equals(itm8_) && itm2_.equals(true)) {
                win_vertical_2.setVisibility(View.VISIBLE);
                ResetGameOnline(dataSnapshot, String.valueOf(turn));
            }
            if (itm3_.equals(itm6_) && itm3_.equals(itm9_) && itm3_.equals(true)) {
                win_vertical_3.setVisibility(View.VISIBLE);
                ResetGameOnline(dataSnapshot, String.valueOf(turn));
            }
            if (itm1_.equals(itm5_) && itm1_.equals(itm9_) && itm1_.equals(true)) {
                win_diagonal_1.setVisibility(View.VISIBLE);
                ResetGameOnline(dataSnapshot, String.valueOf(turn));
            }
            if (itm3_.equals(itm5_) && itm3_.equals(itm7_) && itm3_.equals(true)) {
                win_diagonal_2.setVisibility(View.VISIBLE);
                ResetGameOnline(dataSnapshot, String.valueOf(turn));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //if draw no win
        try {
            if (win_vertical_1.getVisibility() == View.GONE && win_vertical_2.getVisibility() == View.GONE && win_vertical_3.getVisibility() == View.GONE &&
                    win_horizontal_1.getVisibility() == View.GONE && win_horizontal_2.getVisibility() == View.GONE && win_horizontal_3.getVisibility() == View.GONE &&
                    win_diagonal_1.getVisibility() == View.GONE && win_diagonal_2.getVisibility() == View.GONE
                    &&
                    item1_.getVisibility() == View.VISIBLE && item2_.getVisibility() == View.VISIBLE && item3_.getVisibility() == View.VISIBLE &&
                    item4_.getVisibility() == View.VISIBLE && item5_.getVisibility() == View.VISIBLE && item6_.getVisibility() == View.VISIBLE &&
                    item7_.getVisibility() == View.VISIBLE && item8_.getVisibility() == View.VISIBLE && item9_.getVisibility() == View.VISIBLE
            ) {
                Toast.makeText(PlayActivity.this, "Draw", Toast.LENGTH_SHORT).show();
                ResetGameOnline(dataSnapshot, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ResetGame(String turn) {
        if (turn.equals("false")) {
            nbr_win_p2.setText(String.valueOf(Integer.parseInt(nbr_win_p2.getText().toString()) + 1));
        } else if (turn.equals("true")) {
            nbr_win_p1.setText(String.valueOf(Integer.parseInt(nbr_win_p1.getText().toString()) + 1));
        }
        restItems();
    }
    private void ResetGameOnline(DataSnapshot dataSnapshot, String turn) {
        if (turn.equals("false")) {
            nbr_win_p2.setText(String.valueOf(Integer.parseInt(nbr_win_p2.getText().toString()) + 1));

            //start New Parties in Firebase
            newGame(false);

            dataSnapshot.getRef().removeValue();
            restItems();
        } else if (turn.equals("true")) {
            nbr_win_p1.setText(String.valueOf(Integer.parseInt(nbr_win_p1.getText().toString()) + 1));

            //start New Parties in Firebase
            newGame(false);

            dataSnapshot.getRef().removeValue();
            restItems();
        } else if (turn.equals("")) {
            nbr_win_p1.setText(String.valueOf(Integer.parseInt(nbr_win_p1.getText().toString()) + 1));

            //start New Parties in Firebase
            newGame(false);

            dataSnapshot.getRef().removeValue();
            restItems();
        }
    }

    private void newGame(boolean b) {
        DatabaseReference r1 = FirebaseDatabase.getInstance().getReference("Parties");
        HashMap<String, Object> hashMap1 = new HashMap<>();

        hashMap1.put("player1",getIntent().getStringExtra("id"));
        hashMap1.put("player2",getIntent().getStringExtra("FriendId"));
        hashMap1.put("play","true");
        hashMap1.put("turn",b);

        hashMap1.put("itm1","false");
        hashMap1.put("itm2","false");
        hashMap1.put("itm3","false");
        hashMap1.put("itm4","false");
        hashMap1.put("itm5","false");
        hashMap1.put("itm6","false");
        hashMap1.put("itm7","false");
        hashMap1.put("itm8","false");
        hashMap1.put("itm9","false");

        r1.push().setValue(hashMap1);

    }

    private void restItems() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                item1_.setVisibility(View.GONE);
                item2_.setVisibility(View.GONE);
                item3_.setVisibility(View.GONE);
                item4_.setVisibility(View.GONE);
                item5_.setVisibility(View.GONE);
                item6_.setVisibility(View.GONE);
                item7_.setVisibility(View.GONE);
                item8_.setVisibility(View.GONE);
                item9_.setVisibility(View.GONE);
                win_vertical_1.setVisibility(View.GONE);
                win_vertical_2.setVisibility(View.GONE);
                win_vertical_3.setVisibility(View.GONE);
                win_horizontal_1.setVisibility(View.GONE);
                win_horizontal_2.setVisibility(View.GONE);
                win_horizontal_3.setVisibility(View.GONE);
                win_diagonal_1.setVisibility(View.GONE);
                win_diagonal_2.setVisibility(View.GONE);

                itm1 = itm2 = itm3 = itm4 = itm5 = itm6 = itm7 = itm8 = itm9 = false;
                itm1_ = itm2_ = itm3_ = itm4_ = itm5_ = itm6_ = itm7_ = itm8_ = itm9_ = false;
            }
        }, 2000);
    }

}