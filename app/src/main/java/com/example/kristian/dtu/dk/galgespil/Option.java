package com.example.kristian.dtu.dk.galgespil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Kristian on 12-10-2016.
 */

public class Option extends Activity {

    Db db = new Db();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        context = this;
        TextView lbl = (TextView) findViewById(R.id.textViewHighScoreLbl);
        EditText hs= (EditText) findViewById(R.id.textHighScore);
        lbl.setText("HighScore: værgo' at sætte den :)");
        String result = db.readFromFile(this);
        hs.setText(""+result);

        hs.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                db.writeToFile(""+s,context);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }
}
