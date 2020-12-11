package com.example.pele;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomCursorAdapterUser extends CursorAdapter{

    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CustomCursorAdapterUser(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.row_data_dashboardpencoblos, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ListIdCalon = (TextView)v.findViewById(R.id.listIDCalon);
        holder.ListNoUrut = (TextView)v.findViewById(R.id.tv_NoUrutCalon);
        holder.ListGambarCalon = (ImageView)v.findViewById(R.id.gambarcalon);
        holder.ListCalonCount = (TextView)v.findViewById(R.id.tv_calon_count);
        holder.ListNamaKetua = (TextView)v.findViewById(R.id.tv_NamaKetua);
        holder.ListNamaWakil = (TextView)v.findViewById(R.id.tv_NamaWakil);
        holder.ListBtnPilih = (Button)v.findViewById(R.id.btn_calon_pilih);
        v.setTag(holder);
        return v;
    }

    @Override
    public void
    bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.ListIdCalon.setText(cursor.getString(cursor.getColumnIndex(DBHelper.calon_id)));
        holder.ListNoUrut.setText(cursor.getString(cursor.getColumnIndex(DBHelper.calon_nourut)));
        holder.ListNamaKetua.setText(cursor.getString(cursor.getColumnIndex(DBHelper.calon_namaketua)));
        holder.ListNamaWakil.setText(cursor.getString(cursor.getColumnIndex(DBHelper.calon_namawakil)));

    }

    class MyHolder{
        TextView ListIdCalon;
        TextView ListNoUrut;
        ImageView ListGambarCalon;
        TextView ListCalonCount;
        TextView ListNamaKetua;
        TextView ListNamaWakil;
        Button ListBtnInformasi;
        Button ListBtnPilih;
    }

}
