package com.hoh.nycbusschedules;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class GetFirstLettersActivity extends Activity 
{

	@Override
	  protected void onCreate(Bundle savedInstanceState) 
	  {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.get_first_letter_activity);
	    ListView lettersListView = (ListView)findViewById(R.id.firstLetterListView);
	    new getIt(lettersListView, R.layout.list_item, R.id.button1,this).execute("");
	    
	    
	    
	  }

	
}
