package com.example.gkaakash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("ParserError")
public class preferences extends Activity {
	CheckBox cbProject;
	EditText etProject;
	CheckBox cbAccCode;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferences);
		etProject = (EditText) findViewById(R.id.etProject);
		etProject.setVisibility(EditText.GONE);
		addListenerOnChkIos();
	}

	private void addListenerOnChkIos() {
		// TODO Auto-generated method stub
		cbProject = (CheckBox) findViewById(R.id.cbProject);
		cbProject.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((CheckBox) v).isChecked()) {
					etProject = (EditText) findViewById(R.id.etProject);
					etProject.setVisibility(EditText.VISIBLE);
				}
				else {
					etProject = (EditText) findViewById(R.id.etProject);
					etProject.setVisibility(EditText.GONE);
				}
			}
			});
	}
}
