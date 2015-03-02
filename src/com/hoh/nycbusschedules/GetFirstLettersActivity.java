package com.hoh.nycbusschedules;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ListView;

public class GetFirstLettersActivity extends Activity 
{

	@Override
	  protected void onCreate(Bundle savedInstanceState) 
	  {
	    super.onCreate(savedInstanceState);
	    
	    ConnectivityManager cm =
	            (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
	     
	    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	    boolean isConnected = activeNetwork != null &&
	                          activeNetwork.isConnectedOrConnecting();
	    //setContentView(R.layout.get_first_letter_activity);
	    if(isConnected)
	    {
	    	setContentView(R.layout.get_first_letter_activity);
	    	
	    	ListView lettersListView = (ListView)findViewById(R.id.firstLetterListView);
		    AsyncDBQuery it = new AsyncDBQuery(lettersListView, R.layout.list_item, R.id.button1,this);
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
		   
			CustomStringListAdapter myAdapter =  new CustomStringListAdapter( this,list);
			
		    lettersListView.setAdapter(myAdapter);
	    }
	    else
	    {
	    	setContentView(R.layout.no_connection_layout);
	    }
	    
	    
	    
		
	    
	  }

	
}
