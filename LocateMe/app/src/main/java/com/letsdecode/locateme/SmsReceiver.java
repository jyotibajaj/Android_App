package com.letsdecode.locateme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class SmsReceiver extends BroadcastReceiver {
    LocationManager mLocationManager;
    long LOCATION_REFRESH_TIME;
    float LOCATION_REFRESH_DISTANCE;
    SmsMessage[] message = null;
    String phoneNumber;
    String messageBody;
    double latitude;
    double longitude;
    Geocoder geocoder;
    private SharedPreferences sharedpreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        //get the sms message passed in
        Bundle bundle = intent.getExtras();
        //get reference of SharedPreferences via context
        sharedpreferences = context.getSharedPreferences(DisplayActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        if (bundle != null && sharedpreferences != null) {
            //get the value of code
            final String secretCodeValue = sharedpreferences.getString(DisplayActivity.SECRET_CODE, null);
            //if null or empty return
            if (secretCodeValue == null || secretCodeValue.isEmpty()) {
                return;
            }
            // Read the sms content
            Object[] sms = (Object[]) bundle.get("pdus");
            message = new SmsMessage[sms.length];
            for (int i = 0; i < message.length; i++) {
                message[i] = SmsMessage.createFromPdu((byte[]) sms[i]);
                if (i == 0) {
                    //get the sender's phonenumber
                    phoneNumber = message[i].getOriginatingAddress();
                }
                //messageBody
                messageBody = message[i].getMessageBody().toString();
            }
            //Verify sms content to see if secret code matches
            if (messageBody.equals(secretCodeValue)) {
                //handling of location on/off
                boolean gpsAvailable = false;
                //Initialize Geocoder before subscribing for locations
                geocoder = new Geocoder(context, Locale.getDefault());
                mLocationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
                if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_REFRESH_TIME,
                                LOCATION_REFRESH_DISTANCE, mLocationListener);
                        gpsAvailable = true;
                    } else {
                        gpsAvailable = false;
                    }
                } else {
                    gpsAvailable = true;
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                            LOCATION_REFRESH_DISTANCE, mLocationListener);
                }

                if (gpsAvailable == false) {
                    sendMessage("Sorry, GPS on my device is disabled. I can't provide my current location at this moment");
                }
                //else if messageBody notEquals secretCodeValue kindly return
            } else {
                return;
            }

        }
    }

    private void sendMessage(String sms) {
        final SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, sms, null, null);

    }

    final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            //single call to update location
            mLocationManager.removeUpdates(mLocationListener);
            if (geocoder == null) return;
            String sms = "http://maps.google.com/?q=" + latitude
                    + "," + longitude;
            try {
                //this uses internet
                List<Address> addressList = geocoder.getFromLocation(
                        latitude, longitude, 1);

                if (addressList != null && addressList.size() > 0) {
                    Address address = addressList.get(0);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        sb.append(address.getAddressLine(i)).append("\n");
                    }
                    sb.append(address.getLocality()).append("\n");
                    sb.append(address.getPostalCode()).append("\n");
                    sb.append(address.getCountryName());
                    //if everything works fine then append geo coded address and use this to send
                    sms = sb.toString();
                }
            } catch (IOException e) {

            }
            sendMessage(sms);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d("Latitude", "disable");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d("Latitude", "enable");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("Latitude", "status");
        }
    };


}


