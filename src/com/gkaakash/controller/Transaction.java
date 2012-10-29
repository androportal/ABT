package com.gkaakash.controller;

import java.util.ArrayList;

import org.xmlrpc.android.XMLRPCException;

import android.R.integer;

import com.gkaakash.coreconnection.CoreConnection;

public class Transaction {
	private CoreConnection conn;
	private Integer setVoucher;
	private Object[] contraAccounts;
	private Object[] journalAccounts;
	private Object[] receivableAccounts;
	private Object[] paymentAccounts;
	private Object[] debitNoteAccounts;
	private Object[] creditNoteAccounts;
	private Object[] salesAccounts;
	private Object[] purchaseAccounts;
	private Object[] salesReturnAccounts;
	private Object[] purchaseReturnAccounts;
	private Object[] searchedVouchers;
	
	/***
	 * Default constructor
	 * create instance of CoreConnection() to get connection with server
	 */ 
	public Transaction() {
		conn = new CoreConnection();
	} 
	
	/*  
	 * get list of all contra accounts 
	 */

public Integer setTransaction(Object[] params_master,ArrayList<ArrayList> params_details,Object client_id) {
	try {  
		setVoucher = (Integer)conn.getClient().call("transaction.setTransaction",params_master,params_details,client_id);
		 
	} catch (XMLRPCException e) {
		e.printStackTrace();
	}
	return setVoucher; 
	}
	

/*
 * get list of all contra accounts
 */
public Object getContraAccounts(Object client_id) { 
		
		try {
			System.out.println("client id is "+client_id);
			contraAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getContraAccounts",client_id);
			System.out.println("contra :"+contraAccounts);
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		System.out.println("acc so far are...");
		System.out.println(contraAccounts);
		return contraAccounts;
	}


/*
 * get list of all jornal accounts
 */
public Object getJournalAccounts(Object client_id) {
	
	try {
		journalAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getJournalAccounts",client_id);
		
	} catch (XMLRPCException e) {
		
		e.printStackTrace();
	}
	return journalAccounts;
}


/*
 * get list of all receivable accounts
 */
public Object getReceivableAccounts(Object[] params,Object client_id) {
	
	try {
		receivableAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getReceivableAccounts",params,client_id);
		
	} catch (XMLRPCException e) {
		
		e.printStackTrace();
	}
	return receivableAccounts;
}


/*
 * get list of all payment accounts
 */ 
public Object getPaymentAccounts(Object[] params,Object client_id) {
	
	try {
		paymentAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getPaymentAccounts",params,client_id);
		
	} catch (XMLRPCException e) {
		
		e.printStackTrace();
	}
	return paymentAccounts;
}


/*
 * get list of all debit note accounts
 */
public Object getDebitNoteAccounts(Object[] params,Object client_id) {
	
	try {
		debitNoteAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getDebitNoteAccounts",params,client_id);
		
	} catch (XMLRPCException e) {
		
		e.printStackTrace();
	}
	return debitNoteAccounts;
}


/*
 * get list of all credit note accounts
 */
public Object getCreditNoteAccounts(Object[] params,Object client_id) {
	
	try {
		creditNoteAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getCreditNoteAccounts",params,client_id);
		
	} catch (XMLRPCException e) {
		
		e.printStackTrace();
	}
	return creditNoteAccounts;
}


/*
 * get list of all sales accounts
 */
public Object getSalesAccounts(Object[] params,Object client_id) {
	
	try {
		salesAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getSalesAccounts",params,client_id);
		
	} catch (XMLRPCException e) {
		
		e.printStackTrace();
	}
	return salesAccounts;
}


/*
 * get list of all purchase accounts
 */
public Object getPurchaseAccounts(Object[] params,Object client_id) {
	
	try {
		purchaseAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getPurchaseAccounts",params,client_id);
		
	} catch (XMLRPCException e) {
		
		e.printStackTrace();
	}
	return purchaseAccounts;
}


/*
 * get list of all sales return accounts
 */
public Object getSalesReturnAccounts(Object[] params,Object client_id) {
	
	try {
		salesReturnAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getSalesReturnAccounts",params,client_id);
		
	} catch (XMLRPCException e) {
		
		e.printStackTrace();
	}
	return salesReturnAccounts;
}

  
/*
 * get list of all purchase return accounts
 */
public Object getPurchaseReturnAccounts(Object[] params,Object client_id) {
	
	try {
		purchaseReturnAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getPurchaseReturnAccounts",params,client_id);
		
	} catch (XMLRPCException e) {
		
		e.printStackTrace();
	}
	return purchaseReturnAccounts;
}

public Object searchVoucher(Object[] params,Object client_id) {
	
	try {
		searchedVouchers = (Object[])conn.getClient().call("transaction.searchVoucher",params,client_id);
		
	} catch (XMLRPCException e) {
		
		e.printStackTrace();
	}
	return searchedVouchers;
}


}
