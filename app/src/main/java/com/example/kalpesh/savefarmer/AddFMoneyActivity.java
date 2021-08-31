package com.example.kalpesh.savefarmer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalpesh.savefarmer.API.ApiCall;

import java.util.ArrayList;

public class AddFMoneyActivity extends AppCompatActivity implements View.OnClickListener {

    private Bundle extra;
    //  private String ssamount;
    //  private TextView txtamount;
    private Spinner spintype;
    private Spinner spinmonth;
    private Spinner spinyear;
    private EditText editcno1;
    private EditText editcno2;
    private EditText editcno3;
    private EditText editcno4;
    private EditText editcvv, et_amount;
    private ArrayList<String> types;
    private ArrayList<String> months;
    private ArrayList<String> years;
    private ArrayAdapter<String> adpater1;
    private ArrayAdapter<String> adpater2;
    private ArrayAdapter<String> adpater3;
    private Button btnPay;
    private String scno1;
    private String scno2;
    private String scno3;
    private String scno4;
    private String scvv;
    private String smm;
    private String syy;
    private String scno;
    private String fdate;
    private String ftime;
    private String uid;
    private String sstype;
    private String ssdate;
    int balance, addamount, finalamount;
    //private ArrayList<User> resultList;
    private SharedPreferences sharedPreferences;
    String role = "", st = "";
    TextView txt_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        txt_amount = findViewById(R.id.txt_amount);
        SharedPreferences splogin = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
        uid = splogin.getString(LoginActivity.wholesellerid, "");
        balance = Integer.parseInt(splogin.getString(LoginActivity.wholeselleramount, ""));
        // extra = getIntent().getExtras();
        txt_amount.setText(String.valueOf(balance));
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            role = b.getString("role");
            st = b.getString("st");
    //        Toast.makeText(this, role, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        //     ssamount = extra.getString("amount").toString().trim();

        //   txtamount = (TextView) findViewById(R.id.textView6);
        //   txtamount.setText("Pay " + ssamount + " Rs");

        spintype = (Spinner)

                findViewById(R.id.spinner1);

        spinmonth = (Spinner)

                findViewById(R.id.spinner2);

        spinyear = (Spinner)

                findViewById(R.id.spinner3);

        editcno1 = (EditText)

                findViewById(R.id.editText1);

        editcno2 = (EditText)

                findViewById(R.id.editText3);

        editcno3 = (EditText)

                findViewById(R.id.editText4);

        editcno4 = (EditText)

                findViewById(R.id.editText5);

        editcvv = (EditText)

                findViewById(R.id.editText2);

        et_amount = (EditText)

                findViewById(R.id.et_amount);

        types = new ArrayList<String>();
        months = new ArrayList<String>();
        years = new ArrayList<String>();

        types.add("Credit Card");
        types.add("Debit Card");

        months.add("MM");
        months.add("01");
        months.add("02");
        months.add("03");
        months.add("04");
        months.add("05");
        months.add("06");
        months.add("07");
        months.add("08");
        months.add("09");
        months.add("10");
        months.add("11");
        months.add("12");

        years.add("YY");
        years.add("2019");
        years.add("2020");
        years.add("2021");
        years.add("2022");
        years.add("2023");
        years.add("2024");
        years.add("2025");
        years.add("2026");
        years.add("2027");
        years.add("2028");

        adpater1 = new ArrayAdapter<String>(AddFMoneyActivity.this, android.R.layout.simple_spinner_dropdown_item, types);
        adpater2 = new ArrayAdapter<String>(AddFMoneyActivity.this, android.R.layout.simple_spinner_dropdown_item, months);
        adpater3 = new ArrayAdapter<String>(AddFMoneyActivity.this, android.R.layout.simple_spinner_dropdown_item, years);

        spintype.setAdapter(adpater1);
        spinmonth.setAdapter(adpater2);
        spinyear.setAdapter(adpater3);

        editcno1.addTextChangedListener(new

                                                TextWatcher() {

                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                        // TODO Auto-generated method stub
                                                        if (editcno1.getText().toString().length() == 4)     //size as per your requirement
                                                        {
                                                            editcno2.requestFocus();
                                                        }
                                                    }

                                                    public void beforeTextChanged(CharSequence s, int start,
                                                                                  int count, int after) {
                                                        // TODO Auto-generated method stub

                                                    }

                                                    @Override
                                                    public void afterTextChanged(Editable s) {
                                                        // TODO Auto-generated method stub

                                                    }


                                                });

        editcno2.addTextChangedListener(new

                                                TextWatcher() {

                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                        // TODO Auto-generated method stub
                                                        if (editcno2.getText().toString().length() == 4)     //size as per your requirement
                                                        {
                                                            editcno3.requestFocus();
                                                        }
                                                    }

                                                    public void beforeTextChanged(CharSequence s, int start,
                                                                                  int count, int after) {
                                                        // TODO Auto-generated method stub

                                                    }

                                                    public void afterTextChanged(Editable s) {
                                                        // TODO Auto-generated method stub
                                                    }

                                                });


        editcno3.addTextChangedListener(new

                                                TextWatcher() {

                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                        // TODO Auto-generated method stub
                                                        if (editcno3.getText().toString().length() == 4)     //size as per your requirement
                                                        {
                                                            editcno4.requestFocus();
                                                        }
                                                    }

                                                    public void beforeTextChanged(CharSequence s, int start,
                                                                                  int count, int after) {
                                                        // TODO Auto-generated method stub

                                                    }

                                                    public void afterTextChanged(Editable s) {
                                                        // TODO Auto-generated method stub
                                                    }

                                                });

        //	 editcno1.addTextChangedListener(new FourDigitCardFormatWatcher());
        //	 editcno2.addTextChangedListener(new FourDigitCardFormatWatcher());

        btnPay = (Button)

                findViewById(R.id.button1);
        btnPay.setOnClickListener(this);

        //resultList = new ArrayList<User>();


    }

    @Override
    public void onClick(View v) {
        if (v == btnPay) {
            scno1 = editcno1.getText().toString().trim();
            scno2 = editcno2.getText().toString().trim();
            scno3 = editcno3.getText().toString().trim();
            scno4 = editcno4.getText().toString().trim();
            scvv = editcvv.getText().toString().trim();

            smm = spinmonth.getSelectedItem().toString().trim();
            syy = spinyear.getSelectedItem().toString().trim();

            addamount = Integer.parseInt(et_amount.getText().toString());


            scno = (new StringBuilder()).append(scno1).append(scno2).append(scno3).append(scno4).toString();
            if (!et_amount.getText().toString().equals("")) {
                if (scno1.equals("") || scno2.equals("") || scno3.equals("") || scno4.equals("")) {
                    Toast.makeText(AddFMoneyActivity.this, "Enter Valid Card Number", Toast.LENGTH_SHORT).show();
                    return;
                } else if (scno.length() == 16) {
                    if (smm.contains("MM") || syy.contains("YY")) {
                        Toast.makeText(AddFMoneyActivity.this, "Select Valid Expiry Date", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (scvv.equals("")) {
                        Toast.makeText(AddFMoneyActivity.this, "Enter CVV", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (scvv.length() == 3) {
                        finalamount = balance + addamount;
                        new Updateamountclass().execute();
                    } else {
                        Toast.makeText(AddFMoneyActivity.this, "Enter Valid CVV", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(AddFMoneyActivity.this, "Enter Valid Card Number", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(this, "Amount cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    public static class FourDigitCardFormatWatcher implements TextWatcher {

        // Change this to what you want... ' ', '-' etc..
        private static final char space = ' ';

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Remove spacing char
            if (s.length() > 0 && (s.length() % 5) == 0) {
                final char c = s.charAt(s.length() - 1);
                if (space == c) {
                    s.delete(s.length() - 1, s.length());
                }
            }
            // Insert char where needed.
            if (s.length() > 0 && (s.length() % 5) == 0) {
                char c = s.charAt(s.length() - 1);
                // Only if its a digit where there should be a space we insert a space
                if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                    s.insert(s.length() - 1, String.valueOf(space));
                }
            }
        }

    }


    public class Updateamountclass extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            String result = api.Updateamountfunction(uid, String.valueOf(finalamount));
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(LoginActivity.wholeselleramount, String.valueOf(finalamount));
                editor.commit();
                Toast.makeText(AddFMoneyActivity.this, "Amount added to wallet", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddFMoneyActivity.this, WholesellerhomeActivity.class));
            } else {
                Toast.makeText(AddFMoneyActivity.this, s, Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(s);
        }

    }

/*    public class PostPayment extends AsyncTask<Void, Void, AllUser> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(AddMoneyActivity.this);
            dialog.setMessage("Please Wait..");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected AllUser doInBackground(Void... voids) {

            AllUser result = new AllUser();
            result = new APICall().PostWalletAmount(uid, ssamount);
            return result;

        }

        @Override
        protected void onPostExecute(AllUser allUser) {
            super.onPostExecute(allUser);

            if (allUser != null) {
                resultList.clear();
                resultList.addAll(allUser.getData());

                String result = resultList.get(0).getResult().toString().trim();
                String msg = resultList.get(0).getMsg().toString().trim();

                if (result.contains("No")) {
                } else if (result.contains("Yes")) {
                    Intent Home = new Intent(AddMoneyActivity.this, UserPaymentActivity.class);
                    startActivity(Home);
                    finish();
                }
            }
            dialog.dismiss();
        }
    }*/

    @Override
    public void onBackPressed() {
        switch (st) {
            case "0":
                startActivity(new Intent(AddFMoneyActivity.this, FarmerhomeActivity.class)
                        .putExtra("role", role));
                finish();
                break;

            default:
                startActivity(new Intent(AddFMoneyActivity.this, WholesellerhomeActivity.class));
                finish();
        }
        /*   */
        super.onBackPressed();
    }
}
