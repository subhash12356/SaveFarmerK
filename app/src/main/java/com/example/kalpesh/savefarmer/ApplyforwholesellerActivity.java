package com.example.kalpesh.savefarmer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kalpesh.savefarmer.API.ApiCall;
import com.example.kalpesh.savefarmer.model.AllWholesellerdetails;
import com.example.kalpesh.savefarmer.model.Wholesellerdetails;

import java.util.ArrayList;

public class ApplyforwholesellerActivity extends AppCompatActivity {

    private ListView list;
    private ArrayList<Wholesellerdetails> ItemList;
    private ItemAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyforwholeseller);

        list = (ListView) findViewById(R.id.lv_data);
        ItemList = new ArrayList<Wholesellerdetails>();

        adapter1 = new ItemAdapter();
        list.setAdapter(adapter1);
        new getDataclass().execute();
    }

    public class getDataclass extends AsyncTask<Void, Void, AllWholesellerdetails> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(ApplyforwholesellerActivity.this);
            dialog.setMessage("Please wait..!");
            dialog.show();
            dialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected AllWholesellerdetails doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            AllWholesellerdetails result = api.ShowAllWholesellerdatafunction();
            return result;
        }

        @Override
        protected void onPostExecute(AllWholesellerdetails allWholesellerdetails) {
            if (allWholesellerdetails != null) {
                ItemList.clear();
                ItemList.addAll(allWholesellerdetails.getData());
                adapter1.notifyDataSetChanged();

            }
            dialog.dismiss();
            super.onPostExecute(allWholesellerdetails);
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
                convertView = getLayoutInflater().inflate(R.layout.listviewapplyforwholerseller, parent, false);
                holder = new ViewHolder();

                holder.wholesellername = (TextView) convertView.findViewById(R.id.tv_wholersellername);
                holder.address = (TextView) convertView.findViewById(R.id.tv_address);
                holder.statename = (TextView) convertView.findViewById(R.id.tv_state);
                holder.cityname = (TextView) convertView.findViewById(R.id.tv_city);
                holder.contactno = (TextView) convertView.findViewById(R.id.tv_cno);
                holder.email = (TextView) convertView.findViewById(R.id.tv_email);
                holder.pincode = (TextView) convertView.findViewById(R.id.tv_pincode);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.wholesellername.setText(ItemList.get(position).getWhole_seller_Name());
            holder.address.setText(ItemList.get(position).getAddress());
            holder.statename.setText(ItemList.get(position).getState_Name());
            holder.cityname.setText(ItemList.get(position).getCity_Name());
            holder.contactno.setText(ItemList.get(position).getContact_No());
            holder.email.setText(ItemList.get(position).getEmail_id());
            holder.pincode.setText(ItemList.get(position).getPin_Code());


            return convertView;
        }
    }

    private static class ViewHolder {
        TextView wholesellername, address, statename, cityname;
        TextView email, pincode, contactno;
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(ApplyforwholesellerActivity.this, FarmerhomeActivity.class));
        finish();
        super.onBackPressed();
    }
}
