package com.xogamer.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.EditText;

public class DialogWithFriend extends Dialog {

    private EditText edttext;
    private Button accept,copy;

    public DialogWithFriend(Activity activity){
        super(activity , R.style.ThemeOverlay_AppCompat_Dialog);
        setContentView(R.layout.dialog_with_friend);

        this.edttext = (EditText) findViewById(R.id.edttext);
        this.accept = (Button) findViewById(R.id.dialog_accept);
        this.copy = (Button) findViewById(R.id.dialog_copy);
    }

    public EditText getEdttext() {
        return edttext;
    }

    public Button getAccept() {
        return accept;
    }

    public Button getCopy() {
        return copy;
    }

    public void build(){
        show();
    }
}
