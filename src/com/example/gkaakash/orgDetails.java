package com.example.gkaakash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 

public class orgDetails<View> extends Activity {
 
	Button btnorgDetailSave;
 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.org_details);
		btnorgDetailSave = (Button) findViewById(R.id.btnOrgDetailSave);
		addListenerOnButton();
	}


	private void addListenerOnButton() {
		// TODO Auto-generated method stub
		final Context context = this;
		btnorgDetailSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(android.view.View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, preferences.class);
                startActivity(intent);   
			}
		});
	}
 
}