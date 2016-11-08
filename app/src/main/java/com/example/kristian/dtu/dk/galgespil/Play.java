package com.example.kristian.dtu.dk.galgespil;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
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
    Db db = new Db();
    Button btn;
    Button btn2;
    Button btn3;
    private Context context = this;
    EditText gussed;
    TextView highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        imageView = (ImageView) findViewById(R.id.imV);
        imageView.setImageResource(R.drawable.galge);
        gl.startGame();
        TextView textView =(TextView) findViewById(R.id.txtVQtoGuess);
        textView.setText("Du skal gætte ordet: "+gl.getWordWithCorrectChar());
        highScore =(TextView) findViewById(R.id.txtHighscore);
        highScore.setText("Highscore: "+db.readFromFile(context));
        btn = (Button) findViewById(R.id.btnGuess);
        btn.setOnClickListener(myHandler);
        btn2 = (Button) findViewById(R.id.btnPlay);
        btn2.setOnClickListener(myHandler);
        btn3 = (Button) findViewById(R.id.btnGetDR);
        btn3.setOnClickListener(myHandler);
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
                case R.id.btnPlay:
                    gl.restart();
                    gl.startGame();
                    String image = gl.checkStatus();
                    int id = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
                    imageView.setImageResource(id);
                    btn.setClickable(true);
                    gussed.setText("");
                    wordsThatHasBeenUsed = (TextView) findViewById(R.id.txtVGuessed);
                    wordsThatHasBeenUsed.setText("Bogstaver du har gættet på: " + gl.getListOfWordsThatHasBeenUsed());
                    TextView textView = (TextView) findViewById(R.id.txtVQtoGuess);
                    textView.setText("Du skal gætte ordet: "+gl.getWordWithCorrectChar());
                    break;
                case R.id.btnGetDR:
                    Toast.makeText(context,"Henter ord fra DRs server....",Toast.LENGTH_LONG).show();
                    new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object... arg0) {
                            try {
                                gl.hentOrdFraDr();
                                return "Ordene blev korrekt hentet fra DR's server";
                            } catch (Exception e) {
                                e.printStackTrace();
                                return "Ordene blev ikke hentet korrekt: "+e;
                            }
                        }


                        @Override
                        protected void onPostExecute(Object resultat) {
                            Toast.makeText(context,"resultat: \n" + resultat,Toast.LENGTH_LONG).show();
                        }
                    }.execute();
            }
        }
    };

    private boolean checkStatusGame(){
        if(gl.gameWon||!gl.gameOver){
            if (gl.gameWon){Toast.makeText(context,"Du vandt",Toast.LENGTH_LONG).show(); db.checkHighScore(""+gl.wrongGuesses,context);
                String highscore = db.readFromFile(context);
                highScore.setText("Highscore: "+highscore);
                return false;}
        }
        else { Toast.makeText(context,"TABER!!!",Toast.LENGTH_LONG).show(); btn.setClickable(false); return false;}
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
