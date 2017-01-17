package com.example.kristian.dtu.dk.galgespil;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.key;

/**
 * Created by Kristian on 12-10-2016.
 */

public class Play extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener {

    ImageView imageView;
    GalgeLogic gl = new GalgeLogic();
    Db db = new Db();
    Button btn, btn2 ,btn3;
    private Context context = this;
    EditText gussed;
    TextView highScore,wordToGuess;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.registerOnSharedPreferenceChangeListener(this);

        imageView = (ImageView) findViewById(R.id.imV);
        imageView.setImageResource(R.drawable.galge);

        gl.startGame();

        wordToGuess =(TextView) findViewById(R.id.txtVQtoGuess);
        wordToGuess.setText("Du skal gætte ordet: "+gl.getWordWithCorrectChar());

        highScore =(TextView) findViewById(R.id.txtHighscore);
        highScore.setText("Highscore: "+db.readFromFile(context));

        btn = (Button) findViewById(R.id.btnGuess);
        btn.setOnClickListener(myHandler);
        btn2 = (Button) findViewById(R.id.btnPlay);
        btn2.setOnClickListener(myHandler);
        btn3 = (Button) findViewById(R.id.btnGetDR);
        btn3.setOnClickListener(myHandler);

        gussed = (EditText) findViewById(R.id.editTxtGuessed);
    }

    View.OnClickListener myHandler = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnGuess:
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
                    Intent list = new Intent(context, ListActivity.class);
                    startActivity(list);
                    break;
            }
        }
    };

    private boolean checkStatusGame(){
        if(gl.gameWon||!gl.gameOver){
            if (gl.gameWon){Toast.makeText(context,"Du vandt",Toast.LENGTH_LONG).show(); db.checkHighScore(""+gl.wrongGuesses,context);
                String titler = prefs.getString("vundet", "(ingen titler)"); // Hent fra prefs
                int res = Integer.parseInt(titler)+1;
                prefs.edit().putString("vundet", ""+res).commit();
                String highscore = db.readFromFile(context);
                highScore.setText("Highscore: "+highscore  +" vundet i alt:"+res);
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String wordToGuees = sharedPreferences.getString("wordToGuess","");
        String result  = "Du skal gætte ordet: "+ gl.wordToChar(wordToGuees);
        wordToGuess.setText(result);
        gl.setWordToGuess(wordToGuees);
    }
}
