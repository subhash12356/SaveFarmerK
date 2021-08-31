package com.example.kalpesh.savefarmer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalpesh.savefarmer.API.ApiCall;
import com.example.kalpesh.savefarmer.model.AllFarmerTans;
import com.example.kalpesh.savefarmer.model.FarmerTans;

import java.util.ArrayList;

public class Farmer_detail_transationActivity extends AppCompatActivity {
    private ListView list;
    private ArrayList<FarmerTans> ItemList;
    private ItemAdapter adapter1;
    private ItemAdapterwholeseller adapter1wholeseller;
    private ItemAdapterFarmerStorage itemAdapterFarmerStorage;
    private SharedPreferences sharedPreferences;
    String fid = "", wid = "";
    String role;
    Spinner spn_selection;
    ArrayList<String> spnselector;
    ArrayAdapter<String> stringArrayAdapter;
    SharedPreferences splogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_detail_transation);
        splogin = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);

        list = (ListView) findViewById(R.id.lv_data);
        spn_selection = (Spinner) findViewById(R.id.spn_selection);
        ItemList = new ArrayList<FarmerTans>();
        spnselector = new ArrayList<String>();
        spnselector.add("Select");
        spnselector.add("Storage only");
        spnselector.add("Storage and Sales");

        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            role = b.getString("role");
            if (role.equals("W")) {
                wid = splogin.getString(LoginActivity.wholesellerid, "");
                adapter1wholeseller = new ItemAdapterwholeseller();
                list.setAdapter(adapter1wholeseller);
                new GetWholesellertransclass().execute();
                spn_selection.setVisibility(View.GONE);

            } else {

                fid = splogin.getString(LoginActivity.farmerid, "");
                adapter1 = new ItemAdapter();
                list.setAdapter(adapter1);
                new GetFarmertransclass().execute();
                spn_selection.setVisibility(View.VISIBLE);
            }
        }
        spinner2meth();

        spn_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    switch (position) {
                        case 1:
                            fid = splogin.getString(LoginActivity.farmerid, "");
                            itemAdapterFarmerStorage = new ItemAdapterFarmerStorage();
                            list.setAdapter(itemAdapterFarmerStorage);
                            new GetFarmerstoragetransclass().execute();
                            break;
                        case 2:
                            fid = splogin.getString(LoginActivity.farmerid, "");
                            adapter1 = new ItemAdapter();
                            list.setAdapter(adapter1);
                            new GetFarmertransclass().execute();

                            break;
                        default:
                            Toast.makeText(Farmer_detail_transationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public <ViewGroup> void spinner2meth() {
        stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, spnselector) {
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
        spn_selection.setAdapter(stringArrayAdapter);
    }


    public class GetWholesellertransclass extends AsyncTask<Void, Void, AllFarmerTans> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Farmer_detail_transationActivity.this);
            dialog.setMessage("Please wait..!");
            dialog.show();
            dialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected AllFarmerTans doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            AllFarmerTans result = api.GetWholesellertransdetailfunction(wid);
            return result;
        }

        @Override
        protected void onPostExecute(AllFarmerTans allFarmerTans) {
            if (allFarmerTans != null) {
                ItemList.clear();
                ItemList.addAll(allFarmerTans.getData());
                adapter1wholeseller.notifyDataSetChanged();
            } else {
                Toast.makeText(Farmer_detail_transationActivity.this, "no data", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            super.onPostExecute(allFarmerTans);
        }
    }

    public class GetFarmertransclass extends AsyncTask<Void, Void, AllFarmerTans> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Farmer_detail_transationActivity.this);
            dialog.setMessage("Please wait..!");
            dialog.show();
            dialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected AllFarmerTans doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            AllFarmerTans result = api.GetFarmertransdetailfunction(fid);
            return result;
        }

        @Override
        protected void onPostExecute(AllFarmerTans allFarmerTans) {
            if (allFarmerTans != null) {
                ItemList.clear();
                ItemList.addAll(allFarmerTans.getData());
                adapter1.notifyDataSetChanged();
            } else {
                Toast.makeText(Farmer_detail_transationActivity.this, "no data", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            super.onPostExecute(allFarmerTans);
        }
    }

    public class GetFarmerstoragetransclass extends AsyncTask<Void, Void, AllFarmerTans> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Farmer_detail_transationActivity.this);
            dialog.setMessage("Please wait..!");
            dialog.show();
            dialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected AllFarmerTans doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            AllFarmerTans result = api.GetFarmerstoragetransdetailfunction(fid);
            return result;
        }

        @Override
        protected void onPostExecute(AllFarmerTans allFarmerTans) {
            if (allFarmerTans != null) {
                ItemList.clear();
                ItemList.addAll(allFarmerTans.getData());
                itemAdapterFarmerStorage.notifyDataSetChanged();
            } else {
                Toast.makeText(Farmer_detail_transationActivity.this, "no data", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            super.onPostExecute(allFarmerTans);
        }
    }

    private class ItemAdapterwholeseller extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return ItemList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return ItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            final ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.listviewwholesellertrans, parent, false);
                holder = new ViewHolder();

                holder.tv_farmername = (TextView) convertView.findViewById(R.id.tv_farmername);
                holder.tv_storagename = (TextView) convertView.findViewById(R.id.tv_storagename);
                holder.tv_paymenttype = (TextView) convertView.findViewById(R.id.tv_paymenttype);
                holder.tv_quantity = (TextView) convertView.findViewById(R.id.tv_quantity);
                holder.tv_rate = (TextView) convertView.findViewById(R.id.tv_rate);
                holder.tv_totalamount = (TextView) convertView.findViewById(R.id.tv_totalamount);
                holder.tv_tax = (TextView) convertView.findViewById(R.id.tv_tax);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
               holder.tv_farmername.setText(ItemList.get(position).getFarmer_Name());
               holder.tv_storagename.setText(ItemList.get(position).getStorage_Name());
               holder.tv_paymenttype.setText(ItemList.get(position).getPayment_Type());
               holder.tv_quantity.setText(ItemList.get(position).getQuantity_Sale());
               holder.tv_rate.setText(ItemList.get(position).getNewPurchaseRate());

               int rate= Integer.parseInt(ItemList.get(position).getNewPurchaseRate());
               int newTotalAmt=Integer.parseInt(ItemList.get(position).getQuantity_Sale())*
                       rate;

               holder.tv_totalamount.setText(String.valueOf(newTotalAmt));

               int newTaxValue=newTotalAmt*4/100;
               holder.tv_tax.setText(String.valueOf(newTaxValue));
            }catch (Exception e){}


            return convertView;
        }
    }

    private class ItemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return ItemList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return ItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            final ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.listviewfarmertrans, parent, false);
                holder = new ViewHolder();

                holder.tv_wholesellername = (TextView) convertView.findViewById(R.id.tv_wholesellername);
                holder.tv_storagename = (TextView) convertView.findViewById(R.id.tv_storagename);
                holder.tv_paymenttype = (TextView) convertView.findViewById(R.id.tv_paymenttype);
                holder.tv_quantity = (TextView) convertView.findViewById(R.id.tv_quantity);
                holder.tv_rate = (TextView) convertView.findViewById(R.id.tv_rate);
                holder.tv_totalamount = (TextView) convertView.findViewById(R.id.tv_totalamount);
                holder.tv_tax = (TextView) convertView.findViewById(R.id.tv_tax);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.tv_wholesellername.setText(ItemList.get(position).getWhole_seller_Name());
            holder.tv_storagename.setText(ItemList.get(position).getStorage_Name());
            holder.tv_paymenttype.setText(ItemList.get(position).getPayment_Type());
            holder.tv_quantity.setText(ItemList.get(position).getQuantity_Sale());
            holder.tv_rate.setText(ItemList.get(position).getRate());
            holder.tv_totalamount.setText(ItemList.get(position).getTotal_Amt());
            holder.tv_tax.setText(ItemList.get(position).getTax_Value());


            return convertView;
        }
    }

    private class ItemAdapterFarmerStorage extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return ItemList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return ItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            final ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.listviewfarmerstoragetrans, parent, false);
                holder = new ViewHolder();

                holder.tv_storagename = (TextView) convertView.findViewById(R.id.tv_storagename);
                holder.tv_applydate = (TextView) convertView.findViewById(R.id.tv_applydate);
                holder.tv_quantity = (TextView) convertView.findViewById(R.id.tv_quantity);
                holder.tv_rate = (TextView) convertView.findViewById(R.id.tv_rate);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.tv_storagename.setText(ItemList.get(position).getStorage_Name());
            holder.tv_applydate.setText(ItemList.get(position).getApply_Date());
            holder.tv_quantity.setText(ItemList.get(position).getProduct_Quantity());
            holder.tv_rate.setText(ItemList.get(position).getPayable_Rate());


            return convertView;
        }
    }

    private static class ViewHolder {
        TextView tv_wholesellername, tv_storagename, tv_paymenttype, tv_quantity, tv_rate;
        TextView tv_totalamount, tv_tax, tv_farmername, tv_applydate;

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(Farmer_detail_transationActivity.this, FarmerhomeActivity.class)
                .putExtra("role", role));
        finish();
        super.onBackPressed();
    }
}
