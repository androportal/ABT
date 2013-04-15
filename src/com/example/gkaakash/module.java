package com.example.gkaakash;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.ArrayAdapter;

import com.gkaakash.controller.PdfGenaretor;
import com.gkaakash.controller.Startup;
import com.gkaakash.controller.Transaction;
import com.itextpdf.text.DocumentException;

public class module {
	static Integer client_id;
	private Transaction transaction;
	static String vouchertypeflag; 
	static Object[] voucherAccounts;
	static List<String> Accountlist;
	static ArrayAdapter<String> dataAdapter;
	boolean validateflag;
	
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
	 
	void generate_pdf(final Context c,final String[] params,final String sFilename,final ArrayList<ArrayList> Grid){
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
											+ sFilename);
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
	
	
	void generate_pdf1(final Context c,final String[] params,final String sFilename,final ArrayList<ArrayList> Grid,final ArrayList<ArrayList> Grid1){
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
	            			        builder1.setMessage("Pdf genration completed ..see /mnt/sdcard/"+sFilename);
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
	
	
	void csv_writer(ArrayList<ArrayList> Grid,String sFilename){
		try {
			
			FileWriter fw = new FileWriter("/mnt/sdcard/"+sFilename+".csv");

			for (int i = 0; i < Grid.size(); i++) {
				for (int j = 0; j < Grid.get(i).size(); j++) {
					
					fw.append(Grid.get(i).get(j).toString());
					fw.append(',');
				}
				fw.append('\n');
			}
			fw.flush();
			fw.close();

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
		}

	}
	
	void csv_writer1(ArrayList<ArrayList> Grid,ArrayList<ArrayList> Grid1,String sFilename){
		try {
			FileWriter fw = new FileWriter("/mnt/sdcard/"+sFilename+".csv");
			
			for (int i = 0; i < Grid.size(); i++) {
				for (int j = 0; j < Grid.get(i).size(); j++) {
					
					fw.append(Grid.get(i).get(j).toString());
					fw.append(',');
				}
				fw.append('\n');
			}
			fw.flush();

			fw.append('\n');

			for (int i = 0; i < Grid1.size(); i++) {
				for (int j = 0; j < Grid1.get(i).size(); j++) {
					
					fw.append(Grid1.get(i).get(j).toString());
					fw.append(',');
				}
				fw.append('\n');

			}
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
}