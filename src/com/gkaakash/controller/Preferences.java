/**
 * 
 */
package com.gkaakash.controller;

import com.gkaakash.coreconnection.CoreConnection;

/**
 * @author ashwini
 *
 */
public class Preferences {

	private CoreConnection conn;
	private Boolean setPreference;
	private Object[] accCodeParams;
	private Object[] refNoParams;
	private Boolean setProject;
	private String getPreference;
	
	/***
	 * default constructor
	 * connect with core_engine see CoreConnection
	 */
	public Preferences() {
		conn = new CoreConnection();
	}
	/***
	 * setPreferences method will call xmlrpc_setPreferences from rpc_organisation.py
	 * @param params[1,reference no,2,account-code flag,project name ]
	 * @param client_id
	 * @return boolean True
	 */
	public Boolean setPreferences(Object[] params,Object client_id)
	{
		try {
			
			accCodeParams = new Object[]{params[2],params[3]};
			setPreference= (Boolean)conn.getClient().call("organisation.setPreferences",accCodeParams,client_id);
			refNoParams = new Object[]{params[0],params[1]};
			setPreference= (Boolean)conn.getClient().call("organisation.setPreferences",refNoParams,client_id);
			setProject= (Boolean)conn.getClient().call("organisation.setProjects", new Object[]{params[4]},client_id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return setPreference;
		
	}
	/***
	 * getPreferences method will call xmlrpc_getPreferences from rpc_organisation.py
	 * @param if want account code flag then [2] else [1]
	 * @param client_id
	 * @return flag type of String 
	 */
	public String getPreferences(Object[] params,Object client_id)
	{
		try {
			System.out.println(params[0]);
			System.out.println(client_id);
			getPreference= (String)conn.getClient().call("organisation.getPreferences",params,client_id);
			System.out.println("getPreference"+getPreference);
			} catch (Exception e) {
				System.out.println("cant call");
			 e.printStackTrace();
		}
		return getPreference;
		
	}
	
		
}
