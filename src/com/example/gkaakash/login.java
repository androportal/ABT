package com.example.gkaakash;

import java.util.List;

import com.gkaakash.controller.Preferences;
import com.gkaakash.controller.Startup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class login extends Activity {
	final Context context = this;
	private Integer client_id;
	static String IPaddr;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Calling create_account.xml
		setContentView(R.layout.login);
		client_id = Startup.getClient_id();
		IPaddr = MainActivity.IPaddr;
	    System.out.println("in createorg"+IPaddr);
		OnClickListener();
}

	private void OnClickListener() {
		// TODO Auto-generated method stub
		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, menu.class);
				// To pass on the value to the next page
				startActivity(intent);
			}
		});
	}
}