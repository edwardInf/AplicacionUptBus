package com.example.gcoaquira.aplicacionuptbus;

import android.content.BroadcastReceiver;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.util.Log;

public class ProximityIntentReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {

        String key = LocationManager.KEY_PROXIMITY_ENTERING;
        Boolean entering = intent.getBooleanExtra(key, false);

        if (entering) {
            Log.d(getClass().getSimpleName(), "entering");
        }
        else {
            Log.d(getClass().getSimpleName(), "exiting");
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(FragmentoRutas.act, 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(FragmentoRutas.act);

        builder.setAutoCancel(false);
        builder.setTicker("Alerta");
        builder.setContentTitle("Bus Cerca");
        builder.setContentText("mensaje");
        builder.setSmallIcon(R.drawable.ic_launcher_user);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setNumber(100);

        myNotication = builder.getNotification();
        manager.notify(11, myNotication);

    }
    NotificationManager manager;
    Notification myNotication;

    private Notification createNotification() {
        Notification notification = new Notification();

        notification.icon = R.drawable.ic_launcher_user;
        notification.when = System.currentTimeMillis();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;

        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;

        notification.ledARGB = Color.WHITE;
        notification.ledOnMS = 1500;
        notification.ledOffMS = 1500;

        return notification;
    }
}
