package com.example.kristian.dtu.dk.galgespil;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Kristian on 12-10-2016.
 */

public class Help extends Activity {

    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        TextView textView = (TextView) findViewById(R.id.textViewHelp);
        String wordToGuees = prefs.getString("wordToGuess","");
        String result  = "Her har du ordet du skal gætte: "+ wordToGuees + "                        var det nok hjælp?";
        textView.setText(""+result);
    }
}
