package com.example.gkaakash;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;


@SuppressLint("ParserError") public class MainActivity extends Activity {

	Button create_org;
	Button select_org;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        create_org = (Button) findViewById(R.id.bcreateOrg);
        select_org = (Button) findViewById(R.id.bselectOrg);
        addListenerOnButton();
    }
    	void addListenerOnButton() {
		// TODO Auto-generated method stub
		final Context context = this;
		create_org = (Button) findViewById(R.id.bcreateOrg);
		create_org.setOnClickListener(new OnClickListener() {
			 
			public void onClick(View arg0) {
				 
			    Intent intent = new Intent(context, createOrg.class);
                            startActivity(intent);   
 
			}
		});
	}
}
