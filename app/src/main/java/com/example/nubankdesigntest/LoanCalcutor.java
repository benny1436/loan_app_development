package com.example.nubankdesigntest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoanCalcutor extends AppCompatActivity {
    ImageView backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_calcutor);
        EditText LoanInput = findViewById(R.id.LoanInput);
        TextView OutputInterest = findViewById(R.id.OutputInterest);
        TextView outputLateFee = findViewById(R.id.outputLateFee);
        EditText inputLateDays = findViewById(R.id.inputLateDays);
        TextView outputTotalPayment = findViewById(R.id.outputTotalPayment);
        Button btnEnter = findViewById(R.id.btnEnter);


        backbtn = findViewById(R.id.backbutton);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeDashboard.class);
                startActivity(intent);
            }
        });

        //When the User Clicks the button Enter
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int latedays = 0;
                double loan = 0;
                double interest = 0;
                double latefee = 35;
                double totalpayment = 0;

                //When the user does not enter any number it will automatically sets it to 0
                if(LoanInput.getText().toString().length() == 0){
                    LoanInput.setText("0");
                }
                if(inputLateDays.getText().toString().length() == 0){
                    inputLateDays.setText("0");
                }

                loan = Double.parseDouble(LoanInput.getText().toString());
                interest = (12 * loan) / 100;
                latedays = Integer.parseInt(inputLateDays.getText().toString());
                latefee = latedays * latefee;
                totalpayment = loan + interest + latefee;


                OutputInterest.setText(String.format("%.2f",interest));
                outputLateFee.setText(String.valueOf(latefee));
                outputTotalPayment.setText(String.format("%.2f",totalpayment));
            }
        });

    }
}