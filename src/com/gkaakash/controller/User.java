package com.gkaakash.controller;

import org.xmlrpc.android.XMLRPCException;

import android.os.Message;

import com.gkaakash.coreconnection.CoreConnection;

public class User {
	private CoreConnection conn;
	String setuser;
	boolean isuserexist,isuserunique,isadmin;
	
	public User() {
		conn = new CoreConnection();
	}
	
	public String setUser(Object[] params,Object client_id)
	{
		try {
			setuser = (String)conn.getClient().call("user.setUser",params,client_id);
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		System.out.println("user: "+setuser);
		return setuser;
	}
	
	public boolean isUserExist(Object[] params,Object client_id)
	{
		try {
			isuserexist = (Boolean) conn.getClient().call("user.isUserExist",params,client_id);
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		System.out.println("user: "+isuserexist);
		return isuserexist;
		
	}
	public boolean isUserUnique(Object[] params,Object client_id)
	{
		try {
			isuserunique = (Boolean) conn.getClient().call("user.isUserUnique",params,client_id);
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return isuserunique;
	}
	
		public boolean isAdmin(Object client_id)
		{
			try {
				isadmin = (Boolean) conn.getClient().call("user.isAdmin",client_id);
			} catch (XMLRPCException e) {
				
				e.printStackTrace();
			}
			return isadmin;
		}
			
		
		
	
}
