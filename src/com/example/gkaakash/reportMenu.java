package com.example.gkaakash;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class reportMenu extends ListActivity{
	//adding report list items
	String[] reportType = new String[] { "Ledger","Trial Balance","Project Statement","Cash Flow","Balance Sheet","Profit and Loss Account" };
	final Context context = this;
	AlertDialog dialog;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//calling report.xml page
		setListAdapter(new ArrayAdapter<String>(this, R.layout.report,reportType));
		
		//getting the list view and setting background
		final ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		listView.setBackgroundColor(Color.BLACK);
		listView.setCacheColorHint(Color.TRANSPARENT);
		
		//when report list items are clicked, code for respective actions goes here ...
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//for "Ledger"
				if(position == 0)
				{
					
					
					/*String reporttypeflag  = parent.getItemAtPosition(position).toString();
					Intent intent = new Intent(context, report.class);
					intent.putExtra("flag", reporttypeflag);
					// To pass on the value to the next page
					startActivity(intent);*/
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.ledger, (ViewGroup) findViewById(R.id.layout_root));
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					
					
					builder.setTitle("");
					builder.setPositiveButton("Set",new  DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
						
					});
					
					builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
						
					});
					dialog=builder.create();
	        		dialog.show();
					
				}
				if(position == 1)
				{
					/*String reporttypeflag  = parent.getItemAtPosition(position).toString();
					Intent intent = new Intent(context, report.class);
					intent.putExtra("flag", reporttypeflag);
					// To pass on the value to the next page
					startActivity(intent);*/
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.trial_balance, (ViewGroup) findViewById(R.id.layout_root));
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					builder.setTitle("");
					
					builder.setPositiveButton("Set",new  DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
						
					});
					
					builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
						
					});
					dialog=builder.create();
	        		dialog.show();
				}
				if(position == 2)
				{
					/*String reporttypeflag  = parent.getItemAtPosition(position).toString();
					Intent intent = new Intent(context, report.class);
					intent.putExtra("flag", reporttypeflag);
					// To pass on the value to the next page
					startActivity(intent);*/
					
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.project_statement, (ViewGroup) findViewById(R.id.layout_root));
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					builder.setTitle("");
					
				
					builder.setPositiveButton("Set",new  DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
						
					});
					
					builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
						
					});
					dialog=builder.create();
	        		dialog.show();
				}
				if(position == 3)
				{
					/*String reporttypeflag  = parent.getItemAtPosition(position).toString();
					Intent intent = new Intent(context, report.class);
					intent.putExtra("flag", reporttypeflag);
					// To pass on the value to the next page
					startActivity(intent);*/
					
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.report_dialog, (ViewGroup) findViewById(R.id.layout_root));
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					
					
					 builder.setPositiveButton("Set",new  DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
						 
					 });
					 builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
							 
						 });
					dialog=builder.create();
	        		dialog.show();
				}
				if(position == 4)
				{
					/*String reporttypeflag  = parent.getItemAtPosition(position).toString();
					Intent intent = new Intent(context, report.class);
					intent.putExtra("flag", reporttypeflag);
					// To pass on the value to the next page
					startActivity(intent);*/
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.balance_sheet, (ViewGroup) findViewById(R.id.layout_root));
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					
					
					
					builder.setPositiveButton("Set",new  DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
						
					});
					
					builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
						
					});
					dialog=builder.create();
	        		dialog.show();
				}
				if(position == 5)
				{
					/*String reporttypeflag  = parent.getItemAtPosition(position).toString();
					Intent intent = new Intent(context, report.class);
					intent.putExtra("flag", reporttypeflag);
					// To pass on the value to the next page
					startActivity(intent);*/
					
					
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.report_dialog, (ViewGroup) findViewById(R.id.layout_root));
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					
					
					 builder.setPositiveButton("Set",new  DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
						 
					 });
					 builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
							 
						 });
					dialog=builder.create();
	        		dialog.show();
				}
				
				
			} 
		});
	}
}
