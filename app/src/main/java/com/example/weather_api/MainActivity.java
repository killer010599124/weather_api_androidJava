package com.example.weather_api;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button startBtn;

    private EditText url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
     private void initView(){
        startBtn = findViewById(R.id.start);
        startBtn.setOnClickListener(this);
        url = findViewById(R.id.url);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.start){

            MyAsyncTask asyncTask = new MyAsyncTask();
            asyncTask.wa = this;
            asyncTask.execute();
        }
    }
    public void nextActivity(String responseData) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("response_data", responseData);
        startActivity(intent);
    }

    public class MyAsyncTask extends AsyncTask<String, Integer, String> {

        public MainActivity wa;
        public ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {

//            loadingPanel.setVisibility(View.VISIBLE);
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Wait a second...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            progressDialog.setProgress(progress[0]);
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                String location = url.getText().toString();
                Request request = new Request.Builder()
                        .url("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"+location +"?unitGroup=us&key=3C8TRCWYPKSPU83H6U8CJ5CUR&contentType=json")
                        .method("GET", null)
                        .build();
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    // Handle the successful response
                    String jsonData = response.body().string();
                    wa.nextActivity(jsonData);
                    return response.body().string();
                } else {
                    // Handle the error response
                    return response.message();
                }
            } catch (Exception e) {
                // Handle exceptions
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            if (result != null) {
                // Process the response data
                // Update UI or do any other required tasks
            } else {
                // Handle the error scenario
            }
        }
    }



    public void showToast(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,
                        Text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}

