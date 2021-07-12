package com.example.nubankdesigntest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;


public class MainActivity extends AppCompatActivity  {

    Button signin_button;
    TextView textsignup;
    EditText emailNum;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signin_button = findViewById(R.id.createAccBtn);
        textsignup = findViewById(R.id.clickSignIn);
        emailNum = findViewById(R.id.signup_emailNum);
        password = findViewById(R.id.signup_password);
        textsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Registration.class);
                startActivity(intent);
            }
        });

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, emailNum.getText().toString(), Toast.LENGTH_SHORT).show();
                try {
                    postRequest(emailNum.getText().toString(),password.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*Intent intent = new Intent(getApplicationContext(),HomeDashboard.class);
                startActivity(intent);*/
            }
        });
    }
    public void postRequest(String signInEmailNum, String signInPassword) throws IOException {
        //Toast.makeText(MainActivity.this, signInEmailNum+signInPassword, Toast.LENGTH_SHORT).show();
        String url = "https://script.google.com/macros/s/AKfycbyiRYBEIDmbG2cf1T_BKXHNGiOYpzZcMry43JZbEzRFm9whq72GCWj4dRl0o6vyki3y/exec";

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("action","login")
                .addFormDataPart("email", signInEmailNum)
                .addFormDataPart("password", signInPassword)
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
                        if(responseText.equals("Login Success")){
                            Toast.makeText(MainActivity.this, responseText, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),HomeDashboard.class);
                            startActivity(intent);
                            //storing
                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("email", signInEmailNum);
                            myEdit.commit();
                        }
                        else if(responseText.equals("Incorrect Credentials")){
                            Toast.makeText(MainActivity.this, responseText, Toast.LENGTH_SHORT).show();
                        }
                        else if(responseText.equals("Empty Credentials")){
                            Toast.makeText(MainActivity.this, responseText, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, responseText, Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(MainActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                    }
                });
                //Log.e(TAG, mMessage);
            }
        });
    }
}