package com.example.gkaakash;

import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class transaction_tab extends TabActivity {

	AlertDialog dialog;
	final Context context = this;
	Boolean nameflag;
	String name;
	LocalActivityManager manager;
	static TabHost tabHost;
	static String tabname;
	EditText etRefNumber;
	String from_report_flag;
	static String vouchertypeflag;
	static TabSpec editspec;
	static TextView tab;
	static String flag_for_rollover=null;
	static Boolean create_tab_afterrollover=false;
	TabHost.TabSpec spec;
	Intent intent;

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tab);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
		}
		else{ 
			// report type flag is not null when coming from any report page or bank reconciliation
			from_report_flag = extras.getString("flag");
			
			//this flag gets name of the tab from search voucher page
			name=extras.getString("name_flag");
			
//			//if flag_for_rollover is null,only search voucher tab will be there,else create tab will be visible
			flag_for_rollover= extras.getString("flag_for_rollover");
		}

		//tab name flag,whether to change the name of the tab
		nameflag=MainActivity.nameflag;

		//if from_report_flag is null,it is coming from create voucher page,else coming from report
		//if search flag is true then,it is coming from search voucher page
		if (from_report_flag == null){
			
			if(MainActivity.searchFlag != true){
				vouchertypeflag = "Contra";
			}else{
				vouchertypeflag = SearchVoucher.vouchertypeflag;
			}
		} else if(from_report_flag.equalsIgnoreCase("from_ledger")){
			vouchertypeflag =  ledger.vouchertypeflag;
			name=ledger.name;
		}else if(from_report_flag.equalsIgnoreCase("from_bankrecon")){
			vouchertypeflag =  bankReconciliation.vouchertypeflag;
			name=bankReconciliation.name;

		}else if (from_report_flag.equalsIgnoreCase("after_rollover")) {
			vouchertypeflag =  SearchVoucher.vouchertypeflag;
		}  
		manager = new LocalActivityManager(transaction_tab.this, true);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
       
        manager.dispatchCreate(savedInstanceState);
        tabHost.setup(manager);
      
        if(from_report_flag==null && menu.existRollOver==false){
        	// default mode will open up ie. to create voucher and search/edit/delete
        	create();
        	search(); 
        }else {
        	if(menu.existRollOver==true){ //after roll over
        		
        		//if flag_for_rollover is null , then only search voucher page will be visible with name List of details
        		if("after_rollover".equals(flag_for_rollover)){
        			tabHost.clearAllTabs();
            		create();
            		nameflag=true;
        			name="View Voucher details";
        		}else {
        			tabHost.clearAllTabs();
            		search();
        			nameflag=true;
        			name="List of vouchers";
				}
        		
        	}else {
        		//to view voucher details from reports and bank reconciliation
        		tabHost.clearAllTabs();
        		create();
			}
        }
        
//		//if nameflag is true ,tab name is going to change
//		//setting tab name while editing and cloning
		if(nameflag==true){
			tab = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
			tab.setText(name);
		}else {
			//setting tab name while creating account
			tab = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
			tab.setText("Create voucher");			
			tabname = "Create voucher";
		} 
		
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				//manager.dispatchPause(isFinishing());
				manager.dispatchResume();
				
			}
		});
		
	}
	
	//search voucher tab creation
	private void search() {
		intent = new Intent(transaction_tab.this, SearchVoucher.class);   
		spec = tabHost.newTabSpec("Search voucher").setIndicator("Search voucher").setContent(intent);
		tabHost.addTab(spec);
	}
	
	//create voucher tab creation
	private void create() {
		intent = new Intent(transaction_tab.this, createVoucher.class); 
        intent.putExtra("flag", from_report_flag);
        spec = tabHost.newTabSpec("Create voucher").setIndicator("Create voucher").setContent(intent);
        tabHost.addTab(spec);
		
	}

}