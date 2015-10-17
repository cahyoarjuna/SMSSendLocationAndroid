package com.example.xeranta.testtest;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.location.Location;
import android.location.Geocoder;
import android.location.Address;
import java.util.List;
import android.widget.EditText;
import android.widget.Toast;
import android.telephony.SmsManager;
import java.util.Locale;
import android.location.LocationManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import java.io.IOException;
public class MainActivity extends Activity {

  //  Context context;
  Location location;
    Geocoder geocoder;
    List<Address> addresses;
    double latitude=0;
    double longitude=0;
    EditText txtNumberPhone;
    String phoneNo = "";
    String message ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        final Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        geocoder = new Geocoder(this, Locale.getDefault());
        txtNumberPhone = (EditText)findViewById(R.id.etPhone);
        Button btnSends = (Button) findViewById(R.id.btnSend);
        btnSends.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                message =    "ADDRESS : "+address +" "+city+" " +state+" " +country +"         LOCATION :"+longitude+" " +latitude    ;
                phoneNo = txtNumberPhone.getText().toString();
                Log.i("===========  ",""+phoneNo);
                sendSMSMessage();


            }
        });

    }

    protected void sendSMSMessage() {


      //  String message = txtMessage.getText().toString();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
           Toast.makeText(getApplicationContext(), "SMS sent.",
                   Toast.LENGTH_LONG).show();
        } catch (Exception e) {
          Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    }


