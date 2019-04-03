package com.example.messagemanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MessageManager extends AppCompatActivity implements ListAdapter.OnSMSOpener {
    int img[]={R.drawable.avatar_1,R.drawable.avatar_2,R.drawable.avatar_3,R.drawable.avatar_4,R.drawable.avatar_5,R.drawable.avatar_6,R.drawable.avatar_7,R.drawable.avatar_8,
           R.drawable.avatar_9,R.drawable.avatar_10,R.drawable.avatar_11,R.drawable.avatar_12,R.drawable.avatar_13,R.drawable.avatar_14,R.drawable.avatar_15,R.drawable.avatar_16};
    ArrayList<String>title,body,numbers;
    ArrayList<item_Adapter> al;
    ListAdapter obj;
    RecyclerView rv;
    Toolbar toolbar;
    Bundle bd;
    public static final String TAG="Message Manager App";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_manager);
        Log.d(TAG, "onCreate: Started Main");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Mesages");
        rv=findViewById(R.id.rv);
        FloatingActionButton fab = findViewById(R.id.fab);
        setSupportActionBar(toolbar);
        al=new ArrayList<>();
        body=new ArrayList<>();
        title=new ArrayList<>();
        numbers=new ArrayList<>();
        al=new ArrayList<>();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               newMessage();
            }
        });

        int permission= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        if(permission== PackageManager.PERMISSION_GRANTED) {
            showContacts();
        }
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},100);
            permission= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
            if(permission== PackageManager.PERMISSION_GRANTED) {
                showContacts();
            }
        }

        for(int i=0;i<title.size();i++)
        {
            Log.d(TAG, "onCreate: Fetching All messages");
            al.add(new item_Adapter(img[i%15], title.get(i), body.get(i), numbers.get(i)));
        }
        obj=new ListAdapter(al, this);
        rv.setAdapter(obj);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void newMessage() {

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
        Log.d(TAG, "showContacts: On message Container");
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
                System.out.println(smsDate);
                System.out.println(calendar.get(Calendar.YEAR));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    System.out.println(Month.of(calendar.get(Calendar.MONTH)).name());
                }
                System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
                System.out.println(calendar.get((Calendar.MINUTE)));
                System.out.println(calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
                System.out.println(calendar.get(Calendar.DATE));
                System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
                System.out.println(calendar.get(Calendar.SECOND));

                title.add(number);
                body.add(text);
                numbers.add(smsDate.substring(0,16));
            }
            cr.close();
        }

    }
    @Override
    public void onSMSClick(int position) {
        //Toast.makeText(this, ""+title.get(position), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onSMSClick: On Single Item clicked");
        Intent i1=new Intent(this,SingleSms.class);
        bd=new Bundle();
        bd.putInt("keyposition",position);
        bd.putIntArray("keyimg",img);
        bd.putStringArrayList("keytitle",title);
        bd.putStringArrayList("keybody",body);
        bd.putStringArrayList("keynumbers",numbers);
        startActivity(i1);
    }

}
