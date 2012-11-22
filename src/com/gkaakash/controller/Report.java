package com.gkaakash.controller;

import org.xmlrpc.android.XMLRPCException;
import org.xmlrpc.android.XMLRPCFault;

import android.R.string;

import com.gkaakash.coreconnection.CoreConnection;

public class Report {
	private CoreConnection conn;
	private Object[] trialResult;
	private Object[] ledgerResult;
	private Object[] grosstrialResult;
	private Object[] extendedtrialResult;
	private Object[] projectStatement;
	
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
			trialResult = (Object[])conn.getClient().call("reports.getTrialBalance",params,client_id);
			
		} catch (XMLRPCFault e) {
			try {
				trialResult = (Object[])conn.getClient().call("reports.getTrialBalance",params,client_id);
			} catch (XMLRPCException e1) {
			
				e1.printStackTrace();
			}
		}
		catch (Exception e) { 
			
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
		} catch (XMLRPCFault e) {
			try {
				ledgerResult = (Object[])conn.getClient().call("reports.getLedger",params,client_id);
			} catch (XMLRPCException e1) {
				
				e1.printStackTrace();
			}
		}catch (Exception e) {
			
		}
		
		return ledgerResult;
	}
public Object getGrossTrialBalance(Object[] params,Object client_id) {
		
		try {
			System.out.println(client_id);
			System.out.println("we are in gross trial");
			System.out.println(params);
			grosstrialResult = (Object[])conn.getClient().call("reports.getGrossTrialBalance",params,client_id);
			
		} catch (XMLRPCFault e) {
			try {
				grosstrialResult = (Object[])conn.getClient().call("reports.getGrossTrialBalance",params,client_id);
			} catch (XMLRPCException e1) {
			
				e1.printStackTrace(); 
			}
		}
		catch (Exception e) { 
			
			e.printStackTrace(); 
		} 
		return grosstrialResult;
	}
	public Object getExtendedTrialBalance(Object[] params,Object client_id) {
		
		try {
			System.out.println(client_id);
			System.out.println("we are in extendedtrial");
			System.out.println(params);
			extendedtrialResult = (Object[])conn.getClient().call("reports.getExtendedTrialBalance",params,client_id);
			
		} catch (XMLRPCFault e) {
			try {
				extendedtrialResult = (Object[])conn.getClient().call("reports.getExtendedTrialBalance",params,client_id);
			} catch (XMLRPCException e1) {
			
				e1.printStackTrace(); 
			}
		}
		catch (Exception e) { 
			
			e.printStackTrace();
		} 
		return extendedtrialResult;
	}

	
	/*
	 * get project statement report
	 */ 
	public Object getProjectStatementReport(Object[] params,Object client_id) {
		
		try {
			projectStatement = (Object[])conn.getClient().call("reports.getProjectStatementReport",params,client_id);
		} catch (XMLRPCException e1) {
		
			e1.printStackTrace();
		}
		
		catch (Exception e) { 
			
			e.printStackTrace();
		} 
		return projectStatement;
	}
}
