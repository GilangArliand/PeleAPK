package com.example.pele;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TambahDataPencoblos extends AppCompatActivity {
    DBHelper dbHelper;
    EditText TxNIK, TxNama, TxTempatlahir, TxTanggal, TxAlamat;
    Spinner SpJK, SpAgama, SpStatus, SpKewarganegaraan;
    long id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_pencoblos);

        dbHelper = new DBHelper(this);
        id = getIntent().getLongExtra(DBHelper.user_id, 0);

        TxNIK = (EditText)findViewById(R.id.txNIK_Add);
        TxNama = (EditText)findViewById(R.id.txNama_Add);
        TxTempatlahir = (EditText)findViewById(R.id.txTempat_Lahir_Add);
        TxTanggal = (EditText)findViewById(R.id.txTglLahir_Add);
        TxAlamat = (EditText)findViewById(R.id.txAlamat_Add);
        SpJK = (Spinner)findViewById(R.id.spJK_Add);
        SpAgama = (Spinner)findViewById(R.id.spAgama_Add);
        SpStatus = (Spinner)findViewById(R.id.spStatus_Add);
        SpKewarganegaraan = (Spinner)findViewById(R.id.spKewarganegaraan_Add);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        TxTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
    }

    private void showDateDialog(){
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                TxTanggal.setText(dateFormatter.format(newDate.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu_pencoblos, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_add:
                String NIK = TxNIK.getText().toString().trim();
                String nama = TxNama.getText().toString().trim();
                String tempatlahir = TxTempatlahir.getText().toString().trim();
                String tanggal = TxTanggal.getText().toString().trim();
                String alamat = TxAlamat.getText().toString().trim();
                String jk = SpJK.getSelectedItem().toString().trim();
                String agama = SpAgama.getSelectedItem().toString().trim();
                String status = SpStatus.getSelectedItem().toString().trim();
                String kewarganegaraan = SpKewarganegaraan.getSelectedItem().toString().trim();

                ContentValues values = new ContentValues();
                values.put(DBHelper.user_nik, NIK);
                values.put(DBHelper.user_nama, nama);
                values.put(DBHelper.user_tempatlahir, tempatlahir);
                values.put(DBHelper.user_tanggallahir, tanggal);
                values.put(DBHelper.user_alamat, alamat);
                values.put(DBHelper.user_jeniskelamin, jk);
                values.put(DBHelper.user_agama, agama);
                values.put(DBHelper.user_status, status);
                values.put(DBHelper.user_kewarganegaraan, kewarganegaraan);

                if (NIK.equals("") || nama.equals("") || tempatlahir.equals("") || tanggal.equals("") || alamat.equals("") || jk.equals("") || agama.equals("") || status.equals("") || kewarganegaraan.equals("")) {
                    Toast.makeText(TambahDataPencoblos.this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{
 
                    dbHelper.insertDataUser(values);
                    Toast.makeText(TambahDataPencoblos.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                    finish();

                }
        }
        return super.onOptionsItemSelected(item);
    }


}