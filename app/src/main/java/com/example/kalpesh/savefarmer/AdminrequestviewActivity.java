package com.example.kalpesh.savefarmer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalpesh.savefarmer.API.ApiCall;
import com.example.kalpesh.savefarmer.model.Adminviewrequest;
import com.example.kalpesh.savefarmer.model.AllAdminviewrequest;

import java.util.ArrayList;

public class AdminrequestviewActivity extends AppCompatActivity {
    ListView lv_data;
    private ArrayList<Adminviewrequest> ItemList;
    private ItemAdapter adapter1;
    String abid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminrequestview);

        lv_data = findViewById(R.id.lv_data);

        ItemList = new ArrayList<Adminviewrequest>();

        adapter1 = new ItemAdapter();
        lv_data.setAdapter(adapter1);

        new Getdataforadminclass().execute();

        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                abid = ItemList.get(i).getApply_Buy_Id();
                new Updateadminrequestclass().execute();
            }
        });
    }

    public class Updateadminrequestclass extends AsyncTask<Void, Void, String> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(AdminrequestviewActivity.this);
            dialog.setMessage("Please Wait..!");
            dialog.show();
            dialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            String result = api.UpdaterequestlistFunction(abid);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("Updated..!")) {
                Toast.makeText(AdminrequestviewActivity.this, s, Toast.LENGTH_SHORT).show();
                new Getdataforadminclass().execute();

            } else {
                Toast.makeText(AdminrequestviewActivity.this, s, Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            super.onPostExecute(s);
        }
    }

    public class Getdataforadminclass extends AsyncTask<Void, Void, AllAdminviewrequest> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(AdminrequestviewActivity.this);
            dialog.setMessage("Please Wait..!");
            dialog.show();
            dialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected AllAdminviewrequest doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            AllAdminviewrequest result = api.ShowAllAdminrequestfunction();
            return result;
        }

        @Override
        protected void onPostExecute(AllAdminviewrequest allAdminviewrequest) {
            if (allAdminviewrequest != null) {
                ItemList.clear();
                ItemList.addAll(allAdminviewrequest.getData());
                adapter1.notifyDataSetChanged();
            } else {
                Toast.makeText(AdminrequestviewActivity.this, "Everything is fine not to worry", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            super.onPostExecute(allAdminviewrequest);
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
                convertView = getLayoutInflater().inflate(R.layout.listviewforadminrequestview, parent, false);
                holder = new ViewHolder();

                holder.wholesellername = (TextView) convertView.findViewById(R.id.tv_wholesellername);
                holder.productname = (TextView) convertView.findViewById(R.id.tv_productname);
                holder.quantity = (TextView) convertView.findViewById(R.id.tv_quantity);
                holder.date = (TextView) convertView.findViewById(R.id.tv_date);
                holder.descri = (TextView) convertView.findViewById(R.id.tv_descri);
                holder.status = (TextView) convertView.findViewById(R.id.tv_status);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.wholesellername.setText(ItemList.get(position).getWhole_seller_Name());
            holder.productname.setText(ItemList.get(position).getProduct_Name());
            holder.quantity.setText(ItemList.get(position).getQuantity());
            holder.date.setText(ItemList.get(position).getDate());
            holder.descri.setText(ItemList.get(position).getDescri());
            holder.status.setText(ItemList.get(position).getStatus());


            return convertView;
        }
    }

    private static class ViewHolder {
        TextView wholesellername, productname, quantity, date, descri, status;
    }


    @Override
    public void onBackPressed() {
      //startActivity(new Intent(AdminrequestviewActivity.this, AdminhomeActivity.class));
        finish();
        super.onBackPressed();
    }
}
