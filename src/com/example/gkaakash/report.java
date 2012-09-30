package com.example.gkaakash;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


public class report extends Activity {
	AlertDialog dialog;
	final Context context = this;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.report_menu);
        
      //customizing title bar
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.report_title);
        final TextView label = (TextView) findViewById(R.id.tvReportTitle);
        String Reporttypeflag = getIntent().getExtras().getString("flag");
        label.setText("Menu >> Transaction >>" + Reporttypeflag);
        final Button home = (Button) findViewById(R.id.btnhome);
        home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, menu.class);
				// To pass on the value to the next page
				startActivity(intent);
				
			}
        	
        });
        final Button changeReport = (Button) findViewById(R.id.btnChangeReport);
        changeReport.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final CharSequence[] items = {"Ledger","Trial Balnce","Project Statement","Cash Flow","Balance Sheet", "Income and Expenditure" };
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
		                   
		                   label.setText("Menu >> Report >> Ledger ");
		         }break;
		                case 1:
                        {
                      	  
                      	  label.setText("Menu >> Report >> Trial Balnce ");
                }break;
		                case 2:
                        {
                      	  
                      	  label.setText("Menu >> Report >> Project Statement ");
                }break;
		                case 3:
                        {
                      	  
                      	  label.setText("Menu >> Report >> Cash Flow ");
                }break;
		                case 4:
                        {
                      	  
                      	  label.setText("Menu >> Report >> Balance Sheet ");
                }break;
		                case 5:
                        {
                      	  
                      	  label.setText("Menu >> Report >> Income and Expenditure ");
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