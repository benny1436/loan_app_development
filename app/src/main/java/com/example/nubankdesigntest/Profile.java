package com.example.nubankdesigntest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Profile extends AppCompatActivity {
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(Profile.this,HomeDashboard.class));
        this.finish();
    }
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    CircleImageView clientImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tv1 = findViewById(R.id.txtviewfullname);
        tv2 = findViewById(R.id.txtviewemail);
        clientImage = findViewById(R.id.imageClient);

        //fetching
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String fetched_email = sh.getString("email", "");
        try {
            postRequest(fetched_email,"first_name",tv1);
            postRequest(fetched_email,"email",tv2);
            postRequestImage(fetched_email,"picture",clientImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.profileicon);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.homeicon:
                        startActivity(new Intent(getApplicationContext(),HomeDashboard.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.moneytalksicon:
                        startActivity(new Intent(getApplicationContext(),MoneyTalk.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    public void postRequest(String email,String data, TextView ntextview) throws IOException {
        //Toast.makeText(MainActivity.this, signInEmailNum+signInPassword, Toast.LENGTH_SHORT).show();
        String url = "https://script.google.com/macros/s/AKfycbyiRYBEIDmbG2cf1T_BKXHNGiOYpzZcMry43JZbEzRFm9whq72GCWj4dRl0o6vyki3y/exec";

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("action","fetchData")
                .addFormDataPart("email",email)
                .addFormDataPart("data",data)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //String mMessage = e.getMessage().toString();
                //Log.w("failure Response", mMessage);
                //call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                /*InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);*/

                    runOnUiThread(new Runnable() {
                        public void run() {
                            ntextview.setText(responseText);
                            //Toast.makeText(MainActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                        }
                    });


                //Log.e(TAG, mMessage);
            }
        });
    }

    public void postRequestImage(String email,String data, CircleImageView ntextview) throws IOException {
        //Toast.makeText(MainActivity.this, signInEmailNum+signInPassword, Toast.LENGTH_SHORT).show();
        String url = "https://script.google.com/macros/s/AKfycbyiRYBEIDmbG2cf1T_BKXHNGiOYpzZcMry43JZbEzRFm9whq72GCWj4dRl0o6vyki3y/exec";

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("action","fetchData")
                .addFormDataPart("email",email)
                .addFormDataPart("data",data)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //String mMessage = e.getMessage().toString();
                //Log.w("failure Response", mMessage);
                //call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(responseText).getContent());
                runOnUiThread(new Runnable() {
                    public void run() {
                ntextview.setImageBitmap(bitmap);
                        Toast.makeText(Profile.this, responseText, Toast.LENGTH_SHORT).show();
                    }
                });
                //Log.e(TAG, mMessage);
            }
        });
    }

}