package com.example.kalpesh.savefarmer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView gifImageView, gifImageView1;
    String status = "Farmer Area";
    TextView tv_stafflogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gifImageView = findViewById(R.id.gif);
        gifImageView1 = findViewById(R.id.gif1);
        tv_stafflogin = findViewById(R.id.tv_stafflogin);


        gifImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FarmerregActivity.class)
                        .putExtra("status", status));
                finish();
            }
        });

        gifImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //status = "Wholeseller Area";
                status = "Wholeseller Area";
                startActivity(new Intent(MainActivity.this, FarmerregActivity.class)
                        .putExtra("status", status));
                finish();
            }
        });

        tv_stafflogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StaffLoginActivity.class));
                finish();
            }
        });
    }


}

