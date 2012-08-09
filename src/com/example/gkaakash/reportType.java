package com.example.gkaakash;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class reportType extends ListActivity{
	//adding report list items
	String[] reportType = new String[] { "Ledger","Trial Balance","Project Statement","Cash Flow","Balance Sheet","Income and Expenditure" };
	final Context context = this;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//calling report.xml page
		setListAdapter(new ArrayAdapter<String>(this, R.layout.report,reportType));
		
		//getting the list view and setting background
		final ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		listView.setBackgroundColor(Color.BLACK);
		listView.setCacheColorHint(Color.TRANSPARENT);
		
		//when report list items are clicked, code for respective actions goes here ...
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//for "Ledger"
				if(position == 0)
				{
					Intent intent = new Intent(context, Report.class);
					// To pass on the value to the next page
					startActivity(intent);
				}
				
			} 
		});
	}
}
