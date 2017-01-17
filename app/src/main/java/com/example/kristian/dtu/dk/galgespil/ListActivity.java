package com.example.kristian.dtu.dk.galgespil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kristian on 15-01-2017.
 */
public class ListActivity extends Activity implements AsyncResponse {
    DownloadDRTask ddt = new DownloadDRTask();

    ArrayList<String> mobileArray = new ArrayList<>();
    ListView listView;
    Context context;
    SharedPreferences app_preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        context = this;
        app_preferences = PreferenceManager.getDefaultSharedPreferences(context);

        //this to set delegate/listener back to this class
        ddt.delegate = this;

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, mobileArray);

        listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

        //execute the async task
        ddt.execute();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String result = mobileArray.get(position);
                SharedPreferences.Editor myEditor = app_preferences.edit();
                //edit and commit
                myEditor.putString("wordToGuess", result);
                myEditor.commit();
                finish();
            }
        });

    }

    @Override
    public void processFinish(ArrayList<String> output){
        //Here you will receive the result fired from async class
        mobileArray.addAll(output);
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
    }
}

