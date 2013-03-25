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
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class transaction_tab extends TabActivity {

	static TextView tab1 = null;
	static TextView tab2 = null;
	AlertDialog dialog;
	final Context context = this;
	Boolean nameflag;
	String name;

	static TabHost tabHost;
	static String tabname;
	EditText etRefNumber;
	String from_report_flag;
	String vouchertypeflag;
	static TabSpec editspec;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
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
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.voucher_title);
		final TextView label = (TextView) findViewById(R.id.tvVoucherTitle);
		//String vouchertypeflag = voucherMenu.vouchertypeflag;
		if (from_report_flag == null){
			vouchertypeflag =  voucherMenu.vouchertypeflag;
		} else if(from_report_flag.equalsIgnoreCase("from_ledger")){
			vouchertypeflag =  ledger.vouchertypeflag;
			name=ledger.name;
		}else if(from_report_flag.equalsIgnoreCase("from_bankrecon")){
			vouchertypeflag =  bankReconciliation.vouchertypeflag;
			//System.out.println("v_type:"+vouchertypeflag);
			name=bankReconciliation.name;

		}  
		
		label.setText("Menu >> Transaction >> " + vouchertypeflag);
		final Button home = (Button) findViewById(R.id.btnhome);
		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, menu.class);
				// To pass on the value to the next page
				startActivity(intent);

			}

		});


		tabHost = getTabHost();
		//creating TabSpec for create voucher
		TabSpec createspec = tabHost.newTabSpec("tab1");
		tab1 = new TextView(this);
		//setting properties in textView
		tab1.setGravity(android.view.Gravity.CENTER);
		tab1.setTextSize(18.0f);
		tab1.setHeight(50);
		tab1.setTextColor(Color.WHITE);

		if(nameflag==true){//setting tab name while editing and cloning
			tab1.setText(name);
			//System.out.println("name:"+name);
			System.out.println("In if");
		}else {//setting tab name while creating account
			tab1.setText("Create voucher");
			tabname=(String) tab1.getText();
			//System.out.println("In else");
		} 
		createspec.setIndicator(tab1);//assigning TextView to tab Indicator
		Intent create = new Intent(this, createVoucher.class);
		create.putExtra("flag", from_report_flag);
		createspec.setContent(create);
		tabHost.addTab(createspec);  // Adding create tab

		//creating TabSpec for edit voucher
		editspec = tabHost.newTabSpec("tab2");
		tab2 = new TextView(this);
		//setting properties in textView
		tab2.setGravity(android.view.Gravity.CENTER);
		tab2.setTextSize(18.0f);
		tab2.setHeight(50);
		tab2.setTextColor(Color.WHITE);
		tab2.setText("Search voucher");
		editspec.setIndicator(tab2);//assigning TextView to tab Indicator
		Intent edit = new Intent(this, SearchVoucher.class);
		//edit.putExtra("flag","Contra");
		editspec.setContent(edit);
		tabHost.addTab(editspec); // Adding edit tab

		if (name == "Voucher details") {
			tabHost.getTabWidget().getChildAt(1).setVisibility(View.INVISIBLE);
		} else {
			tabHost.getTabWidget().getChildAt(1).setVisibility(View.VISIBLE);
		}
		//		    else if (name=="Edit voucher" || name=="Copy voucher") {
		//		    	 tab1.setText(name); 
		//		    tabHost.getTabWidget().getChildAt(1).setVisibility(View.VISIBLE);
		//			}else {
		//				tab1.setText("Create voucher"); 
		//				 tabHost.getTabWidget().getChildAt(1).setVisibility(View.VISIBLE);
		//			}

	}

}