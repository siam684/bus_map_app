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
import android.widget.Toast;

public class GetRoutesActivity extends Activity {

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
			setContentView(R.layout.activity_get_routes);
			ListView routesListView = (ListView)this.findViewById(R.id.routesListView);
			Intent intent = getIntent();
			String selectedLetter = intent.getStringExtra(CustomStringListAdapter.SELECTED_ROUTE_LETTER);
			Toast.makeText(this, "button clicked again: " + selectedLetter + " " + selectedLetter.length(), Toast.LENGTH_SHORT).show();
			
			AsyncDBQuery it = new AsyncDBQuery(routesListView, R.layout.list_item, R.id.button1,this,selectedLetter);
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
			
			CustomStringListAdapter myAdapter =  new CustomStringListAdapter( this,list,selectedLetter);
			routesListView.setAdapter(myAdapter);
	    }
	    else
	    {
	    	setContentView(R.layout.no_connection_layout);
	    }
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_routes, menu);
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
