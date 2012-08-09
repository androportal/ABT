package com.example.gkaakash;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Voucher extends Activity  {
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	        //Calling voucher.xml 
	        setContentView(R.layout.voucher);
	        //customising title bar
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.voucher_title);

}
}
