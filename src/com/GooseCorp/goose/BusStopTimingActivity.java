package com.GooseCorp.goose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.GooseCorp.goose.MapActivity.BusStopInformation;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class BusStopTimingActivity extends Activity {
	
	stopDetailsTripIDS[] stopDetails;
	stopDetailsBusNumbers[] busNumbersForStop;
	String stop_id;
	int numberOfTripId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_stop_timing);
		TextView stopId = (TextView)findViewById(R.id.busStopID);
		Intent intent = getIntent();
		stopId.setText(intent.getStringExtra("stop_id"));
		
		//Get busStopInformation
		getStopTrips();
//		getStopBusses();
		
	}
	
	public class stopDetailsTripIDS
	{
		private String trip_id;
		private String arrival_time;
		private String departure_time;
		private String stop_sequence;
	}
	
	public class stopDetailsBusNumbers
	{
		private String route_id;
		private String service_id;
		private String trip_headsign;
		private String direction;
		private String block_id;
		private String shape_id;
	}
	
	public void getStopTrips()
	{
		BufferedReader br = null;
    	String line = "";
    	String delim = ",";

    	//Get the count first
    	try {
			br = new BufferedReader(new InputStreamReader(getAssets().open("stop_times.txt")));
			while((line = br.readLine()) != null)
			{
				String[] stopInfo = line.split(delim);
				
				Log.d("2","Entered");
				
				//Only get the information for the stop clicked
				if(stopInfo[3] == stop_id)
				{
					numberOfTripId++;
					Log.d("3",Double.toString(numberOfTripId));
				}
				
			}
			
			//Initialize using count
			stopDetails = new stopDetailsTripIDS[numberOfTripId];
			
			numberOfTripId = 0;
			
			//Add the trip details now
			while((line = br.readLine()) != null)
			{
				String[] stopInfo = line.split(delim);
				
				//Only get the information for the stop clicked
				if(stopInfo[3].equalsIgnoreCase(stop_id))
				{
					stopDetails[numberOfTripId].trip_id = stopInfo[0];
					stopDetails[numberOfTripId].arrival_time = stopInfo[1];
					stopDetails[numberOfTripId].departure_time = stopInfo[2];
					stopDetails[numberOfTripId].stop_sequence = stopInfo[4];
					numberOfTripId++;
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
	}
	
	public void getStopBusses()
	{
		BufferedReader br = null;
    	String line = "";
    	String delim = ",";
    	int count = 0;
    	
    	//Get the count first
    	try {
			br = new BufferedReader(new InputStreamReader(getAssets().open("trips.txt")));
			
			//Initialize using count
			busNumbersForStop = new stopDetailsBusNumbers[numberOfTripId];
			
			//Add the trip details now
			while((line = br.readLine()) != null)
			{
				String[] stopInfo = line.split(delim);
				
				//Only get the information for the stop clicked
				if(stopInfo[2].equalsIgnoreCase(stopDetails[count].trip_id))
				{
					busNumbersForStop[count].route_id = stopInfo[0];
					busNumbersForStop[count].service_id = stopInfo[1];
					busNumbersForStop[count].trip_headsign = stopInfo[3];
					busNumbersForStop[count].direction = stopInfo[4];
					busNumbersForStop[count].block_id = stopInfo[5];
					busNumbersForStop[count].shape_id = stopInfo[6];
					Log.d("2",stopInfo[0]);
					count++;
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bus_stop_timing, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
