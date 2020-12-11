package com.example.pele;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardAdmin extends AppCompatActivity {

    Button btnDataPencoblos,btnDataCalon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        btnDataPencoblos = (Button)findViewById(R.id.btnDataPencoblos);
        btnDataCalon = (Button)findViewById(R.id.btnDataCalon);

        btnDataPencoblos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardAdmin.this, DataPencoblos.class));
            }
        });

        btnDataCalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardAdmin.this, DataCalon.class));
            }
        });
    }
}