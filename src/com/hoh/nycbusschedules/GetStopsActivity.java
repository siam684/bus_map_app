package com.hoh.nycbusschedules;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class GetStopsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_stops);
		
		/*
		 * intent intent = getIntent();
			Bundle extras = intent.getExtras();
			String username_string = extras.getString("EXTRA_USERNAME");
			String password_string = extras.getString("EXTRA_PASSWORD");
		 * */
		
		
		ListView stopsListView = (ListView)this.findViewById(R.id.stopsListView);
		Intent intent = getIntent();
		
		Bundle bundle = intent.getExtras();
		String selectedLetter = bundle.getString(CustomStringListAdapter.SELECTED_ROUTE_LETTER);
		String selectedRouteId = bundle.getString(CustomStringListAdapter.SELECTED_ROUTE_ID);
		String selectedDirection = bundle.getString(CustomStringListAdapter.SELECTED_DIRECTION);
		char direction = selectedDirection.charAt(selectedDirection.length()-2);
		
		if(selectedRouteId=="")
		{
			Log.e("GetStopsActivity","route id is null");
		}
		else
		{
			Log.e("GetStopsActivity",selectedRouteId);
			Log.e("GetStopsActivity",selectedDirection);
			Log.e("GetStopsActivity",selectedLetter);
		}
		
		Log.e("GetStopsActivity","Direction = " + String.valueOf(direction));
		getIt it = new getIt(stopsListView, R.layout.list_item, R.id.button1,this,selectedRouteId,String.valueOf(direction));
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
		
		if(list==null)
		{
			Log.e("GetStopsActivity","Null String list in get stops activity");
		}
		else
		{
			for(int i=0;i<list.size();i++)
			{
				Log.e("GetStopsActivity",list.get(i));
			}
		}
		
		CustomStringListAdapter myAdapter =  new CustomStringListAdapter( this,list,selectedLetter,selectedRouteId,selectedDirection);
		stopsListView.setAdapter(myAdapter);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.get_stops, menu);
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
