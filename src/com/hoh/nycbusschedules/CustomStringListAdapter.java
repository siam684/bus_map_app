package com.hoh.nycbusschedules;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class CustomStringListAdapter extends ArrayAdapter<String> 
{
	private final Context context;
	private final ArrayList<String> values;
	public static String SELECTED_ROUTE_LETTER = "SELECTED_ROUTE_LETTER";
	public static String SELECTED_ROUTE_ID  = "SELECTED_ROUTE_ID";
	public static String SELECTED_DIRECTION = "SELECTED_DIRECTION";
	public static String SELECTED_STOP_ID = "SELECTED_STOP_ID";
	
	private String localSelectedRouteLetter = null;
	private String localSelectedRouteId = null;
	private String localSelectedDirection = null;
	//private String localSelectedstopId = null;
	
	
	public CustomStringListAdapter(	Context context, 
									ArrayList<String> values) 
	{
	    super(context, R.layout.list_item, values);
	    this.context = context;
	    this.values = values;
	}
	
	public CustomStringListAdapter(	Context context, 
									ArrayList<String> values,
									String localSelectedRouteLetter) 
	{
	    super(context, R.layout.list_item, values);
	    this.context = context;
	    this.values = values;
	    this.localSelectedRouteLetter = localSelectedRouteLetter;
	}
	
	
	public CustomStringListAdapter(	Context context, 
									ArrayList<String> values,
									String localSelectedRouteLetter,
									String localSelectedRouteId) 
	{
	    super(context, R.layout.list_item, values);
	    this.context = context;
	    this.values = values;
	    this.localSelectedRouteLetter = localSelectedRouteLetter;
	    this.localSelectedRouteId = localSelectedRouteId;
	}
	
	public CustomStringListAdapter(	Context context, 
									ArrayList<String> values,
									String localSelectedRouteLetter,
									String localSelectedRouteId,
									String localSelectedDirection) 
	{
	super(context, R.layout.list_item, values);
	this.context = context;
	this.values = values;
	this.localSelectedRouteLetter = localSelectedRouteLetter;
	this.localSelectedRouteId = localSelectedRouteId;
	this.localSelectedDirection = localSelectedDirection;
	}
	
	public CustomStringListAdapter(	Context context, 
									ArrayList<String> values,
									String localSelectedRouteLetter,
									String localSelectedRouteId,
									String localSelectedDirection,
									String localSelectedStopId) 
	{
	super(context, R.layout.list_item, values);
	this.context = context;
	this.values = values;
	this.localSelectedRouteLetter = localSelectedRouteLetter;
	this.localSelectedRouteId = localSelectedRouteId;
	this.localSelectedDirection = localSelectedDirection;
	//this.localSelectedstopId = localSelectedStopId;
	}
	
	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, final ViewGroup parent) 
	{
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View listItemView = inflater.inflate(R.layout.list_item, parent, false);
	    
	    final Button button = (Button) listItemView.findViewById(R.id.button1);
	    button.setText(values.get(position));
	    
	    button.setOnClickListener(new View.OnClickListener() 
	    {
            @Override
            public void onClick(View view) 
            {
            	Intent intent = null;
            	
            	if(context.getClass().equals(GetFirstLettersActivity.class))
            	{
            		intent = new Intent(context, GetRoutesActivity.class);
            		Bundle extras = new Bundle();
            		extras.putString(SELECTED_ROUTE_LETTER, button.getText().toString());
            		intent.putExtras(extras);
            		context.startActivity(intent);
            	}
            	else if((context.getClass().equals(GetRoutesActivity.class)))
            	{
            		intent = new Intent(context, GetDirectionActivity.class);
            		Bundle extras = new Bundle();
            		extras.putString(SELECTED_ROUTE_LETTER, localSelectedRouteLetter);
            		extras.putString(SELECTED_ROUTE_ID, button.getText().toString());
            		intent.putExtras(extras);
            		context.startActivity(intent);
            	}
            	else if((context.getClass().equals(GetDirectionActivity.class)))
            	{
            		intent = new Intent(context, GetStopsActivity.class);
            		Bundle extras = new Bundle();
            		extras.putString(SELECTED_ROUTE_LETTER, localSelectedRouteLetter);
            		extras.putString(SELECTED_ROUTE_ID, localSelectedRouteId);
            		extras.putString(SELECTED_DIRECTION, button.getText().toString());
            		intent.putExtras(extras);
            		context.startActivity(intent);
            	}
            	else if((context.getClass().equals(GetStopsActivity.class)))
            	{
            		intent = new Intent(context, GetArrivalTimesActivity.class);
            		Bundle extras = new Bundle();
            		extras.putString(SELECTED_ROUTE_LETTER, localSelectedRouteLetter);
            		extras.putString(SELECTED_ROUTE_ID, localSelectedRouteId);
            		extras.putString(SELECTED_DIRECTION, localSelectedDirection);
            		extras.putString(SELECTED_STOP_ID, button.getText().toString());
            		intent.putExtras(extras);
            		context.startActivity(intent);
            	}
            }
        });
	    
	    return listItemView;
	}
	
	
}
