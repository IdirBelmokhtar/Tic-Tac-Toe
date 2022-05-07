package com.xogamer.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class DialogSearch extends Dialog {

    private LinearLayout player;
    private CircleImageView player_image;
    private TextView player_name;

    public DialogSearch(Activity activity){
        super(activity , R.style.ThemeOverlay_AppCompat_Dialog);
        setContentView(R.layout.dialog_search);

        this.player = (LinearLayout) findViewById(R.id.player);
        this.player_image = (CircleImageView) findViewById(R.id.player_image);
        this.player_name = (TextView) findViewById(R.id.player_name);
    }

    public LinearLayout getPlayer() {
        return player;
    }

    public CircleImageView getPlayer_image() {
        return player_image;
    }

    public TextView getPlayer_name() {
        return player_name;
    }

    public void build(){
        show();
    }
}
