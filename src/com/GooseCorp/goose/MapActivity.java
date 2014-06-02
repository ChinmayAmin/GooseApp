package com.GooseCorp.goose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

public class MapActivity extends FragmentActivity implements ConnectionCallbacks, LocationListener, OnConnectionFailedListener{
	
	GoogleMap map;
	LocationClient locClient;
	Location userLocation;
	BusStopInformation[] busStopInformation;
	
    @Override	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        //Setup the location client
        locClient = new LocationClient(this,this,this);
        
        //Connect to GooglePlayServices
        locClient.connect();
        
        //Get the Map and enable location detection
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);
        
        //Add listener on marker so we can click on it
        map.setOnMarkerClickListener(myMarkerClickListener);
        
        //Get all the stops from and check if not null
        if(getAllStops() != null)
        {
//        	busStopInformation = getAllStops();
        }
        
//        System.out.println(busStopInformation[0].stop_id);
    }
    
    public class BusStopInformation
    {
    	private String stop_id;
    	private String stop_code;
    	private String stop_name;
    	private String stop_desc;
    	private LatLng stop_loc;
    	private String zone_id;
    	private String stop_url;
    	private String location_type;
    	private String parent_station;
    }
    
    public BusStopInformation[] getAllStops()
    {
    	BusStopInformation[] busStops = new BusStopInformation[5500];
		
    	BufferedReader br = null;
    	String line = "";
    	String delim = ",";
    	int count = 0;
    	
    	try {
			br = new BufferedReader(new InputStreamReader(getAssets().open("stops.txt")));
			while((line = br.readLine()) != null)
			{
				String[] stopInfo = line.split(delim);
				busStops[count] = new BusStopInformation();
				busStops[count].stop_id = stopInfo[0];
				busStops[count].stop_code = stopInfo[1];
				busStops[count].stop_name = stopInfo[2];
				busStops[count].stop_desc = stopInfo[3];
				busStops[count].stop_loc = new LatLng(Double.parseDouble(stopInfo[4]),Double.parseDouble(stopInfo[5]));
				busStops[count].zone_id = stopInfo[6];
				busStops[count].stop_url = stopInfo[7];
				busStops[count].location_type = stopInfo[8];
				busStops[count].parent_station = stopInfo[9];
				
				count++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    		return busStops;   	
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
		userLocation = locClient.getLastLocation();
		   map.clear();

		   MarkerOptions mp = new MarkerOptions();

		   mp.position(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()));

		   mp.title("Bus stop #");
		   
		   mp.icon(BitmapDescriptorFactory.fromResource(R.drawable.busstop));
		   map.addMarker(mp);
		  // Tools to help 
		   map.animateCamera(CameraUpdateFactory.newLatLngZoom(
		    new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 16));
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		userLocation = location;
//		  map.clear();
//
//		   MarkerOptions mp = new MarkerOptions();
//
//		   mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
//
//		   mp.title("Bus stop #");
//		   
//		   mp.icon(BitmapDescriptorFactory.fromResource(R.drawable.busstop));
//		   map.addMarker(mp);
//		  // Tools to help 
//		   map.animateCamera(CameraUpdateFactory.newLatLngZoom(
//		    new LatLng(location.getLatitude(), location.getLongitude()), 16));
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}
}
