package com.example.kristian.dtu.dk.galgespil;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button play,help,option;
    private Context context = this;
    GalgeLogic gl = new GalgeLogic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = (Button) findViewById(R.id.btnPlay);
        play.setOnClickListener(myHandler);
        option = (Button) findViewById(R.id.btnOptions);
        option.setOnClickListener(myHandler);
        help = (Button) findViewById(R.id.btnHelp);
        help.setOnClickListener(myHandler);
    }

    View.OnClickListener myHandler = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnPlay:
                    Intent play = new Intent(context, Play.class);
                    startActivity(play);
                    break;
                case R.id.btnHelp:
                    Intent help = new Intent(context, Help.class);
                    startActivity(help);
                    break;
                case R.id.btnOptions:
                    Intent option = new Intent(context, Option.class);
                    startActivity(option);
                    break;
            }
        }
    };
}
