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
import com.example.kalpesh.savefarmer.model.AllWholesellerdata;
import com.example.kalpesh.savefarmer.model.Wholesellerdata;

import java.util.ArrayList;

public class WholesellerhomeActivity extends AppCompatActivity {
    ListView lv_data;
    private ArrayList<Wholesellerdata> ItemList;
    private ItemAdapter adapter1;
    ImageView img_logout;
    private SharedPreferences sharedPreferences;
    int wamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholesellerhome);
        lv_data = findViewById(R.id.lv_data);

        ItemList = new ArrayList<Wholesellerdata>();
        img_logout = findViewById(R.id.img_logout);
        adapter1 = new ItemAdapter();
        lv_data.setAdapter(adapter1);

        new GetDataclass().execute();

        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, MODE_PRIVATE);
        wamount = Integer.parseInt(sharedPreferences.getString(LoginActivity.wholeselleramount, ""));

        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int samount = Integer.parseInt(ItemList.get(i).getSellRate());

                if (wamount >= samount) {

                    startActivity(new Intent(WholesellerhomeActivity.this, WholesellerpurchaseActivity.class)
                            .putExtra("pid", ItemList.get(i).getProduct_Id())
                            .putExtra("quantity", ItemList.get(i).getProduct_Quantity())
                            .putExtra("asid", ItemList.get(i).getApply_Storage_Id())
                            .putExtra("fid", ItemList.get(i).getFarmer_Id())
                            .putExtra("samount", String.valueOf(samount)));
                } else {
                    startActivity(new Intent(WholesellerhomeActivity.this, AddFMoneyActivity.class)
                            .putExtra("amount", String.valueOf(samount)));
                    finish();
                }
            }
        });

        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WholesellerhomeActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    public class GetDataclass extends AsyncTask<Void, Void, AllWholesellerdata> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(WholesellerhomeActivity.this);
            dialog.setMessage("Please wait..!");
            dialog.show();
            dialog.setCancelable(false);
            super.onPreExecute();
        }


        @Override
        protected AllWholesellerdata doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            AllWholesellerdata result = api.ShowAllWholesellerdatafordisplayfunction();
            return result;
        }

        @Override
        protected void onPostExecute(AllWholesellerdata allWholesellerdata) {
            if (allWholesellerdata != null) {
                ItemList.clear();
                ItemList.addAll(allWholesellerdata.getData());
                adapter1.notifyDataSetChanged();

            }
            dialog.dismiss();
            super.onPostExecute(allWholesellerdata);
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
                convertView = getLayoutInflater().inflate(R.layout.listviewforwholesellerdisplay, parent, false);
                holder = new ViewHolder();

                holder.productname = (TextView) convertView.findViewById(R.id.tv_productname);
                holder.farmername = (TextView) convertView.findViewById(R.id.tv_farmername);
                holder.productquantity = (TextView) convertView.findViewById(R.id.tv_productquantity);
                holder.applydate = (TextView) convertView.findViewById(R.id.tv_applydate);
                holder.status = (TextView) convertView.findViewById(R.id.tv_status);
                holder.payablerate = (TextView) convertView.findViewById(R.id.tv_payablerate);
                holder.applyfor = (TextView) convertView.findViewById(R.id.tv_applyfor);
                holder.sellingrate = (TextView) convertView.findViewById(R.id.tv_sellrate);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.productname.setText(ItemList.get(position).getProduct_Name());
            holder.farmername.setText(ItemList.get(position).getFarmer_Name());
            holder.productquantity.setText(ItemList.get(position).getProduct_Quantity());
            holder.applydate.setText(ItemList.get(position).getApply_Date());
            holder.status.setText(ItemList.get(position).getStatus());
            holder.payablerate.setText(ItemList.get(position).getPayable_Rate());
            holder.applyfor.setText(ItemList.get(position).getApplyFor());
            holder.sellingrate.setText(ItemList.get(position).getSellRate());


            return convertView;
        }
    }

    private static class ViewHolder {
        TextView productname, farmername, productquantity, applydate, status;
        TextView payablerate, applyfor, sellingrate;

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(WholesellerhomeActivity.this,FarmerhomeActivity.class)
        .putExtra("role","W"));
        finish();
        super.onBackPressed();
    }
}
