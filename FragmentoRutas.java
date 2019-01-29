package com.example.gcoaquira.aplicacionuptbus;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gcoaquira.aplicacionuptbus.api.BusConductorResp;
import com.example.gcoaquira.aplicacionuptbus.api.Conexion;
import com.example.gcoaquira.aplicacionuptbus.modelo.BusesConductores;
import com.example.gcoaquira.aplicacionuptbus.utils.LocationHelper;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentoRutas extends Fragment implements OnMapReadyCallback {
    private View rootView;
    private GoogleMap mMap;
    MapView mMapView;
    public static FragmentActivity act;


    private static final long MINIMUM_DISTANCECHANGE_FOR_UPDATE = 1;
    private static final long MINIMUM_TIME_BETWEEN_UPDATE = 3000;
    private static final long POINT_RADIUS = 30;
    private static final long PROX_ALERT_EXPIRATION = -1;
    private static final String POINT_LATITUDE_KEY = "POINT_LATITUDE_KEY";
    private static final String POINT_LONGITUDE_KEY = "POINT_LONGITUDE_KEY";
    private static final String PROX_ALERT_INTENT = "com.example.gcoaquira.aplicacionuptbus.ProximityAlert";

    private static final NumberFormat nf = new DecimalFormat("##.########");

    private LocationManager locationManager;


    Marker driverPoint;

    ArrayList<Marker> driverMarkers;

    CountDownTimer getDriversTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        driverMarkers = new ArrayList<>();

        try {
            rootView = inflater.inflate(R.layout.fragment_fragmento_rutas, container, false);
            MapsInitializer.initialize(this.getActivity());

            mMapView = (MapView) rootView.findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
            act = getActivity();


            LocationHelper locationHelper = new LocationHelper(getContext());
            locationHelper.loadGps(new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                    Log.d("ZF", "X:" + String.valueOf(location.getLatitude()) + " - Y:" + String.valueOf(location.getLongitude()));

                    new MiUbicacionRequest().execute("1", String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));

                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, mMap.getCameraPosition().zoom > 5 ? mMap.getCameraPosition().zoom : 16);
                    mMap.animateCamera(cameraUpdate);

                    moveDriverPin(location.getLatitude(), location.getLongitude(), location.getBearing());
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });

        } catch (InflateException e) {
            e.printStackTrace();
        }


        locationManager = (LocationManager) act.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return null;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATE, MINIMUM_DISTANCECHANGE_FOR_UPDATE, new MyLocationListener());



        return rootView;

    }

    public void moveDriverPin(double lat, double lng, float rotation) {
        LatLng driver = new LatLng(lat, lng);
        driverPoint.setPosition(driver);
        driverPoint.setRotation(rotation);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    public static ArrayList<BusesConductores> coordenadas;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLastKnownLocation();

        if (getDriversTimer != null)
            getDriversTimer.cancel();

        getDriversTimer = new CountDownTimer(1200000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Conexion.obtenerLocalizacionBuses(new Callback<BusConductorResp>() {
                    @Override
                    public void onResponse(Call<BusConductorResp> call, Response<BusConductorResp> response) {
                        BusConductorResp jsonResponse = response.body();
                        ArrayList<BusesConductores> bc = new ArrayList<BusesConductores>(Arrays.asList(jsonResponse.getData()));


                        for (Marker marker : driverMarkers) {
                            marker.remove();
                            driverMarkers.remove(marker);
                        }

                        for (BusesConductores driver : bc) {
                            addDriverToMap(driver);
                        }

                        if (MenuActivity.usuario.equals("Conductor")) {

                            for( int i = 0; i<coordenadas.size(); i++){
                                saveProximityAlertPoint(coordenadas.get(i).getLat(),coordenadas.get(i).getLng());


                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<BusConductorResp> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }

    public void addDriverToMap(BusesConductores driver) {
        driverMarkers.add(mMap.addMarker(new MarkerOptions()
                .position(new LatLng(driver.getLat(), driver.getLng()))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_conductor))
                .anchor(0.5f, 0.5f)
        ));
    }

    private void getLastKnownLocation() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("ZF", "NO HAY PERMISOS");
            return;
        }

        Log.d("ZF", "SI HAY PERMISOS");

        LocationManager manager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = manager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = manager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }

        LatLng latLng;

        if (bestLocation == null)
            latLng = new LatLng(Float.parseFloat("40.7484"), Float.parseFloat("-73.9857"));
        else
            latLng = new LatLng(bestLocation.getLatitude(), bestLocation.getLongitude());

        if (driverPoint == null)
            driverPoint = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_bus)));
        else
            driverPoint.setPosition(latLng);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
    }

    private GeofencingClient mGeofencingClient;


    private void saveProximityAlertPoint(double latitud, double longitud) {
        if (ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        Location location =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            Toast.makeText(act, "No last known location. Aborting...", Toast.LENGTH_LONG).show();
            return;
        }
        saveCoordinatesInPreferences((float) latitud, (float) longitud);


        addProximityAlert(location.getLatitude(), location.getLongitude());
    }

    private void addProximityAlert(double latitude, double longitude) {

        Intent intent = new Intent(PROX_ALERT_INTENT);
        PendingIntent proximityIntent = PendingIntent.getBroadcast(act, 0, intent, 0);

        if (ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        locationManager.addProximityAlert(
                latitude,
                longitude,
                POINT_RADIUS,
                PROX_ALERT_EXPIRATION,
                proximityIntent
        );

        IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);
        act.registerReceiver(new ProximityIntentReceiver(), filter);

    }

    private void poblarCoordenadasFromLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location!=null) {

        }
    }

    private void saveCoordinatesInPreferences(float latitude, float longitude) {
        SharedPreferences prefs =
                act.getSharedPreferences(getClass().getSimpleName(),
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putFloat(POINT_LATITUDE_KEY, latitude);
        prefsEditor.putFloat(POINT_LONGITUDE_KEY, longitude);
        prefsEditor.commit();
    }

    private Location retrievelocationFromPreferences() {
        SharedPreferences prefs =
                act.getSharedPreferences(getClass().getSimpleName(),
                        Context.MODE_PRIVATE);
        Location location = new Location("POINT_LOCATION");
        location.setLatitude(prefs.getFloat(POINT_LATITUDE_KEY, 0));
        location.setLongitude(prefs.getFloat(POINT_LONGITUDE_KEY, 0));
        return location;
    }

    public class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Location pointLocation = retrievelocationFromPreferences();
            float distance = location.distanceTo(pointLocation);
            Toast.makeText(act, "Distancia del Punto:"+distance, Toast.LENGTH_LONG).show();
        }
        public void onStatusChanged(String s, int i, Bundle b) {
        }
        public void onProviderDisabled(String s) {
        }
        public void onProviderEnabled(String s) {
        }
    }



}
