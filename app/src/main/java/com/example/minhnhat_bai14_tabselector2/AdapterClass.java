package com.example.minhnhat_bai14_tabselector2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdapterClass extends ArrayAdapter<BaiHat> {
    Activity context= null;
    ArrayList<BaiHat> myarray=null;
    int layoutid;

    public AdapterClass( Activity context, int layoutid, ArrayList<BaiHat> myarray) {
        super(context, layoutid, myarray);
        this.context = context;
        this.myarray = myarray;
        this.layoutid = layoutid;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        convertView = inflater.inflate(layoutid,null);
        final BaiHat myItem= myarray.get(position);
        final TextView tieude= (TextView) convertView.findViewById(R.id.txtten);
        tieude.setText(myItem.getTenBai());
        final TextView maso= (TextView) convertView.findViewById(R.id.txtms);
        maso.setText(myItem.getMabai());
        final ImageView like = convertView.findViewById(R.id.btnlove);
        final ImageView unlike = convertView.findViewById(R.id.btnunlove);
        int thich= myItem.getLike();
        if(thich==0){
                like.setVisibility(View.INVISIBLE);
                unlike.setVisibility(View.VISIBLE);
            } else {
                like.setVisibility(View.VISIBLE);
                unlike.setVisibility(View.INVISIBLE);
            }
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values= new ContentValues();
                values.put("YeuThich",0);
                MainActivity.database.update("ArirangSongList", values,"MaBH=?",new String[]{maso.getText().toString()});
                like.setVisibility(View.INVISIBLE);
                unlike.setVisibility(View.VISIBLE);
            }
        });
        unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values= new ContentValues();
                values.put("YeuThich",1);
                MainActivity.database.update("ArirangSongList", values,"MaBH=?",new String[]{maso.getText().toString()});
                like.setVisibility(View.VISIBLE);
                unlike.setVisibility(View.INVISIBLE);
            }
        });
        tieude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tieude.setTextColor(Color.RED);
                maso.setTextColor(Color.RED);
                Intent intent1= new Intent(context,subactivity.class);
                Bundle bundle1= new Bundle();
                bundle1.putString("maso",maso.getText().toString());
                intent1.putExtra("package",bundle1);
                context.startActivity(intent1);
            }
        });
        return convertView;
    }
}
