package com.gkaakash.controller;

import org.xmlrpc.android.XMLRPCException;
import com.gkaakash.coreconnection.CoreConnection;

public class Startup {
	static Integer client_id = null;
	static Object[] result = null;
	Object[] orgnames = null;
	CoreConnection conn;
	private Object[] cities;
	private Object[] states;
	private Object[] financialyear;
	
	/***
	 * default constructor
	 * do connection with core_engine 
	 */
	public Startup() {
		conn = new CoreConnection();
		CoreConnection.Connection();
	} // default constructor
	
	/***
	 * getOrganisationName method will call xmlrpc_getOrganisationNames from rpc_main.py
	 * @return orgnames which is the list of organisation names in gnukhata.xml
	 */
	public Object[] getOrgnisationName() {
		
		
		try {
			orgnames = (Object[]) CoreConnection.getClient().call("getOrganisationNames");
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return orgnames;
	}
	/***
	 * 
	 * @param params
	 * @return
	 */
	public Object[] getFinancialYear(Object params) {
		
		try {
			financialyear = (Object[]) CoreConnection.getClient().call("getFinancialYear",params);
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return financialyear;
	}
	/***
	 * deploy method will call xmlrpc_Deploy from rpc_main.py
	 * @param params list containing organisation name,from date,to-date,organisation type 
	 * @return result which will be the list contain boolean true, client_id
	 */
	public static Integer deploy(Object[] params)
	{
		
		try {
			result = (Object[]) CoreConnection.getClient().call("Deploy",params);
			client_id=(Integer) result[1];
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		
		return client_id;
	}
	/***
	 * login method will call xmlrpc_getConnection from rpc_main.py  
	 * @param params list containing organisation name,from date,to-date
	 * @return client_id type of integer
	 */
	public static Integer login(Object[] params)
	{
	try {
			client_id = (Integer) CoreConnection.getClient().call("getConnection",params);
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return client_id;
	}
	
	/***
	 * method getCities it will take params states 
	 * call getCityNames from core_engine data.py
	 * @param params of states
	 * @return cities depend on parameters
	 */
	public Object[] getCities (Object[] params) {
		try {
			cities = (Object[]) CoreConnection.getClient().call("data.getCityNames",params);
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		
		return cities;
	}
	/***
	 * method getStateNames will call getStateNames method from core_engine data.py
	 * @return list of states
	 */
	public Object[] getStates()
	{
		try {
			states =(Object[]) CoreConnection.getClient().call("data.getStateNames");
		} catch (XMLRPCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return states;
		
	}
	/***
	 * getter method for client_id
	 * @return client_id
	 */
	
	public static Integer getClient_id() {
		return client_id;
	}
	/***
	 * setter for client_id
	 * @param client_id
	 */
	public static void setClient_id(Integer client_id) {
		Startup.client_id = client_id;
	}
	
}
