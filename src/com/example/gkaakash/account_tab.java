package com.example.gkaakash;

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
	
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.tab);
	        accCodeCheckFlag = getIntent().getExtras().getString("flag");
	 
	        final TabHost tabHost = getTabHost();
	        //creating TabSpec for create account
	        TabSpec createspec = tabHost.newTabSpec("tab1");
	        tab1 = new TextView(this);
	        //setting properties in textView
	        tab1.setGravity(android.view.Gravity.CENTER);
	        tab1.setTextSize(18.0f);
	        tab1.setHeight(50);
	        tab1.setTextColor(Color.WHITE);
	        tab1.setText("Create Account");
	        createspec.setIndicator(tab1);//assigning TextView to tab Indicator
	        tab1.setBackgroundColor(Color.parseColor("#60AFFE"));
	        Intent create = new Intent(this, createAccount.class);
	        create.putExtra("flag",accCodeCheckFlag);
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
	        tab2.setText("Edit Account");
	        editspec.setIndicator(tab2);//assigning TextView to tab Indicator
	        Intent edit = new Intent(this, edit_account.class);
	        editspec.setContent(edit);
	        tabHost.addTab(editspec); // Adding edit tab
	        tabHost.setCurrentTab(0);//setting tab1 on load
	       
	        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
	        	  @Override
	        	  public void onTabChanged(String tabId) {
	        		  setTabColor(tabHost);//setting the bellow method
	        	  }
	        	  
				private void setTabColor(TabHost tabHost) {
					
					    for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
					    {
					        tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#26466D")); //unselected
					    }
					    tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#60AFFE")); // selected
					}
	        });   
	 }
}
