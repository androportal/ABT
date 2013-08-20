/**
 *
 */
package com.gkaakash.controller;

import java.util.ArrayList;
import java.util.List;

import org.xmlrpc.android.XMLRPCException;

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
    private Object[] getPreference;
    private boolean setproject;
    private String editproject;
   
    /***
     * default constructor
     * connect with core_engine see CoreConnection
     */
    public Preferences(String ip) {
        conn = new CoreConnection(ip);
    }
    /***
     * setPreferences method will call xmlrpc_setPreferences from rpc_organisation.py
     * @param params[flagno,flagname]
     * @param client_id
     * @return boolean True
     */
    public Boolean setPreferences(Object[] params,Object client_id)
    {
        try {
        	setPreference= (Boolean)conn.getClient().call("organisation.setPreferences",params,client_id);
	      
        } catch (Exception e) {
            e.printStackTrace();
        }
        return setPreference;
       
    }
    /***
     * getPreferences method will call xmlrpc_getPreferences from rpc_organisation.py
     * @param if want account code flag then [1]
     * @param client_id
     * @return flag type of String
     */
    public Object[] getPreferences(Object[] params,Object client_id)
    {
        try {
         
            getPreference= (Object[])conn.getClient().call("organisation.getPreferences",params,client_id);
          
            } catch (Exception e) {
                System.out.println("cant call");
             e.printStackTrace();
        }
        return getPreference;
       
    }
    
    public boolean setProjects(Object[] params, Object client_id) {
		
		try {   setproject = (Boolean)conn.getClient().call("organisation.setProjects",params,client_id);

		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return setproject;
	}
   
    public String editProject(Object[] params, Object client_id) {
		
		try {
			editproject = (String)conn.getClient().call("organisation.editProject", params, client_id);
            
		} catch (XMLRPCException e) {
			
			e.printStackTrace();
		}
		return editproject;
	}


public String deleteProjectName(Object[] params, Object client_id) {
	
	try {
		editproject = (String)conn.getClient().call("organisation.deleteProjectName", params, client_id);
        
	} catch (XMLRPCException e) {
		
		e.printStackTrace();
	}
	return editproject;
}
    
}