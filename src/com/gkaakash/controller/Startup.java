package com.gkaakash.controller;

import org.xmlrpc.android.XMLRPCException;
import com.gkaakash.coreconnection.CoreConnection;

public class Startup {
	private static Integer client_id;
	static Object[] result = null;
	Object[] orgnames;
	private Object[] cities;
	private Object[] states;
	private Object[] financialyear;
	private CoreConnection conn;
	private Boolean deleteOrg, closeConnectionFlag;
	private static String  financialFromDate,financialToDate;
	
	
	/***
	 * default constructor
	 * do connection with core_engine 
	 */
	public Startup(String ip) {
		conn = new CoreConnection(ip);
		
	} // default constructor
	
	/***
	 * getOrganisationName method will call xmlrpc_getOrganisationNames from rpc_main.py
	 * @return orgnames which is the list of organisation names in gnukhata.xml
	 */
	public Object[] getOrgnisationName() {
		try {
			orgnames = (Object[])conn.getClient().call("getOrganisationNames");
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return orgnames;
	}
	
	/***
	 * deleteOrganisation method will call xmlrpc_deleteOrganisation from rpc_main.py
	 * @return True if organisation get delete from gnukhata.xml else False
	 */
	public Boolean deleteOrgnisationName(Object[] params) {
		try {
			deleteOrg = (Boolean)conn.getClient().call("deleteOrganisation",params);
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return deleteOrg;
	}
	
	/***
	 * this method will call  xmlrpc_getFinancialYear from rpc_main.py
	 * @param params [organisation name ]
	 * @return list of financialyear for respective organisation
	 */
	public Object[] getFinancialYear(Object params) {
		
		try {
			financialyear = (Object[])conn.getClient().call("getFinancialYear",params);
			
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
	public Integer deploy(Object[] params)
	{
		
		try {
			
			result = (Object[])conn.getClient().call("Deploy",params);
			client_id=(Integer) result[1];
			financialFromDate= (String) params[1];
			financialToDate = (String) params[2];
			/*if (params[0]=="NGO")
			{   
				setorg_result = (Object[]) CoreConnection.getClient().call("organisation.setOrganisation",params);
			}
			else{
				setorg_result = (Object[]) CoreConnection.getClient().call("organisation.setOrganisation",params);
			}*/
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
	public Integer login(Object[] params)
	{
	try {
			//System.out.println(params[0]+""+params[1]);
			client_id = (Integer)conn.getClient().call("getConnection",params);
			//System.out.println(client_id);
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return client_id;
	}
	
	public Boolean closeConnection(Object[] params)
	{
	try {
			
		closeConnectionFlag = (Boolean)conn.getClient().call("closeConnection",params);
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return closeConnectionFlag;
	}
	
	
	
	/***
	 * method getCities it will take params states 
	 * call getCityNames from core_engine data.py
	 * @param params of states
	 * @return cities depend on parameters
	 */
	public Object[] getCities (Object[] params) {
		try {
			cities = (Object[])conn.getClient().call("data.getCityNames",params);
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
			states =(Object[])conn.getClient().call("data.getStateNames");
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

	
	/***
	 * getter and setter for financialFromDate and financialToDate
	 */
	public static String getFinancialToDate() {
		return financialToDate;
	}

	public static void setFinancialToDate(String financialToDate) {
		Startup.financialToDate = financialToDate;
	}

	public static String getfinancialFromDate() {
		return financialFromDate;
	}

	public static void setfinancialFromDate(String financialFromDate) {
		Startup.financialFromDate = financialFromDate;
	}

	
}
