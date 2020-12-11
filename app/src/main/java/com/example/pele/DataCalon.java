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

public class DataCalon extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView listview;
    DBHelper dbHelper;
    LayoutInflater inflater;
    View dialogView;

    TextView tvNamaKetua, tvNamaWakil, tvVisi, tvMisi, tvGambar, tvNourut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_calon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataCalon.this, TambahDataCalon.class));
            }
        });

        dbHelper = new DBHelper(this);
        listview = (ListView) findViewById(R.id.list_data);
        listview.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_calon, menu);
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
        Cursor cursor = dbHelper.allDataCalon();
        CustomCursorAdapterCalon customCursorAdapter = new CustomCursorAdapterCalon(this, cursor, 1);
        listview.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView getId = (TextView) view.findViewById(R.id.listID1);
        final long id = Long.parseLong(getId.getText().toString());
        final Cursor cur = dbHelper.ambilSatuDataCalon(id);
        cur.moveToFirst();


        final AlertDialog.Builder builder = new AlertDialog.Builder(DataCalon.this);
        builder.setTitle("Pilih Opsi");
        String[] options = {"Lihat Data", "Edit Data", "Hapus Data"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        final AlertDialog.Builder viewData = new AlertDialog.Builder(DataCalon.this);
                        inflater = getLayoutInflater();
                        dialogView = inflater.inflate(R.layout.activity_lihat_data_calon, null);
                        viewData.setView(dialogView);
                        viewData.setTitle("Lihat Data");

                        tvNamaKetua = (TextView)dialogView.findViewById(R.id.tv_NamaKetua);
                        tvNamaWakil = (TextView)dialogView.findViewById(R.id.tv_NamaWakil);
                        tvVisi = (TextView)dialogView.findViewById(R.id.tv_Visi);
                        tvMisi = (TextView)dialogView.findViewById(R.id.tv_Misi);
                        tvGambar = (TextView)dialogView.findViewById(R.id.tv_Gambar);
                        tvNourut = (TextView)dialogView.findViewById(R.id.tv_Nourut);

                        tvNamaKetua.setText("Nama Ketua : " + cur.getString(cur.getColumnIndex(DBHelper.calon_namaketua)));
                        tvNamaWakil.setText("Nama Wakil : " + cur.getString(cur.getColumnIndex(DBHelper.calon_namawakil)));
                        tvVisi.setText("Visi : " + cur.getString(cur.getColumnIndex(DBHelper.calon_visi)));
                        tvMisi.setText("Misi : " + cur.getString(cur.getColumnIndex(DBHelper.calon_misi)));
                        tvGambar.setText("Gambar : " + cur.getString(cur.getColumnIndex(DBHelper.calon_gambar)));
                        tvNourut.setText("No Urut : " + cur.getString(cur.getColumnIndex(DBHelper.calon_nourut)));

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
                        Intent iddata = new Intent(DataCalon.this, UpdateDataCalon.class);
                        iddata.putExtra(DBHelper.calon_id, id);
                        startActivity(iddata);
                }
                switch (which){
                    case 2:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(DataCalon.this);
                        builder1.setMessage("Data ini akan dihapus.");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.deleteDataCalon(id);
                                Toast.makeText(DataCalon.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
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