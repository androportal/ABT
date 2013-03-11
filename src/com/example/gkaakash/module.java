package com.example.gkaakash;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;

import com.gkaakash.controller.Startup;
import com.gkaakash.controller.Transaction;

public class module {
	static Integer client_id;
	private Transaction transaction;
	static String vouchertypeflag;
	static Object[] voucherAccounts;
	static List<String> Accountlist;
	static ArrayAdapter<String> dataAdapter;
	
	void getAccountsByRule(Object[] DrCrFlag, String vouchertypeflag2, Context context) {
		transaction = new Transaction();
       	client_id= Startup.getClient_id();
		System.out.println();
		
		vouchertypeflag=vouchertypeflag2;
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
		System.out.println("result"+voucherAccounts);
		for(Object ac : voucherAccounts)
		{	
			Accountlist.add((String) ac);
		}
		
		dataAdapter = new ArrayAdapter<String>(context,
    			android.R.layout.simple_spinner_item, Accountlist);
    	//set resource layout of spinner to that adapter
    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}
	
	 void toastValidationMessage(Context c) {
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
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
		
}