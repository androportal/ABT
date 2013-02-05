/**
 * 
 */
package com.gkaakash.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ashwini
 *
 */
public class DateClass {

	private static SimpleDateFormat dateformat ;
	
	static{
		
		dateformat = new SimpleDateFormat("yyyy-MM-dd");
		
	}
	public static Date strToDate(String date)
	{
		Date returndate=null;
		
		try {
			
			returndate = dateformat.parse(date);
			
		}
		catch (ParseException e) {
			
			e.printStackTrace();
		}
		return returndate;
	}
	public static SimpleDateFormat getDateformat() {
		
		return dateformat;
	}
	public static void setDateformat(SimpleDateFormat dateformat) {
		
		DateClass.dateformat = dateformat;
	}
	
	/*public static void main(String arg[])
	{ 
		String from_date = "2012-10-20";
		
		System.out.println(DateClass.strToDate(from_date));
	}*/
}

