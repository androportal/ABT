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

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tab);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			//Toast.makeText(context, "i am null", Toast.LENGTH_SHORT).show();
			name=SearchVoucher.name;
		}
		else{    		
			from_report_flag = extras.getString("flag");
		}

		//tab name flag
		nameflag=MainActivity.nameflag;

		//Toast.makeText(context,"name"+name,Toast.LENGTH_SHORT).show();
		//customizing title bar
		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.voucher_title);
		//final TextView label = (TextView) findViewById(R.id.tvVoucherTitle);
		//String vouchertypeflag = voucherMenu.vouchertypeflag;
		if (from_report_flag == null){
			//vouchertypeflag =  voucherMenu.vouchertypeflag
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
			//System.out.println("v_type:"+vouchertypeflag);
			name=bankReconciliation.name;

		}  
		
		//label.setText("Menu >> Transaction >> " + vouchertypeflag);
//		final Button home = (Button) findViewById(R.id.btnhome);
//		home.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				Intent intent = new Intent(context, menu.class);
//				// To pass on the value to the next page
//				startActivity(intent);
//
//			}
//
//		});

		manager = new LocalActivityManager(transaction_tab.this, true);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
       
        manager.dispatchCreate(savedInstanceState);
        tabHost.setup(manager);
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent(transaction_tab.this, createVoucher.class); 
        intent.putExtra("flag", from_report_flag);
        System.out.println("transaction tab :"+from_report_flag);
        spec = tabHost.newTabSpec("Create voucher").setIndicator("Create voucher").setContent(intent);
        tabHost.addTab(spec);
  
        intent = new Intent(transaction_tab.this, SearchVoucher.class);   
        spec = tabHost.newTabSpec("Search voucher").setIndicator("Search voucher").setContent(intent);
        tabHost.addTab(spec);
		
        //change the tab text color
        TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        tv.setTextColor(Color.WHITE);
        tv = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        tv.setTextColor(Color.WHITE);
        
		if(nameflag==true){//setting tab name while editing and cloning
			tab = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
			tab.setText(name);
			System.out.println("name:"+name);
			System.out.println("In if");
		}else {//setting tab name while creating account
			tab = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
			tab.setText("Create voucher");			
			tabname = "Create voucher";
			System.out.println("In else");
		} 
		
		if (name == "Voucher details") {
			tabHost.getTabWidget().getChildAt(1).setVisibility(View.INVISIBLE);
		} else {
			tabHost.getTabWidget().getChildAt(1).setVisibility(View.VISIBLE);
		}
		tabHost.getTabWidget().setCurrentTab(0);
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				//manager.dispatchPause(isFinishing());
				manager.dispatchResume();
				
			}
		});
		
	}

}