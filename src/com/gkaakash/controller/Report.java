package com.gkaakash.controller;

import org.xmlrpc.android.XMLRPCException;

import android.R.string;

import com.gkaakash.coreconnection.CoreConnection;

public class Report {
	private CoreConnection conn;
	private Object[] trialResult;
	private Object[] ledgerResult;
	
	/***
	 * Default constructor
	 * create instance of CoreConnection() to get connection with server
	 */ 
	public Report() {
		conn = new CoreConnection();
	}
	
	/*
	 * get trial balance
	 */ 
	public Object getTrialBalance(Object[] params,Object client_id) {
		
		try {
			System.out.println(client_id);
			System.out.println("we are in trial");
			System.out.println(params);
			trialResult = (Object[])conn.getClient().call("reports.getTrialBalance",params,client_id);
			
		} catch (XMLRPCException e) { 
			
			e.printStackTrace();
		} 
		return trialResult;
	}
	 
	public Object getLedger(Object[] params,Object client_id) {
		
		try {
			System.out.println(client_id);
			System.out.println("we are in ledger");
			System.out.println(params);
			ledgerResult = (Object[])conn.getClient().call("reports.getLedger",params,client_id);
			System.out.println(ledgerResult);
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return ledgerResult;
	}

}
