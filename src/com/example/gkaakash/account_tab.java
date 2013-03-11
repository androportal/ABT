package com.example.gkaakash;

import com.gkaakash.controller.Preferences; 
import com.gkaakash.controller.Startup;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class account_tab extends TabActivity{
	String accCodeCheckFlag;
	TextView tab1 = null;
	TextView tab2 = null;
	private Integer client_id;
	private Preferences preferences;
	
	 
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.tab);
	        //accCodeCheckFlag = getIntent().getExtras().getString("flag");
	 
	        final TabHost tabHost = getTabHost();
	        //creating TabSpec for create account
	        TabSpec createspec = tabHost.newTabSpec("tab1");
	        tab1 = new TextView(this);
	        //setting properties in textView
	        tab1.setGravity(android.view.Gravity.CENTER);
	        tab1.setTextSize(18.0f);
	        tab1.setHeight(50);
	        tab1.setTextColor(Color.WHITE);
	        tab1.setText("Create account");
	        createspec.setIndicator(tab1);//assigning TextView to tab Indicator
	        preferences = new Preferences();
	        // this is client_id get after getConnetion method call for existing organisation 
	        client_id = Startup.getClient_id();
	        // call getPreferences to get flag for account code
	        accCodeCheckFlag = preferences.getPreferences(new Object[]{2},client_id);
	        //for visibility of account tab layout 
			MainActivity.tabFlag = true;
	        Intent create = new Intent(this, createAccount.class);
	        // flag for finish button of account page 
	        //create.putExtra("finish_flag","menu");
	        createspec.setContent(create);
	        tabHost.addTab(createspec);  // Adding create tab
	        
	        //creating TabSpec for edit account
	        TabSpec editspec = tabHost.newTabSpec("tab2");
	        tab2 = new TextView(this);
	        //setting properties in textView
	        tab2.setGravity(android.view.Gravity.CENTER);
	        tab2.setTextSize(18.0f);
	        tab2.setHeight(50);
	        tab2.setTextColor(Color.WHITE);
	        tab2.setText("Search/Edit account");
	        editspec.setIndicator(tab2);//assigning TextView to tab Indicator
	        Intent edit = new Intent(this, edit_account.class);
	        editspec.setContent(edit);
	        tabHost.addTab(editspec); // Adding edit tab
	        tabHost.setCurrentTab(0);//setting tab1 on load
	       
	 }
}
