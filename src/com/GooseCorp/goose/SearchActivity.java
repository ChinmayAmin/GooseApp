package com.GooseCorp.goose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class SearchActivity extends Activity  {
	
	private ListView busStopListView;
	private String[] busStops;   
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		busStopListView = (ListView)findViewById(R.id.busStopsLV);
			
		//Initialize
		busStops = new String[5500];
		
		//Populate busStops
		getBusStops();
		
		List<String> list = new ArrayList<String>(Arrays.asList(busStops));
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		busStopListView.setAdapter(dataAdapter);
		busStopListView.setOnItemSelectedListener(
	                new OnItemSelectedListener() {
	                    public void onItemSelected(
	                            AdapterView<?> parent, View view, int position, long id) {
	                        
	                    }

	                    public void onNothingSelected(AdapterView<?> parent) {
	                        
	                    }
	                });
		for(int i = 0; i < busStops.length - 1; i++)
		{
			if(busStops[i] != null)
			Log.d("1",busStops[i]);
		}
	}
	
	private void getBusStops()
	{
		String line = "";
		String delim = ",";
		int count = 0;
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("stops.txt")));
			while((line = br.readLine()) != null)
			{
				String[] stopInfo = line.split(delim);
				
				if(count != 0){
					busStops[count] = stopInfo[0];
				}
				
				count++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
