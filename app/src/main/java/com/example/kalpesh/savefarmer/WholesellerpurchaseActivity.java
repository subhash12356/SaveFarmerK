package com.example.kalpesh.savefarmer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kalpesh.savefarmer.API.ApiCall;

import java.util.ArrayList;
import java.util.List;

public class WholesellerpurchaseActivity extends AppCompatActivity {

    String productid;
    String wholesellerid;
    String quantity;
    String farmerid;
    String asid;
    String samount;
    private SharedPreferences sharedPreferences;
    ArrayList<String> alldata;
    String wamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholesellerpurchase);

        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
        wholesellerid = sharedPreferences.getString(LoginActivity.wholesellerid, "");
        wamount = sharedPreferences.getString(LoginActivity.wholeselleramount, "");

        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            productid = b.getString("pid");
            quantity = b.getString("quantity");
            farmerid = b.getString("fid");
            asid = b.getString("asid");
            samount = b.getString("samount");

        }

        View rootView = findViewById(android.R.id.content);
        final List<TextInputLayout> textInputLayouts = Utils.findViewsWithType(
                rootView, TextInputLayout.class);

        Button button = findViewById(R.id.save_button);
        Button button1 = findViewById(R.id.cancel_button);

        alldata = new ArrayList<String>();


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
                   newPurchaseRate=samount;
                    int newAmount = Integer.parseInt(samount) * Integer.parseInt(alldata.get(0));
                    samount = String.valueOf(newAmount);
                   // Log.e("samount", samount);
                    Toast.makeText(WholesellerpurchaseActivity.this, samount, Toast.LENGTH_SHORT).show();
                    new AddBuyproductclass().execute();
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WholesellerpurchaseActivity.this, WholesellerhomeActivity.class));
                finish();
            }
        });

    }

    private String newPurchaseRate="";
    public class AddBuyproductclass extends AsyncTask<Void, Void, String> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(WholesellerpurchaseActivity.this);
            dialog.setMessage("Please Wait..!");
            dialog.show();
            dialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            String result = api.AddBuyproductFunction(wholesellerid, productid, quantity, alldata.get(0), alldata.get(1), farmerid, asid, wamount, samount,newPurchaseRate);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("Added..!")) {
                Toast.makeText(WholesellerpurchaseActivity.this, s, Toast.LENGTH_SHORT).show();

                int amount = Integer.parseInt(wamount) - Integer.parseInt(samount);
                sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(LoginActivity.wholeselleramount, String.valueOf(amount));
                editor.commit();

                startActivity(new Intent(WholesellerpurchaseActivity.this, WholesellerhomeActivity.class));
                finish();
            } else {
                Toast.makeText(WholesellerpurchaseActivity.this, s, Toast.LENGTH_SHORT).show();
            }
            alldata.clear();
            dialog.dismiss();
            super.onPostExecute(s);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(WholesellerpurchaseActivity.this, WholesellerhomeActivity.class));
        finish();
        super.onBackPressed();
    }
}
