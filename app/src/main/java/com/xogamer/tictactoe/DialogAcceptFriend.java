package com.xogamer.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class DialogAcceptFriend extends Dialog {

    private CircleImageView accept_player_image;
    private TextView accept_player_name;
    private Button accept_player_button,refuse_player_button;

    public DialogAcceptFriend(Activity activity){
        super(activity , R.style.ThemeOverlay_AppCompat_Dialog);
        setContentView(R.layout.dialog_accept_friend);

        this.accept_player_image = (CircleImageView) findViewById(R.id.accept_player_image);
        this.accept_player_name = (TextView) findViewById(R.id.accept_player_name);
        this.accept_player_button = (Button) findViewById(R.id.accept_player_button);
        this.refuse_player_button = (Button) findViewById(R.id.refuse_player_button);
    }

    public CircleImageView getAccept_player_image() {
        return accept_player_image;
    }

    public TextView getAccept_player_name() {
        return accept_player_name;
    }

    public Button getAccept_player_button() {
        return accept_player_button;
    }

    public Button getRefuse_player_button() {
        return refuse_player_button;
    }

    public void build(){
        show();
    }
}
