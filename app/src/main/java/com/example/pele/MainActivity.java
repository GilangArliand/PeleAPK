package com.example.pele;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.pele.LoginAdmin.fromHtml;

public class MainActivity extends AppCompatActivity {
    EditText txNIK;
    Button btnMasukUser;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txNIK = (EditText) findViewById(R.id.txNIK);
        btnMasukUser = (Button)findViewById(R.id.btnMasukUser);
        dbHelper = new DBHelper(this);
        TextView tvMasukAdmin = (TextView) findViewById(R.id.tvMasukAdmin);
        tvMasukAdmin.setText(fromHtml("Masuk Sebagai Admin" + "</font><font color='#3b5998'>"));
        tvMasukAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginAdmin.class));
            }
        });
        btnMasukUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nik = txNIK.getText().toString().trim();


                Boolean res = dbHelper.checkUser(nik);
                if (res == true) {
                    Toast.makeText(MainActivity.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, DashboardUser.class));
                } else {
                    Toast.makeText(MainActivity.this, "Anda Tidak terdaftar dalam pemilu", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public static Spanned fromHtml(String html) {
        Spanned hasil;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            hasil = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            hasil = Html.fromHtml(html);
        }
        return hasil;
    }

}