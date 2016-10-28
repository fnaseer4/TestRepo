package com.example.usmanyaqoob.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import API.StackOverFlowAPI;
import Models.StackOverFlowQuestions;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog progressDialog=null;
    public static final String Base_Url = "https://api.stackexchange.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.appBar);
        //set support for toolbar
        setSupportActionBar(toolbar);
        ReteroFiller serverGet = new ReteroFiller();
        serverGet.execute();

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onPrepareOptionsMenu(menu);
    }

    private class ReteroFiller extends AsyncTask<String ,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.stackexchange.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            // prepare call in Retrofit 2.0
            StackOverFlowAPI stackOverflowAPI = retrofit.create(StackOverFlowAPI.class);

            Call<StackOverFlowQuestions> call = stackOverflowAPI.loadQuestions("android");
            //asynchronous call
            //call.enqueue();

            // synchronous call would be with execute, in this case you
            // would have to perform this outside the main thread
            // call.execute()

            // to cancel a running request
            // call.cancel();
            // calls can only be used once but you can easily clone them
            //Call<StackOverflowQuestions> c = call.clone();
            //c.enqueue(this);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();

        }
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "ProgressDialog",
                    "Progressing");
        }


        @Override
        protected void onProgressUpdate(String... text) {


        }


    }
}
