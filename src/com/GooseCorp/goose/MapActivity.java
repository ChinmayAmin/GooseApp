package com.GooseCorp.goose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
	LatLng[] stopLocations;
	
    @Override	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        //Setup the location client
        locClient = new LocationClient(this,this,this);
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        
        boolean enabledGPS = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean enabledWiFi = service
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!enabledGPS) {
            Toast.makeText(this, "GPS signal not found", Toast.LENGTH_LONG).show();
        }
        else if(!enabledWiFi){
        	Toast.makeText(this, "Network signal not found", Toast.LENGTH_LONG).show();
        }else
        {
            //Get the Map and enable location detection
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            map.setMyLocationEnabled(true);
            
            //Add listener on marker so we can click on it
            map.setOnMarkerClickListener(myMarkerClickListener);
        	locClient.connect();
            
            //Get all the stops
            busStopInformation = new BusStopInformation[2606];
            getAllStops();
           
        }

    }

    public class BusStopInformation
    {
    	private String stop_id;
    	private String stop_code;
    	private String stop_name;
    	private String stop_desc;
    	private String stop_lat;
    	private String stop_long;
    	private String zone_id;
    	private String stop_url;
    	private String location_type;
    	private String parent_station;
    }
    
    public void getAllStops()
    {
    	BufferedReader br = null;
    	String line = "";
    	String delim = ",";
    	int count = 0;
    	stopLocations = new LatLng[2606];
    	busStopInformation = new BusStopInformation[2606];
    	
    	try {
			br = new BufferedReader(new InputStreamReader(getAssets().open("stops.txt")));
			while((line = br.readLine()) != null)
			{
				String[] stopInfo = line.split(delim);
				busStopInformation[count] = new BusStopInformation();
				
				
				if(count != 0){
					busStopInformation[count].stop_id = stopInfo[0];
					busStopInformation[count].stop_code = stopInfo[1];	
					busStopInformation[count].stop_name = stopInfo[2];	
					busStopInformation[count].stop_desc = stopInfo[3];	
					busStopInformation[count].stop_lat = stopInfo[4];
					busStopInformation[count].stop_long = stopInfo[5];
					busStopInformation[count].zone_id = stopInfo[6];	
					busStopInformation[count].stop_url = stopInfo[7];	
					busStopInformation[count].location_type = stopInfo[8];
					stopLocations[count] = new LatLng(Double.parseDouble(stopInfo[4]),Double.parseDouble(stopInfo[5]));
					Log.d("1",stopInfo[4]);
				}
				count++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   	
    }
    
    private GoogleMap.OnMarkerClickListener myMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
		
		@Override
		public boolean onMarkerClick(Marker marker) {
			// TODO Auto-generated method stub
//			Intent intent = new Intent(MapActivity.this,BusStopTimingActivity.class);
//			intent.putExtra("stop_id", marker.getTitle());
//			startActivityForResult(intent,0);
			return false;
		}
	};
	
	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		userLocation = locClient.getLastLocation();
		
        busStopInformation = new BusStopInformation[2606];
        getAllStops();
    
        map.clear();
		MarkerOptions mp = new MarkerOptions();
		
        //Add the markers for the location 
        for(int i = 0;i < stopLocations.length; i++)
        {
        	if(stopLocations[i] != null){
        	   mp.position(stopLocations[i]);
	 		   mp.icon(BitmapDescriptorFactory.fromResource(R.drawable.busstop));
	 		   mp.title(busStopInformation[i].stop_id);
	 		   map.addMarker(mp);
        	}
        }
                
        //	   map.clear();
//
//	   MarkerOptions mp = new MarkerOptions();
//
//	   mp.position(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()));
//
//	   mp.title("Bus stop #");
//	   
//	   mp.icon(BitmapDescriptorFactory.fromResource(R.drawable.busstop));
//	   map.addMarker(mp);
//	  // Tools to help 
	   map.animateCamera(CameraUpdateFactory.newLatLngZoom(
	    new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 16));
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Connect to GPS", Toast.LENGTH_SHORT);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		userLocation = location;
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Connec to GPS", Toast.LENGTH_SHORT);
	}
			
	
}
