package com.example.kristian.dtu.dk.galgespil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kristian on 12-10-2016.
 */

public class Play extends Activity {

    ImageView imageView;
    GalgeLogic gl = new GalgeLogic();
    Button btn;
    private Context context = this;
    EditText gussed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        imageView = (ImageView) findViewById(R.id.imV);
        imageView.setImageResource(R.drawable.galge);
        gl.startGame();
        TextView textView =(TextView) findViewById(R.id.txtVQtoGuess);
        textView.setText("Du skal gætte ordet: "+gl.getWordWithCorrectChar());
        btn = (Button) findViewById(R.id.btnGuess);
        btn.setOnClickListener(myHandler);
    }

    View.OnClickListener myHandler = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnGuess:
                    gussed = (EditText) findViewById(R.id.editTxtGuessed);
                    if (checkInputFromUser(gussed.getText().toString())) {
                        String w = gl.guessedWord(gussed.getText().toString());
                        TextView textView = (TextView) findViewById(R.id.txtVQtoGuess);
                        textView.setText("Du skal gætte ordet: " + w);
                        String image = gl.checkStatus();
                        int id = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
                        imageView.setImageResource(id);
                    }
                    gussed.setText("");
                    TextView wordsThatHasBeenUsed = (TextView) findViewById(R.id.txtVGuessed);
                    wordsThatHasBeenUsed.setText("Bogstaver du har gættet på: " + gl.getListOfWordsThatHasBeenUsed());
                    checkStatusGame();
                    break;
            }
        }
    };

    private boolean checkStatusGame(){
        if(gl.gameWon||!gl.gameOver){
            if (gl.gameWon){Toast.makeText(context,"Du vandt",Toast.LENGTH_LONG).show(); return false;}
        }
        else { Toast.makeText(context,"TABER!!!",Toast.LENGTH_LONG).show(); return false;}
        return  true;
    }

    private boolean checkInputFromUser(String gussed){
        if(checkStatusGame()) {
            if (gussed.length() > 1) {
                Toast.makeText(context, "Et bogstav, din mongol!", Toast.LENGTH_LONG).show();
                return false;
            }
            if (gussed.length() < 1) {
                Toast.makeText(context, "du har ikke givet mig et bogstav, din mongol!", Toast.LENGTH_LONG).show();
                return false;
            }
            if (gl.getListOfWordsThatHasBeenUsed().contains(gussed)) {
                Toast.makeText(context, "Amen det har du prøvet, din mongol!", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }
        else{return false;}
    }

}