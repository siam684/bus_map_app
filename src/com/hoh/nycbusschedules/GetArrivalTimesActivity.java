package com.hoh.nycbusschedules;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class GetArrivalTimesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ConnectivityManager cm =
	            (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
	     
	    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	    boolean isConnected = activeNetwork != null &&
	                          activeNetwork.isConnectedOrConnecting();
		
		if(isConnected)
	    {
			setContentView(R.layout.activity_get_arrival_times);
			
			//R.id.arrivalTmesListView;
			
			ListView arrivalTimesListView = (ListView)this.findViewById(R.id.arrivalTmesListView);
			Intent intent = getIntent();
			
			Bundle bundle = intent.getExtras();
			String selectedLetter = bundle.getString(CustomStringListAdapter.SELECTED_ROUTE_LETTER);
			String selectedRouteId = bundle.getString(CustomStringListAdapter.SELECTED_ROUTE_ID);
			String selectedDirection = bundle.getString(CustomStringListAdapter.SELECTED_DIRECTION);
			String selectedStopId = bundle.getString(CustomStringListAdapter.SELECTED_STOP_ID);
			char direction = selectedDirection.charAt(selectedDirection.length()-2);
			
			AsyncDBQuery it = new AsyncDBQuery(	arrivalTimesListView, 
									R.layout.list_item, R.id.button1,
									this,
									selectedRouteId,
									String.valueOf(direction),
									selectedStopId.substring(selectedStopId.length()-7, selectedStopId.length()-1));
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

			CustomStringListAdapter myAdapter =  new CustomStringListAdapter( 	this,
																				list,selectedLetter,
																				selectedRouteId,
																				selectedDirection,
																				selectedStopId);
			arrivalTimesListView.setAdapter(myAdapter);
	    }
	    else
	    {
	    	setContentView(R.layout.no_connection_layout);
	    }
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_arrival_times, menu);
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
