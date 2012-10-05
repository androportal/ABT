package com.gkaakash.controller;

import org.xmlrpc.android.XMLRPCException;

import com.gkaakash.coreconnection.CoreConnection;

public class Account {
	
	private CoreConnection conn;
	private Object[] accparams;
	private String setaccount;
	private String setsubgroup;

	/***
	 * Default constructor
	 * create instance of CoreConnection() to get connection with server
	 */
	public Account() {
		conn = new CoreConnection();
	}
	/***
	 * setAccount method to save account call xmlrpc_setAccount
	 * @param params will contain accCodeCheckFlag,selGrpName,selSubGrpName,newsubgrpname,accountname,accountcode, and openingbalance
	 * @param client_id
	 * @return string
	 */
	public String setAccount(Object[] params,Object client_id) {
		
		try {
			
			if( params[0] == "true")
			{
			 	accparams = new Object[]{params[1],params[2],params[3],params[4],params[0],params[6],params[6],params[5]};
			}	
			else{
				accparams = new Object[]{params[1],params[2],params[3],params[4],"",params[6],params[6],""};
			}
				
			setaccount = (String)conn.getClient().call("account.setAccount",accparams,client_id);	
			
			if(params[3]!= "")
			{
				Object[] grpparams = new Object[]{params[1],params[3]};
			
				setsubgroup = (String)conn.getClient().call("groups.setSubGroup",grpparams,client_id);
			}
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return "success";
	}
}
