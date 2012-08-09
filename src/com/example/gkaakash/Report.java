package com.example.gkaakash;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;


public class Report extends Activity {
	AlertDialog dialog;
	final Context context = this;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        //Calling activity_roport.xml
        setContentView(R.layout.rtype);
        //For calling customised title bar
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
        TextView label = (TextView) findViewById(R.id.tvReportTitle);
        label.setText("Ledger");
        Button b1 = (Button) findViewById(R.id.btnChangeReport);
        b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
			

    });
       
    }
    
}