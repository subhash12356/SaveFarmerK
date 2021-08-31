package com.example.kalpesh.savefarmer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.kalpesh.savefarmer.API.ApiCall;
import com.example.kalpesh.savefarmer.model.AllState;
import com.example.kalpesh.savefarmer.model.State;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    String status, stateid;
    Spinner spnstate;
    ArrayList<State> stateArrayList;
    ArrayAdapter<String> stringArrayAdapter;
    ArrayList<String> stringArrayList;
    private AwesomeValidation awesomeValidation;
    String gender;
    ArrayList<String> alldata;
    RadioGroup rg_sexgroup;
    RadioButton rb_sexgroup;
    TextView tv_xyz;

    /* private static final Pattern PASSWORD_PATTERN =
             Pattern.compile("^" +
                     "(?=.*[0-9])" +         //at least 1 digit
                     "(?=.*[a-z])" +         //at least 1 lower case letter
                     "(?=.*[A-Z])" +         //at least 1 upper case letter
                     "(?=.*[a-zA-Z])" +      //any letter
                     "(?=.*[@#$%^&+=])" +    //at least 1 special character
                     "(?=\\S+$)" +           //no white spaces
                     ".{4,}" +               //at least 4 characters
                     "$");
 */
    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        tv_xyz = findViewById(R.id.tv_xyz);
        Intent in = getIntent();
        Bundle b = in.getExtras();


        if (b != null) {
            status = b.getString("status");
            if (status.equals("Farmer Area")) {
                tv_xyz.setText("Farmer Registration Area");
            } else if (status.equals("Wholeseller Area")) {
                tv_xyz.setText("WholeSaler Registration Area");
            } else {
                Toast.makeText(this, "Contact developer", Toast.LENGTH_SHORT).show();
            }
        }

        View rootView = findViewById(android.R.id.content);
        final List<TextInputLayout> textInputLayouts = Utils.findViewsWithType(
                rootView, TextInputLayout.class);

        Button button = findViewById(R.id.save_button);
        Button button1 = findViewById(R.id.cancel_button);

        spnstate = findViewById(R.id.spnstate);
        stateArrayList = new ArrayList<State>();
        stringArrayList = new ArrayList<String>();
        rg_sexgroup = (RadioGroup) findViewById(R.id.rggender);

        alldata = new ArrayList<String>();
        spinner2meth();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.name_edit_text, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
       // awesomeValidation.addValidation(this, R.id.email_edit_text, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.email_edit_text, "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+", R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.contactno_edit_text, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);

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
                    if (awesomeValidation.validate()) {


                        if (!status.equals("")) {
                            int g = rg_sexgroup.getCheckedRadioButtonId();
                            rb_sexgroup = (RadioButton) findViewById(g);
                            gender = rb_sexgroup.getText().toString().trim();

                            //alldata.get(0), gender, alldata.get(1), stateid, alldata.get(2), alldata.get(3), alldata.get(4), alldata.get(5),status)
                            if (alldata.get(0).length() > 100 || alldata.get(1).length() > 100 || alldata.get(2).length() > 100 || alldata.get(3).length() > 100 || alldata.get(5).length() > 100) {
                                Toast.makeText(RegistrationActivity.this, "limit Excedded", Toast.LENGTH_SHORT).show();
                            } else {
                                if (!isValidPassword(alldata.get(5))) {
                                    Toast.makeText(RegistrationActivity.this, "Password to weak" + alldata.get(5), Toast.LENGTH_SHORT).show();
                                    alldata.clear();
                                } else {
                                    new Registrationclass().execute();
                                }
                            }
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Contact developer", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                finish();
            }
        });

        new GetAllStateclass().execute();

        spnstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    stateid = stateArrayList.get(i - 1).getState_id();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public class Registrationclass extends AsyncTask<Void, Void, String> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(RegistrationActivity.this);
            dialog.setMessage("Please Wait...!");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            String result = api.RegistrationFunction(alldata.get(0), gender, alldata.get(1), stateid, alldata.get(2), alldata.get(3), alldata.get(4), alldata.get(5), status);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("Added..!")) {
                Toast.makeText(RegistrationActivity.this, s, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(RegistrationActivity.this, s, Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            super.onPostExecute(s);
        }
    }

    public <ViewGroup> void spinner2meth() {
        stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, stringArrayList) {
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTextColor(Color.BLACK);
                v.setTextSize(15);
                return v;
            }

            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTextColor(Color.BLACK);
                v.setTextSize(15);
                return v;
            }
        };

        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnstate.setAdapter(stringArrayAdapter);
    }


    public class GetAllStateclass extends AsyncTask<Void, Void, AllState> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(RegistrationActivity.this);
            dialog.setMessage("Please Wait...!");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected AllState doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            AllState result = api.ShowAllStatefunction();
            return result;
        }

        @Override
        protected void onPostExecute(AllState allState) {
            if (allState != null) {
                stateArrayList.clear();
                stringArrayList.clear();
                stringArrayList.add("Select State");
                stateArrayList.addAll(allState.getData());
                for (State s : allState.getData()) {
                    stringArrayList.add(s.getState_Name());
                }
                stringArrayAdapter.notifyDataSetChanged();
            }
            alldata.clear();
            dialog.dismiss();
            super.onPostExecute(allState);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
}
