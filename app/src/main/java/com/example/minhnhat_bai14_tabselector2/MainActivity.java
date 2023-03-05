package com.example.minhnhat_bai14_tabselector2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edttim;
    ListView lv1,lv2,lv3;
    ArrayList<BaiHat> list1,list2,list3;
    AdapterClass myarray1,myarray2,myarray3;
    TabHost tab;
    ImageButton btnxoa;
    String DB_PATH_SUFFIX= "/databases/";
    public  static SQLiteDatabase database= null;
    public  static  String DATABASE_NAME= "arirang.sqlite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        processCopy();
        database = openOrCreateDatabase("arirang.sqlite", MODE_PRIVATE, null);
        addControl();
        addTim();
        addEvents();
    }




    private void addControl() {
        btnxoa = (ImageButton) findViewById(R.id.btnxoa);
        tab = (TabHost) findViewById(R.id.tabhost);
        tab.setup();
        TabHost.TabSpec tab1= tab.newTabSpec("t1");
        TabHost.TabSpec tab2= tab.newTabSpec("t2");
        TabHost.TabSpec tab3= tab.newTabSpec("t3");
        tab1.setContent(R.id.tab1);
        tab2.setContent(R.id.tab2);
        tab3.setContent(R.id.tab3);
        tab1.setIndicator("",getResources().getDrawable(R.drawable.search));
        tab2.setIndicator("",getResources().getDrawable(R.drawable.list));
        tab3.setIndicator("",getResources().getDrawable(R.drawable.favourite));
        tab.addTab(tab1);
        tab.addTab(tab2);
        tab.addTab(tab3);
        lv1 = (ListView) findViewById(R.id.lv1);
        lv2 =(ListView) findViewById(R.id.lv2);
        lv3 = (ListView) findViewById(R.id.lv3);
        list1 =new ArrayList<BaiHat>();
        list2 =new ArrayList<BaiHat>();
        edttim =(EditText) findViewById(R.id.edttim);
        list3 =new ArrayList<BaiHat>();
        myarray1 = new AdapterClass(MainActivity.this,R.layout.listview_custom,list1);
        myarray2 = new AdapterClass(MainActivity.this,R.layout.listview_custom,list2);
        myarray3 = new AdapterClass(MainActivity.this,R.layout.listview_custom,list3);
        lv1.setAdapter(myarray1);
        lv2.setAdapter(myarray2);
        lv3.setAdapter(myarray3);
    }

    private void addEvents() {
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if(s.equalsIgnoreCase("t2")){
                    addDanhSach();
                }
                if (s.equalsIgnoreCase("t3")) {
                    addYeuthich();
                }
            }


        });
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edttim.setText("");
            }
        });
    }

    private void addDanhSach() {
        myarray2.clear();
        Cursor c= database.rawQuery("SELECT * FROM ArirangSongList ",null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            list2 .add(new BaiHat(c.getString(1),c.getString(2),c.getInt(6)));
            c.moveToNext();
        }
        c.close();
        myarray2.notifyDataSetChanged();
    }

    private void addYeuthich() {
        myarray3.clear();
        Cursor c= database.rawQuery("SELECT * FROM ArirangSongList WHERE YEUTHICH=1",null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            list3.add(new BaiHat(c.getString(1),c.getString(2),c.getInt(6)));
            c.moveToNext();
        }
        c.close();
        myarray3.notifyDataSetChanged();
    }
    private void addTim() {
        edttim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getdata();
            }

            private void getdata() {
                String dulieunhap= edttim.getText().toString();
                myarray1.clear();
                if(!edttim.getText().toString().equals("")){
                    Cursor c = database.rawQuery("SELECT * FROM ArirangSongList WHERE TENBH1 LIKE '"+"%"+dulieunhap+"%"+"' OR MABH LIKE '"+"%"+dulieunhap+"%"+"'", null);
                    c.moveToFirst();
                    while (!c.isAfterLast()){
                        list1.add(new BaiHat(c.getString(1),c.getString(2),c.getInt(6)));
                        c.moveToNext();
                    }
                    c.close();
                }
                myarray1.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void processCopy() {

        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try{CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }
    public void CopyDataBaseFromAsset() {
// TODO Auto-generated method stub
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
// Path to the just created empty db
            String outFileName = getDatabasePath();
//if the path doesn't exist first, create it
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
//Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
//transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer, 0, length);
            }
//Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();} catch (IOException e) {
//TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

