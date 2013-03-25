package com.example.gkaakash;

import java.util.ArrayList;
import java.util.List; 

import com.gkaakash.controller.Organisation;
import com.gkaakash.controller.Startup;
import com.gkaakash.controller.Transaction;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class voucherMenu extends ListActivity {
	//adding voucher list items
	String[] voucherType = new String[] { "Contra","Journal","Payment","Receipt","Credit note","Debit note","Sales","Sales return","Purchase","Purchase return" };
	final Context context = this;
	static String vouchertypeflag;
	static Object[] voucherAccounts;
	static Integer client_id;
	private Transaction transaction;
	static List<String> Accountlist;
	static ArrayList<String> DrAccountlist;
	static ArrayList<String> CrAccountlist;
	static boolean flag;
	static module m;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		transaction = new Transaction();
       	client_id= Startup.getClient_id();
		flag = true;
		//calling transactions.xml page
		setListAdapter(new ArrayAdapter<String>(this, R.layout.transactions,voucherType));
		
		//setting title
		setTitle("Menu >> Transaction");
		m=new module();
		//getting the list view and setting background
		final ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		listView.setBackgroundColor(R.drawable.dark_gray_background);
		listView.setCacheColorHint(Color.TRANSPARENT);
		
		//when voucher list items are clicked, code for respective actions goes here ...
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Object[] params = new Object[]{"Dr"};
				Accountlist = new ArrayList<String>();
				String vtf = parent.getItemAtPosition(position).toString(); //voucher type flag:vtf
				IntentToVoucher(vtf,params);	
				
			}
		});
	}
	
	
	void IntentToVoucher(String vtf, Object[] params) {
		//for "Contra" voucher
		if(vtf.equalsIgnoreCase("Contra"))
		{
			contraJournal(vtf, params);
			
		}
		if(vtf.equalsIgnoreCase("Journal"))
		{
			
			contraJournal(vtf, params);
		}
		if(vtf.equalsIgnoreCase("Payment"))
		{
			
			exceptContraJournal(vtf, params);
		}
		if(vtf.equalsIgnoreCase("Receipt"))
		{
			exceptContraJournal(vtf, params);
		}
		if(vtf.equalsIgnoreCase("Credit Note"))
		{
			exceptContraJournal(vtf, params);
		}
		if(vtf.equalsIgnoreCase("Debit Note"))
		{
			exceptContraJournal(vtf, params);
		}
		if(vtf.equalsIgnoreCase("Sales"))
		{
			exceptContraJournal(vtf, params);
		}
		if(vtf.equalsIgnoreCase("Sales Return"))
		{
			exceptContraJournal(vtf, params);
		}
		if(vtf.equalsIgnoreCase("Purchase"))
		{
			exceptContraJournal(vtf, params);
			
		}
		if(vtf.equalsIgnoreCase("Purchase Return"))
		{
			exceptContraJournal(vtf, params);
		}
		
	}

	 void exceptContraJournal(String vtf, Object[] params) {
		vouchertypeflag  = vtf;				
		
		DrAccountlist = new ArrayList<String>();
		Object[] paramDr = new Object[]{"Dr"};
		m.getAccountsByRule(paramDr,vouchertypeflag, context);
		Accountlist = module.Accountlist;
		DrAccountlist.addAll(Accountlist);
		
		CrAccountlist = new ArrayList<String>();
		Object[] paramCr = new Object[]{"Cr"};
		m.getAccountsByRule(paramCr,vouchertypeflag, context);
		Accountlist = module.Accountlist;
		CrAccountlist.addAll(Accountlist);
		System.out.println(vouchertypeflag);
		System.out.println("CList:"+CrAccountlist);
		
		
		if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
			m.toastValidationMessage(voucherMenu.this);
		}
		else{
			Intent intent = new Intent(context, transaction_tab.class);
			// To pass on the value to the next page
			startActivity(intent);
		}
		
	}

	 void contraJournal(String vtf, Object[] params) {
		vouchertypeflag  = vtf;
		m.getAccountsByRule(params, vouchertypeflag, context);
		
		Accountlist = module.Accountlist;
		if(Accountlist.size() < 2){
			m.toastValidationMessage(voucherMenu.this);
		}
		else{
			
			Intent intent = new Intent(context, transaction_tab.class);
			// To pass on the value to the next page
			startActivity(intent);
		}
		
	}
	
	
}