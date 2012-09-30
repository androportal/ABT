package com.gkaakash.controller;

import java.util.Vector;

import org.xmlrpc.android.XMLRPCException;

import com.gkaakash.coreconnection.CoreConnection;

public class Group {

	private CoreConnection conn;
	private Object[] groups;
	private Object[] subgroups;
	private Object[] subgroupname;
	
	/***
	 * Default constructor
	 * create instance of CoreConnection() to get connection with server
	 */
	public Group() {
		conn = new CoreConnection();
	}
	
	/***
	 * call getAllGroups method from core_engine groups.py 
	 * @param params client id
	 * @return list contain gropucode , groupname , description
	 */
	public Object getAllGroups(Object client_id) {
		
		try {
			groups = (Object[])conn.getClient().call("groups.getAllGroups",client_id);
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return groups;
	}
	/***
	 * call getSubGroupsByGroup method from core_engine groups.py 
	 * @param params list contain groupname
	 * @param client_id
	 * @return subgroupname list
	 */
	public Object[] getSubGroupsByGroup(Object[] params,Object client_id) {
		
		try {
			
			subgroups = (Object[])conn.getClient().call("groups.getSubGroupsByGroup",params,client_id);
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return subgroups;
	}
	/***
	 * call getSubGroupByName method from core_engine groups.py 
	 * @param params list contain subgroupname
	 * @param client_id
	 * @return subgroupname
	 */
	public Object[] getSubGroupByName(Object[] params,Object client_id) {
		
		try {
			
			subgroupname = (Object[])conn.getClient().call("groups.getSubGroupByName",params,client_id);
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return subgroupname;
	}
	
}
