package com.example.ahmed.map_example2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements LocationListener ,OnMapReadyCallback {


    private LocationManager locationManager;

    private GoogleMap mMap;

    boolean flag = false ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }


    public  void click (View v){


        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Check if enabled and if not send user to the GPS settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);

            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, MainActivity.this);

    }

    @Override
    public void onLocationChanged(Location location) {

        if (flag ==false){

            flag = true ;

            drawMarkers(location);
        }

    }


    public void drawMarkers(Location location){

        LatLng pos = new LatLng(location.getLatitude() , location.getLongitude());

        Bitmap icon =  BitmapFactory.decodeResource(this.getResources(), R.drawable.icon);

        icon = Bitmap.createScaledBitmap(icon, 60, 60, false);

        // clear all makers
        mMap.clear();

        // add defualt marker
        mMap.addMarker(new MarkerOptions().position(pos).title("My Place"));


        // add marker with custom icon
        Marker marker = mMap.addMarker(new MarkerOptions().position(pos).title("My Place").icon(BitmapDescriptorFactory.fromBitmap(icon)));





        //zoom to the map
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 10.0f));
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

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap ;
    }
}
