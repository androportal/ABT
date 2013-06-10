package com.gkaakash.controller;

import org.xmlrpc.android.XMLRPCException;

import com.gkaakash.coreconnection.CoreConnection;

public class Organisation {

	private CoreConnection conn;
	private boolean organisation;
	private Object[] projects;
	Object[] Params;
	private String orgtype;
	private Object[] getOrganisation;
	String updateOrg;
	private Object[] orgResult;
	private String result;
	/***
	 *
	 */
	 public Organisation(String ip) {
		 conn = new CoreConnection(ip);
	 }
	 /***
	  * setOrganisation method will call xmlrpc_setOrganisation from rpc_organisation.py
	  * @param ,client_id
	  * @return
	  */
	 public boolean setOrganisation(Object[] params,Object client_id) {

		 try {

			 if(params[1].equals("NGO"))
			 {
				 System.out.println("rg date:"+params[17]);
				 Params=new Object[]{params[0],params[1],params[2],
						 params[3],params[4],params[5],params[6],params[7],
						 params[8],params[9],params[10],params[11],"","",
						 params[14],params[15],params[16],params[17]};
				 organisation = (Boolean)conn.getClient().call("organisation.setOrganisation",Params,client_id);
			 }
			 else
			 {
				 Params=new Object[]{params[0],params[1],params[2],
						 params[3],params[4],params[5],params[6],params[7],
						 params[8],params[9],params[10],params[11],params[12],
						 params[13],"","","",""};
				 organisation = (Boolean)conn.getClient().call("organisation.setOrganisation",Params,client_id);
			 }
		 } catch (XMLRPCException e) {

			 e.printStackTrace();
		 }
		 return organisation;
	 }

	 /***
	  * call getAllGroups method from core_engine groups.py
	  * @param params client id
	  * @return list contain gropucode , groupname , description
	  */ 
	 public Object getAllProjects(Object client_id) {

		 try {
			 System.out.println("wr are in projects");
			 projects = (Object[])conn.getClient().call("organisation.getAllProjects",client_id);
			 System.out.println("wr are in projects..."+projects);
		 } catch (XMLRPCException e) {

			 e.printStackTrace(); 
		 }
		 return projects;
	 }

	 /*
	  * get organisation type by name
	  */
	 public String getorgTypeByname(Object[] params,Object client_id) {

		 try {
			 orgtype = (String)conn.getClient().call("organisation.getorgTypeByname",params,client_id);
		 } catch (XMLRPCException e) {

			 e.printStackTrace();
		 }
		 return orgtype;
	 }
	 
	 public Object getOrganisation(Object client_id) {
		 try { 
			 System.out.println("wr are in getOrganisation");
			 getOrganisation = (Object[])conn.getClient().call("organisation.getOrganisation",client_id);
			 System.out.println("wr are in getOrganisation..."+getOrganisation);
		 } catch (XMLRPCException e) {
			 e.printStackTrace(); 
		 } 
		 return getOrganisation;
	 }
	 
	 public String updateOrg(Object[] params,Object client_id) {


		 try {
			 System.out.println("wr are in update org");
			 // System.out.println(Params);
			 updateOrg = (String)conn.getClient().call("organisation.updateOrg",params,client_id);
			 System.out.println("details:"+updateOrg);
		 } catch (XMLRPCException e) {
			 System.out.println(e);
			 e.printStackTrace();
		 }
		 return updateOrg;
	 }

	 	/***
		 * This function will dump all tables in a file and create xml tags in bckp.xml file
		 * for given organisation
		 * @param params contain [orgname,financial-from, financial-to,orgType]
		 * @return String encrypted dbname
		 */
		public String Export(Object[] params,Object client_id) {
			
			try { 
				result = (String) conn.getClient().call("exportOrganisation",params,client_id);
				
			} catch (XMLRPCException e) {
				
				e.printStackTrace();
			}
			return result; 
		}
		
		
		public Object getAllExportedOrganisations() {
			
			try { 
				//use the ledger variable to store the result
				orgResult = (Object[]) conn.getClient().call("getAllExportedOrganisations");
				
			} catch (XMLRPCException e) {
				
				e.printStackTrace();
			}
			return orgResult; 
		}
		
		public String Import(Object[] params) {
			
			try { 
				result = (String) conn.getClient().call("Import", params);
				
			} catch (XMLRPCException e) {
				
				e.printStackTrace();
			}
			return result; 
		}
	 
}