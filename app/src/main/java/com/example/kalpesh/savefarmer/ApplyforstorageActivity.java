package com.example.kalpesh.savefarmer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kalpesh.savefarmer.API.ApiCall;
import com.example.kalpesh.savefarmer.model.AllStoragetypedata;
import com.example.kalpesh.savefarmer.model.Storagetypedata;

import java.util.ArrayList;

public class ApplyforstorageActivity extends AppCompatActivity {

    private ListView list;
    private ArrayList<Storagetypedata> ItemList;
    private ItemAdapter adapter1;
    ImageView img_logout;
    private SharedPreferences sharedPreferences;
    int wamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyforstorage);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
        wamount = Integer.parseInt(sharedPreferences.getString(LoginActivity.farmeramount, ""));

        list = (ListView) findViewById(R.id.lv_data);
        img_logout = findViewById(R.id.img_logout);
        ItemList = new ArrayList<Storagetypedata>();

        adapter1 = new ItemAdapter();
        list.setAdapter(adapter1);

        new Getdataclass().execute();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(ApplyforstorageActivity.this, ItemList.get(i).getStorage_id(), Toast.LENGTH_SHORT).show();
                int samount = Integer.parseInt(ItemList.get(i).getRate());

                if (wamount >= samount) {


                    startActivity(new Intent(ApplyforstorageActivity.this, FarmerallpyforstorageActivity.class)
                            .putExtra("storageid", ItemList.get(i).getStorage_id())
                            .putExtra("max", ItemList.get(i).getCapacity()));
                } else {
                    startActivity(new Intent(ApplyforstorageActivity.this, AddMoneyActivity.class)
                            .putExtra("amount", String.valueOf(samount))
                            .putExtra("role", "F")
                            .putExtra("st", "1"));
                    finish();
                }
            }
        });

        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ApplyforstorageActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    public class Getdataclass extends AsyncTask<Void, Void, AllStoragetypedata> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(ApplyforstorageActivity.this);
            dialog.setMessage("Please wait..!");
            dialog.show();
            dialog.setCancelable(false);
            super.onPreExecute();
        }

        @Override
        protected AllStoragetypedata doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            AllStoragetypedata result = api.ShowAllStoragetypedatafunction();
            return result;
        }

        @Override
        protected void onPostExecute(AllStoragetypedata allStoragetypedata) {
            if (allStoragetypedata != null) {
                ItemList.clear();
                ItemList.addAll(allStoragetypedata.getData());
                adapter1.notifyDataSetChanged();
            }
            dialog.dismiss();
            super.onPostExecute(allStoragetypedata);
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
                convertView = getLayoutInflater().inflate(R.layout.listviewapplyforstorage, parent, false);
                holder = new ViewHolder();

                holder.storagename = (TextView) convertView.findViewById(R.id.tv_storagename);
                holder.address = (TextView) convertView.findViewById(R.id.tv_address);
                holder.statename = (TextView) convertView.findViewById(R.id.tv_state);
                holder.cityname = (TextView) convertView.findViewById(R.id.tv_city);
                holder.area = (TextView) convertView.findViewById(R.id.tv_area);
                holder.capacity = (TextView) convertView.findViewById(R.id.tv_capacity);
                holder.description = (TextView) convertView.findViewById(R.id.tv_descri);
                holder.contactno = (TextView) convertView.findViewById(R.id.tv_cno);
                holder.storagetype = (TextView) convertView.findViewById(R.id.tv_storagetype);
                holder.rate = (TextView) convertView.findViewById(R.id.tv_rate);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.storagename.setText(ItemList.get(position).getStorage_Name());
            holder.address.setText(ItemList.get(position).getAddress());
            holder.statename.setText(ItemList.get(position).getState_Name());
            holder.cityname.setText(ItemList.get(position).getCity_Name());
            holder.area.setText(ItemList.get(position).getArea());
            holder.capacity.setText(ItemList.get(position).getCapacity());
            holder.description.setText(ItemList.get(position).getDescription());
            holder.contactno.setText(ItemList.get(position).getContact_No());
            holder.storagetype.setText(ItemList.get(position).getStorage_Type());
            holder.rate.setText(ItemList.get(position).getRate());


            return convertView;
        }
    }

    private static class ViewHolder {
        TextView storagename, address, statename, cityname, area;
        TextView capacity, description, contactno, storagetype, rate;

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(ApplyforstorageActivity.this, FarmerhomeActivity.class)
        .putExtra("role","F"));
        finish();
        super.onBackPressed();
    }
}
