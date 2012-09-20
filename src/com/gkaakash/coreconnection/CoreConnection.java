package com.gkaakash.coreconnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.xmlrpc.android.XMLRPCClient;
public class CoreConnection {
	
	private static URL url= null;
	private static XMLRPCClient client = null;
	
	public CoreConnection()  {} //default constructor
	
	/***
	 * connection method static so it will be same for all instances of class 
	 * create xml_rpc client 
	 * @return XMLRPCClient client object 
	 */
	
	public static XMLRPCClient Connection(){
		
		try {
			url = new URL("http://10.0.2.2:7081");
			client = new XMLRPCClient(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client;
	
	}
	/***
	 * getter for url , It's a private member
	 * @return url
	 */
	public static URL getUrl() {
		return url;
	}
	/***
	 * setter method , can set url
	 * @param url
	 */
	public static void setUrl(URL url) {
		CoreConnection.url = url;
	}
	/***
	 * getter for XMLRPCClient
	 * @return client object
	 */
	public static XMLRPCClient getClient() {
		return client;
	}
	/***
	 * setter for Client 
	 * @param client
	 */
	public static void setClient(XMLRPCClient client) {
		CoreConnection.client = client;
	}
	
}


