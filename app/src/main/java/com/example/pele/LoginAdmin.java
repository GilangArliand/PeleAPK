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

import static android.text.Html.fromHtml;

public class LoginAdmin extends AppCompatActivity {

    EditText txUsername, txPassword;
    Button BtnLoginAdmin;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        txUsername = (EditText) findViewById(R.id.txUsername);
        txPassword = (EditText) findViewById(R.id.txPassword);
        BtnLoginAdmin = (Button) findViewById(R.id.btnLoginAdmin);

        dbHelper = new DBHelper(this);

        TextView tvBuatAkunAdmin = (TextView) findViewById(R.id.tvBuatAkunAdmin);
        tvBuatAkunAdmin.setText(fromHtml("buat akun" + "</font><font color='#3b5998'>"));

        tvBuatAkunAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAdmin.this, RegisterAdmin.class));
            }
        });

        BtnLoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txUsername.getText().toString().trim();
                String password = txPassword.getText().toString().trim();

                Boolean res = dbHelper.checkAdmin(username, password);
                if (res == true) {
                    Toast.makeText(LoginAdmin.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginAdmin.this, DashboardAdmin.class));
                } else {
                    Toast.makeText(LoginAdmin.this, "Login Gagal", Toast.LENGTH_SHORT).show();
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