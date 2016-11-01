package com.example.usmanyaqoob.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import API.StackOverFlowAPI;
import Models.Question;
import Models.StackOverFlowQuestions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  implements Callback<StackOverFlowQuestions> {
    Toolbar toolbar;
    ProgressDialog progressDialog=null;
    public static final String Base_Url = "https://api.stackexchange.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.appBar);
        //set support for toolbar
        setSupportActionBar(toolbar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // prepare call in Retrofit 2.0
        StackOverFlowAPI stackOverflowAPI = retrofit.create(StackOverFlowAPI.class);

        Call<StackOverFlowQuestions> call = stackOverflowAPI.loadQuestions("android");
        //asynchronous call
        // call.enqueue();

        // synchronous call would be with execute, in this case you
        // would have to perform this outside the main thread
        // call.execute()

        // to cancel a running request
        // call.cancel();
        // calls can only be used once but you can easily clone them
        //Call<StackOverflowQuestions> c = call.clone();
        call.enqueue(this);


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onResponse(Call<StackOverFlowQuestions> call, Response<StackOverFlowQuestions> response) {
       // String msg = " Message: "+response.message()+" Body : "+response.body().items;
        TextView resBox = (TextView) findViewById(R.id.result_text);
        String res="";
//        for( int i=0;i<response.body().items.size();i++){
//            res=res+response.body().items.get(i).title+"\n\n";
//        }

        res = response.body().has_more;
       resBox.setText(res);

        //Log.i("RETERO TEST :",msg);

    }

    @Override
    public void onFailure(Call<StackOverFlowQuestions> call, Throwable t) {
        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

}
