package com.example.kalpesh.savefarmer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kalpesh.savefarmer.API.ApiCall;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {
    String role;
    private SharedPreferences sharedPreferences;
    String iid;
    ArrayList<String> alldata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        View rootView = findViewById(android.R.id.content);
        final List<TextInputLayout> textInputLayouts = Utils.findViewsWithType(
                rootView, TextInputLayout.class);
        Button button = findViewById(R.id.save_button);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            role = b.getString("role");
        }
        alldata = new ArrayList<String>();

        if (role.equals("F")) {
            sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
            iid = sharedPreferences.getString(LoginActivity.farmerid, "");
        } else if (role.equals("W")) {
            sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
            iid = sharedPreferences.getString(LoginActivity.wholesellerid, "");
        }
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
                    new AddFeedbackclass().execute();
                }
            }
        });


    }


    public class AddFeedbackclass extends AsyncTask<Void, Void, String> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(FeedbackActivity.this);
            dialog.setMessage("Please wait..!");
            dialog.show();
            dialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            String result = api.AddfeedbackFunction(iid, alldata.get(0), role);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                if (s.equals("Added..!")) {
                    Toast.makeText(FeedbackActivity.this, s, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(FeedbackActivity.this, FarmerhomeActivity.class)
                            .putExtra("role", role));
                    finish();
                } else {
                    Toast.makeText(FeedbackActivity.this, s, Toast.LENGTH_LONG).show();
                }
            }
            alldata.clear();
            dialog.dismiss();

            super.onPostExecute(s);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FeedbackActivity.this, FarmerhomeActivity.class)
                .putExtra("role", role));
        finish();
        super.onBackPressed();
    }
}
