package com.GooseCorp.goose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity implements LocationListener {
	
	GoogleMap map;
	private LocationManager locationManager;
	private String provider;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        //Get the map
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        //Check if the user is using wifi gps enabled
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        
        //Check if gps is enabled
        boolean enabledGPS = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        
        //Check if wifi enabled
        boolean enabledWiFi = service
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        
        //If gps is not on then error message
        if (!enabledGPS) {
            Toast.makeText(this, "GPS signal not found", Toast.LENGTH_LONG).show();
        }
        else if(!enabledWiFi){
        	Toast.makeText(this, "GPS signal not found", Toast.LENGTH_LONG).show();
        }
       
    }

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		Location loc = arg0;
        map.addMarker(new MarkerOptions()
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.busstop))
        .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
        .position(new LatLng(45.4000,75.6667)));
	}
}
