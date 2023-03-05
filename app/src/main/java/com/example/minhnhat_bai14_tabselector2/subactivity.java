package com.example.minhnhat_bai14_tabselector2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class subactivity extends AppCompatActivity {
    TextView txtmaso,txtbaihat,txtloibaihat,txttacgia;
    ImageButton btnlove, btnunlove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity);
        txtmaso= (TextView) findViewById(R.id.edtMaSo);
        txtbaihat= (TextView) findViewById(R.id.edtSong);
        txtloibaihat=(TextView)  findViewById(R.id.edtloibaihat);
        txttacgia= (TextView) findViewById(R.id.edtTacgia);
        btnlove= (ImageButton) findViewById(R.id.btnlove);
        btnunlove= (ImageButton) findViewById(R.id.btnunlove);
        Intent callerIntent1= getIntent();
        Bundle backagecaller1= callerIntent1.getBundleExtra("package");
        String maso=  backagecaller1.getString("maso");
        Cursor c= MainActivity.database.rawQuery("SELECT * FROM ArirangSongList WHERE MABH LIKE '"+ maso+"'",null);
        txtmaso.setText(maso);
        c.moveToFirst();
        txtbaihat.setText(c.getString(2));
        txtloibaihat.setText(c.getString(3));
        txttacgia.setText(c.getString(4));
        if(c.getInt(6)==0){
            btnlove.setVisibility(View.INVISIBLE);
            btnunlove.setVisibility(View.VISIBLE);
        }else {
            btnlove.setVisibility(View.VISIBLE);
            btnunlove.setVisibility(View.INVISIBLE);
        }
        c.close();
        btnlove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values= new ContentValues();
                values.put("YeuThich",0);
                MainActivity.database.update("ArirangSongList", values,"MaBH=?",new String[]{txtmaso.getText().toString()});
                btnlove.setVisibility(View.INVISIBLE);
                btnunlove.setVisibility(View.VISIBLE);
            }
        });
        btnunlove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values= new ContentValues();
                values.put("YeuThich",1);
                MainActivity.database.update("ArirangSongList", values,"MaBH=?",new String[]{txtmaso.getText().toString()});
                btnlove.setVisibility(View.VISIBLE);
                btnunlove.setVisibility(View.INVISIBLE);
            }
        });
    }
}