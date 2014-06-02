package com.GooseCorp.goose;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Remove the action bar
				ActionBar actionBar = getActionBar();
				actionBar.setTitle("Goose");
				actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6db5ff")));
				actionBar.show();
				
				//Get the spinners and the buttons
				Button mapButton = (Button)findViewById(R.id.goToMap);
				Button searchButton = (Button)findViewById(R.id.search);
				Button savedButton = (Button)findViewById(R.id.savedStops);
				Button aboutButton = (Button)findViewById(R.id.about);
				
				//Set the background
				mapButton.setBackground(this.getResources().getDrawable(R.drawable.mapicon));
				searchButton.setBackground(this.getResources().getDrawable(R.drawable.search));
				savedButton.setBackground(this.getResources().getDrawable(R.drawable.bookmarks));
				aboutButton.setBackground(this.getResources().getDrawable(R.drawable.aboutus));
				
				//User clicked on search(find by stop number of bus)
				searchButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(MainActivity.this,SearchActivity.class);
						startActivity(intent);
					}
				});
				
				//Bookmarks
				savedButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						Intent intent = new Intent(MainActivity.this,BookmarkActivity.class);
//						startActivity(intent);
					}
				});
				
				//About us
				aboutButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
//						Intent intent = new Intent(MainActivity.this,AboutActivity.class);
//						startActivity(intent);
					}
				});
				//User clicked on map. Go to Map Activity
				mapButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(MainActivity.this,MapActivity.class);
						startActivity(intent);
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
