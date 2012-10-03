package com.example.gkaakash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class login extends Activity {
	String accCodeCheckFlag;
	final Context context = this;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Calling create_account.xml
		setContentView(R.layout.login);
		accCodeCheckFlag = getIntent().getExtras().getString("flag");
		OnClickListener();
}

	private void OnClickListener() {
		// TODO Auto-generated method stub
		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, menu.class);
				intent.putExtra("flag", accCodeCheckFlag);
				// To pass on the value to the next page
				startActivity(intent);
			}
		});
	}
}