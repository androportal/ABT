package com.gkaakash.coreconnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.xmlrpc.android.XMLRPCClient;
import android.os.StrictMode;
public class CoreConnection {
	
	private URL url;
	private XMLRPCClient client; 
	
	private static String Port = "7081";
	/***
	 * default constructor         
	 * connect to server
	 * create xml_rpc client
	*/   
	public CoreConnection(String IPaddress)  {    
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			
			System.out.println("ip address:"+IPaddress);
			System.out.println("port:"+Port);
			String link = "http://"+IPaddress+":"+Port;
			System.out.println(link);
			url = new URL(link); 
			
			client = new XMLRPCClient(url);
			System.out.println("client"+client);
		} catch (MalformedURLException e) {   
			  
			  
			e.printStackTrace();
		}  
	} //default constructor
	/***
	 * getter for url , It's a private member
	 * @return url
	 */
	public URL getUrl() {
		return url;
	}
	/***
	 * getter for XMLRPCClient
	 * @return client object
	 */
	
	public XMLRPCClient getClient() {
		return client;
	}
	/***
	 * setter for XMLRPCClient 
	 * @param client
	 */
	public void setClient(XMLRPCClient client) {
		this.client = client;
	}
	/***
	 * setter method , can set url
	 * @param url
	 */
	public void setUrl(URL url) {
		this.url = url;
	}
	 
}


