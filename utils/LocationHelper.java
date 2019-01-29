package com.example.gcoaquira.aplicacionuptbus.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.example.gcoaquira.aplicacionuptbus.interfaces.LocationChangeListener;

public class LocationHelper {
    private static LocationManager mLocationManager;
    private static LocationManager mLocationManagerG;
    private Context con;
    private static double Latitude = 0;
    private static double Longitude = 0;
    public LocationHelper(Context context){
        con = context;
    }

    public static double getLatitude() {
        return Latitude;
    }

    public static double getLongitude() {
        return Longitude;
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            Latitude = location.getLatitude();
            Longitude = location.getLongitude();
            try {
                LocationChangeListener l = (LocationChangeListener) con;
                l.OnLocationChange(String.valueOf(Latitude), String.valueOf(Longitude));
            } catch (Exception ignored) {
            }


        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }
    };



    public void loadGps(LocationListener listener, LocationListener listener1) {
        mLocationManager = (LocationManager) con.getSystemService(Context.LOCATION_SERVICE);
        mLocationManagerG = (LocationManager) con.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(con, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        if (listener == null) {

            mLocationManagerG.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 20, mLocationListener);
        } else {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, listener1);
            mLocationManagerG.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 20, listener);
        }
    }

    public void loadGps(LocationListener listener) {
        mLocationManager = (LocationManager) con.getSystemService(Context.LOCATION_SERVICE);
        mLocationManagerG = (LocationManager) con.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(con, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(con, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mLocationManagerG.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, listener);

    }

    public static int distFrom(double lat1, double lng1, double lat2, double lng2) {

        float[] res= new float[3];
        Location locationA = new Location("LocationA");
        locationA.setLatitude(lat1);
        locationA.setLongitude(lng1);
        Location locationB = new Location("LocationB");
        locationB.setLatitude(lat2);
        locationB.setLongitude(lng2);
        locationA.distanceTo(locationB);

        return (int)locationA.distanceTo(locationB);
    }
}
