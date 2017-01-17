package com.example.kristian.dtu.dk.galgespil;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristian on 15-01-2017.
 */

public class DownloadDRTask extends AsyncTask<String, Void, ArrayList<String>> {

    GalgeLogic galgeLogic = new GalgeLogic();
    public AsyncResponse delegate = null;

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        try {
            galgeLogic.hentOrdFraDr();
            ArrayList<String> list = galgeLogic.getListOfWordsToGuess();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
        delegate.processFinish(result);
    }
}
