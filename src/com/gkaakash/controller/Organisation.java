package com.gkaakash.controller;

import org.xmlrpc.android.XMLRPCException;

import com.gkaakash.coreconnection.CoreConnection;

public class Organisation {
	
	private CoreConnection conn;
	private boolean organisation;
	/***
	 * 
	 */
	public Organisation() {
		conn = new CoreConnection();
	}
	/***
	 * setOrganisation method will call xmlrpc_setOrganisation from rpc_organisation.py
	 * @param ,client_id
	 * @return
	 */
	public boolean setOrganisation(Object[] params,Object client_id) {
		
		try {
			if (params[0]=="NGO")
			{
				organisation = (Boolean)conn.getClient().call("organisation.setOrganisation",params,client_id);
			}
			else
			{
				organisation = (Boolean)conn.getClient().call("organisation.setOrganisation",params,client_id);
			}
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return organisation;
	}
  
}
