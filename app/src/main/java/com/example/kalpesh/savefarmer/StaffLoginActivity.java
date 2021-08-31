package com.example.kalpesh.savefarmer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.kalpesh.savefarmer.API.ApiCall;
import com.example.kalpesh.savefarmer.model.AllStaff;
import com.example.kalpesh.savefarmer.model.Staff;

import java.util.ArrayList;
import java.util.List;

public class StaffLoginActivity extends AppCompatActivity {
    String status, role = "";
    ArrayList<String> alldata;
    private AwesomeValidation awesomeValidation;
    // String ;
    public static final String MyPREFERENCES = "MyPrefsStaff";
    public static final String staffid = "staffid";
    public static final String staffname = "staffname";
    public static final String storageid = "storageid";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);
        View rootView = findViewById(android.R.id.content);
        alldata = new ArrayList<String>();
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
                        //                   new Loginclass().execute();
                        new Staffloginclass().execute();
                    }
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StaffLoginActivity.this, MainActivity.class));
                finish();
            }
        });


    }

    private ProgressDialog dialog;


    public class Staffloginclass extends AsyncTask<Void, Void, AllStaff> {
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(StaffLoginActivity.this);
            dialog.setMessage("Please Wait...!");
            dialog.show();

            super.onPreExecute();
        }

        @Override
        protected AllStaff doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            AllStaff result = api.StaffLoginFunction(alldata.get(0), alldata.get(1));
            return result;
        }

        @Override
        protected void onPostExecute(AllStaff allStaff) {
            if (allStaff != null) {
                String sid = "", sname = "", stoid = "";
                for (Staff s : allStaff.getData()) {
                    sid = s.getStaff_id();
                    sname = s.getStaff_Name();
                    stoid = s.getStorage_id();
                }
                sharedPreferences = getSharedPreferences(StaffLoginActivity.MyPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(StaffLoginActivity.staffid, sid);
                editor.putString(StaffLoginActivity.staffname, sname);
                editor.putString(StaffLoginActivity.storageid, stoid);
                editor.commit();

                startActivity(new Intent(StaffLoginActivity.this, StaffHomeActivity.class));
                finish();
            }
            dialog.dismiss();
            super.onPostExecute(allStaff);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(StaffLoginActivity.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
}
