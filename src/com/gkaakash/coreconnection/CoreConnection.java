package com.gkaakash.coreconnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.xmlrpc.android.XMLRPCClient;
import android.os.StrictMode;
public class CoreConnection {
	
	private URL url;
	private XMLRPCClient client; 
	/***
	 * default constructor  
	 * connect to server
	 * create xml_rpc client
	*/
	public CoreConnection()  { 
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy); 
			url = new URL("http://127.0.0.1:7081"); //uncomment on tablet
			//url = new URL("http://10.0.2.2:7081"); // uncomment for emulator  
			client = new XMLRPCClient(url);
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


