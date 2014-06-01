package com.GooseCorp.goose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements ConnectionCallbacks, android.location.LocationListener{
	
	GoogleMap map;
	LocationClient locClient;
	LocationManager locManager;
	
    @Override	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        
        //Get the mapmLocationClient
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);
        
        map.setOnMarkerClickListener(myMarkerClickListener);

    }
    
    private GoogleMap.OnMarkerClickListener myMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
		
		@Override
		public boolean onMarkerClick(Marker arg0) {
			// TODO Auto-generated method stub
			
			return false;
		}
	};
	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		  map.clear();

		   MarkerOptions mp = new MarkerOptions();

		   mp.position(new LatLng(location.getLatitude(), location.getLongitude()));

		   mp.title("Bus stop #");
		   
		   mp.icon(BitmapDescriptorFactory.fromResource(R.drawable.busstop));
		   map.addMarker(mp);

		   map.animateCamera(CameraUpdateFactory.newLatLngZoom(
		    new LatLng(location.getLatitude(), location.getLongitude()), 16));
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
}
