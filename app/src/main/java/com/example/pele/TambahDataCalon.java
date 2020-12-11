package com.example.pele;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
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


public class TambahDataCalon extends AppCompatActivity {
    DBHelper dbHelper;
    EditText txNamaKetua, txNamaWakil, txVisi, txMisi, txNourut;
    long id;
    CircularImageView imageView;
    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_calon);

        dbHelper = new DBHelper(this);
        id = getIntent().getLongExtra(DBHelper.calon_id, 0);

        txNamaKetua = (EditText)findViewById(R.id.txNamaKetua_Add);
        txNamaWakil = (EditText)findViewById(R.id.txNamaWakil_Add);
        txVisi = (EditText)findViewById(R.id.txVisi_Add);
        txMisi = (EditText)findViewById(R.id.txMisi_Add);
        imageView = (CircularImageView) findViewById(R.id.image_profile);
        txNourut = (EditText)findViewById(R.id.txNourut_Add);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.startPickImageActivity(TambahDataCalon.this);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu_calon, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_add:
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
                values.put(DBHelper.calon_gambar, String.valueOf(uri));
                values.put(DBHelper.calon_nourut, nourut);

                if (namaketua.equals("") || namawakil.equals("") || visi.equals("") || misi.equals("") || nourut.equals("")) {
                    Toast.makeText(TambahDataCalon.this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else {

                    dbHelper.insertDataCalon(values);
                    Toast.makeText(TambahDataCalon.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                    finish();

                }
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi (Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageuri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)) {
                uri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                startCrop(imageuri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result =  CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                imageView.setImageURI(result.getUri());
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