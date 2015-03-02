package com.hoh.nycbusschedules;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AsyncDBQuery extends AsyncTask<String, Void, ArrayList<String>>
  {
        ArrayList<String> list = null;
        Context context = null;
        String routeLetter = null;
        String routeId = null;
        String direction = null;
        String stopId = null;
        DBQueryList query = null;
        ListView listview = null;					
        boolean getDefinedList = false;
        boolean getFirstLetter = false;
        boolean getDirection = false;
		boolean getStopList = false;
		boolean getArrivalTimes = false;
        int listViewRow = 0;					
        int textViewComponent = 0;			
        ProgressDialog mDialog;
        View contextView;
        
		public AsyncDBQuery(	ListView listview, 
        				int listViewRow, 
        				int textViewComponent, 
        				Context context )
        {
        	this.context = context;
        	this.listview = listview;
        	this.listViewRow = listViewRow;
        	this.textViewComponent = textViewComponent;
        	getFirstLetter = true;
        }
        
        
		public AsyncDBQuery(	ListView listview, 
        				int listViewRow, 
        				int textViewComponent, 
        				Context context, 
        				String r )
        {
        	this.context = context;
        	this.listview = listview;
        	this.listViewRow = listViewRow;
        	this.textViewComponent = textViewComponent;
        	
        	if(r.length()==1)
        	{
        		this.routeLetter = r;
            	getDefinedList = true;
        	}
        	else
        	{
        		this.routeId = r;
        		getDirection = true;
        	}
        }
        
        
		public AsyncDBQuery(	ListView listview, 
        				int listViewRow, 
        				int textViewComponent, 
        				Context context, 
        				String routeId, 
        				String direction )
        {
        	this.context = context;
        	this.listview = listview;
        	this.listViewRow = listViewRow;
        	this.textViewComponent = textViewComponent;
        	this.routeId = routeId;
        	this.direction = direction;
        	getStopList = true;
        }
        
        
		public AsyncDBQuery(	ListView listview, 
        				int listViewRow, 
        				int textViewComponent, 
        				Context context, 
        				String routeId, 
        				String direction, 
        				String stopId)
        {
        	this.context = context;
        	this.listview = listview;
        	this.listViewRow = listViewRow;
        	this.textViewComponent = textViewComponent;
        	this.routeId = routeId;
        	this.direction = direction;
        	this.stopId = stopId;
        	getArrivalTimes = true;
        }
        
		
		@Override
        protected void onPreExecute() {
            

            mDialog = new ProgressDialog(context);
            mDialog.setMessage("loading data...");
            //contextView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
            //((Activity) context).setContentView(R.layout.no_connection_layout);
            mDialog.show();
            super.onPreExecute();
        }
		


        @Override
	    protected ArrayList<String> doInBackground(String... urls) 
        {
            query = new DBQueryList();
            try 
            {
				if(getFirstLetter)
            	{
            		list = query.getFirstLetterRoutes().toStringList();
            	}
            	else if(getDefinedList)
            	{
            		list = query.getDefinedListRoutes(routeLetter).toStringList();
            	}
            	else if(getDirection)
            	{
            		list = query.getDirection(routeId).toStringList();
            	}
            	else if(getStopList)
            	{
            		list = query.getStopList(routeId, direction).toStringList();
            	}
            	else if(getArrivalTimes)
            	{
            		list = query.getArrivalTimes(routeId, direction, stopId).toStringList();
            	}            	
			} 
            catch (IOException e) 
            {
				e.printStackTrace();
			}
			return list;
        }
   
		protected void onPostExecute(ArrayList<String> result) 
		{
			
			mDialog.dismiss();
			super.onPostExecute(result);
			//((ViewGroup)contextView.getParent()).removeView(contextView);
			//((Activity) context).setContentView(contextView);
	}

}  
