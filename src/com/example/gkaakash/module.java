package com.example.gkaakash;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableRow;
import android.widget.TextView;

import com.gkaakash.controller.Group;
import com.gkaakash.controller.PdfGenaretor;
import com.gkaakash.controller.Startup;
import com.gkaakash.controller.Transaction;
import com.gkaakash.controller.User;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class module {
	static Integer client_id;
	private Transaction transaction;
	static String vouchertypeflag; 
	static Object[] voucherAccounts;
	static List<String> Accountlist;
	static ArrayAdapter<String> dataAdapter;
	static boolean validateflag;
	static AlertDialog dialog ;
	boolean resetFlag = false;
	boolean menu_flag;
	int columnSize;
	FileWriter fw;
	String security_password = null;
	static String IPaddr;
	static String givenfromDateString, givenToDateString;
	static boolean validateDateFlag;
    private Group group;

	
	void getAccountsByRule(Object[] DrCrFlag, String vouchertypeflag2, Context context) {
		IPaddr = MainActivity.IPaddr;
		System.out.println("in createorg"+IPaddr);
		transaction = new Transaction(IPaddr);
		client_id= Startup.getClient_id();
		System.out.println();
		

		vouchertypeflag=vouchertypeflag2;
		if("Contra".equals(vouchertypeflag)){
			voucherAccounts = (Object[]) transaction.getContraAccounts(client_id);
		}
		else if("Journal".equals(vouchertypeflag)){
			voucherAccounts = (Object[]) transaction.getJournalAccounts(client_id);
		}
		else if("Receipt".equals(vouchertypeflag)){

			voucherAccounts = (Object[]) transaction.getReceivableAccounts(DrCrFlag,client_id);
		}
		else if("Payment".equals(vouchertypeflag)){

			voucherAccounts = (Object[]) transaction.getPaymentAccounts(DrCrFlag,client_id);
		}
		else if("Debit Note".equalsIgnoreCase(vouchertypeflag)){

			voucherAccounts = (Object[]) transaction.getDebitNoteAccounts(DrCrFlag,client_id);
		}
		else if("Credit Note".equalsIgnoreCase(vouchertypeflag)){

			voucherAccounts = (Object[]) transaction.getCreditNoteAccounts(DrCrFlag,client_id);
		}
		else if("Sales".equals(vouchertypeflag)){

			voucherAccounts = (Object[]) transaction.getSalesAccounts(DrCrFlag,client_id);
		}
		else if("Purchase".equals(vouchertypeflag)){

			voucherAccounts = (Object[]) transaction.getPurchaseAccounts(DrCrFlag,client_id);
		}
		else if("Sales Return".equalsIgnoreCase(vouchertypeflag)){

			voucherAccounts = (Object[]) transaction.getSalesReturnAccounts(DrCrFlag,client_id);
		}
		else if("Purchase Return".equalsIgnoreCase(vouchertypeflag)){

			voucherAccounts = (Object[]) transaction.getPurchaseReturnAccounts(DrCrFlag,client_id);
		}
		Accountlist = new ArrayList<String>();
		System.out.println("result"+voucherAccounts);
		for(Object ac : voucherAccounts)
		{	
			Accountlist.add((String) ac);
		}

		dataAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, Accountlist);
		//set resource layout of spinner to that adapter
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}
	void toastSuccessfulMessage(Context c,String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
		builder.setMessage(msg);

		AlertDialog alert = builder.create();
		alert.show();
		alert.setCancelable(true);
		alert.setCanceledOnTouchOutside(true);
	}

	public void toastValidationMessage(Context c,String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
		builder.setMessage(msg)
		.setCancelable(false)
		.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	void generate_pdf(final Context c,String[] params,final ArrayList<ArrayList> Grid, final String password){
		PdfGenaretor pdfgen = new PdfGenaretor();
		try {
			
			pdfgen.generatePDFFile(Grid, params, password);
			AlertDialog.Builder builder1 = new AlertDialog.Builder(c);
			builder1.setMessage("PDF exported please see at/mnt/sdcard/"+ params[1]);
			AlertDialog alert1 = builder1.create();
			alert1.show();
			alert1.setCancelable(true);
			alert1.setCanceledOnTouchOutside(true);
		} catch (DocumentException e) {

			e.printStackTrace();
		}
	}


	void generate_pdf1(final Context c,String[] params,final ArrayList<ArrayList> Grid,final ArrayList<ArrayList> Grid1, final String password){
		PdfGenaretor pdfgen = new PdfGenaretor();
		try {
			pdfgen.generateBalancePDFFile(Grid,Grid1,params, password);
			AlertDialog.Builder builder1 = new AlertDialog.Builder(c);
			builder1.setMessage("Pdf exported please see at/mnt/sdcard/"+params[1]);
			AlertDialog alert1 = builder1.create();
			alert1.show();
			alert1.setCancelable(true);
			alert1.setCanceledOnTouchOutside(true);
		} catch (DocumentException e) {
			// TODO Auto-generated catch
			// block
			e.printStackTrace();
		}
	}


	void csv_writer(String[] params,ArrayList<ArrayList> Grid){
		try {

			cerateTitle(params);
			fw.append('\n');columnSize = Grid.get(0).size();
			columnSize = Grid.get(0).size();
			System.out.println(Grid.size());
			for (int i = 0; i < Grid.size(); i++) {
				for (int j = 0; j < Grid.get(i).size(); j++) {
					System.out.println(Grid.get(i).get(j).toString());
					fw.append(Grid.get(i).get(j).toString());
					fw.append(',');
				}
				fw.append('\n');

			}
			fw.append('\n');
			if(!params[0].equalsIgnoreCase("L")&&!params[0].equalsIgnoreCase("cash"))
			{    
				for (int i = 0; i < columnSize-2; i++) {
					fw.append(" ");
					fw.append(',');
				}
				fw.append("Difference in Opening Balances:");
				fw.append(','); 
				fw.append(params[7]);

			}
			fw.flush();
			fw.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
		}   

	} 

	void csv_writer1(String[] params,ArrayList<ArrayList> Grid,ArrayList<ArrayList> Grid1){
		try {
			cerateTitle(params);
			fw.append('\n');

			columnSize = Grid.get(0).size();
			for (int i = 0; i < Grid.size(); i++) {

				for (int j = 0; j < Grid.get(i).size(); j++) {
					if(i==0)
					{
						if((params[0].equals("Conv_bal")&&j==1||j==2||j==3)||
								(params[0].equalsIgnoreCase("cash")&&j==1)||
								(params[0].equalsIgnoreCase("I&E")&&j==2)||
								(params[0].equalsIgnoreCase("Sources_bal")&&j!=0)||
								(params[0].equalsIgnoreCase("P&L")&&j==2))// for the values if the list (first,second and third column)

						{	
							//System.out.println("in rupes");
							fw.append(params[8]+""+Grid.get(i).get(j).toString());
						}else
						{
							//System.out.println("in no rupes");
							fw.append(Grid.get(i).get(j).toString());
						}
					}
					else
					{
						fw.append(Grid.get(i).get(j).toString());
					}
					fw.append(',');
				}
				fw.append('\n');
				fw.append('\n');
			}
			fw.flush();
			fw.append('\n');
			fw.append('\n');
			for (int i = 0; i < Grid1.size(); i++) {
				for (int j = 0; j < Grid1.get(i).size(); j++) {

					if(i==0)
					{
						if((params[0].equals("Conv_bal")&&j==1||j==2||j==3)||
								(params[0].equalsIgnoreCase("cash")&&j==1)||
								(params[0].equalsIgnoreCase("I&E")&&j==2)||
								(params[0].equalsIgnoreCase("Sources_bal")&&j!=0)||
								(params[0].equalsIgnoreCase("P&L")&&j==2))// for the values if the list (first,second and third column)
						{	
							fw.append(params[8]+""+Grid1.get(i).get(j).toString());
						}else    
						{
							fw.append(Grid1.get(i).get(j).toString());
						}
					}
					else   
					{
						fw.append(Grid1.get(i).get(j).toString());
					}
					fw.append(',');
				}
				fw.append('\n');
				fw.append('\n');

			}
			fw.flush();
			fw.append('\n');
			fw.append('\n');
			String[] params1 = params[7].toString().split(":");
			System.out.println("print line "+params1[0]+""+params1[1]);
			for (int i = 0; i < columnSize-2; i++) {
				fw.append(" ");
				fw.append(',');
			}
			fw.append(params1[0]);
			fw.append(',');  
			fw.append(params1[1]);

			fw.flush();

			fw.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
		}
	}
	public static boolean isEmpty(Object[] params)
	{
		for(Object signup : params)
		{
			if(signup.equals(""))
			{
				validateflag = true;
				break;
			}else
			{
				validateflag = false;
			}

		}
		return validateflag;

	}

	static void RunAsRoot(String[] command) {
		// run as a system command
		try {
			Process process = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(process.getOutputStream());
			for (String tmpmd : command){
				os.writeBytes(tmpmd +"\n" );
			}              
			os.writeBytes("exit\n");
			os.flush();

		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public View builder_with_inflater(Context c,String title, int layout1){
		LayoutInflater inflater = ((Activity)c).getLayoutInflater();
		View layout = inflater.inflate(layout1, null);
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				c);
		builder.setView(layout);
		builder.setTitle(title);

		dialog = builder.create();
		dialog.show();
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		//customizing the width and location of the dialog on screen 
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = 800;
		
		dialog.getWindow().setAttributes(lp);
		dialog.setCanceledOnTouchOutside(false);
		return layout;
	}



	void msg(Context c,String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(
				c);

		builder.setMessage(msg);

		AlertDialog alert = builder.create();
		alert.setCancelable(true);
		alert.setCanceledOnTouchOutside(true);
		alert.show();
	}

	void resetPassword(final Context context, final String username, final String user_role, boolean go_to_menu, final Integer client_id){
		final View layout=this.builder_with_inflater(context, "",R.layout.change_password);
		menu_flag = go_to_menu;
		LinearLayout l1=(LinearLayout) layout.findViewById(R.id.changeusername);
		l1.setVisibility(View.GONE);
		Button cancel = (Button) layout.findViewById(R.id.btnCancel);
		TextView header = (TextView) layout.findViewById(R.id.tvheader1);
		header.setText("Please reset your password");

		TextView tvoldpass = (TextView) layout.findViewById(R.id.tvOldPass);
		tvoldpass.setVisibility(View.GONE);

		final EditText oldpass = (EditText) layout.findViewById(R.id.etOldPass);
		oldpass.setVisibility(View.GONE);

		final TextView error_msg = (TextView) layout.findViewById(R.id.tverror_msg1);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(menu_flag){
					dialog.cancel();
				}else{
					error_msg.setVisibility(TextView.VISIBLE);
					error_msg.setText("Please reset your password");
				}

			}
		});

		Button save = (Button) layout
				.findViewById(R.id.btnSave);


		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				EditText newpass = (EditText) layout.findViewById(R.id.etNewPass);
				String new_pass=newpass.getText().toString();
				System.out.println("newpass:"+new_pass);

				EditText confirmpass = (EditText) layout.findViewById(R.id.etconfirmPass);
				String confirm_pass=confirmpass.getText().toString();
				System.out.println("confirm_pass:"+confirm_pass);


				if(!"".equals(new_pass)&!"".equals(confirm_pass)){
					if(new_pass.equals(confirm_pass)){
						// create instance of user class to call setUser method
						IPaddr = MainActivity.IPaddr;
						System.out.println("in createorg"+IPaddr);
						User user = new User(IPaddr);
						System.out.println("we are about to reset"+username+new_pass+user_role);
						Boolean reset= user.resetPassword(new Object[]{username,new_pass,user_role}, client_id);
						System.out.println("r:"+reset);

						if(reset==false){
							error_msg.setVisibility(TextView.VISIBLE);
							error_msg.setText("User not present");
							newpass.setText("");
							confirmpass.setText(""); 
						}else {
							error_msg.setVisibility(TextView.VISIBLE);
							error_msg.setText( "Password updated successully");
							newpass.setText("");
							confirmpass.setText(""); 
							menu_flag = true;
						}
					}else {
						error_msg.setVisibility(TextView.VISIBLE);
						error_msg.setText( "New password and confirm password fields doesnot match!");
						newpass.setText("");
						confirmpass.setText(""); 
					}

				}else {
					error_msg.setVisibility(TextView.VISIBLE);
					error_msg.setText("Fill the empty fields");	
				}
			}
		});
	}
	/***
	 * This function is to create title to each pdf    
	 * @param pdf_params
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	void cerateTitle(String[] params)
	{
		/* Get the /mnt/sdcard */

		Date date= new Date();
		String date_format = new SimpleDateFormat("dMMMyyyy_HHmmss").format(date);
		/* Create new file with sFilename*/ 

		try {
			fw = new FileWriter("/mnt/sdcard/"+params[1]+".csv");
			fw.append("Genrated Date:  "+new SimpleDateFormat("EEE d-MMM-yyyy HH:mm:ss").format(date));
			fw.append(',');
			fw.append("   "+params[2]);
			fw.append(',');
			fw.append(params[3]);  
			fw.append(',');
			fw.append(params[4]);
			fw.append(',');
			fw.append(params[5]);
			fw.append(',');
			fw.append(params[6]);
			fw.append('\n');


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param context
	 * @param inflater
	 * @param layout_id : id of layout to be displayed
	 * @param i: this is flag for 1 grid or 2 grids
	 *        if 0, 1 grid and another
	 * @param pdf_params
	 * @param Grid1: for all reports
	 * @param Grid2: except ledger, trial balance and project statement
	 * @return String security_password and it calls generate_pdf method 
	 *         to generate pdf with or without password
	 */
	public String setPasswordForPdfFile(final Context context, LayoutInflater inflater, 
			int layout_id, final int i, final String[] pdf_params, 
			final ArrayList<ArrayList> Grid1, final ArrayList<ArrayList> Grid2) {


		final View layout = inflater.inflate(layout_id, null);
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(layout);
		builder.setCancelable(true);

		// get the id of signup.xml header
		TextView tvheader = (TextView) layout.findViewById(R.id.tvalertHead1);
		tvheader.setText("File security");

		// get the id of table row of user role and visible it
		TableRow truserrole = (TableRow) layout.findViewById(R.id.trUserRole);
		truserrole.setVisibility(View.VISIBLE);

		// get the id of question row and answer row and invisible it
		TableRow transwer = (TableRow) layout.findViewById(R.id.trAnswer);
		TableRow trquestion = (TableRow) layout.findViewById(R.id.trQuestion);
		TableRow rowGender = (TableRow) layout.findViewById(R.id.rowGender);
		TableRow row_username = (TableRow) layout.findViewById(R.id.row_username);
		transwer.setVisibility(View.GONE);
		trquestion.setVisibility(View.GONE);
		rowGender.setVisibility(View.GONE);
		row_username.setVisibility(View.GONE);
		
		final EditText epassword = (EditText) layout.findViewById(R.id.ePassword);
		final EditText econfpassword = (EditText) layout.findViewById(R.id.eConfPassword);
		

		//get the reference of warning control
		final TextView tvwarning = (TextView) layout.findViewById(R.id.tvWarning);

		//change the value of textview and radio buttons
		final TextView tRole = (TextView) layout.findViewById(R.id.tRole);
		tRole.setText("Do you want to set password for security?");

		final RadioButton rbmanager = (RadioButton) layout.findViewById(R.id.rbManager);
		rbmanager.setText("Yes");

		final RadioButton rbOperator = (RadioButton) layout.findViewById(R.id.rbOperator);
		rbOperator.setText("No");

		final Button btndone = (Button) layout.findViewById(R.id.btnSignUp);
		btndone.setText("Set password and save file");
		
		rbOperator.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(rbOperator.isChecked()){
					TableRow row_password = (TableRow) layout.findViewById(R.id.row_password);
					TableRow row_confirmPassword = (TableRow) layout.findViewById(R.id.row_confirmPassword);
					row_password.setVisibility(View.INVISIBLE);
					row_confirmPassword.setVisibility(View.INVISIBLE);
					btndone.setText("Save file");
					tvwarning.setVisibility(View.GONE);
				}
				
			}
		});
		rbmanager.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(rbmanager.isChecked()){
					TableRow row_password = (TableRow) layout.findViewById(R.id.row_password);
					TableRow row_confirmPassword = (TableRow) layout.findViewById(R.id.row_confirmPassword);
					row_password.setVisibility(View.VISIBLE);
					row_confirmPassword.setVisibility(View.VISIBLE);
					btndone.setText("Set password and save file");
				}
				
			}
		});
		
		
		
		btndone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/* generate PDF file with password security
				 * validate password field
				 * and then call generate_pdf to generate pdf file
				 */
				if(rbmanager.isChecked()){
					String password = epassword.getText().toString();
					String confpassword = econfpassword.getText().toString();
					Object[] params = new Object[] { password, confpassword};
					if (module.isEmpty(params)
							|| module.isEmpty(new Object[] { confpassword })) {
						String message = "please fill blank field";
						tvwarning.setVisibility(TextView.VISIBLE);
						tvwarning.setText(message);

					} else if (!password.equals(confpassword)) {
						epassword.setText("");
						econfpassword.setText("");
						String message = "Please enter correct password";
						tvwarning.setVisibility(TextView.VISIBLE);
						tvwarning.setText(message);
					} else {
						security_password = password;

						//generate PDF file
						if(i == 0){
							generate_pdf(context, pdf_params,Grid1, password);
						}else if(i == 1){
							generate_pdf1(context, pdf_params, Grid1,Grid2, password);
						}

						dialog.dismiss();
					}
				}else{
					/* generate PDF file without password security
					 * pass null parameter if no password
					 */
					if(i == 0){
						System.out.println("in i 0");
						generate_pdf(context, pdf_params,Grid1, null);
					}else if(i == 1){
						System.out.println("in i 1");
						generate_pdf1(context, pdf_params, Grid1,Grid2, null);
					}
					dialog.dismiss();
				}
			}
		});


		Button btncancel = (Button) layout.findViewById(R.id.btnCancel);
		btncancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});

		dialog = builder.create();
		dialog.show();
		return security_password;
	}
	
	public String changeDateFormat(String Date){
		// to get month in words
		SimpleDateFormat read = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat write = new SimpleDateFormat("dd-MMM-yyyy");
		String date = null;
		try {
			System.out.println(Date);
			date = write.format(read.parse(Date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public boolean validateDate(String financialFromDate, String financialToDate, String fromdate, String todate, String flag,TextView tvWarning) {
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date date1 = sdf.parse(financialFromDate);
	    	Date date2 = sdf.parse(financialToDate);
			
	    	Calendar cal1 = Calendar.getInstance(); //financial from date
	    	Calendar cal2 = Calendar.getInstance(); //financial to date
	    	Calendar cal3 = Calendar.getInstance(); //from date
	    	Calendar cal4 = Calendar.getInstance(); //to date
	    	
	    	cal1.setTime(date1);
	    	cal2.setTime(date2); 
	    	System.out.println("flag:"+flag);
	    	
			if("validatebothFromToDate".equals(flag)){
				String FromDay = fromdate.substring(0,2);
//				System.out.println("From datee:"+fromdate);
				String FromMonth = fromdate.substring(3, 5);
//				System.out.println("Datteee1:"+FromMonth);
				String FromYear = fromdate.substring(6,10);
			   	
			   	givenfromDateString = FromDay+"-"+FromMonth+"-"+FromYear;
			   	
			   	Date date3 = sdf.parse(givenfromDateString);
			   	cal3.setTime(date3);
			}
			
			String T0Day = todate.substring(0,2);
			String T0Month = todate.substring(3, 5);
			
			String T0Year = todate.substring(6,10);
		   	
		   	givenToDateString = T0Day+"-"+T0Month+"-"+T0Year ;
		   	
		   	Date date4 = sdf.parse(givenToDateString);
		   	cal4.setTime(date4);  
			
	    	System.out.println("all dates are...........");
	    	System.out.println(fromdate+" "+todate+" "+financialFromDate+"---"+financialToDate+"---"+givenfromDateString+"---"+givenToDateString);
	    	
	    	if("validatebothFromToDate".equals(flag)){
	    		if(((cal3.after(cal1)&&(cal3.before(cal2))) || (cal3.equals(cal1) || (cal3.equals(cal2)))) 
	        			&& ((cal4.after(cal1) && (cal4.before(cal2))) || (cal4.equals(cal2)) || (cal4.equals(cal1)))){
	        		
	        		validateDateFlag = true;
	        	}
	        	else{
	        		String message = "Please enter proper date";
	        		//m.toastValidationMessage(reportMenu.this,message);
	        		tvWarning.setVisibility(View.VISIBLE);
	        		tvWarning.setText(message);
	        		validateDateFlag = false;
//	        		System.out.println("1st else");
	        	}
	    	}
	    	else {
	    		if((cal4.after(cal1) && cal4.before(cal2)) || cal4.equals(cal1) || cal4.equals(cal2) ){
					
	    			validateDateFlag = true;
	        	}
	        	else{
	        		String message = "Please enter proper date";
	        		//m.toastValidationMessage(reportMenu.this,message);
	        		tvWarning.setVisibility(View.VISIBLE);
	        		tvWarning.setText(message);
	        		validateDateFlag = false;
//	        		System.out.println("2nd else");

	        	}
	    	}
    	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return validateDateFlag;
		
	} 
	 
	
	List<String> get_all_groupname(){
		IPaddr = MainActivity.IPaddr;
		group = new Group(IPaddr);
		 Object[] groupnames = (Object[]) group.getAllGroups(client_id);
	        // create new array list of type String to add gropunames
	        List<String> groupnamelist = new ArrayList<String>();
	        // create new array list of type Integer to add gropcode
	        
	        for(Object gs : groupnames)
	        {    
	            Object[] g = (Object[]) gs;
	            groupnamelist.add((String) g[1]); //groupname
	            //groupdesc.add(g[2]); //description
	        }    
	        
	        return groupnamelist;
	}
}