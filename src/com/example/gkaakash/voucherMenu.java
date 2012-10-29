package com.example.gkaakash;

import java.util.ArrayList;
import java.util.List;

import com.gkaakash.controller.Organisation;
import com.gkaakash.controller.Startup;
import com.gkaakash.controller.Transaction;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class voucherMenu extends ListActivity {
	//adding voucher list items
	String[] voucherType = new String[] { "Contra","Journal","Payment","Receipt","Credit Note","Debit Note","Sales","Sales Return","Purchase","Purchase Return" };
	final Context context = this;
	static String vouchertypeflag;
	static Object[] voucherAccounts;
	static Integer client_id;
	private Transaction transaction;
	List<String> Accountlist;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		transaction = new Transaction();
       	client_id= Startup.getClient_id();
		
		//calling transactions.xml page
		setListAdapter(new ArrayAdapter<String>(this, R.layout.transactions,voucherType));
		
		//setting title
		setTitle("Menu >> Transaction");
		
		//getting the list view and setting background
		final ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		listView.setBackgroundColor(Color.BLACK);
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
						Toast.makeText(context
								, "At lease 2 accounts should be require to enter transaction, please create account!"
								, Toast.LENGTH_SHORT).show();
					}
					else{
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
						Toast.makeText(context
								, "At lease 2 accounts should be require to enter transaction, please create account!"
								, Toast.LENGTH_SHORT).show();
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
					ArrayList<String> DrAccountlist = new ArrayList<String>();
					DrAccountlist.addAll(Accountlist);
					
					Object[] params1 = new Object[]{"Cr"};
					getAccountsByRule(params1);
					ArrayList<String> CrAccountlist = new ArrayList<String>();
					CrAccountlist.addAll(Accountlist);
					
					if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
						Toast.makeText(context
								, "At lease 2 accounts should be require to enter transaction, please create account!"
								, Toast.LENGTH_SHORT).show();
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
					ArrayList<String> DrAccountlist = new ArrayList<String>();
					DrAccountlist.addAll(Accountlist);
					
					Object[] params1 = new Object[]{"Cr"};
					getAccountsByRule(params1);
					ArrayList<String> CrAccountlist = new ArrayList<String>();
					CrAccountlist.addAll(Accountlist);
					
					if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
						Toast.makeText(context
								, "At lease 2 accounts should be require to enter transaction, please create account!"
								, Toast.LENGTH_SHORT).show();
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
					ArrayList<String> DrAccountlist = new ArrayList<String>();
					DrAccountlist.addAll(Accountlist);
					
					Object[] params1 = new Object[]{"Cr"};
					getAccountsByRule(params1);
					ArrayList<String> CrAccountlist = new ArrayList<String>();
					CrAccountlist.addAll(Accountlist);
					
					if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
						Toast.makeText(context
								, "At lease 2 accounts should be require to enter transaction, please create account!"
								, Toast.LENGTH_SHORT).show();
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
					ArrayList<String> DrAccountlist = new ArrayList<String>();
					DrAccountlist.addAll(Accountlist);
					
					Object[] params1 = new Object[]{"Cr"};
					getAccountsByRule(params1);
					ArrayList<String> CrAccountlist = new ArrayList<String>();
					CrAccountlist.addAll(Accountlist);
					
					if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
						Toast.makeText(context
								, "At lease 2 accounts should be require to enter transaction, please create account!"
								, Toast.LENGTH_SHORT).show();
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
					ArrayList<String> DrAccountlist = new ArrayList<String>();
					DrAccountlist.addAll(Accountlist);
					
					Object[] params1 = new Object[]{"Cr"};
					getAccountsByRule(params1);
					ArrayList<String> CrAccountlist = new ArrayList<String>();
					CrAccountlist.addAll(Accountlist);
					
					if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
						Toast.makeText(context
								, "At lease 2 accounts should be require to enter transaction, please create account!"
								, Toast.LENGTH_SHORT).show();
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
					ArrayList<String> DrAccountlist = new ArrayList<String>();
					DrAccountlist.addAll(Accountlist);
					
					Object[] params1 = new Object[]{"Cr"};
					getAccountsByRule(params1);
					ArrayList<String> CrAccountlist = new ArrayList<String>();
					CrAccountlist.addAll(Accountlist);
					
					if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
						Toast.makeText(context
								, "At lease 2 accounts should be require to enter transaction, please create account!"
								, Toast.LENGTH_SHORT).show();
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
					ArrayList<String> DrAccountlist = new ArrayList<String>();
					DrAccountlist.addAll(Accountlist);
					
					Object[] params1 = new Object[]{"Cr"};
					getAccountsByRule(params1);
					ArrayList<String> CrAccountlist = new ArrayList<String>();
					CrAccountlist.addAll(Accountlist);
					
					if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
						Toast.makeText(context
								, "At lease 2 accounts should be require to enter transaction, please create account!"
								, Toast.LENGTH_SHORT).show();
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
					ArrayList<String> DrAccountlist = new ArrayList<String>();
					DrAccountlist.addAll(Accountlist);
					
					Object[] params1 = new Object[]{"Cr"};
					getAccountsByRule(params1);
					ArrayList<String> CrAccountlist = new ArrayList<String>();
					CrAccountlist.addAll(Accountlist);
					
					if(DrAccountlist.size() < 1 || CrAccountlist.size() < 1){
						Toast.makeText(context
								, "At lease 2 accounts should be require to enter transaction, please create account!"
								, Toast.LENGTH_SHORT).show();
					}
					else{
						Intent intent = new Intent(context, transaction_tab.class);
						// To pass on the value to the next page
						startActivity(intent);
					}
				}
				
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
		else if("Debit Note".equals(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getDebitNoteAccounts(DrCrFlag,client_id);
		}
		else if("Credit Note".equals(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getCreditNoteAccounts(DrCrFlag,client_id);
		}
		else if("Sales".equals(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getSalesAccounts(DrCrFlag,client_id);
		}
		else if("Purchase".equals(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getPurchaseAccounts(DrCrFlag,client_id);
		}
		else if("Sales Return".equals(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getSalesReturnAccounts(DrCrFlag,client_id);
		}
		else if("Purchase Return".equals(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getPurchaseReturnAccounts(DrCrFlag,client_id);
		}
		Accountlist = new ArrayList<String>();
		for(Object ac : voucherAccounts)
		{	
			Accountlist.add((String) ac);
		}	
		
	}

}
