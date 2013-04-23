package com.example.gkaakash;



import org.apache.tools.ant.types.Resource;

import com.gkaakash.controller.Preferences;
import com.gkaakash.controller.Startup;


import android.R.color;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class userSetting extends TabActivity{
	TextView tab1 = null;
	TextView tab2 = null;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);
		
		 final TabHost tabHost = getTabHost();
	        //creating TabSpec for create account
	        TabSpec createspec = tabHost.newTabSpec("tab1");
	        tab1 = new TextView(this);
	        //setting properties in textView
	        tab1.setGravity(android.view.Gravity.CENTER);
	        tab1.setTextSize(18.0f);
	        tab1.setHeight(50);
	        tab1.setTextColor(Color.WHITE);
	        tab1.setText("Add User");
	        createspec.setIndicator(tab1);//assigning TextView to tab Indicator
	       // preferences = new Preferences();
	        // this is client_id get after getConnetion method call for existing organisation 
	        //client_id = Startup.getClient_id();
	        // call getPreferences to get flag for account code
	        //accCodeCheckFlag = preferences.getPreferences(new Object[]{2},client_id);
	        //for visibility of account tab layout 
			MainActivity.tabFlag = true;
	        Intent add = new Intent(this,User_table.class);
	        // flag for finish button of account page 
	        //create.putExtra("finish_flag","menu");
	        createspec.setContent(add);
	        tabHost.addTab(createspec);  // Adding create tab
	        
	        //creating TabSpec for edit account
	        TabSpec editspec = tabHost.newTabSpec("tab2");
	        tab2 = new TextView(this);
	        //setting properties in textView
	        tab2.setGravity(android.view.Gravity.CENTER);
	        tab2.setTextSize(18.0f);
	        tab2.setHeight(50);
	        tab2.setTextColor(Color.WHITE);
	        tab2.setText("Edit/Change password/Delete user");
	        editspec.setIndicator(tab2);//assigning TextView to tab Indicator
	        Intent edit = new Intent(this,editUser.class);
	        editspec.setContent(edit);
	        tabHost.addTab(editspec); // Adding edit tab
	        tabHost.setCurrentTab(0);//setting tab1 on load
		
	}// end of onCreate method


}// end of UserSetting class
