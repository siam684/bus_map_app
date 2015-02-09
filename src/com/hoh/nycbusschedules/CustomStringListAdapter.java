package com.hoh.nycbusschedules;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class CustomStringListAdapter extends ArrayAdapter<String> 
{
	private final Context context;
	private final ArrayList<String> values;
	public final static String SELECTED_LETTER = null;
	
	public CustomStringListAdapter(Context context, ArrayList<String> values) 
	{
	    super(context, R.layout.list_item, values);
	    this.context = context;
	    this.values = values;
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
            	Toast.makeText(parent.getContext(), "button clicked: " + button.getText() + " " + button.getText().toString().length(), Toast.LENGTH_SHORT).show();
            	
        	    Intent intent = new Intent(context, GetRoutesActivity.class);
				intent.putExtra(SELECTED_LETTER, button.getText().toString());
        	    context.startActivity(intent);
            }
        });
	    
	    return listItemView;
	}
	
	
}
