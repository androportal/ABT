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
	        System.out.println("vtypeflag is :....");
	        String vouchertypeflag = voucherMenu.vouchertypeflag;
	        System.out.println("vtypeflag is :"+vouchertypeflag);
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
	        final Button changeVoucher = (Button) findViewById(R.id.btnChangeVoucher);
	        changeVoucher.setOnClickListener(new OnClickListener() {
  
				@Override
				public void onClick(View arg0) {
					final CharSequence[] items = {"Contra","Journal","Payment","Receipt","Credit note","Debit note","Sales","Sales return","Purchase","Purchase return" };
					//creating a dialog box for popup
			        AlertDialog.Builder builder = new AlertDialog.Builder(context);
			        //setting title
			        builder.setTitle("Transaction types");
			        //adding items
			        builder.setItems(items, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int pos) {
			        	//code for the actions to be performed on clicking popup item goes here ...
			            switch (pos) {
			                case 0:
			                 {
			                   
			                   label.setText("Menu >> Transaction >> Contra ");
			         }break;
			                case 1:
                            {
                          	  
                          	  label.setText("Menu >> Transaction >> Journal ");
                    }break;
			                case 2:
                            {
                          	  
                          	  label.setText("Menu >> Transaction >> Payment ");
                    }break;
			                case 3:
                            {
                          	  
                          	  label.setText("Menu >> Transaction >> Receipt ");
                    }break;
			                case 4:
                            {
                          	  
                          	  label.setText("Menu >> Transaction >> Credit note ");
                    }break;
			                case 5:
                            {
                          	  
                          	  label.setText("Menu >> Transaction >> Debit note ");
                    }break;
			                case 6:
                            {
                          	  
                          	  label.setText("Menu >> Transaction >> Sales ");
                    }break;
			                case 7:
                            {
                          	  
                          	  label.setText("Menu >> Transaction >> Sales return ");
                    }break;
			                case 8:
                            {
                          	 
                          	  label.setText("Menu >> Transaction >> Purchase ");
                    }break;
			                case 9:
                            {
                          	 
                          	  label.setText("Menu >> Transaction >> Purchasee return ");
                    }break;
			        }
			    }});
		        //building a complete dialog
				dialog=builder.create();
				dialog.show();
				WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
				//customizing the width and location of the dialog on screen 
				lp.copyFrom(dialog.getWindow().getAttributes());
				lp.width = 290;
				lp.x=325;
				lp.y=100;
				dialog.getWindow().setAttributes(lp);
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
	        tab1.setText("Create Voucher");
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
	        tab2.setText("Edit Voucher");
	        editspec.setIndicator(tab2);//assigning TextView to tab Indicator
	        Intent edit = new Intent(this, editVoucher.class);
	        edit.putExtra("flag",vouchertypeflag);
	        editspec.setContent(edit);
	        tabHost.addTab(editspec); // Adding edit tab
	        tabHost.setCurrentTab(0);//setting tab1 on load
	       
	        
	       
	 }
}
