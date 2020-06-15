package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private String url = "";

    private TextView emptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new AdapterView.OnClickListener() {

            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                url = "https://openweathermap.org/data/2.5/weather?q=" + text + "&appid=439d4b804bc8187953eb36d2a8c26a02";
                
                // Start the AsyncTask to fetch data
                DownloadTask task = new DownloadTask();
                task.execute(url);
            }
        });

    }


    private class DownloadTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            // Perform the HTTP request for earthquake data and process the response.
            ArrayList<String> result = QueryUtils.fetchData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<String> weatherArray) {

            if (weatherArray != null && !weatherArray.isEmpty()) {
                TextView main = (TextView) findViewById(R.id.mainTextView);
                TextView description = (TextView) findViewById(R.id.descTextView);
                main.setText("Main: " + weatherArray.get(0));
                description.setText("Description: " + weatherArray.get(1));
            }
        }
    }
}