package com.example.gcoaquira.aplicacionuptbus.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.gcoaquira.aplicacionuptbus.R;
import com.example.gcoaquira.aplicacionuptbus.interfaces.AlertDialogEvent;

public class CommonHelper {
    public static void displayPromptForEnablingGPS(final AppCompatActivity activity) {
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = activity.getString(R.string.prompt_enable_gps);
        LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialogBuilder.show(activity, message, AlertDialogBuilder.DialogButton.OK_CANCEL, new AlertDialogEvent() {
                @Override
                public void onAnswerDialog(AlertDialogBuilder.DialogResult result) {
                    if (result == AlertDialogBuilder.DialogResult.OK)
                        activity.startActivity(new Intent(action));
                }
            });
        }
    }

    public static void checkLocationPermission(final AppCompatActivity appCompatActivity) {
        // Here, thisActivity is the current activity
        if (ActivityCompat.checkSelfPermission(appCompatActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(appCompatActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(appCompatActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialogBuilder.show(appCompatActivity, appCompatActivity.getString(R.string.prompt_location_service), AlertDialogBuilder.DialogButton.OK, new AlertDialogEvent() {
                    @Override
                    public void onAnswerDialog(AlertDialogBuilder.DialogResult result) {
                        int MY_PERMISSIONS_REQUEST_LOCATION = 1;
                        ActivityCompat.requestPermissions(appCompatActivity,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION);
                    }
                });
            } else {
                int MY_PERMISSIONS_REQUEST_LOCATION = 1;
                ActivityCompat.requestPermissions(appCompatActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }
}
