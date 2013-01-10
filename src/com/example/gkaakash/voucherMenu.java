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
				//for "Contra" voucher
				if(position == 0)
				{
					vouchertypeflag  = parent.getItemAtPosition(position).toString();
					getAccountsByRule(params);
					if(Accountlist.size() < 2){
						toastValidationMessage();
					}
					else{
						MainActivity.searchFlag=false;
						Intent intent = new Intent(context, transaction_tab.class);
						// To pass on the value to the next page
						startActivity(intent);
					}
					
				}
				if(position == 1)
				{
					vouchertypeflag  = parent.getItemAtPosition(position).toString();
					
					getAccountsByRule(params);
					if(Accountlist.size() < 2){
						toastValidationMessage();
					}
					else{
						Intent intent = new Intent(context, transaction_tab.class);
						// To pass on the value to the next page
						startActivity(intent);
					}
				}
				if(position == 2)
				{
					vouchertypeflag  = parent.getItemAtPosition(position).toString();
					
					getAccountsByRule(params);
					DrAccountlist = new ArrayList<String>();
					DrAccountlist.addAll(Accountlist);
					
					Object[] params1 = new Object[]{"Cr"};
					getAccountsByRule(params1);
					CrAccountlist = new ArrayList<String>();
					CrAccountlist.addAll(Accountlist);
					
					if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
						toastValidationMessage();
					}
					else{
						Intent intent = new Intent(context, transaction_tab.class);
						// To pass on the value to the next page
						startActivity(intent);
					}
				}
				if(position == 3)
				{
					vouchertypeflag  = parent.getItemAtPosition(position).toString();
					
					getAccountsByRule(params);
					DrAccountlist = new ArrayList<String>();
					DrAccountlist.addAll(Accountlist);
					
					Object[] params1 = new Object[]{"Cr"};
					getAccountsByRule(params1);
					CrAccountlist = new ArrayList<String>();
					CrAccountlist.addAll(Accountlist);
					
					if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
						toastValidationMessage();
					}
					else{
						Intent intent = new Intent(context, transaction_tab.class);
						// To pass on the value to the next page
						startActivity(intent);
					}
				}
				if(position == 4)
				{
					vouchertypeflag  = parent.getItemAtPosition(position).toString();
					
					getAccountsByRule(params);
					DrAccountlist = new ArrayList<String>();
					DrAccountlist.addAll(Accountlist);
					
					Object[] params1 = new Object[]{"Cr"};
					getAccountsByRule(params1);
					CrAccountlist = new ArrayList<String>();
					CrAccountlist.addAll(Accountlist);
					
					if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
						toastValidationMessage();
					}
					else{
						Intent intent = new Intent(context, transaction_tab.class);
						// To pass on the value to the next page
						startActivity(intent);
					}
				}
				if(position == 5)
				{
					vouchertypeflag  = parent.getItemAtPosition(position).toString();
					
					getAccountsByRule(params);
					DrAccountlist = new ArrayList<String>();
					DrAccountlist.addAll(Accountlist);
					
					Object[] params1 = new Object[]{"Cr"};
					getAccountsByRule(params1);
					CrAccountlist = new ArrayList<String>();
					CrAccountlist.addAll(Accountlist);
					
					if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
						toastValidationMessage();
					}
					else{
						Intent intent = new Intent(context, transaction_tab.class);
						// To pass on the value to the next page
						startActivity(intent);
					}
				}
				if(position == 6)
				{
					vouchertypeflag  = parent.getItemAtPosition(position).toString();
					
					getAccountsByRule(params);
					DrAccountlist = new ArrayList<String>();
					DrAccountlist.addAll(Accountlist);
					
					Object[] params1 = new Object[]{"Cr"};
					getAccountsByRule(params1);
					CrAccountlist = new ArrayList<String>();
					CrAccountlist.addAll(Accountlist);
					
					if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
						toastValidationMessage();
					}
					else{
						Intent intent = new Intent(context, transaction_tab.class);
						// To pass on the value to the next page
						startActivity(intent);
					}
				}
				if(position == 7)
				{
					vouchertypeflag  = parent.getItemAtPosition(position).toString();
					
					getAccountsByRule(params);
					DrAccountlist = new ArrayList<String>();
					DrAccountlist.addAll(Accountlist);
					
					Object[] params1 = new Object[]{"Cr"};
					getAccountsByRule(params1);
					CrAccountlist = new ArrayList<String>();
					CrAccountlist.addAll(Accountlist);
					
					if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
						toastValidationMessage();
					}
					else{
						Intent intent = new Intent(context, transaction_tab.class);
						// To pass on the value to the next page
						startActivity(intent);
					}
				}
				if(position == 8)
				{
					vouchertypeflag  = parent.getItemAtPosition(position).toString();
					
					getAccountsByRule(params);
					DrAccountlist = new ArrayList<String>();
					DrAccountlist.addAll(Accountlist);
					
					Object[] params1 = new Object[]{"Cr"};
					getAccountsByRule(params1);
					CrAccountlist = new ArrayList<String>();
					CrAccountlist.addAll(Accountlist);
					
					if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
						toastValidationMessage();
					}
					else{
						Intent intent = new Intent(context, transaction_tab.class);
						// To pass on the value to the next page
						startActivity(intent);
					}
				}
				if(position == 9)
				{
					vouchertypeflag  = parent.getItemAtPosition(position).toString();
					
					getAccountsByRule(params);
					DrAccountlist = new ArrayList<String>();
					DrAccountlist.addAll(Accountlist);
					
					Object[] params1 = new Object[]{"Cr"};
					getAccountsByRule(params1);
					CrAccountlist = new ArrayList<String>();
					CrAccountlist.addAll(Accountlist);
					
					if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
						toastValidationMessage();
					}
					else{
						Intent intent = new Intent(context, transaction_tab.class);
						// To pass on the value to the next page
						startActivity(intent);
					}
				}
				
			}

			public void toastValidationMessage() {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
		        builder.setMessage("At lease 2 accounts require to enter transaction, please create account!")
		                .setCancelable(false)
		                .setPositiveButton("Ok",
		                        new DialogInterface.OnClickListener() {
		                            public void onClick(DialogInterface dialog, int id) {
		                            	
		                            }
		                        });
		                
		        AlertDialog alert = builder.create();
		        alert.show();
				
			} 
		});
	}
	
	private void getAccountsByRule(Object[] DrCrFlag) {
		if("Contra".equals(vouchertypeflag)){
			voucherAccounts = (Object[]) transaction.getContraAccounts(client_id);
		}
		else if("Journal".equals(vouchertypeflag)){
			voucherAccounts = (Object[]) transaction.getJournalAccounts(client_id);
		}
		else if("Receipt".equals(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getReceivableAccounts(DrCrFlag,client_id);
		}
		else if("Payment".equals(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getPaymentAccounts(DrCrFlag,client_id);
		}
		else if("Debit Note".equalsIgnoreCase(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getDebitNoteAccounts(DrCrFlag,client_id);
		}
		else if("Credit Note".equalsIgnoreCase(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getCreditNoteAccounts(DrCrFlag,client_id);
		}
		else if("Sales".equals(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getSalesAccounts(DrCrFlag,client_id);
		}
		else if("Purchase".equals(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getPurchaseAccounts(DrCrFlag,client_id);
		}
		else if("Sales Return".equalsIgnoreCase(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getSalesReturnAccounts(DrCrFlag,client_id);
		}
		else if("Purchase Return".equalsIgnoreCase(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getPurchaseReturnAccounts(DrCrFlag,client_id);
		}
		Accountlist = new ArrayList<String>();
		for(Object ac : voucherAccounts)
		{	
			Accountlist.add((String) ac);
		}	
		
	}

}
