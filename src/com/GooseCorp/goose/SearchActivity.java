package com.GooseCorp.goose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.GooseCorp.goose.MapActivity.BusStopInformation;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class SearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		Log.d("1", "HERE");
		String line = "";
		String delim = ",";
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("stops.txt")));
			while((line = br.readLine()) != null)
			{
				String[] stopInfo = line.split(delim);
				Log.d("1",stopInfo[0]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
