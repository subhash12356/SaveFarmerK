package com.example.kalpesh.savefarmer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.kalpesh.savefarmer.API.ApiCall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForgotpasswordActivity extends AppCompatActivity {

    String status, role = "";
    ArrayList<String> alldata, farmerarray, wholesellerarray;
    private AwesomeValidation awesomeValidation;
    int randomPIN;
    String pin, finalpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        Intent in = getIntent();
        Bundle b = in.getExtras();

        if (b != null) {
            status = b.getString("status");

            switch (status) {
                case "Farmer Area":
                    role = "F";
                    break;

                case "Wholeseller Area":
                    role = "W";
                    break;

                default:
                    Toast.makeText(this, "Contact developer", Toast.LENGTH_SHORT).show();
            }
        }

        randomPIN = (int) (Math.random() * 9000) + 1000;
        pin = "" + randomPIN;


        View rootView = findViewById(android.R.id.content);
        alldata = new ArrayList<String>();
        farmerarray = new ArrayList<String>();
        wholesellerarray = new ArrayList<String>();
        final List<TextInputLayout> textInputLayouts = Utils.findViewsWithType(
                rootView, TextInputLayout.class);

        final Button button = findViewById(R.id.update_button);
        Button button1 = findViewById(R.id.cancel_button);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.email_edit_text, Patterns.EMAIL_ADDRESS, R.string.emailerror);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alldata.clear();
                for (TextInputLayout textInputLayout : textInputLayouts) {
                    alldata.add(textInputLayout.getEditText().getText().toString());
                }

                if (button.getText().toString().equals("Otp")) {
                    // All fields are valid!
                    if (awesomeValidation.validate()) {
                        if (!status.equals("")) {
                            if (alldata.get(1).equals(alldata.get(2))) {
                                // new Updatepasswordclass().execute();
                                new FindEmailclass().execute();
                                button.setText("Check Otp");

                            } else {
                                Toast.makeText(ForgotpasswordActivity.this, "Password Mis-match", Toast.LENGTH_SHORT).show();

                            }
                        }
                    } else {
                        alldata.clear();
                        Toast.makeText(ForgotpasswordActivity.this, "Contact developer", Toast.LENGTH_SHORT).show();
                    }
                } else if (button.getText().toString().equals("Check Otp")) {
                    if (alldata.get(1).equals(finalpin)) {
                        Toast.makeText(ForgotpasswordActivity.this, "OTP matched", Toast.LENGTH_SHORT).show();
                        button.setText("Update");

                    } else {
                        Toast.makeText(ForgotpasswordActivity.this, "Wrong OTP", Toast.LENGTH_SHORT).show();

                    }
                } else if (button.getText().toString().equals("Update")) {
                    if (alldata.get(1).equals(finalpin)) {
                        new Updatepasswordclass().execute();
                    }
                }

            }

        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotpasswordActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    public class FindEmailclass extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(ForgotpasswordActivity.this);
            dialog.setMessage("Please Wait..!");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            String result = api.FindEmailFunction(alldata.get(0), role);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("Found")) {
                String fromEmail = "suketu1899@gmail.com";
                String fromPassword = "suketu123";
                String toEmails = alldata.get(0);
                List<String> toEmailList = Arrays.asList(toEmails
                        .split("\\s*,\\s*"));
                Log.i("SendMailActivity", "To List: " + toEmailList);
                String emailSubject = "OTP";
                String emailBody = "your OTP is " + pin;
                new SendMailTask(ForgotpasswordActivity.this).execute(fromEmail,
                        fromPassword, toEmailList, emailSubject, emailBody);
                finalpin = pin;

            } else {
                Toast.makeText(ForgotpasswordActivity.this, s, Toast.LENGTH_SHORT).show();
                alldata.clear();
            }
            dialog.dismiss();
            super.onPostExecute(s);
        }
    }

    private ProgressDialog dialog;

    public class Updatepasswordclass extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(ForgotpasswordActivity.this);
            dialog.setMessage("Please Wait..!");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            String result = api.UpdatePasswordFunction(alldata.get(0), alldata.get(2), role);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("Updated..!")) {
                Toast.makeText(ForgotpasswordActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ForgotpasswordActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(ForgotpasswordActivity.this, s, Toast.LENGTH_SHORT).show();
                alldata.clear();
            }
            dialog.dismiss();
            super.onPostExecute(s);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ForgotpasswordActivity.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
}
