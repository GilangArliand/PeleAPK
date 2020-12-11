package com.example.pele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "db_pele";
    public static final String table1 = "admin";
    public static final String table2 = "user";
    public static final String table3 = "calon";


    public static final String admin_id = "_id";
    public static final String admin_username = "username";
    public static final String admin_password = "password";
    public static final String user_id = "_id";
    public static final String user_nik = "nik";
    public static final String user_nama = "namauser";
    public static final String user_alamat = "alamatuser";
    public static final String user_jeniskelamin = "jeniskelaminuser";
    public static final String user_tempatlahir = "tempatlahiruser";
    public static final String user_tanggallahir = "tanggallahiruser";
    public static final String user_agama = "agamauser";
    public static final String user_status = "statususer";
    public static final String user_kewarganegaraan = "kewarganegaraanuser";
    public static final String calon_id = "_id";
    public static final String calon_namaketua = "namaketuacalon";
    public static final String calon_namawakil = "namawakilcalon";
    public static final String calon_visi = "visicalon";
    public static final String calon_misi = "misicalon";
    public static final String calon_gambar = "gambarcalon";
    public static final String calon_nourut = "nourutcalon";

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String t1 = "CREATE TABLE " +
                table1 +
                "(" +
                admin_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                admin_username + " TEXT," +
                admin_password + " TEXT)";
        String t2 = "CREATE TABLE " +
                table2 +
                "(" +
                user_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                user_nik + " TEXT," +
                user_nama + " TEXT," +
                user_alamat + " TEXT," +
                user_jeniskelamin + " TEXT," +
                user_tempatlahir + " TEXT," +
                user_tanggallahir + " DATE," +
                user_agama + " TEXT," +
                user_status + " TEXT," +
                user_kewarganegaraan + " TEXT)";
        String t3 = "CREATE TABLE " +
                table3 +
                "(" +
                calon_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                calon_namaketua + " TEXT," +
                calon_namawakil + " TEXT," +
                calon_visi + " TEXT," +
                calon_misi + " TEXT," +
                calon_gambar + " TEXT," +
                calon_nourut + " INT)";

        db.execSQL(t1);
        db.execSQL(t2);
        db.execSQL(t3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+table1);
        db.execSQL("DROP TABLE IF EXISTS "+table2);
        db.execSQL("DROP TABLE IF EXISTS "+table3);
        onCreate(db);
    }

    public void insertDataAdmin(ContentValues values) {

        db.insert(table1, null, values);

    };

    public boolean checkAdmin(String username, String password){
        String[] columns = {admin_id};
        SQLiteDatabase db = getReadableDatabase();
        String seleksi = admin_username + "=?" + " and " + admin_password + "=?";
        String[] selectionArgs = {username,password};
        Cursor cursor = db.query(table1, columns, seleksi, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count>0)
            return true;
        else
            return false;
    }

    public boolean checkUser(String nik){
        String[] columns = {user_id};
        SQLiteDatabase db = getReadableDatabase();
        String seleksi = user_nik + "=?";
        String[] selectonArgs = {nik};
        Cursor cursor = db.query(table2, columns, seleksi, selectonArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count>0)
            return true;
        else
            return false;
    }

    public Cursor allDataUser(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table2, null);
        return cur;
    }

    public Cursor allDataCalon(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table3, null);
        return cur;
    }

    public Cursor ambilSatuDataUser(Long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + table2 + " WHERE " + user_id + "=" + id, null);
        return cur;
    }

    public Cursor ambilSatuDataCalon(Long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + table3 + " WHERE " + calon_id + "=" + id, null);
        return cur;
    }

    public void insertDataUser(ContentValues values){
        db.insert(table2, null, values);
    }

    public void insertDataCalon(ContentValues values){
        db.insert(table3, null, values);
    }

    public void updateDataUser(ContentValues values, long id){
        db.update(table2, values, user_id + "=" + id, null);
    }

    public void updateDataCalon(ContentValues values, long id){
        db.update(table3, values, calon_id + "=" + id, null);
    }

    public void deleteDataUser(long id){
        db.delete(table2, user_id + "=" + id, null);
    }

    public void deleteDataCalon(long id){
        db.delete(table3, calon_id + "=" + id, null);
    }

    }

