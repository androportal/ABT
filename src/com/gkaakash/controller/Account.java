package com.gkaakash.controller;

import java.text.DecimalFormat;

import org.xmlrpc.android.XMLRPCException;

import com.gkaakash.coreconnection.CoreConnection;

public class Account {
	
	private static CoreConnection conn;
	private static Object[] accparams;
	private static Object getaccountname;
	private static String setaccount;
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
	private static String accChar;
	private String accountCodeExists;
	private Object[] accountDetails;
	private Object editaccount;
	private Object deleteaccount,deleteAccountNameMaster;
	private Double closing_bal;
	private Object[] getAllBankAccounts;
	
	/***
	 * Default constructor
	 * create instance of CoreConnection() to get connection with server
	 */
	public Account(String ip) {
		conn = new CoreConnection(ip);
		
	}
	/***
	 * setAccount method to save account call xmlrpc_setAccount from xmlrpc_account.py
	 * @param params will contain accCodeCheckFlag,selGrpName,selSubGrpName,new subgroup name,account name,account code, and opening balance
	 * @param client_id
	 * @return string
	 */
	public String setAccount(Object[] params,Object client_id) {
		
		try {      
			
			if("manually".equals(params[0]))
			{
			 	accparams = new Object[]{params[1],params[2],params[3],params[4],params[0],params[6],params[5]};
			}	
			else{
				accparams = new Object[]{params[1],params[2],params[3],params[4],params[0],params[6],""};
			}
			setaccount = (String)conn.getClient().call("account.setAccount",accparams,client_id);	
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return setaccount;
	}
	/***
	 * getDrOpeningBalance method to get total of debit opening balances 
	 * call getDrOpeningBalance xmlrpc function from rpc_acount.py
	 * @param client_id
	 * @return getTotalDrBalance 
	 */
	public Object getDrOpeningBalance(Object client_id) {
		try {
			
			getTotalDrBalance =(Object) conn.getClient().call("account.getDrOpeningBalance",client_id);
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return getTotalDrBalance;
		}
	/***
	 * getCrOpeningBalance method to get total of credit opening balances 
	 * call getCrOpeningBalance xmlrpc function from rpc_acount.py
	 * @param client_id
	 * @return getTotalCrBalance
	 */
	public Object getCrOpeningBalance(Object client_id) {
		try {
			
			getTotalCrBalance =(Object) conn.getClient().call("account.getCrOpeningBalance",client_id);
		
			} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		
		return getTotalCrBalance;
		
	}
	/***
	 * getDiffInBalance method will call getDrOpeningBalance ,getCrOpeningBalance
	 * @param client_id
	 * @return difference in total of debit and credit balances
	 */
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
	
	/***
	 * call getAllAccountNames method from core_engine account.py 
	 * @param params client id
	 */
	public Object getAllAccountNames(Object client_id) {
		
		try {
			allAccountNames = (Object[])conn.getClient().call("account.getAllAccountNames",client_id);
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return allAccountNames;
	}
	
	
	/***
	 * call getAllAccountCodes method from core_engine account.py 
	 * @param params client id
	 */
	public Object getAllAccountCodes(Object client_id) {
		
		try {
			allAccountCodes = (Object[])conn.getClient().call("account.getAllAccountCodes",client_id);
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return allAccountCodes;
	}
	
	
	/***
	 * 
	 * @param params [account name , accountCodeFlag , groupnameChar]
	 * @param client_id
	 * @return
	 */
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
	
	/*
	 * call getAccount method from rpc_account.py
	 * @param: if search account by account code
	 * 				params: 1, accountname
	 * 
	 * 		   if search account by account name
	 * 				params: 2, accountname
	 * 
	 * @return account detail list: accountcode, groupname, subgroupname,
	 * accountname, opening balance
	 */
	public Object getAccount(Object[] params,Object client_id) {
		
		try {
			accountDetails = (Object[])conn.getClient().call("account.getAccount",params,client_id);
			
		} catch (XMLRPCException e) {  
			
			e.printStackTrace(); 
		}
		return accountDetails;
	}
	
	/*
	 * call editAccount method from rpc_account.py
	 * @param: [newAccountName, accountcode, groupname, 
	 * newOpeningBalance(for all groupnames except DI,DE, II, IE)] and client_id
	 * @return closing balance and updates account table
	 */
	public Object editAccount(Object[] params,Object client_id) {
			
			try {
				editaccount = (Object)conn.getClient().call("account.editAccount",params,client_id);
				
			} catch (XMLRPCException e) {
				
				e.printStackTrace(); 
			}
			
			return editaccount;
		}
	
	public Object deleteAccountNameMaster(Object[] params,Object client_id) {
		
		try {
			deleteAccountNameMaster = (Object)conn.getClient().call("account.deleteAccountNameMaster",params,client_id);
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		
		return deleteAccountNameMaster;
	}
	
	
public Object deleteAccount(Object[] params,Object client_id) {
		
		try {
			deleteaccount = (Object)conn.getClient().call("account.deleteAccount",params,client_id);
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		
		return deleteaccount;
	}
	
	
	/***
	 * call getAllBankAccounts method from core_engine account.py 
	 * @param params client id
	 */
	public Object getAllBankAccounts(Object client_id) {
		
		try {
			getAllBankAccounts = (Object[])conn.getClient().call("account.getAllBankAccounts",client_id);
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return getAllBankAccounts;
	}
	
	/***
	 * call getAccountNameByAccountCode method from core_engine account.py
	 * @param account code, client id
	 * returns accountname
	 */
	public static Object getAccountNameByAccountCode(Object[] accname, Object client_id) {
		
		try {
			getaccountname = conn.getClient().call("account.getAccountNameByAccountCode",accname,client_id);
			
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return getaccountname;
	}
	
	
	
}