package com.example.gkaakash;

import java.util.ArrayList;
import java.util.List;

import com.gkaakash.controller.Preferences; 
import com.gkaakash.controller.Report;
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
	TabHost.TabSpec spec;
    Intent intent;
    TabHost tabHost;
	
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
            tabHost = (TabHost) findViewById(android.R.id.tabhost);
           
            manager.dispatchCreate(savedInstanceState);
            tabHost.setup(manager);
            
//            String getOrgName = menu.OrgName;
//			String financialFrom = menu.financialFromDate;
//			String financialTo = menu.financialToDate;
			Report report = new Report(IPaddr);
			//String orgtype=detailsList_foredit.get(1);
			String orgtype = menu.orgtype;
	
            //Object[] rollover_exist_params = new Object[] {getOrgName,financialFrom,financialTo};
            Boolean existRollOver = menu.existRollOver;
            
            if(existRollOver.equals(false)){
            	create();
            	edit();
            }
            else
            {
            	edit();
            }
            tabHost.setOnTabChangedListener(new OnTabChangeListener() {
				@Override
				public void onTabChanged(String tabId) {
					manager.dispatchPause(isFinishing());
					manager.dispatchResume();
					
				}
			});
	 }
	
	//creation of create account tab
	private void create() {
		intent = new Intent(account_tab.this, createAccount.class);   
		spec = tabHost.newTabSpec("Create Account").setIndicator("Create Account").setContent(intent);
		tabHost.addTab(spec);
		
	}

	//creation of edit account tab
	private void edit() {
		intent = new Intent(account_tab.this, edit_account.class);  
		if(menu.existRollOver==true){
			//when roll over is true
			intent.putExtra("rollover", "exist");
			spec = tabHost.newTabSpec("List of Account ").setIndicator("List of Account").setContent(intent);
		}else {
			intent.putExtra("rollover", "not_exist");
			spec = tabHost.newTabSpec("Edit Account").setIndicator("Edit Account").setContent(intent);
		}
		tabHost.addTab(spec);		
	}

	
}