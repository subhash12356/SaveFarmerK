package com.example.kalpesh.savefarmer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FarmerregActivity extends AppCompatActivity {
    TextView tv_status;
    String status;
    Button rl_registration, rl_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmerreg);
        tv_status = findViewById(R.id.tv_status);
        rl_registration = findViewById(R.id.rl_registration);
        rl_login = findViewById(R.id.rl_login);

        Intent in = getIntent();
        Bundle b = in.getExtras();

        if (b != null) {
            status = b.getString("status");

            tv_status.setText(status);
        }

        rl_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FarmerregActivity.this,RegistrationActivity.class)
                .putExtra("status",status));
                finish();
            }
        });

        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FarmerregActivity.this,LoginActivity.class)
                        .putExtra("status",status));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FarmerregActivity.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
}
