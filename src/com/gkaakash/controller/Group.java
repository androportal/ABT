package com.gkaakash.controller;

import org.xmlrpc.android.XMLRPCException;
import com.gkaakash.coreconnection.CoreConnection;

public class Group {

	private CoreConnection conn;
	private Object[] groups;
	private Object[] subgroups;
	private String subgroupname;
	//private Object[] grpparams;
	//private Object[] setaccount;
	//private Object[] setsubgroup;
	//private Object[] accparams;
	
	/***
	 * Default constructor
	 * create instance of CoreConnection() to get connection with server
	 */
	public Group(String ip) {
		conn = new CoreConnection(ip);
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
	 * @return list of subgroupname
	 */
	
	public Object[] getSubGroupsByGroupName(Object[] params,Object client_id) {
		
		try {
			
			subgroups = (Object[]) conn.getClient().call("groups.getSubGroupsByGroupName",params,client_id);
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return subgroups;
	}
	
	/***
	 * call subgroupExists method from core_engine groups.py 
	 * @param params list contain subgroupname
	 * @param client_id
	 * @return 1 if subgroupname already present else return 0
	 */
	public String subgroupExists(Object[] params,Object client_id) {
		
		try {
			
			subgroupname = (String)conn.getClient().call("groups.subgroupExists",params,client_id);
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return subgroupname;
	}
	
}
