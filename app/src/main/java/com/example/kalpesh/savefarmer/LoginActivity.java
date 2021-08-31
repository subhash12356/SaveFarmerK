package com.example.kalpesh.savefarmer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.kalpesh.savefarmer.API.ApiCall;
import com.example.kalpesh.savefarmer.model.AllFarmer;
import com.example.kalpesh.savefarmer.model.AllFpayment;
import com.example.kalpesh.savefarmer.model.AllWholesellerdetails;
import com.example.kalpesh.savefarmer.model.AllWpayment;
import com.example.kalpesh.savefarmer.model.Farmer;
import com.example.kalpesh.savefarmer.model.Fpayment;
import com.example.kalpesh.savefarmer.model.Wholesellerdetails;
import com.example.kalpesh.savefarmer.model.Wpayment;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    String status, role = "";
    ArrayList<String> alldata, farmerarray, wholesellerarray;
    private AwesomeValidation awesomeValidation;
    // String ;
    TextView tv_forgotpassword;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String farmerid = "farmerid";
    public static final String farmeramount = "farmeramount";
    public static final String wholesellerid = "wholesellerid";
    public static final String wholeselleramount = "wholeselleramount";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        View rootView = findViewById(android.R.id.content);
        alldata = new ArrayList<String>();
        farmerarray = new ArrayList<String>();
        wholesellerarray = new ArrayList<String>();
        tv_forgotpassword = findViewById(R.id.tv_forgotpassword);
        final List<TextInputLayout> textInputLayouts = Utils.findViewsWithType(
                rootView, TextInputLayout.class);

        Button button = findViewById(R.id.login_button);
        Button button1 = findViewById(R.id.cancel_button);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.email_edit_text, Patterns.EMAIL_ADDRESS, R.string.emailerror);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean noErrors = true;
                for (TextInputLayout textInputLayout : textInputLayouts) {
                    String editTextString = textInputLayout.getEditText().getText().toString();
                    if (editTextString.isEmpty()) {
                        textInputLayout.setError(getResources().getString(R.string.error_string));
                        noErrors = false;
                    } else {
                        textInputLayout.setError(null);
                        alldata.add(textInputLayout.getEditText().getText().toString());
                    }
                }

                if (noErrors) {
                    // All fields are valid!
                    if (awesomeValidation.validate()) {
                        if (!status.equals("")) {
                            if (alldata.get(0).equals("a@g.com") && alldata.get(1).equals("a")) {
                               // startActivity(new Intent(LoginActivity.this, AdminhomeActivity.class));
                                finish();
                            } else {
                                if (alldata.get(0).length() > 50 || alldata.get(1).length() > 30) {
                                    Toast.makeText(LoginActivity.this, "Length Excedded", Toast.LENGTH_SHORT).show();
                                } else {
                                    new Loginclass().execute();
                                }
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Contact developer", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

        tv_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotpasswordActivity.class)
                        .putExtra("status", status));
                finish();
            }
        });

    }

    private ProgressDialog dialog;

    public class Loginclass extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Please Wait...!");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            String result = api.LoginFunction(alldata.get(0), alldata.get(1), role);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("Login Successful")) {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();

                switch (role) {
                    case "F":
                        new GetFarmerIdclass().execute();
                        break;

                    case "W":
                        new GetWholesellerIdclass().execute();
                        break;

                    default:
                        Toast.makeText(LoginActivity.this, "Contact developer", Toast.LENGTH_SHORT).show();
                }

            } else if (s.equals("Invalid Login Details")) {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                alldata.clear();
            } else {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            super.onPostExecute(s);
        }
    }

    public class GetWholesellerIdclass extends AsyncTask<Void, Void, AllWholesellerdetails> {

        @Override
        protected AllWholesellerdetails doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            AllWholesellerdetails result = api.GetWholesellerIdfunction(alldata.get(0));
            return result;
        }

        @Override
        protected void onPostExecute(AllWholesellerdetails allWholesellerdetails) {
            if (allWholesellerdetails != null) {
                wholesellerarray.clear();
                for (Wholesellerdetails w : allWholesellerdetails.getData()) {
                    wholesellerarray.add(w.getWhole_seller_id());
                }
                sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(LoginActivity.wholesellerid, wholesellerarray.get(0));
                editor.commit();
                new GetWholeselleramountclass().execute();
                //   startActivity(new Intent(LoginActivity.this, WholesellerhomeActivity.class));
                /*startActivity(new Intent(LoginActivity.this, FarmerhomeActivity.class)
                        .putExtra("role", role));
                finish();
*/
            }
            alldata.clear();
            dialog.dismiss();

            super.onPostExecute(allWholesellerdetails);
        }
    }


    public class GetWholeselleramountclass extends AsyncTask<Void, Void, AllWpayment> {

        @Override
        protected AllWpayment doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            AllWpayment result = api.GetWAmountfunction(wholesellerarray.get(0));
            return result;
        }

        @Override
        protected void onPostExecute(AllWpayment allWpayment) {
            if (allWpayment != null) {
                String amount = "";
                for (Wpayment w : allWpayment.getData()) {
                    amount = w.getAmount();
                }

                sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(LoginActivity.wholeselleramount, amount);
                editor.commit();
                startActivity(new Intent(LoginActivity.this, FarmerhomeActivity.class)
                        .putExtra("role", role));
                finish();

            } else {
                Toast.makeText(LoginActivity.this, "Amount not init", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(allWpayment);
        }
    }

    public class GetFarmerIdclass extends AsyncTask<Void, Void, AllFarmer> {

        @Override
        protected AllFarmer doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            AllFarmer result = api.ShowFarmerIdfunction(alldata.get(0));
            return result;
        }

        @Override
        protected void onPostExecute(AllFarmer allFarmer) {
            if (allFarmer != null) {
                farmerarray.clear();
                for (Farmer f : allFarmer.getData()) {
                    farmerarray.add(f.getFarmer_id());
                }

                sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(LoginActivity.farmerid, farmerarray.get(0));
                editor.commit();

                new GetFatmerAmountclass().execute();

            }
            alldata.clear();
            dialog.dismiss();

            super.onPostExecute(allFarmer);
        }
    }

    public class GetFatmerAmountclass extends AsyncTask<Void, Void, AllFpayment> {

        @Override
        protected AllFpayment doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            AllFpayment result = api.GetFAmountfunction(farmerarray.get(0));
            return result;
        }

        @Override
        protected void onPostExecute(AllFpayment allFpayment) {
            if (allFpayment != null) {
                String amount = "";
                for (Fpayment w : allFpayment.getData()) {
                    amount = w.getAmount();
                }
                sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(LoginActivity.farmeramount, amount);
                editor.commit();
                startActivity(new Intent(LoginActivity.this, FarmerhomeActivity.class)
                        .putExtra("role", role));
                finish();


            } else {
                Toast.makeText(LoginActivity.this, "Amount not init", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(allFpayment);
        }
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
}
