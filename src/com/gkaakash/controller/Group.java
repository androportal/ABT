package com.gkaakash.controller;

import java.util.Vector;

import org.xmlrpc.android.XMLRPCException;

import com.gkaakash.coreconnection.CoreConnection;

public class Group {

	private CoreConnection conn;
	private Object[] groups;
	
	/***
	 * Default constructor
	 */
	public Group() {}
	
	/***
	 * call getAllGroups method from core_engine groups.py 
	 * @param params client id
	 * @return list contain gropucode , groupname , description
	 */
	public Object getAllGroups(Object params) {
		
		
		try {
			groups = (Object[])CoreConnection.getClient().call("groups.getAllGroups",params);
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return groups;
	}
	
}
