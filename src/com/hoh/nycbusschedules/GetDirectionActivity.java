package com.hoh.nycbusschedules;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class GetDirectionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_direction);
		
		ListView directionListView = (ListView)this.findViewById(R.id.directionsListView);
		Intent intent = getIntent();
		String selectedLetter = intent.getStringExtra(CustomStringListAdapter.SELECTED_ROUTE_LETTER);
		String selectedRouteId = intent.getStringExtra(CustomStringListAdapter.SELECTED_ROUTE_ID);
		Toast.makeText(this, "button clicked again: " + selectedLetter + " " + selectedLetter.length() + " " + selectedRouteId , Toast.LENGTH_SHORT).show();
		
		getIt it = new getIt(directionListView, R.layout.list_item, R.id.button1,this,selectedRouteId);
		ArrayList<String> list = null;
		
		try {
			list = it.execute("").get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CustomStringListAdapter myAdapter =  new CustomStringListAdapter( this,list,selectedLetter,selectedRouteId);
		directionListView.setAdapter(myAdapter);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_direction, menu);
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
