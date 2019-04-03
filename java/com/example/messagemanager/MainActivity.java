package com.example.messagemanager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Toolbar tb;
    ListView lv;
    ArrayList<String> al;
    ArrayAdapter<String> adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        al=new ArrayList<>();
        tb=findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        lv=findViewById(R.id.lv);
        int permission= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        if(permission== PackageManager.PERMISSION_GRANTED) {
            showContacts();
        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},100);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==100)
        {
            showContacts();
        }
        else Toast.makeText(this, "App cannot work Without permission", Toast.LENGTH_SHORT).show();
    }
    private void showContacts() {
        Uri inboxuri= Uri.parse("content://sms/inbox");
        Cursor cr=getContentResolver().query(inboxuri,null,null,null,null);
        if (cr != null) {
            while (cr.moveToNext())
            {
                String number=cr.getString(cr.getColumnIndexOrThrow("address"));
                String text=cr.getString(cr.getColumnIndexOrThrow("body"));
               long millis = cr.getLong(cr.getColumnIndexOrThrow("date"));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis( millis );
                Date finalDate = calendar.getTime();
                String smsDate = finalDate.toString();

                /*
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(smsDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                */
                //String newString = new SimpleDateFormat("H:mm").format(smsDate);
                //System.out.println(newString);
                al.add("Number :"+number+"\n"+text+"\n"+smsDate);
            }
            cr.close();
        }
        adp=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,al);
        lv.setAdapter(adp);
    }
}
