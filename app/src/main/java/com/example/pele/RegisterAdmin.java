package com.example.pele;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

public class RegisterAdmin extends AppCompatActivity {

    EditText txUsername, txPassword, txCnfPassword;
    Button BtnRegister;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

        dbHelper = new DBHelper(this);

        txUsername = (EditText)findViewById(R.id.txUsernameReg);
        txPassword = (EditText)findViewById(R.id.txPasswordReg);
        txCnfPassword = (EditText)findViewById(R.id.txCnfPassword);
        BtnRegister = (Button)findViewById(R.id.BtnRegister);

        TextView tvRegister = (TextView)findViewById(R.id.tvRegister);

        tvRegister.setText(fromHtml("Back to Login" + "</font><font color='#3b5998'>"));

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterAdmin.this, LoginAdmin.class));
            }
        });

        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txUsername.getText().toString().trim();
                String password = txPassword.getText().toString().trim();
                String cnfpassword = txCnfPassword.getText().toString().trim();

                ContentValues values = new ContentValues();

                if (!password.equals(cnfpassword)){
                    Toast.makeText(RegisterAdmin.this, "Password Tidak Cocok", Toast.LENGTH_SHORT).show();
                } else if (password.equals("") || username.equals("")){
                    Toast.makeText(RegisterAdmin.this, "Username dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    values.put(DBHelper.admin_username, username);
                    values.put(DBHelper.admin_password, password);
                    dbHelper.insertDataAdmin(values);

                    Toast.makeText(RegisterAdmin.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                    finish();
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