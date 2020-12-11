package com.example.pele;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
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

public class UpdateDataPencoblos extends AppCompatActivity {
    DBHelper dbHelper;
    EditText TxNIK, TxNama, TxTempatlahir, TxTanggal, TxAlamat;
    Spinner SpJK, SpAgama, SpStatus, SpKewarganegaraan;
    long id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_pencoblos);

        dbHelper = new DBHelper(this);
        id = getIntent().getLongExtra(DBHelper.user_id, 0);

        TxNIK = (EditText)findViewById(R.id.txNIK_edit);
        TxNama = (EditText)findViewById(R.id.txNama_edit);
        TxTempatlahir = (EditText)findViewById(R.id.txTempat_Lahir_edit);
        TxTanggal = (EditText)findViewById(R.id.txTglLahir_edit);
        TxAlamat = (EditText)findViewById(R.id.txAlamat_edit);
        SpJK = (Spinner)findViewById(R.id.spJK_edit);
        SpAgama = (Spinner)findViewById(R.id.spAgama_edit);
        SpStatus = (Spinner)findViewById(R.id.spStatus_edit);
        SpKewarganegaraan = (Spinner)findViewById(R.id.spKewarganegaraan_edit);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        TxTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        getData();

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
    private void getData() {
        Cursor cursor = dbHelper.ambilSatuDataUser(id);
        if(cursor.moveToFirst()){
            String NIK = cursor.getString(cursor.getColumnIndex(DBHelper.user_nik));
            String nama = cursor.getString(cursor.getColumnIndex(DBHelper.user_nama));
            String tempatlahir = cursor.getString(cursor.getColumnIndex(DBHelper.user_tempatlahir));
            String tanggal = cursor.getString(cursor.getColumnIndex(DBHelper.user_tanggallahir));
            String alamat = cursor.getString(cursor.getColumnIndex(DBHelper.user_alamat));
            String jk = cursor.getString(cursor.getColumnIndex(DBHelper.user_jeniskelamin));
            String agama = cursor.getString(cursor.getColumnIndex(DBHelper.user_agama));
            String status = cursor.getString(cursor.getColumnIndex(DBHelper.user_status));
            String kewarganegaraan = cursor.getString(cursor.getColumnIndex(DBHelper.user_kewarganegaraan));

            TxNIK.setText(NIK);
            TxNama.setText(nama);
            TxTempatlahir.setText(tempatlahir);
            TxTanggal.setText(tanggal);
            TxAlamat.setText(alamat);

            if (jk.equals("Laki-laki")){
                SpJK.setSelection(0);
            }else if(jk.equals("Perempuan")){
                SpJK.setSelection(1);
            }
            if (agama.equals("Islam")){
                SpAgama.setSelection(0);
            }else if(jk.equals("Kristen")){
                SpAgama.setSelection(1);
            }
            if (status.equals("Belum Menikah")){
                SpStatus.setSelection(0);
            }else if(jk.equals("Sudah Menikah")){
                SpStatus.setSelection(1);
            }
            if (kewarganegaraan.equals("Indonesia")){
                SpStatus.setSelection(0);
            }else if(jk.equals("Sudah Menikah")){
                SpStatus.setSelection(1);
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu_pencoblos, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_edit:
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
                    Toast.makeText(UpdateDataPencoblos.this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{

                    dbHelper.updateDataUser(values, id);
                    Toast.makeText(UpdateDataPencoblos.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                    finish();

                }
        }
        switch (item.getItemId()){
            case R.id.delete_edit:
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDataPencoblos.this);
                builder.setMessage("Data ini akan dihapus.");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteDataUser(id);
                        Toast.makeText(UpdateDataPencoblos.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}