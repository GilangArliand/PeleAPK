package com.example.pele;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardUser extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listview;
    DBHelper dbHelper;
    LayoutInflater inflater;
    View dialogView;

    TextView tv_NoUrutCalon, tv_CountCalon, tv_NamaKetua, tv_NamaWakil, tvNamaKetua, tvNamaWakil, tvVisi, tvMisi, tvGambar, tvNourut;
    ImageView gambarcalon;
    Button btn_informasi, btn_pilih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);
        btn_pilih = (Button)findViewById(R.id.btn_calon_pilih);
        dbHelper = new DBHelper(this);
        listview = (ListView) findViewById(R.id.list_data);


    }

    public void setListView() {
        Cursor cursor = dbHelper.allDataCalon();
        CustomCursorAdapterUser customCursorAdapter = new CustomCursorAdapterUser(this, cursor, 1);
        listview.setAdapter(customCursorAdapter);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView getId = (TextView) view.findViewById(R.id.listIDCalon);
        final long id = Long.parseLong(getId.getText().toString());
        final Cursor cur = dbHelper.ambilSatuDataCalon(id);
        cur.moveToFirst();


        final AlertDialog.Builder builder = new AlertDialog.Builder(DashboardUser.this);
        builder.setTitle("Pilih Opsi");
        String[] options = {"Lihat Data"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        final AlertDialog.Builder viewData = new AlertDialog.Builder(DashboardUser.this);
                        inflater = getLayoutInflater();
                        dialogView = inflater.inflate(R.layout.activity_lihat_data_calon, null);
                        viewData.setView(dialogView);
                        viewData.setTitle("Lihat Data");

                        tvNamaKetua = (TextView)dialogView.findViewById(R.id.tv_NamaKetua);
                        tvNamaWakil = (TextView)dialogView.findViewById(R.id.tv_NamaWakil);
                        tvVisi = (TextView)dialogView.findViewById(R.id.tv_Visi);
                        tvMisi = (TextView)dialogView.findViewById(R.id.tv_Misi);
                        tvNourut = (TextView)dialogView.findViewById(R.id.tv_Nourut);

                        tvNamaKetua.setText("Nama Ketua : " + cur.getString(cur.getColumnIndex(DBHelper.calon_namaketua)));
                        tvNamaWakil.setText("Nama Wakil : " + cur.getString(cur.getColumnIndex(DBHelper.calon_namawakil)));
                        tvVisi.setText("Visi : " + cur.getString(cur.getColumnIndex(DBHelper.calon_visi)));
                        tvMisi.setText("Misi : " + cur.getString(cur.getColumnIndex(DBHelper.calon_misi)));
                        tvNourut.setText("No Urut : " + cur.getString(cur.getColumnIndex(DBHelper.calon_nourut)));

                        viewData.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        viewData.show();
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