package com.example.pele;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.AdapterView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DataPencoblos extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listview;
    DBHelper dbHelper;
    LayoutInflater inflater;
    View dialogView;

    TextView tv_NIK, tv_Nama, tv_Tempatlahir, tv_Tanggal, tv_JK, tv_Alamat, tv_Status, tv_Agama, tv_Kewarganegaraan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pencoblos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataPencoblos.this, TambahDataPencoblos.class));
            }
        });



        dbHelper = new DBHelper(this);
        listview = (ListView) findViewById(R.id.list_data);
        listview.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_pencoblos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setListView() {
        Cursor cursor = dbHelper.allDataUser();
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, cursor, 1);
        listview.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView getId = (TextView) view.findViewById(R.id.listID);
        final long id = Long.parseLong(getId.getText().toString());
        final Cursor cur = dbHelper.ambilSatuDataUser(id);
        cur.moveToFirst();

        final AlertDialog.Builder builder = new AlertDialog.Builder(DataPencoblos.this);
        builder.setTitle("Pilih Opsi");
        String[] options = {"Lihat Data", "Edit Data", "Hapus Data"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        final AlertDialog.Builder viewData = new AlertDialog.Builder(DataPencoblos.this);
                        inflater = getLayoutInflater();
                        dialogView = inflater.inflate(R.layout.activity_lihat_data_pencoblos, null);
                        viewData.setView(dialogView);
                        viewData.setTitle("Lihat Data");

                        tv_NIK = (TextView)dialogView.findViewById(R.id.tv_NIK);
                        tv_Nama = (TextView)dialogView.findViewById(R.id.tv_Nama);
                        tv_Tempatlahir = (TextView)dialogView.findViewById(R.id.tv_TempatLahir);
                        tv_Tanggal = (TextView)dialogView.findViewById(R.id.tv_Tanggal);
                        tv_JK = (TextView)dialogView.findViewById(R.id.tv_JK);
                        tv_Alamat = (TextView)dialogView.findViewById(R.id.tv_Alamat);
                        tv_Agama = (TextView)dialogView.findViewById(R.id.tv_Agama);
                        tv_Status = (TextView)dialogView.findViewById(R.id.tv_Status);
                        tv_Kewarganegaraan = (TextView)dialogView.findViewById(R.id.tv_Kewarganegaraan);

                        tv_NIK.setText("NIK : " + cur.getString(cur.getColumnIndex(DBHelper.user_nik)));
                        tv_Nama.setText("Nama Lengkap : " + cur.getString(cur.getColumnIndex(DBHelper.user_nama)));
                        tv_Tempatlahir.setText("TempatLahir : " + cur.getString(cur.getColumnIndex(DBHelper.user_tempatlahir)));
                        tv_Tanggal.setText("Tanggal Lahir : " + cur.getString(cur.getColumnIndex(DBHelper.user_tanggallahir)));
                        tv_JK.setText("Jenis Kelamin : " + cur.getString(cur.getColumnIndex(DBHelper.user_jeniskelamin)));
                        tv_Alamat.setText("Alamat : " + cur.getString(cur.getColumnIndex(DBHelper.user_alamat)));
                        tv_Agama.setText("Agama : " + cur.getString(cur.getColumnIndex(DBHelper.user_agama)));
                        tv_Status.setText("Status : " + cur.getString(cur.getColumnIndex(DBHelper.user_status)));
                        tv_Kewarganegaraan.setText("Kewarganegaraan : " + cur.getString(cur.getColumnIndex(DBHelper.user_kewarganegaraan)));

                        viewData.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        viewData.show();
                }
                switch (which){
                    case 1:
                        Intent iddata = new Intent(DataPencoblos.this, UpdateDataPencoblos.class);
                        iddata.putExtra(DBHelper.user_id, id);
                        startActivity(iddata);
                }
                switch (which){
                    case 2:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(DataPencoblos.this);
                        builder1.setMessage("Data ini akan dihapus.");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.deleteDataUser(id);
                                Toast.makeText(DataPencoblos.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                                setListView();
                            }
                        });
                        builder1.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder1.create();
                        alertDialog.show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

            }
    @Override
    protected void onResume() {
        super.onResume();
        setListView();
    }
}