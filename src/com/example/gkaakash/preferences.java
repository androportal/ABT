package com.example.gkaakash;


import com.gkaakash.controller.Organisation;
import com.gkaakash.controller.Preferences;
import com.gkaakash.controller.Startup;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;



public class preferences extends Activity {
	//Declaring variables
	CheckBox cbProject, cbAccCode ,	cbRollover;
	Button btnNext;
	private Preferences preference;
	private Integer client_id;
	protected Boolean setpref;
	protected String projectname;
	protected Context context;
	Organisation organisation;  
	static String IPaddr;
	public void onCreate(Bundle savedInstanceState) {   
		super.onCreate(savedInstanceState);
		// Calling preferences.xml
		setContentView(R.layout.preferences);
		// get Client_id return by Deploy method
		IPaddr = MainActivity.IPaddr;
		System.out.println("in createorg"+IPaddr);
		organisation = new Organisation(IPaddr);
		preference = new Preferences(IPaddr);
		client_id= Startup.getClient_id();

		//for visibility of account tab layout
		MainActivity.tabFlag = false;
		btnNext = (Button) findViewById(R.id.btnNext);

		cbAccCode = (CheckBox) findViewById(R.id.cbAccCode);
		cbRollover = (CheckBox) findViewById(R.id.cbRollover);
		addListenerOnButton();

	}
	private void addListenerOnButton() {
		
		//Create a class implementing “OnClickListener” and set it as the on click listener for the button
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Setting the account code flag value
				if (cbAccCode.isChecked()) {
					//System.out.println("checked:");
					setpref = preference.setPreferences(new Object[]{"1","manually"},client_id);
				}
				if(cbRollover.isChecked())
				{
					setpref = preference.setPreferences(new Object[]{"2","manually"},client_id);
				}
				//To pass on the activity to the next page called menu page
				Intent intent = new Intent(preferences.this, menu.class);
				startActivity(intent);  
			}
		});

	}

	public void onBackPressed(){
		//To pass on the activity to the next page
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

}