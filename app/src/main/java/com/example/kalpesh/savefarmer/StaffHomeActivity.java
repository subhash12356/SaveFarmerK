package com.example.kalpesh.savefarmer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalpesh.savefarmer.API.ApiCall;
import com.example.kalpesh.savefarmer.model.AllStorage;
import com.example.kalpesh.savefarmer.model.Storage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StaffHomeActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    String staffid = "", staffname = "", storageid = "";
    private ListView list;
    private ArrayList<Storage> ItemList;
    private ItemAdapter adapter1;
    String farmerid = "", wholeseller_id = "", curdate = "", applyid = "", msg = "", abid = "";
    int amount = 0, sellamount = 0, sellquantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_home);


        curdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        sharedPreferences = getSharedPreferences(StaffLoginActivity.MyPREFERENCES, MODE_PRIVATE);
        staffid = sharedPreferences.getString(StaffLoginActivity.staffid, "");
        staffname = sharedPreferences.getString(StaffLoginActivity.staffname, "");
        storageid = sharedPreferences.getString(StaffLoginActivity.storageid, "");

        list = (ListView) findViewById(R.id.lv_data);
        ItemList = new ArrayList<Storage>();

        adapter1 = new ItemAdapter();
        list.setAdapter(adapter1);

        new GetStoragedetailClass().execute();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                applyid = ItemList.get(position).getApply_Storage_id();
                abid = ItemList.get(position).getApply_Buy_id();
                farmerid = ItemList.get(position).getFarmer_id();
                wholeseller_id = ItemList.get(position).getWhole_seller_id();
                // amount = ItemList.get(position).getPayable_Rate();
                sellquantity = Integer.parseInt(ItemList.get(position).getQuantity());
                sellamount = Integer.parseInt(ItemList.get(position).getSellRate());
                amount = sellquantity * sellamount;
                withEditText(view);
            }
        });
    }

    private ProgressDialog dialog;

    public class AddStaffTransclass extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(StaffHomeActivity.this);
            dialog.setMessage("Please Wait...!");
            dialog.show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            String result = api.AddStaffTransFunction(staffid, farmerid, wholeseller_id, curdate, String.valueOf(amount), applyid, msg, abid);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("Added..!")) {
                dialog.dismiss();
                Toast.makeText(StaffHomeActivity.this, s, Toast.LENGTH_SHORT).show();
                new GetStoragedetailClass().execute();
            } else {
                Toast.makeText(StaffHomeActivity.this, s, Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            super.onPostExecute(s);
        }
    }


    public void withEditText(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Amount Transfer:-");

        final EditText input = new EditText(StaffHomeActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //    Toast.makeText(getApplicationContext(), "Text entered is " + input.getText().toString(), Toast.LENGTH_SHORT).show();
                msg = input.getText().toString();
                if (msg.equals("")) {
                    Toast.makeText(StaffHomeActivity.this, "Enter Message", Toast.LENGTH_SHORT).show();
                } else {
                    new AddStaffTransclass().execute();
                }
            }
        });
        builder.show();
    }


    public class GetStoragedetailClass extends AsyncTask<Void, Void, AllStorage> {
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(StaffHomeActivity.this);
            dialog.setMessage("Please Wait...!");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected AllStorage doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            AllStorage result = api.StorageDetailForStaffFunction(storageid);
            return result;
        }

        @Override
        protected void onPostExecute(AllStorage allStorage) {
            if (allStorage != null) {
                ItemList.clear();
                ItemList.addAll(allStorage.getData());
                adapter1.notifyDataSetChanged();
            }
            dialog.dismiss();
            super.onPostExecute(allStorage);
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
                convertView = getLayoutInflater().inflate(R.layout.listviewstorageview, parent, false);
                holder = new ViewHolder();

                holder.tv_wholersellername = (TextView) convertView.findViewById(R.id.tv_wholersellername);
                holder.tv_productname = (TextView) convertView.findViewById(R.id.tv_productname);
                holder.tv_quantity = (TextView) convertView.findViewById(R.id.tv_quantity);
                holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
                holder.tv_descri = (TextView) convertView.findViewById(R.id.tv_descri);
                holder.tv_sellrate = (TextView) convertView.findViewById(R.id.tv_sellrate);
                holder.tv_totalamount = (TextView) convertView.findViewById(R.id.tv_totalamount);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            int quan = Integer.parseInt(ItemList.get(position).getQuantity());
            int sellrate = Integer.parseInt(ItemList.get(position).getSellRate());
            int newTotal = quan * sellrate;

            holder.tv_wholersellername.setText(ItemList.get(position).getWhole_seller_Name());
            holder.tv_productname.setText(ItemList.get(position).getProduct_Name());
                holder.tv_quantity.setText(ItemList.get(position).getQuantity());
            holder.tv_date.setText(ItemList.get(position).getDate());
            holder.tv_descri.setText(ItemList.get(position).getDescri());
            holder.tv_sellrate.setText(ItemList.get(position).getSellRate());
            holder.tv_totalamount.setText(String.valueOf(newTotal));


            return convertView;
        }
    }

    private static class ViewHolder {
        TextView tv_wholersellername, tv_productname, tv_quantity, tv_date;
        TextView tv_descri, tv_totalamount, tv_sellrate;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(StaffHomeActivity.this, StaffLoginActivity.class));
        finish();
        super.onBackPressed();
    }
}
