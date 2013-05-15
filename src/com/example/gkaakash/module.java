package com.example.gkaakash;

import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gkaakash.controller.PdfGenaretor;
import com.gkaakash.controller.Startup;
import com.gkaakash.controller.Transaction;
import com.gkaakash.controller.User;
import com.itextpdf.text.DocumentException;

public class module {
	static Integer client_id;
	private Transaction transaction;
	static String vouchertypeflag; 
	static Object[] voucherAccounts;
	static List<String> Accountlist;
	static ArrayAdapter<String> dataAdapter;
	boolean validateflag;
	static AlertDialog dialog ;
	boolean resetFlag = false;
	boolean menu_flag;
	int columnSize;
	FileWriter fw;
	void getAccountsByRule(Object[] DrCrFlag, String vouchertypeflag2, Context context) {
		transaction = new Transaction();
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
		
		dataAdapter = new ArrayAdapter<String>(context,
    			android.R.layout.simple_spinner_item, Accountlist);
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

	 void toastValidationMessage(Context c,String msg) {
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
	 
	void generate_pdf(final Context c,final String[] params,final ArrayList<ArrayList> Grid){
		AlertDialog.Builder builder = new AlertDialog.Builder(
				c);
		builder.setMessage("Do you want to create PDF")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialog, int id) {
								PdfGenaretor pdfgen = new PdfGenaretor();
								try {
									pdfgen.generatePDFFile(
											Grid, params);
									AlertDialog.Builder builder1 = new AlertDialog.Builder(
											c);
									builder1.setMessage("PDF genration completed ..see /mnt/sdcard/"
											+ params[1]);
									AlertDialog alert1 = builder1
											.create();
									alert1.show();
									alert1.setCancelable(true);
									alert1.setCanceledOnTouchOutside(true);
								} catch (DocumentException e) {
									
									e.printStackTrace();
								}
							}
						})
				.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialog, int id) {

							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	
	void generate_pdf1(final Context c,final String[] params,final ArrayList<ArrayList> Grid,final ArrayList<ArrayList> Grid1){
		AlertDialog.Builder builder = new AlertDialog.Builder(
				c);
		builder.setMessage("Do you want to create PDF")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialog, int id) {
								PdfGenaretor pdfgen = new PdfGenaretor();
								try {
									pdfgen.generateBalancePDFFile(Grid,Grid1,params);
	            			        AlertDialog.Builder builder1 = new AlertDialog.Builder(c);
	            			        builder1.setMessage("Pdf genration completed ..see /mnt/sdcard/"+params[1]);
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
						})
				.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialog, int id) {

							}
						});
		AlertDialog alert = builder.create();
		alert.show();
		
	}
	
	
	void csv_writer(String[] params,ArrayList<ArrayList> Grid){
		try {
			
			cerateTitle(params);
			fw.append('\n');columnSize = Grid.get(0).size();
			columnSize = Grid.get(0).size();
			for (int i = 0; i < Grid.size(); i++) {
				for (int j = 0; j < Grid.get(i).size(); j++) {
					
					fw.append(Grid.get(i).get(j).toString());
					fw.append(',');
				}
				fw.append('\n');
				
			}
			fw.append('\n');
			if(!params[0].equalsIgnoreCase("L"))
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
	public boolean isEmpty(Object[] params)
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
		Button cancel = (Button) layout
				.findViewById(R.id.btnCancel);
		TextView header = (TextView) layout.findViewById(R.id.tvheader1);
		header.setText("Please reset your password");
		
		TextView tvoldpass = (TextView) layout.findViewById(R.id.tvOldPass);
		tvoldpass.setVisibility(View.GONE);
		
		final EditText oldpass = (EditText) layout
				.findViewById(R.id.etOldPass);
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
			
			EditText newpass = (EditText) layout
					.findViewById(R.id.etNewPass);
			String new_pass=newpass.getText().toString();
			System.out.println("newpass:"+new_pass);
			
			EditText confirmpass = (EditText) layout
					.findViewById(R.id.etconfirmPass);
			String confirm_pass=confirmpass.getText().toString();
			System.out.println("confirm_pass:"+confirm_pass);
			
			
			if(!"".equals(new_pass)&!"".equals(confirm_pass)){
				if(new_pass.equals(confirm_pass)){
					// create instance of user class to call setUser method
					User user = new User();
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
       
}