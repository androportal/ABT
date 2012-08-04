package com.example.gkaakash;

import android.annotation.SuppressLint;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class menu extends ListActivity{
	ListView list1;
	String accCodeCheckFlag;
	String[] menuOptions = new String[] { "Create Account", "Transaction", "Reports",
			"Set Preferences", "Administration", "Help" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Calling menu.xml
		//setContentView(R.layout.menu);
		accCodeCheckFlag = getIntent().getExtras().getString("flag");		
	 
		setListAdapter(new ArrayAdapter<String>(this, R.layout.menu,menuOptions));
 
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		final Context context = this;
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position == 0)
				{
					Intent intent = new Intent(context, createAccount.class);
					intent.putExtra("flag", accCodeCheckFlag);
					// To pass on the value to the next page
					startActivity(intent);
					
				}
			}
		});
	 
	}
}