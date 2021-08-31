package com.example.kalpesh.savefarmer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalpesh.savefarmer.API.ApiCall;
import com.example.kalpesh.savefarmer.API.ApiReso;
import com.example.kalpesh.savefarmer.model.AllProduct;
import com.example.kalpesh.savefarmer.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FarmerallpyforstorageActivity extends AppCompatActivity {

    ArrayList<String> stringArrayList, productstringArrayList;
    ArrayAdapter<String> stringArrayAdapter, productstringArrayAdapter;
    Spinner spnapplyfor, spnproduct;
    ArrayList<Product> productArrayList;
    String productid, storageid, farmerid, applyfortext, max, faramount;
    String sratetext, prate;
    ArrayList<String> alldata;
    EditText srate, prorate;
    private SharedPreferences sharedPreferences;
    int diff;
    ImageView img_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmerallpyforstorage);

        View rootView = findViewById(android.R.id.content);
        final List<TextInputLayout> textInputLayouts = Utils.findViewsWithType(
                rootView, TextInputLayout.class);

        Button button = findViewById(R.id.save_button);
        Button button1 = findViewById(R.id.cancel_button);
        img_img = findViewById(R.id.img_img);

        Intent in = getIntent();
        Bundle b = in.getExtras();

        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
        farmerid = sharedPreferences.getString(LoginActivity.farmerid, "");
        faramount = sharedPreferences.getString(LoginActivity.farmeramount, "");

        if (b != null) {
            storageid = b.getString("storageid");
            max = b.getString("max");
        }

        stringArrayList = new ArrayList<String>();
        productArrayList = new ArrayList<Product>();
        alldata = new ArrayList<String>();
        srate = findViewById(R.id.srate_edit_text);
        prorate = findViewById(R.id.prate_edit_text);
        productstringArrayList = new ArrayList<String>();
        stringArrayList.add("Select storage type from below");
        stringArrayList.add("Storage only");
        stringArrayList.add("Storage and Sales");

        spnapplyfor = findViewById(R.id.spnapplyfor);
        spnproduct = findViewById(R.id.spnproduct);
        new GetProductdataclass().execute();
        spinnermeth();
        spinner2meth();

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
                    int maxno, actual;
                    maxno = Integer.parseInt(max);
                    actual = Integer.parseInt(alldata.get(0));
                    if (maxno < actual) {
                        Toast.makeText(FarmerallpyforstorageActivity.this, "Demand is greater than supply", Toast.LENGTH_SHORT).show();
                    } else {
                        diff = maxno - actual;
                        new AddStoragedetailclass().execute();
                    }
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FarmerallpyforstorageActivity.this, ApplyforstorageActivity.class));
                finish();
            }
        });

        spnproduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    productid = productArrayList.get(i - 1).getProduct_Id();
                    prate = productArrayList.get(i - 1).getRate();
                    prorate.setText(prate);
                    img_img.setVisibility(View.VISIBLE);
                    Picasso.get()
                            .load(ApiReso.BASE_URL + "uploads/" + productArrayList.get(i - 1).getImage())
                            .into(img_img);
                    img_img.setVisibility(View.VISIBLE);

                    //Toast.makeText(FarmerallpyforstorageActivity.this, ""+  productArrayList.get(i - 1).getImage(), Toast.LENGTH_SHORT).show();

                } else {
                    img_img.setVisibility(View.GONE);
                    prate = "0";
                    prorate.setText(prate);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnapplyfor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    sratetext = "0";
                }
                if (i == 2) {
                    srate.setEnabled(true);
                } else {
                    srate.setEnabled(false);
                }
                applyfortext = spnapplyfor.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public class AddStoragedetailclass extends AsyncTask<Void, Void, String> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(FarmerallpyforstorageActivity.this);
            dialog.setMessage("Please wait..!");
            dialog.show();
            dialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            String result = api.AddstoragedataFunction(productid, farmerid, alldata.get(0), storageid, alldata.get(1), applyfortext, alldata.get(2), String.valueOf(diff));
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                if (s.equals("Added..!")) {
                    Toast.makeText(FarmerallpyforstorageActivity.this, s, Toast.LENGTH_SHORT).show();

                    int newfaramount, farquantity, farrate;
                    farquantity = Integer.parseInt(alldata.get(0));
                    farrate = Integer.parseInt(alldata.get(1));
                    int newtotal = farquantity * farrate;
                    newfaramount = Integer.parseInt(faramount) - newtotal;

                    sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(LoginActivity.farmeramount, String.valueOf(newfaramount));
                    editor.commit();

                    startActivity(new Intent(FarmerallpyforstorageActivity.this, ApplyforstorageActivity.class));
                    finish();
                } else {
                    Toast.makeText(FarmerallpyforstorageActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
            alldata.clear();
            dialog.dismiss();
            super.onPostExecute(s);
        }
    }

    public class GetProductdataclass extends AsyncTask<Void, Void, AllProduct> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(FarmerallpyforstorageActivity.this);
            dialog.setMessage("Please wait..!");
            dialog.show();
            dialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected AllProduct doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            AllProduct result = api.ShowAllProductdatafunction();
            return result;
        }

        @Override
        protected void onPostExecute(AllProduct allProduct) {
            if (allProduct != null) {
                productArrayList.clear();
                productstringArrayList.clear();
                productArrayList.addAll(allProduct.getData());
                productstringArrayList.add("Select Product");

                for (Product p : allProduct.getData()) {
                    productstringArrayList.add(p.getProduct_Name());
                }
                productstringArrayAdapter.notifyDataSetChanged();
            }
            dialog.dismiss();
            super.onPostExecute(allProduct);
        }
    }


    public <ViewGroup> void spinnermeth() {
        productstringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, productstringArrayList) {
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

        productstringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnproduct.setAdapter(productstringArrayAdapter);
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
        spnapplyfor.setAdapter(stringArrayAdapter);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(FarmerallpyforstorageActivity.this, ApplyforstorageActivity.class));
        finish();
        super.onBackPressed();
    }
}
