package com.example.pele;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class UpdateDataCalon extends AppCompatActivity {
    DBHelper dbHelper;
    EditText txNamaKetua, txNamaWakil, txVisi, txMisi,  txNourut;
    long id;
    CircularImageView iGambar;
    Uri uri;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_calon);

        dbHelper = new DBHelper(this);
        id = getIntent().getLongExtra(DBHelper.calon_id, 0);

        txNamaKetua = (EditText) findViewById(R.id.txNamaKetua_edit);
        txNamaWakil = (EditText) findViewById(R.id.txNamaWakil_edit);
        txVisi = (EditText) findViewById(R.id.txVisi_edit);
        txMisi = (EditText) findViewById(R.id.txMisi_edit);
        iGambar = (CircularImageView) findViewById(R.id.image_profile_edit);
        txNourut = (EditText) findViewById(R.id.txNourut_edit);

        iGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(UpdateDataCalon.this);
            }
        });

        getData();
    }

    private void getData() {
        Cursor cursor = dbHelper.ambilSatuDataCalon(id);
        if (cursor.moveToFirst()) {
            String namaketua = cursor.getString(cursor.getColumnIndex(DBHelper.calon_namaketua));
            String namawakil = cursor.getString(cursor.getColumnIndex(DBHelper.calon_namawakil));
            String visi = cursor.getString(cursor.getColumnIndex(DBHelper.calon_visi));
            String misi = cursor.getString(cursor.getColumnIndex(DBHelper.calon_misi));
            String gambar = cursor.getString(cursor.getColumnIndex(DBHelper.calon_gambar));
            String nourut = cursor.getString(cursor.getColumnIndex(DBHelper.calon_nourut));

            txNamaKetua.setText(namaketua);
            txNamaWakil.setText(namawakil);
            txVisi.setText(visi);
            txMisi.setText(misi);
            txNourut.setText(nourut);

            if(gambar.equals("null")){
                iGambar.setImageResource(R.drawable.ic_baseline_person_24);
            } else {
                iGambar.setImageURI (Uri.parse(gambar));
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu_calon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_edit:
                String namaketua = txNamaKetua.getText().toString().trim();
                String namawakil = txNamaWakil.getText().toString().trim();
                String visi = txVisi.getText().toString().trim();
                String misi = txMisi.getText().toString().trim();
                String nourut = txNourut.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(DBHelper.calon_namaketua, namaketua);
                values.put(DBHelper.calon_namawakil, namawakil);
                values.put(DBHelper.calon_visi, visi);
                values.put(DBHelper.calon_misi, misi);
                values.put(DBHelper.calon_nourut, nourut);
                values.put(DBHelper.calon_gambar, String.valueOf(uri));

                if (namaketua.equals("") || namawakil.equals("") || visi.equals("") || misi.equals("") ||  nourut.equals("")) {
                    Toast.makeText(UpdateDataCalon.this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{

                    dbHelper.updateDataCalon(values, id);
                    Toast.makeText(UpdateDataCalon.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                    finish();

                }
        }
        switch (item.getItemId()){
            case R.id.delete_edit:
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDataCalon.this);
                builder.setMessage("Data ini akan dihapus.");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteDataCalon(id);
                        Toast.makeText(UpdateDataCalon.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
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

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageuri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)) {
                uri = imageuri;
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                startCrop(imageuri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result =  CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                iGambar.setImageURI(result.getUri());
            }
        }
    }

    private void startCrop(Uri imageuri) {
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(this);
        uri = imageuri;
    }
}