package com.example.nubankdesigntest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeDashboard extends AppCompatActivity {

    Button parautangbutton;
    ImageView loanbtn,paymenthistory,buttonnotif;
    TextView tv1;
    TextView nameGreet;



    private long pressedTime;

    @Override
    public void onBackPressed()
    {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            this.finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dashboard);

        paymenthistory = findViewById(R.id.paymenthistoryIcon);
        nameGreet = findViewById(R.id.namegreet);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String fetched_email = sh.getString("email", "");
        try {
            postRequest(fetched_email,"first_name",nameGreet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        paymenthistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PaymentHistory.class);
                startActivity(intent);
            }
        });



        buttonnotif = findViewById(R.id.notifbutton);

        buttonnotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Notification.class);
                startActivity(intent);
            }
        });

        //loan button onclicklistener
        loanbtn = findViewById(R.id.loancalcubutton);

        loanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoanCalcutor.class);
                startActivity(intent);
            }
        });


        //set on click listener for apply button
        parautangbutton = findViewById(R.id.applybutton);

        //set onclick listener for parautang button
        parautangbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(),ApplyLoan.class);
                startActivity(intent);*/
                Intent intent = new Intent(getApplicationContext(),LoanDetails.class);
                startActivity(intent);
            }
        });



        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.homeicon);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profileicon:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
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
}