package com.example.gkaakash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Voucher extends Activity  {
	AlertDialog dialog;
	final Context context = this;
	
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	        //Calling voucher.xml 
	        setContentView(R.layout.voucher);
	        //customising title bar
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.voucher_title);
	        final TextView label = (TextView) findViewById(R.id.tvVoucherTitle);
	        String vouchertypeflag = getIntent().getExtras().getString("flag");
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
					final CharSequence[] items = {"Contra","Journal","Payment","Receipt","Credit Note","Debit Note","Sales","Sales Return","Purchase","Purchase Return" };
					//creating a dialog box for popup
			        AlertDialog.Builder builder = new AlertDialog.Builder(context);
			        //setting title
			        builder.setTitle("Transaction Types");
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
                          	  
                          	  label.setText("Menu >> Transaction >> Credit Note ");
                    }break;
			                case 5:
                            {
                          	  
                          	  label.setText("Menu >> Transaction >> Debit Note ");
                    }break;
			                case 6:
                            {
                          	  
                          	  label.setText("Menu >> Transaction >> Sales ");
                    }break;
			                case 7:
                            {
                          	  
                          	  label.setText("Menu >> Transaction >> Sales Return ");
                    }break;
			                case 8:
                            {
                          	 
                          	  label.setText("Menu >> Transaction >> Purchase ");
                    }break;
			                case 9:
                            {
                          	 
                          	  label.setText("Menu >> Transaction >> Purchase Return ");
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
		}
	}