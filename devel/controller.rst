src/com.gkaakash.controller
===========================

.. toctree::
   :numbered:
   
* This chapter explains source code of all the java(controller) files which are located at ``src/com.gkaakash.controller``.

* These java file contains different methods which acts like a middelman between android client and xmlrpc-server(ABTcore).

* They get the input from android front-ent, sends the request to back-end server and get the respective result.

* We have tried to make seperate modules for organisation, accounts, transactions and reports.

* These methods are used in android activities as per the requirement. 

Startup.java
++++++++++++

* Import following packages,

	.. code-block:: java

		package com.gkaakash.controller;

		import org.xmlrpc.android.XMLRPCException;
		import com.gkaakash.coreconnection.CoreConnection;

* intialize all the essential parameters and variables,

	.. code-block:: java
	
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
	

* default constructor to make connection with core_engine.

	 .. code-block:: java
		public Startup() {
			conn = new CoreConnection();
		
		} // default constructor
	

* getOrganisationName method will call xmlrpc_getOrganisationNames from rpc_main.py.

* @return orgnames which is the list of organisation names in gnukhata.xml.

	.. code-block:: java
	
		public Object[] getOrgnisationName() {
			try {
				orgnames = (Object[])conn.getClient().call("getOrganisationNames");
			
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return orgnames;
		}
	

* deleteOrganisation method will call xmlrpc_deleteOrganisation from rpc_main.py
	 
* @return True if organisation get delete from gnukhata.xml else False
	
	.. code-block:: java
	
		public Boolean deleteOrgnisationName(Object[] params) {
			try {
				deleteOrg = (Boolean)conn.getClient().call("deleteOrganisation",params);
			
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return deleteOrg;
		}
	

* Below method will call  xmlrpc_getFinancialYear from rpc_main.py

* @param params [organisation name ]
	 
* @return list of financialyear for respective organisation

	.. code-block:: java
	
		public Object[] getFinancialYear(Object params) {
		
			try {
				financialyear = (Object[])conn.getClient().call("getFinancialYear",params);
			
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return financialyear;
		}

* deploy method will call xmlrpc_Deploy from rpc_main.py
	 
* @param params list containing organisation name,from date,to-date,organisation type 
	 
* @return result which will be the list contain boolean true, client_id
	 
	.. code-block:: java
	 
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

* login method will call xmlrpc_getConnection from rpc_main.py.
* @param params list containing organisation name,from date,to-date.
* @return client_id type of integer.

	.. code-block:: java
	
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
	
	

* method getCities it will take params states.
* call getCityNames from core_engine data.py.
* @param params of states
* @return cities depend on parameters.

	.. code-block:: java
	
		public Object[] getCities (Object[] params) {
			try {
				cities = (Object[])conn.getClient().call("data.getCityNames",params);
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
		
			return cities;
		}

* method getStateNames will call getStateNames method from core_engine data.py.
* @return list of states.
	
	.. code-block:: java

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

* getter method for client_id
* @return client_id

	.. code-block:: java
	
		public static Integer getClient_id() {
			return client_id;
		}

* setter for client_id
* @param client_id

	.. code-block:: java
	
		public static void setClient_id(Integer client_id) {
			Startup.client_id = client_id;
		}

	
	
* getter and setter for financialFromDate and financialToDate
	
	.. code-block:: java
	
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




Organisation.java
+++++++++++++++++

* Initialize all essential parameters and variables,
	
	.. code-block:: java

		public class Organisation {
		   
		    private CoreConnection conn;
		    private boolean organisation;
		    private Object[] projects;
		    Object[] Params;
		    private String orgtype;
		    private Object[] getOrganisation;
		    String updateOrg;
    

* Default constructer

	.. code-block:: java
		
		public Organisation() {
			conn = new CoreConnection();
		}
 
* setOrganisation method will call xmlrpc_setOrganisation from rpc_organisation.py
* @param ,client_id
* @return

	.. code-block:: java
	
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
   

* call getAllGroups method from core_engine groups.py
* @param params client id
* @return list contain gropucode , groupname , description
 
 	.. code-block:: java
 	
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
     

* get organisation type by name

	.. code-block:: java
	
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
 
Preferences.java
++++++++++++++++

* Initialize all essential parameters and variables,
	
	.. code-block:: java
	
		public class Preferences {

			private CoreConnection conn;
			private Boolean setPreference;
			private Object[] accCodeParams;
			private Object[] refNoParams;
			private Boolean setProject;
			private String getPreference;
			private boolean setproject;
			private String editproject;
   

* default constructor
* connect with core_engine see CoreConnection

	.. code-block:: java

		public Preferences() {
			conn = new CoreConnection();
		}

* setPreferences method will call xmlrpc_setPreferences from rpc_organisation.py
* @param params[1,reference no,2,account-code flag,project name ]
* @param client_id
* @return boolean True

	.. code-block:: java
	
		public Boolean setPreferences(Object[] params,ArrayList<String> list,Object client_id)
			{
			try {
			   
				accCodeParams = new Object[]{params[2],params[3]};
				setPreference= (Boolean)conn.getClient().call("organisation.setPreferences",accCodeParams,client_id);
				refNoParams = new Object[]{params[0],params[1]};
				setPreference= (Boolean)conn.getClient().call("organisation.setPreferences",refNoParams,client_id);
				System.out.println("list :"+list);
				System.out.println("list :"+list);
				if(list.size()>=1)
				{
				   for(String pname : list)
				   {
				       System.out.println("list :"+ pname);
				       setProject= (Boolean)conn.getClient().call("organisation.setProjects",new Object[]{pname},client_id);
				      
				   }
				}
			   
			} catch (Exception e) {
			    	e.printStackTrace();
			}
			return setPreference;
		}

* getPreferences method will call xmlrpc_getPreferences from rpc_organisation.py
* @param if want account code flag then [2] else [1]
* @param client_id
* @return flag type of String

	.. code-block:: java

		public String getPreferences(Object[] params,Object client_id)
		{
			try {
				getPreference= (String)conn.getClient().call("organisation.getPreferences",params,client_id);
				System.out.println("getPreference"+getPreference);
			} catch (Exception e) {
			     	System.out.println("cant call");
			     	e.printStackTrace();
			}
			return getPreference;
		}
    
* To save project names,
	
	.. code-block:: java
	
		public boolean setProjects(ArrayList<String> params, Object client_id) {
	
			try {
				System.out.println("we are params buddy"+params);
				if(params.size()>=1)
	    			{
				for(String pname : params)
				{
					System.out.println(pname);
					setproject = (Boolean)conn.getClient().call("organisation.setProjects",new Object[]{pname},client_id);
				}
		    	}
			} catch (XMLRPCException e) {		
				e.printStackTrace();
			}
			return setproject;
		}

* To edit project name,

	.. code-block:: java
	
		public String editProject(Object[] params, Object client_id) {

			try {
				editproject = (String)conn.getClient().call("organisation.editProject", params, client_id);
		    
			} catch (XMLRPCException e) {
	
				e.printStackTrace();
			}
			return editproject;
		}

* Below method is used to delete project name.

	.. code-block:: java
	
		public String deleteProjectName(Object[] params, Object client_id) {
	
			try {
				editproject = (String)conn.getClient().call("organisation.deleteProjectName", params, client_id);
		
			} catch (XMLRPCException e) {
		
				e.printStackTrace();
			}
			return editproject;
		}
    

Group.java
++++++++++

* Initialize all essential parameters and variables,
	
	.. code-block:: java
	
		public class Group {

			private CoreConnection conn;
			private Object[] groups;
			private Object[] subgroups;
			private String subgroupname;
			//private Object[] grpparams;
			//private Object[] setaccount;
			//private Object[] setsubgroup;
			//private Object[] accparams;
	

* Default constructor
* create instance of CoreConnection() to get connection with server

	.. code-block:: java
	
		public Group() {
			conn = new CoreConnection();
		}
	

* call getAllGroups method from core_engine groups.py 
* @param params client id
* @return list contain gropucode , groupname , description

	.. code-block:: java

		public Object getAllGroups(Object client_id) {
		
			try {
				groups = (Object[])conn.getClient().call("groups.getAllGroups",client_id);
			
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return groups;
		}

* call getSubGroupsByGroup method from core_engine groups.py 
* @param params list contain groupname
* @param client_id
* @return list of subgroupname

	.. code-block:: java
	
		public Object[] getSubGroupsByGroupName(Object[] params,Object client_id) {
		
			try {
			
				subgroups = (Object[]) conn.getClient().call("groups.getSubGroupsByGroupName",params,client_id);
			
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return subgroups;
		}
	

* call subgroupExists method from core_engine groups.py 
* @param params list contain subgroupname
* @param client_id
* @return 1 if subgroupname already present else return 0

	.. code-block:: java

		public String subgroupExists(Object[] params,Object client_id) {
		
			try {
			
				subgroupname = (String)conn.getClient().call("groups.subgroupExists",params,client_id);
			
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return subgroupname;
		}
	

Account.java
++++++++++++

* Initialize all essential parameters and variables,
	
	.. code-block:: java

		public class Account {
	
			private CoreConnection conn;
			private Object[] accparams;
			private String setaccount;
			private Object getTotalDrBalance;
			private Object getTotalCrBalance;
			private String total_drbal;
			private String total_crbal;
			private Double diff_bal;
			private Object[] allAccountNames;
			private Object[] allAccountCodes;
			private String accountExists;
			private Object code_suggestion_chars;
			private String character;
			private String suggested_code;
			private String accChar;
			private String accountCodeExists;
			private Object[] accountDetails;
			private Object editaccount;
			private Object deleteaccount;
			private Double closing_bal;
			private Object[] getAllBankAccounts;
	

* Default constructor
* create instance of CoreConnection() to get connection with server

	.. code-block:: java

		public Account() {
			conn = new CoreConnection();
		}

* setAccount method to save account call xmlrpc_setAccount from xmlrpc_account.py
* @param params will contain accCodeCheckFlag,selGrpName,selSubGrpName,new subgrp name,account name,account code, and opening balance
* @param client_id
* @return string

	.. code-block:: java

		public String setAccount(Object[] params,Object client_id) {
		
			try {
			
				if("manually".equals(params[0]))
				{
				 	accparams = new Object[]{params[1],params[2],params[3],params[4],params[0],params[6],params[6],params[5]};
				}	
				else{
					accparams = new Object[]{params[1],params[2],params[3],params[4],params[0],params[6],params[6],""};
				}
				
				setaccount = (String)conn.getClient().call("account.setAccount",accparams,client_id);	
			
			
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return setaccount;
		}

* getDrOpeningBalance method to get total of debit opening balances 
* call getDrOpeningBalance xmlrpc function from rpc_acount.py
* @param client_id
* @return getTotalDrBalance 

	.. code-block:: java

		public Object getDrOpeningBalance(Object client_id) {
			try {
				getTotalDrBalance =(Object) conn.getClient().call("account.getDrOpeningBalance",client_id);
			
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return getTotalDrBalance;
		}

* getCrOpeningBalance method to get total of credit opening balances 
* call getCrOpeningBalance xmlrpc function from rpc_acount.py
* @param client_id
* @return getTotalCrBalance

	.. code-block:: java

		public Object getCrOpeningBalance(Object client_id) {
			try {
			
				getTotalCrBalance =(Object) conn.getClient().call("account.getCrOpeningBalance",client_id);
		
				} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
		
			return getTotalCrBalance;
		
		}

* getDiffInBalance method will call getDrOpeningBalance ,getCrOpeningBalance
* @param client_id
* @return difference in total of debit and credit balances

	.. code-block:: java

		public Object getDiffInBalance(Object client_id) {
		
			total_drbal = getDrOpeningBalance(client_id).toString();
		
			total_crbal = getCrOpeningBalance(client_id).toString();
		
			int i = Double.compare(Double.parseDouble(total_drbal),Double.parseDouble(total_crbal));
			// check for greater value
			if(i>0)
		
				diff_bal = Double.parseDouble(total_drbal) - Double.parseDouble(total_crbal);
			else
				diff_bal = Double.parseDouble(total_crbal) - Double.parseDouble(total_drbal);
		
			// will give absolute difference in balance
			diff_bal =  Math.abs(diff_bal);
			return  diff_bal;
		
		}
	

* call getAllAccountNames method from core_engine account.py 
* @param params client id

	.. code-block:: java

		public Object getAllAccountNames(Object client_id) {
		
			try {
				allAccountNames = (Object[])conn.getClient().call("account.getAllAccountNames",client_id);
			
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return allAccountNames;
		}
	
	

* call getAllAccountCodes method from core_engine account.py 
* @param params client id

	.. code-block:: java

		public Object getAllAccountCodes(Object client_id) {
		
			try {
				allAccountCodes = (Object[])conn.getClient().call("account.getAllAccountCodes",client_id);
			
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return allAccountCodes;
		}
	
	

* @param params [account name , accountCodeFlag , groupnameChar]
* @param client_id
* @return

	.. code-block:: java

		public String checkAccountName(Object[] params,Object client_id) {
			try {
				accountExists =(String) conn.getClient().call("account.accountExists",new Object[]{params[0]},client_id);
				if(accountExists.equals("1"))
				{
					return "exist";
				}
				else if(params[1].equals("manually"))
				{
					accChar = params[0].toString().substring(0, 1);
					code_suggestion_chars= (params[2].toString()).concat(accChar);
					suggested_code = (String) conn.getClient().call("account.getSuggestedCode",
							new Object[]{code_suggestion_chars},client_id);
					return suggested_code;
				}
				} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return accountExists;
		}
	
		public String checkAccountCode(Object[] params,Object client_id) {
			try {
		
				accountCodeExists =(String) conn.getClient().call("account.accountCodeExists",new Object[]{params[0]},client_id);
		
				} catch (XMLRPCException e) {
		
				e.printStackTrace();
			}
			return accountCodeExists;
		}
	

* call getAccount method from rpc_account.py
* @param: if search account by account code
				params: 1, accountname

  if search account by account name
 				params: 2, accountname

* @return account detail list: accountcode, groupname, subgroupname, accountname, opening balance

	.. code-block:: java

		public Object getAccount(Object[] params,Object client_id) {
		
			try {
				accountDetails = (Object[])conn.getClient().call("account.getAccount",params,client_id);
			
			} catch (XMLRPCException e) {  
			
				e.printStackTrace(); 
			}
			return accountDetails;
		}
	

* call editAccount method from rpc_account.py
* @param: [newAccountName, accountcode, groupname, 
* newOpeningBalance(for all groupnames except DI,DE, II, IE)] and client_id
* @return closing balance and updates account table

	.. code-block:: java

		public Object editAccount(Object[] params,Object client_id) {
			
			try {
				editaccount = (Object)conn.getClient().call("account.editAccount",params,client_id);

			} catch (XMLRPCException e) {

				e.printStackTrace(); 
			}

			return editaccount;
		}

* To delete account,

	.. code-block:: java
	
		public Object deleteAccount(Object[] params,Object client_id) {
		
			try {
				deleteaccount = (Object)conn.getClient().call("account.deleteAccountNameMaster",params,client_id);
			
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
		
			return deleteaccount;
		}
	
	

* call getAllBankAccounts method from core_engine account.py 
* @param params client id

	.. code-block:: java

		public Object getAllBankAccounts(Object client_id) {
		
			try {
				getAllBankAccounts = (Object[])conn.getClient().call("account.getAllBankAccounts",client_id);
			
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return getAllBankAccounts;
		}


Transaction.java
++++++++++++++++

* Initialize all essential parameters and variables,
	
	.. code-block:: java
	
		package com.gkaakash.controller;
		
		import java.util.ArrayList;
		import org.xmlrpc.android.XMLRPCException;
		import android.R.integer;
		import com.gkaakash.coreconnection.CoreConnection;

		public class Transaction {
			private CoreConnection conn;
			private Integer setVoucher;
			private Object[] contraAccounts;
			private Object[] journalAccounts;
			private Object[] receivableAccounts;
			private Object[] paymentAccounts;
			private Object[] debitNoteAccounts;
			private Object[] creditNoteAccounts;
			private Object[] salesAccounts;
			private Object[] purchaseAccounts;
			private Object[] salesReturnAccounts;
			private Object[] purchaseReturnAccounts;
			private Object[] searchedVouchers;
			private Object[] getVoucherMaster;
			private Object[] getVoucherDetails;
			private Object editVoucher;
			private boolean deleteVoucher;
			private String getLastReference;
			private String getLastReffDate;

* Default constructor
* create instance of CoreConnection() to get connection with server

	.. code-block:: java
 
		public Transaction() {
			conn = new CoreConnection();
		} 
 
* get list of all contra accounts 

	.. code-block:: java

		public Integer setTransaction(Object[] params_master,ArrayList<ArrayList> params_details,Object client_id) {
			try {  
				setVoucher = (Integer)conn.getClient().call("transaction.setTransaction",params_master,params_details,client_id);
				 
			} catch (XMLRPCException e) {
				e.printStackTrace();
			}
			return setVoucher; 
		}
	


* get list of all contra accounts

	.. code-block:: java

		public Object getContraAccounts(Object client_id) { 
	
			try {
				System.out.println("client id is "+client_id);
				contraAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getContraAccounts",client_id);
				System.out.println("contra :"+contraAccounts);
			} catch (XMLRPCException e) {
		
				e.printStackTrace();
			}
			System.out.println("acc so far are...");
			System.out.println(contraAccounts);
			return contraAccounts;
		}



* get list of all jornal accounts
	
	.. code-block:: java

		public Object getJournalAccounts(Object client_id) {
	
			try {
				journalAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getJournalAccounts",client_id);
		
			} catch (XMLRPCException e) {
		
				e.printStackTrace();
			}
			return journalAccounts;
		}



* get list of all receivable accounts

	.. code-block:: java

		public Object getReceivableAccounts(Object[] params,Object client_id) {
	
			try {
				receivableAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getReceivableAccounts",params,client_id);
		
			} catch (XMLRPCException e) {
		
				e.printStackTrace();
			}
			return receivableAccounts;
		}



* get list of all payment accounts

	.. code-block:: java

		public Object getPaymentAccounts(Object[] params,Object client_id) {
	
			try {
				paymentAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getPaymentAccounts",params,client_id);
		
			} catch (XMLRPCException e) {
		
				e.printStackTrace();
			}
			return paymentAccounts;
		}


* get list of all debit note accounts

	.. code-block:: java

		public Object getDebitNoteAccounts(Object[] params,Object client_id) {
	
			try {
				debitNoteAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getDebitNoteAccounts",params,client_id);
		
			} catch (XMLRPCException e) {
		
				e.printStackTrace();
			}
			return debitNoteAccounts;
		}



* get list of all credit note accounts

	.. code-block:: java
	
		public Object getCreditNoteAccounts(Object[] params,Object client_id) {
	
			try {
				creditNoteAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getCreditNoteAccounts",params,client_id);
		
			} catch (XMLRPCException e) {
		
				e.printStackTrace();
			}
			return creditNoteAccounts;
		}



* get list of all sales accounts

	.. code-block:: java

		public Object getSalesAccounts(Object[] params,Object client_id) {
	
			try {
				salesAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getSalesAccounts",params,client_id);
		
			} catch (XMLRPCException e) {
		
				e.printStackTrace();
			}
			return salesAccounts;
		}



* get list of all purchase accounts

	.. code-block:: java
	
		public Object getPurchaseAccounts(Object[] params,Object client_id) {
	
			try {
				purchaseAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getPurchaseAccounts",params,client_id);
		
			} catch (XMLRPCException e) {
		
				e.printStackTrace();
			}
			return purchaseAccounts;
		}



* get list of all sales return accounts

	.. code-block:: java

		public Object getSalesReturnAccounts(Object[] params,Object client_id) {
	
			try {
				salesReturnAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getSalesReturnAccounts",params,client_id);
		
			} catch (XMLRPCException e) {
		
				e.printStackTrace();
			}
			return salesReturnAccounts;
		}

  

* get list of all purchase return accounts
	
	.. code-block:: java

		public Object getPurchaseReturnAccounts(Object[] params,Object client_id) {
	
			try {
				purchaseReturnAccounts = (Object[])conn.getClient().call("getaccountsbyrule.getPurchaseReturnAccounts",params,client_id);
		
			} catch (XMLRPCException e) {
		
				e.printStackTrace();
			}
			return purchaseReturnAccounts;
		}

* Below method is used to get the list of vouchers on the basis of either reference number 
  (which can be duplicate),or date range, or some words from narration.

* This means one or more vouchers could be by the same reference number or within a given date range.
		
* The list thus returned contains all details of a given voucher except its exact transactions, 
  i.e the records from voucher_master.

* returns one or more vouchers given the reference number or date range 
  (which ever specified)takes one parameter queryParams as list.

* input parameters: [searchFlag , refeence_no , from_date , to_date , narration ]
  searchFlag integer (1 implies serch by reference,2 as search by date range and 
  3 as search by narration.
  returns a 2 dimensional list containing one or more records from voucher_master
  
* output:[vouchercode , refeence_no , reffdate,vouchertype,dramount ,cramount ,
  totalamount , narration ]

	.. code-block:: java

		public Object searchVoucher(Object[] params,Object client_id) {
	
			try {
				searchedVouchers = (Object[])conn.getClient().call("transaction.searchVoucher",params,client_id);
		
			} catch (XMLRPCException e) {
		
				e.printStackTrace();
			}
			return searchedVouchers;
		}

* Below method is used while editing or cloning.

* It takes one parameter which is a list containing vouchercode.

* returns a record from the voucher master containing single row data for a given transaction.
		
* Returns list containing data from voucher_master.

* input: [voucher_code]

* output:[reference,reffdate,vouchertype,projectname,narrartion]

	.. code-block:: java

		public Object getVoucherMaster(Object[] params,Object client_id){
			try {
				getVoucherMaster = (Object[])conn.getClient().call("transaction.getVoucherMaster",params,client_id);
		
			} catch (XMLRPCException e) {
		
				e.printStackTrace();
			}
			return getVoucherMaster;
		}

* Below method gets the transaction related details of given vouchercode.

* input: [voucher_code]
  returns  2 dimentional list containing rows with 3 columns.

* output: [accountname,typeflag,amount]

	.. code-block:: java
	
		public Object getVoucherDetails(Object[] params,Object client_id){
			try {
				getVoucherDetails = (Object[])conn.getClient().call("transaction.getVoucherDetails",params,client_id);
			
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return getVoucherDetails;
		}
		
* Below methos is used while editing voucher.  

* ``params_master`` list will contain :
	[vouchercode,reffdate,project name,Narration]
		
* ``params_details`` list will contain :
	[AccountName,dr amount,cr amount]

* returns "success".

	.. code-block:: java
	
		public Object editVoucher(Object[] params_master,ArrayList<ArrayList> params_details,Object client_id) {
			try {  
				editVoucher = (Object)conn.getClient().call("transaction.editVoucher",params_master,params_details,client_id);
				 
			} catch (XMLRPCException e) {
				e.printStackTrace();
			}
			return editVoucher; 
		}
	
	
* Below method will delete voucher depending on given voucher code

* input parameter: [vouchercode]

* output: String "deleted"

	.. code-block:: java
	
		public boolean deleteVoucher(Object[] params,Object client_id) {
			try {
				deleteVoucher = (Boolean)conn.getClient().call("transaction.deleteVoucher",params,client_id);
			
				}catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return deleteVoucher;
		}
	
* Below method used to find the last reference number of selected transaction type.

	.. code-block:: java
	
		public String getLastReferenceNumber(Object[] params,Object client_id) {
			System.out.println("voucherType :"+params[0]);
			try {
				getLastReference = (String)conn.getClient().call("transaction.getLastReference",params,client_id);
			
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return getLastReference;
		}
		
* Below method used to find the last reference date of selected transaction type.

	.. code-block:: java
	
		public String getLastReferenceDate(Object[] params,Object client_id) {
		
			try {
				getLastReffDate = (String)conn.getClient().call("transaction.getLastReffDate",params,client_id);
			
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return getLastReffDate;
		}



Report.java
+++++++++++

* Initialize all essential parameters and variables,
	
	.. code-block:: java
	
		package com.gkaakash.controller;

		import java.util.ArrayList;
		import org.xmlrpc.android.XMLRPCException;
		import org.xmlrpc.android.XMLRPCFault;
		import android.R.string;
		import com.gkaakash.coreconnection.CoreConnection;

		public class Report {
			private CoreConnection conn;
			private Object[] trialResult;
			private Object[] ledgerResult;
			private Object[] grosstrialResult;
			private Object[] extendedtrialResult;
			private Object[] projectStatement;
			private Object[] profitLossStatement;
			private Object[] cashFlowStatement;
			private Object[] getBalancesheetDisplay;
			private Object[] getLedgerForBankRecon;
			private String setBankReconciliationResult;
			private Boolean result;
	

* Default constructor
* create instance of CoreConnection() to get connection with server

	.. code-block:: java
 
		public Report() {
			conn = new CoreConnection();
		}
	
	

* Below method gives a complete ``ledger`` for given account.  

* ``input parameters``: [accountname,financialStart,fromdate,todate,projectname]

* ``output``: Returns a grid (2 dimentional list ) with columns as [Date, Particulars, Reference number, Dr, Cr , vouchercode]
 
 	.. code-block:: java
 	
		public Object getLedger(Object[] params,Object client_id) {
		
			try {
				System.out.println(client_id);
				System.out.println("we are in ledger");
				System.out.println(params);
				ledgerResult = (Object[])conn.getClient().call("reports.getLedger",params,client_id);
				System.out.println(ledgerResult);
			} catch (XMLRPCFault e) {
				try {
					ledgerResult = (Object[])conn.getClient().call("reports.getLedger",params,client_id);
				} catch (XMLRPCException e1) {
				
					e1.printStackTrace();
				}
			}catch (Exception e) {
			
			}
		
			return ledgerResult;
		}
	

* Below method is used to get ``Net Trial Balance`` report

* returns a grid of 4 columns and number of rows depending on number of accounts.
		
* note that trial balance is always calculated from the starting of the financial year.
		
* ``input parameter``: [org_financial_from,from_date,to_date]

* ``output``: [serial no , accountname , groupname , debit bal , creadit bal ] and [total debit , total credit]
 
 	.. code-block:: java
 	
		public Object getTrialBalance(Object[] params,Object client_id) {
		
			try {
				trialResult = (Object[])conn.getClient().call("reports.getTrialBalance",params,client_id);
			
			} catch (XMLRPCFault e) {
				try {
					trialResult = (Object[])conn.getClient().call("reports.getTrialBalance",params,client_id);
				} catch (XMLRPCException e1) {
			
					e1.printStackTrace();
				}
			}
			catch (Exception e) { 
			
				e.printStackTrace();
			} 
			return trialResult;
		}
	
* Below function used to get ``Gross Trial Balance`` report
 
* just like the getTrialBalance, this function return list of balances of all accounts.

* instead of the current balance it provides the total Dr and total Cr for all accounts.

* ``input parameters``: [serial no , accountname , groupname , debit bal , creadit bal] and [total debit , total credit]

 	.. code-block:: java

		public Object getGrossTrialBalance(Object[] params,Object client_id) {
		
			try {
				System.out.println(client_id);
				System.out.println("we are in gross trial");
				System.out.println(params);
				grosstrialResult = (Object[])conn.getClient().call("reports.getGrossTrialBalance",params,client_id);
			
			} catch (XMLRPCFault e) {
				try {
					grosstrialResult = (Object[])conn.getClient().call("reports.getGrossTrialBalance",params,client_id);
				} catch (XMLRPCException e1) {
			
					e1.printStackTrace(); 
				}
			}
			catch (Exception e) { 
			
				e.printStackTrace(); 
			} 
			return grosstrialResult;
		}
	
* Below method is used to get ``Extended Trial Balance`` report as on the given date. 

* Returns a grid of 7 columns and number of rows depending on number of accounts.

 	.. code-block:: java
 	
		public Object getExtendedTrialBalance(Object[] params,Object client_id) {
		
			try {
				System.out.println(client_id);
				System.out.println("we are in extendedtrial");
				System.out.println(params);
				extendedtrialResult = (Object[])conn.getClient().call("reports.getExtendedTrialBalance",params,client_id);
			
			} catch (XMLRPCFault e) {
				try {
					extendedtrialResult = (Object[])conn.getClient().call("reports.getExtendedTrialBalance",params,client_id);
				} catch (XMLRPCException e1) {
			
					e1.printStackTrace(); 
				}
			}
			catch (Exception e) { 
			
				e.printStackTrace();
			} 
			return extendedtrialResult;
		}

	

* Below method is used to get the ``Project Statement`` report.

* ``input parameters``: [projectname,financial_fromdate,fromdate,todate]

* ``output``: list of list [serialno,accountname,groupname,totalDr,totalCr]
 
 	.. code-block:: java
 	
		public Object getProjectStatementReport(Object[] params,Object client_id) {
		
			try {
				projectStatement = (Object[])conn.getClient().call("reports.getProjectStatementReport",params,client_id);
			} catch (XMLRPCException e1) {
		
				e1.printStackTrace();
			}
		
			catch (Exception e) { 
			
				e.printStackTrace();
			} 
			return projectStatement;
		}

* Below method is used to get ``Income and Expenditure/Profit and Loss`` report.

* Returns a grid of 4 columns and number of rows depending on number of accounts.
		
* For profit and loss the accounts from group ``direct income`` and ``indirect income`` and ``expence`` are invoke.
		  
* ``Note``: profit and loss is always calculated from the starting of the financial year.
		
* ``input parameters``: [org_financial_from,from_date,to_date]

* ``output``: List of list [serial no,groupcode,accountname,amount,balancetype]

	.. code-block:: java

		public Object getProfitLossDisplay(Object[] params,Object client_id) {
		
			try { 
				profitLossStatement = (Object[])conn.getClient().call("reports.getProfitLossDisplay",params,client_id);
			} catch (XMLRPCException e1) {
		
				e1.printStackTrace();
			}
		
			catch (Exception e) { 
			
				e.printStackTrace();
			} 
			return profitLossStatement;
		}
	

* Below method is used to get ``Cash Flow`` report.

* returns a grid with 4 columns. 

* First 2 columns will have the account name and its sum of received amount, while next 2 columns will have the same. 
		
* ``input parameters``: [financial_from ,start_date and end_date].

	.. code-block:: java
 
		public Object getCashFlow(Object[] params,Object client_id) {
		
			try { 
				cashFlowStatement = (Object[])conn.getClient().call("reports.getCashFlow",params,client_id);
			} catch (XMLRPCException e1) {
		
				e1.printStackTrace();
			}
		
			catch (Exception e) { 
			
				e.printStackTrace();
			} 
			return cashFlowStatement;
		}

* getBalancesheetDisplay method will give ``Balance Sheet`` report grid with the front-end display sequence 1st coloum , 2nd coloum  and headers values

* We can also call getBalancesheetReport contain values to display. 

* ``input parameters``: list containing ``financialyear_from``, ``report_fromdate``, ``report_todate``, ``report_type``, ``org_type``, ``balancesheet_type``.
  
* @param client_id

* @return list of list containing balance sheet report.

	.. code-block:: java

		public Object getBalancesheetDisplay(Object[] params,Object client_id) {
		
			try {
				getBalancesheetDisplay = (Object[])conn.getClient().call("reports.getBalancesheetDisplay",params,client_id);
				System.out.println("getBalancesheetDisplay :"+getBalancesheetDisplay);
			} 
			catch (XMLRPCException e1) {
		
				e1.printStackTrace();
			}
		
			return getBalancesheetDisplay;
		}
	
	

* Below method is used to get all ``uncleared`` transactions from the starting of financial year to the end date of given period ``OR`` to get all uncleared transactions from the starting of financial year to the end date of given period with all cleared transactions of the given period if ``cleared_tran_flag`` is true with ``Bank Reconciliation Statement`` for the given period of time.

* ``input parameters``: 
	+ list 1: [account name, financial start, fromdate and todate,projectname]
	+ list 2: [cleared_tran_flag]
		
* This function returns a grid of 9 columns and number of rows depending on number of uncleared ``OR`` uncleared+cleared transactions in the database. 
* After appending transactions in grid, it appends Bank Reconciliation statement.

* ``Output``: A grid of 9 columns contains, [vouchercode, transaction date, accountname, reference number,dramount, cramount, narration, clearance date and memo]

	.. code-block:: java

		public Object getLedgerForBankRecon(Object[] params,Object[] flag, Integer client_id) {
			try {
			
				getLedgerForBankRecon = (Object[])conn.getClient().call("reports.updateBankRecon",params,flag, client_id);
			
			} catch (XMLRPCFault e1) {
			
				e1.printStackTrace();
			
			}catch (Exception e) {
			
				e.printStackTrace();
			}
		
			return getLedgerForBankRecon;
		}
	

* ``setBankReconciliation`` method is used to set transaction in bankrecon table which are cleared.

* ``input parameter``: [vouchercode,reffdate,accountname,dramount,cramount,clearencedate,memo]

* ``output`` : String success

	.. code-block:: java
	
		public String setBankReconciliation(ArrayList<ArrayList> list, Integer client_id) {
			try {
				setBankReconciliationResult = (String) conn.getClient().call("reports.setBankReconciliation", list, client_id);
			
			} catch (XMLRPCFault e1) {
			
				e1.printStackTrace();
			
			}catch (Exception e) {
			
				e.printStackTrace();
			}
		
			return setBankReconciliationResult;
		}
	

* Below method is used to ``delete`` cleared transaction entry from ``bankrecon`` table. 

* ``input parameters``: [accountname,vouchercode,todate]

	.. code-block:: java

		public Boolean deleteClearedRecon(ArrayList<String> list, Integer client_id) {
			try {
				result = (Boolean) conn.getClient().call("reports.deleteClearedRecon", list, client_id);
			
			} catch (XMLRPCFault e1) {
			
				e1.printStackTrace();
			
			}catch (Exception e) {
			
				e.printStackTrace();
			}
		
			return result;
		}
	
	

