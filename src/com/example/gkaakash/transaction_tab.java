package com.example.gkaakash;

import android.app.AlertDialog;
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
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class transaction_tab extends TabActivity{
	
	TextView tab1 = null;
	TextView tab2 = null;
	AlertDialog dialog;
	final Context context = this;
	
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	        setContentView(R.layout.tab);
	        
	      //customizing title bar
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.voucher_title);
	        final TextView label = (TextView) findViewById(R.id.tvVoucherTitle);
	        String vouchertypeflag = voucherMenu.vouchertypeflag;
	        label.setText("Menu >> Transaction >>" + vouchertypeflag);
	        final Button home = (Button) findViewById(R.id.btnhome);
	        home.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(context, menu.class);
					// To pass on the value to the next page
					startActivity(intent);
					
				}
	        	
	        });
	        
	        final TabHost tabHost = getTabHost();
	        //creating TabSpec for create voucher
	        TabSpec createspec = tabHost.newTabSpec("tab1");
	        tab1 = new TextView(this);
	        //setting properties in textView
	        tab1.setGravity(android.view.Gravity.CENTER);
	        tab1.setTextSize(18.0f);
	        tab1.setHeight(50);
	        tab1.setTextColor(Color.WHITE);
	        tab1.setText("Create voucher");
	        createspec.setIndicator(tab1);//assigning TextView to tab Indicator
	        
	        Intent create = new Intent(this, createVoucher.class);
	        create.putExtra("flag", vouchertypeflag);
	        createspec.setContent(create);
	        tabHost.addTab(createspec);  // Adding create tab
	        
	        //creating TabSpec for edit voucher
	        TabSpec editspec = tabHost.newTabSpec("tab2");
	        tab2 = new TextView(this);
	        //setting properties in textView
	        tab2.setGravity(android.view.Gravity.CENTER);
	        tab2.setTextSize(18.0f);
	        tab2.setHeight(50);
	        tab2.setTextColor(Color.WHITE);
	        tab2.setText("Search voucher");
	        editspec.setIndicator(tab2);//assigning TextView to tab Indicator
	        Intent edit = new Intent(this, SearchVoucher.class);
	        edit.putExtra("flag",vouchertypeflag);
	        editspec.setContent(edit);
	        tabHost.addTab(editspec); // Adding edit tab
	        tabHost.setCurrentTab(0);//setting tab1 on load
	       
	        
	       
	 }
}
