package com.example.pele;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CustomCursorAdapterCalon extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CustomCursorAdapterCalon(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.row_data_calon, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ListID1 = (TextView)v.findViewById(R.id.listID1);
        holder.ListNama1 = (TextView)v.findViewById(R.id.listNama1);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.ListID1.setText(cursor.getString(cursor.getColumnIndex(DBHelper.calon_id)));
        holder.ListNama1.setText(cursor.getString(cursor.getColumnIndex(DBHelper.calon_namaketua)));
    }


    class MyHolder{
        TextView ListID1;
        TextView ListNama1;
    }
}
