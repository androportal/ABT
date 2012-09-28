package com.example.gkaakash;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class menu extends ListActivity{
	//adding a class property to hold a reference to the controls
	String accCodeCheckFlag, voucherTypeFlag;
	private int group1Id = 1;
	int Edit = Menu.FIRST;
	int Delete = Menu.FIRST +1;
	int Finish = Menu.FIRST +2;
	AlertDialog dialog;
	final Context context = this;
	
	//adding list items to the newly created menu list
	String[] menuOptions = new String[] { "Create account", "Transaction", "Reports",
			"Set preferences", "Administration", "Help" };

	//adding options to the options menu
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(group1Id, Edit, Edit, "Edit");
    menu.add(group1Id, Delete, Delete, "Delete");
    menu.add(group1Id, Finish, Finish, "Finish");
    return super.onCreateOptionsMenu(menu); 
    }
	
	//code for the actions to be performed on clicking options menu goes here ...
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case 1:
	    Toast msg = Toast.makeText(menu.this, "Menu 1", Toast.LENGTH_LONG);
	    msg.show();
	    return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	 
	 @Override
	 public void onBackPressed() {
		 Intent intent = new Intent(context, MainActivity.class);
		    startActivity(intent); 
	 }
	 
	//on load...
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//calling menu.xml and adding menu list into the page
		setListAdapter(new ArrayAdapter<String>(this, R.layout.menu,menuOptions));
 
		//getting the list view and setting background
		final ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		listView.setBackgroundColor(Color.BLACK);
		listView.setCacheColorHint(Color.TRANSPARENT);
		
		//when menu list items are clicked, code for respective actions goes here ...
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				//for "create account"
				if(position == 0)
				{
					Intent intent = new Intent(context, account_tab.class);
					//getting the account code flag value from previous page(preferences.xml)
					accCodeCheckFlag = getIntent().getExtras().getString("flag");
					//now, passing this flag to the requested page through intent
					intent.putExtra("flag", accCodeCheckFlag);
					// To pass on the value to the next page
					startActivity(intent);
				}
				
				//for "transaction"
				if(position == 1)
				{
					Intent intent = new Intent(context, voucherMenu.class);
					// To pass on the value to the next page
					startActivity(intent);
				}
			
				//for "reports"
				if(position == 2)
				{
					Intent intent = new Intent(context, reportMenu.class);
					// To pass on the value to the next page
					startActivity(intent);					 
				}
				
				//for "preferences", adding popup menu ...
				if(position == 3)
				{
					final CharSequence[] items = { "Edit organisation details", "Add new project" };
					//creating a dialog box for popup
			        AlertDialog.Builder builder = new AlertDialog.Builder(context);
			        //setting title
			        builder.setTitle("Select preference");
			        //adding items
			        builder.setItems(items, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int pos) {
			        	//code for the actions to be performed on clicking popup item goes here ...
			            switch (pos) {
			                case 0:
			                              {
			                            	  Toast.makeText(context,"Clicked on:"+items[pos],Toast.LENGTH_SHORT).show();
			                      }break;
			                case 1:
			                              {
			                            	  Toast.makeText(context,"Clicked on:"+items[pos],Toast.LENGTH_SHORT).show();
			                      }break;
			            }
			        }
			        });
			        //building a complete dialog
					dialog=builder.create();
					dialog.show();
					}
				} 
		});
	 
	}
}