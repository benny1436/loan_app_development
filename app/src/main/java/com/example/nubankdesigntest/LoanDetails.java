package com.example.nubankdesigntest;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoanDetails extends AppCompatActivity {

    EditText loanAmount, loanTerm, firstName, middleName, lastName, suffix, dateOfBirth, sex, currentAddress, contactNumber, cellularNetwork, fbName, purposeBorrow, sourceOfIncome, monthlyNetIncome, howKnowBank, brokerCode;
    Button send;
    ImageView idImage;
    AnimationDrawable wifiannim;

    Uri urs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_details);

        loanAmount = findViewById(R.id.loanamount);
        loanTerm = findViewById(R.id.loanterm);
        firstName = findViewById(R.id.firstname);
        middleName = findViewById(R.id.middlename);
        lastName = findViewById(R.id.lastname);
        suffix = findViewById(R.id.suffix);
        dateOfBirth = findViewById(R.id.dateofbirth);
        sex = findViewById(R.id.sex);
        currentAddress = findViewById(R.id.address);
        contactNumber = findViewById(R.id.contactnumber);
        cellularNetwork = findViewById(R.id.cellnetwork);
        fbName = findViewById(R.id.fbname);
        purposeBorrow = findViewById(R.id.purposeofborrowing);
        sourceOfIncome = findViewById(R.id.sourceoffund);
        monthlyNetIncome = findViewById(R.id.monthlyIncome);
        howKnowBank = findViewById(R.id.howClarenceBank);
        brokerCode = findViewById(R.id.brokerCode);
        send = findViewById(R.id.submitBtn);
        idImage = findViewById(R.id.idImage);

        idImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                //Intent intent = new Intent(uploadImageTest.this, pickpick.class);
                someActivityResultLaunchers.launch(intent);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q;
                a = loanAmount.getText().toString();
                b = loanTerm.getText().toString();
                c = firstName.getText().toString();
                d = middleName.getText().toString();
                e = lastName.getText().toString();
                f = suffix.getText().toString();
                g = dateOfBirth.getText().toString();
                h = sex.getText().toString();
                i = currentAddress.getText().toString();
                j = contactNumber.getText().toString();
                k = cellularNetwork.getText().toString();
                l = fbName.getText().toString();
                m = purposeBorrow.getText().toString();
                n = sourceOfIncome.getText().toString();
                o = monthlyNetIncome.getText().toString();
                p = howKnowBank.getText().toString();
                q = brokerCode.getText().toString();

                if (urs != null) {
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(urs);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                        byte[] bytes = baos.toByteArray();
                        String encImage = Base64.encodeToString(bytes, Base64.DEFAULT);
                        String url = "https://script.google.com/macros/s/AKfycbxBokZY4ks-EIjGllBS_VgsYOgaJOfEvpL86JhkshZX-f2dLndwKib8G_1vzks3Ytc/exec";
                        OkHttpClient client = new OkHttpClient();
                        Toast.makeText(LoanDetails.this, e, Toast.LENGTH_SHORT).show();
                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("action", "addLoan")
                                .addFormDataPart("loanAmount", a)
                                .addFormDataPart("loanTerm", b)
                                .addFormDataPart("firstName", c)
                                .addFormDataPart("middleName", d)
                                .addFormDataPart("lastName", e)
                                .addFormDataPart("suffix", f)
                                .addFormDataPart("dateOfBirth", g)
                                .addFormDataPart("sex", h)
                                .addFormDataPart("currentAddress", i)
                                .addFormDataPart("contactNumber", j)
                                .addFormDataPart("cellularNetwork", k)
                                .addFormDataPart("fbName", l)
                                .addFormDataPart("purposeBorrow", m)
                                .addFormDataPart("sourceOfIncome", n)
                                .addFormDataPart("monthlyNetIncome", o)
                                .addFormDataPart("howKnowBank", p)
                                .addFormDataPart("brokerCode", q)
                                .addFormDataPart("idTypeImage",encImage)
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
                                        if (responseText.equals("Loan Success")) {
                                            Toast.makeText(LoanDetails.this, responseText, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeDashboard.class);
                                    startActivity(intent);
                                            //storing
                                        } else if (responseText.equals("Incorrect Credentials")) {
                                            Toast.makeText(LoanDetails.this, responseText, Toast.LENGTH_SHORT).show();
                                        } else if (responseText.equals("Empty Credentials")) {
                                            Toast.makeText(LoanDetails.this, responseText, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(LoanDetails.this, responseText, Toast.LENGTH_SHORT).show();
                                        }
                                        //Toast.makeText(MainActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                //Log.e(TAG, mMessage);
                            }
                        });
                    }catch(FileNotFoundException fnfe){

                    }
                }
                else{
                    Toast.makeText(LoanDetails.this, "Error Missing Image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void postRequest(String signInEmailNum, String signInPassword, String fullname) throws IOException {
        //Toast.makeText(MainActivity.this, signInEmailNum+signInPassword, Toast.LENGTH_SHORT).show();

    }
    ActivityResultLauncher<Intent> someActivityResultLaunchers = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        urs = data.getData();

                        idImage.setImageURI(urs);
                    }
                }
            });


}