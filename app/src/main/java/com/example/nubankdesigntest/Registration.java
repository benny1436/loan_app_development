package com.example.nubankdesigntest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class Registration extends AppCompatActivity {

    TextView cliclsigintext;
    EditText fullName,emailNum,passWord;
    Button createAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        fullName = findViewById(R.id.fullname);
        emailNum = findViewById(R.id.signup_emailNum);
        passWord = findViewById(R.id.signup_password);
        createAcc = findViewById(R.id.btnCreateAcc);

        cliclsigintext = findViewById(R.id.clickSignIn);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    postRequest(emailNum.getText().toString(),passWord.getText().toString(),fullName.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        cliclsigintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });
    }

    public void postRequest(String signInEmailNum, String signInPassword, String fullname) throws IOException {
        //Toast.makeText(MainActivity.this, signInEmailNum+signInPassword, Toast.LENGTH_SHORT).show();
        String url = "https://script.google.com/macros/s/AKfycbzgKtn5zXP8lGuYidk868Fh2kXmEV9NbezfV9wzPaRxYQbxes3WoSPx5MOO9GhUiUB0/exec";

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("action","register")
                .addFormDataPart("email", signInEmailNum)
                .addFormDataPart("password", signInPassword)
                .addFormDataPart("fullname", fullname)
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
                        if(responseText.equals("Success")){
                            Toast.makeText(Registration.this, responseText, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            //storing
                        }
                        else if(responseText.equals("Incorrect Credentials")){
                            Toast.makeText(Registration.this, responseText, Toast.LENGTH_SHORT).show();
                        }
                        else if(responseText.equals("Empty Credentials")){
                            Toast.makeText(Registration.this, responseText, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Registration.this, responseText, Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(MainActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                    }
                });
                //Log.e(TAG, mMessage);
            }
        });
    }
}