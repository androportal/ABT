package com.example.gkaakash;

import java.util.List;

import com.gkaakash.controller.Preferences; 
import com.gkaakash.controller.Startup;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class account_tab extends Activity{
	TextView tab1 = null;
	TextView tab2 = null;
	private Integer client_id;
	private Preferences preferences;
	static String IPaddr;
	LocalActivityManager manager;
	
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.tab);
	        //accCodeFlag = getIntent().getExtras().getString("flag");
	        IPaddr = MainActivity.IPaddr;
	        preferences = new Preferences(IPaddr);
	        // this is client_id get after getConnetion method call for existing organisation 
	        client_id = Startup.getClient_id();
	        
	        //for visibility of account tab layout 
			MainActivity.tabFlag = true;
			
            manager = new LocalActivityManager(account_tab.this, true);
            TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
           
            manager.dispatchCreate(savedInstanceState);
            tabHost.setup(manager);
            TabHost.TabSpec spec;
            Intent intent;

            intent = new Intent(account_tab.this, createAccount.class);   
            spec = tabHost.newTabSpec("Create Account").setIndicator("Create Account").setContent(intent);
            tabHost.addTab(spec);
            
            intent = new Intent(account_tab.this, edit_account.class);   
            spec = tabHost.newTabSpec("Edit Account").setIndicator("Edit Account").setContent(intent);
            tabHost.addTab(spec);
            
            //change the tab text color
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);
            tv = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);
            
            tabHost.setOnTabChangedListener(new OnTabChangeListener() {
				
				@Override
				public void onTabChanged(String tabId) {
					manager.dispatchPause(isFinishing());
					manager.dispatchResume();
					
				}
			});
	 }
}
