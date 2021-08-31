package com.example.kalpesh.savefarmer;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalpesh.savefarmer.API.ApiCall;
import com.example.kalpesh.savefarmer.API.ApiReso;
import com.example.kalpesh.savefarmer.model.AllGvscheme;
import com.example.kalpesh.savefarmer.model.Gvscheme;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GovschemeActivity extends AppCompatActivity {
    String role;
    private ListView list;
    private ArrayList<Gvscheme> ItemList;
    private GovschemeAdapter govschemeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_govscheme);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            role = b.getString("role");

        }

        list = (ListView) findViewById(R.id.lv_data);
        ItemList = new ArrayList<Gvscheme>();
        govschemeAdapter = new GovschemeAdapter();
        list.setAdapter(govschemeAdapter);

        new GetgovschemeDataClass().execute();

    }

    public class GetgovschemeDataClass extends AsyncTask<Void, Void, AllGvscheme> {

        @Override
        protected AllGvscheme doInBackground(Void... voids) {
            ApiCall api = new ApiCall();
            AllGvscheme result = api.GetGvschemefunction();
            return result;
        }

        @Override
        protected void onPostExecute(AllGvscheme allGvscheme) {
            if (allGvscheme != null) {
                ItemList.addAll(allGvscheme.getData());
                govschemeAdapter.notifyDataSetChanged();
            }
            super.onPostExecute(allGvscheme);
        }
    }


    private class GovschemeAdapter extends BaseAdapter {

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
                convertView = getLayoutInflater().inflate(R.layout.listview_gvscheme, parent, false);
                holder = new ViewHolder();

                holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
                holder.img_image = (ImageView) convertView.findViewById(R.id.img_image);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_desc.setText(ItemList.get(position).getGv_desc());

            String imgUrl = ItemList.get(position).getGv_image();
            if (!imgUrl.equals("")) {
                Picasso.get()
                        .load(ApiReso.BASE_URL + "gov/" + imgUrl)
                        .into(holder.img_image);
            }

            return convertView;
        }
    }

    private static class ViewHolder {
        TextView tv_desc;
        ImageView img_image;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GovschemeActivity.this, FarmerhomeActivity.class)
                .putExtra("role", role));
        super.onBackPressed();
    }
}
