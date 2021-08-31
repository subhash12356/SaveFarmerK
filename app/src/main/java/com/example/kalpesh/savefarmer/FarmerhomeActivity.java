package com.example.kalpesh.savefarmer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FarmerhomeActivity extends AppCompatActivity {
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmerhome);
        Button button = findViewById(R.id.storage_button);
        Button button1 = findViewById(R.id.feedback_button);
        Button button3 = findViewById(R.id.payrecoed_button);
        Button button4 = findViewById(R.id.addWalletAmtf);
        Button btn_govscheme = findViewById(R.id.btn_govscheme);
        Button logout = findViewById(R.id.logout_button);


        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            role = b.getString("role");

            if (role.equals("W")) {
                button.setText("Apply for product");
                //   button3.setVisibility(View.GONE);
            } else {
                button3.setVisibility(View.VISIBLE);
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (role.equals("F")) {
                    startActivity(new Intent(FarmerhomeActivity.this, ApplyforstorageActivity.class));
                    finish();
                } else if (role.equals("W")) {
                    startActivity(new Intent(FarmerhomeActivity.this, WholesellerhomeActivity.class));
                    finish();
                }
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FarmerhomeActivity.this, FeedbackActivity.class)
                        .putExtra("role", role));
                finish();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FarmerhomeActivity.this, Farmer_detail_transationActivity.class)
                        .putExtra("role", role));
                finish();

            }
        });

        btn_govscheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FarmerhomeActivity.this, GovschemeActivity.class)
                        .putExtra("role", role));
                finish();

            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String st = "0";
                switch (role) {
                    case "W":
                        startActivity(new Intent(FarmerhomeActivity.this, AddFMoneyActivity.class)
                                .putExtra("role", role)
                                .putExtra("st", st));
                        finish();
                        break;

                    case "F":
                        startActivity(new Intent(FarmerhomeActivity.this, AddMoneyActivity.class)
                                .putExtra("role", role)
                                .putExtra("st", st));
                        finish();
                        break;
                    default:
                        Toast.makeText(FarmerhomeActivity.this, "Contact developer", Toast.LENGTH_SHORT).show();
                }

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(FarmerhomeActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FarmerhomeActivity.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
}
