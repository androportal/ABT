package com.example.gkaakash;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import android.R.color;
import android.R.integer;
import android.R.string;
import android.annotation.SuppressLint;
import com.gkaakash.controller.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

 
public class createVoucher extends Activity {
	TableLayout list;
	int rowsSoFar = 0;
	String amount;
	AlertDialog dialog;
	final Context context = this;
	TextView voucherDate;
	final List<String> dr_cr=new ArrayList<String>();
	ListView voucher_date,projetct_name;;
	final Calendar c = Calendar.getInstance();
	static int day, month, year;
	static final int VOUCHER_DATE_DIALOG_ID = 1;
	private SimpleAdapter dateAdapter,projectAdapter;
	String vouchertypeflag;
	static Integer client_id;
	private Transaction transaction;
	private Organisation organisation;
	static Object[] voucherAccounts;
	static Integer setVoucher;
	static ArrayAdapter<String> dataAdapter;
	protected String selDrCr;
	Spinner account;
	Spinner actionButton;
	Spinner DrCr;
	Spinner sp1;
	TableRow newRow;
	EditText et;
	TextView tvTotalDebit;
	TextView tvTotalCredit;
	ArrayList<ArrayList> paramsMaster;
	float totalDr;
	float totalCr;
	TextView projectName;
	static String vDate;
	static String vproject;
	DecimalFormat mFormat;
	String financialFromDate;
	String financialToDate;
	EditText firstRowamount;
	private Object diffbal;
	int tableRowCount;
	Float drcrAmountFirstRow;
	String drcramount;
	boolean addRowFlag = true;
	Float amountdrcr;
	
    @Override 
    public void onCreate(Bundle savedInstanceState) {
	       	super.onCreate(savedInstanceState);
	       	setContentView(R.layout.create_voucher);
	       	 
	       	transaction = new Transaction();
	       	organisation = new Organisation();
	       	client_id= Startup.getClient_id();
	       	
	      //for two digit format date for dd and mm
			mFormat= new DecimalFormat("00");
			mFormat.setRoundingMode(RoundingMode.DOWN);
	       	
	       	list = (TableLayout) findViewById( R.id.Vouchertable );
	       	vouchertypeflag =  voucherMenu.vouchertypeflag;
	       	
	       	account = (Spinner) findViewById(R.id.getAccountByRule);
	    	account.setMinimumWidth(283);
	    	
	       	//set first dr/cr row
	    	Object[] params = new Object[]{"Dr"};
			getAccountsByRule(params);
			//set adaptor with account name list in spinner
	    	account.setAdapter(dataAdapter);
	    	
	    	//for setting voucher date
	       	voucher_date =  (ListView)findViewById(R.id.voucher_list);
	       	voucher_date.setTextFilterEnabled(true);
			voucher_date.setCacheColorHint(color.transparent);
	        setVoucherDate();
	        
	        //for setting project 
			projetct_name =  (ListView)findViewById(R.id.voucher_list4);
	       	projetct_name.setTextFilterEnabled(true);
			projetct_name.setCacheColorHint(color.transparent);
			setProject();
	        
			DrCr = (Spinner) findViewById(R.id.sDrCr);
			//tvTotalDebit = (TextView) findViewById( R.id.tvTotalDebit );
			//tvTotalCredit = (TextView) findViewById( R.id.tvTotalCredit );
	        Button addButton = (Button) findViewById( R.id.add );
	        
	        // Every time the "+" button is clicked,
	        // add a new row to the table.
	        addButton.setOnClickListener( new OnClickListener() {
				public void onClick(View view) { 
					testAmountTally();
					if(totalDr == totalCr){
						Toast.makeText(context, "Debit and Credit amount is tally", Toast.LENGTH_SHORT).show();
					}
					else if (totalCr <=0 || totalDr <=0 || drcrAmountFirstRow <= 0 ) {
                        Toast.makeText(context, "No rows can be added,Pleaase fill the existing rows ", Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "row no :", Toast.LENGTH_SHORT).show();
                    }
					else{
						for(int i=0;i<(tableRowCount);i++){
                            View row = list.getChildAt(i);
                           
                            //amount edittext
                            EditText e = (EditText)((ViewGroup) row).getChildAt(5);
                            drcramount = e.getText().toString().trim();
                            amountdrcr = Float.parseFloat(drcramount);
                            
                            System.out.println("amount :"+amountdrcr);
                            if(amountdrcr<=0){
                            	addRowFlag = false;
                                break;
                            }
                            else{
                            	addRowFlag = true;
                            }
                        }
						
						if(addRowFlag == true){
							//add new row
							addButton();
							ArrayAdapter<String> da1 = new ArrayAdapter<String>(createVoucher.this, android.R.layout.simple_spinner_item,dr_cr);
					  	   	da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					        sp1.setAdapter(da1);
					        
					        //set totalDr and totalCr in textview
					        String tvTotalDr = Float.toString(totalDr);
					        //tvTotalDebit.setText("Total Debit: "+tvTotalDr+"0");
					        
					        String tvTotalCr = Float.toString(totalCr);
					        //tvTotalCredit.setText("Total Credit: "+tvTotalCr+"0");
							
					        //set Dr/Cr selected in dropdown according to the condition and set amount in new row
							if(totalDr > totalCr){
								diffbal = totalDr-totalCr;
								et.setText(String.format("%.2f",diffbal ));
								//set 'Cr' selected in Dr/Cr dropdown
								sp1.setSelection(1);
							}
							else{
								diffbal = totalCr-totalDr;
								et.setText(String.format("%.2f",diffbal ));
								//set 'Dr' selected in Dr/Cr dropdown
								sp1.setSelection(0);
							}
							
						}
						else{
							Toast.makeText(context, "No rows can be added,Pleaase fill the existing rows ", Toast.LENGTH_SHORT).show();
						}
						
					}
				}

				
			});     
	        
	        
	        
	        //add all onclick events in this method
	        OnClickListener();
	        
	        //set second dr/cr row
	        setSecondRow();
	        
	        //on dr/cr item selected from dropdown...
	        OnDrCrItemSelectedListenerFirstRow();
    }
	

    public void testAmountTally() {
    	totalDr = 0;
    	totalCr = 0;
    	
		//selected dr/cr and amount of the first row
		String Dr_Cr = DrCr.getSelectedItem().toString();
		
		firstRowamount = (EditText) findViewById(R.id.etDrCrAmount);
		String drcramountFirstRow = firstRowamount.getText().toString();
		drcrAmountFirstRow = Float.parseFloat(drcramountFirstRow);
		
		if("Dr".equals(Dr_Cr)){
			totalDr = totalDr + drcrAmountFirstRow;
		}
		else if("Cr".equals(Dr_Cr)){
			totalCr = totalCr + drcrAmountFirstRow;
		}
		
		//selected dr/cr and amount of the remaining rows
		
		tableRowCount = list.getChildCount();
		
		for(int i=0;i<(tableRowCount);i++){
			View row = list.getChildAt(i);
			//dr cr spinner
			Spinner s = (Spinner)((ViewGroup) row).getChildAt(1);
			String drcr = s.getSelectedItem().toString();
			
			//amount edittext
			EditText e = (EditText)((ViewGroup) row).getChildAt(5);
			drcramount = e.getText().toString();
			float drcrAmount = Float.parseFloat(drcramount);
			
			if("Dr".equals(drcr)){
				totalDr = totalDr + drcrAmount;
			}
			else if("Cr".equals(drcr)){
				totalCr = totalCr + drcrAmount;
			}
		}
		
		System.out.println(totalDr);
		System.out.println(totalCr);
		
		
	}

	private void setSecondRow() {
		// TODO Auto-generated method stub
		
		addButton();
		
		dr_cr.add("Dr");
    	dr_cr.add("Cr");
    	ArrayAdapter<String> da1 = new ArrayAdapter<String>(createVoucher.this, android.R.layout.simple_spinner_item,dr_cr);
  	   	da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(da1);
        sp1.setSelection(1);
		
		Object[] params = new Object[]{"Cr"};
		getAccountsByRule(params);
		//set adaptor with account name list in spinner
    	actionButton.setAdapter(dataAdapter);
	}




	private void OnDrCrItemSelectedListenerFirstRow() {
		// TODO Auto-generated method stub
    		
    		
	        DrCr.setOnItemSelectedListener(new OnItemSelectedListener() {
	
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
				// TODO Auto-generated method stub
				selDrCr = parent.getItemAtPosition(position).toString();
				if(selDrCr != null){
					Object[] params = new Object[]{selDrCr};
					getAccountsByRule(params);
					account.setAdapter(dataAdapter);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// ignore this method!!! :)
			}
		});
	        
	        sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
					// TODO Auto-generated method stub
					String a = parent.getItemAtPosition(position).toString();
					if(a != null){
						Object[] params = new Object[]{a};
						getAccountsByRule(params);
						actionButton.setAdapter(dataAdapter);
					}
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	}
	
	
	private void getAccountsByRule(Object[] DrCrFlag) {
		// TODO Auto-generated method stub
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
		else if("Debit Note".equals(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getDebitNoteAccounts(DrCrFlag,client_id);
		}
		else if("Credit Note".equals(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getCreditNoteAccounts(DrCrFlag,client_id);
		}
		else if("Sales".equals(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getSalesAccounts(DrCrFlag,client_id);
		}
		else if("Purchase".equals(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getPurchaseAccounts(DrCrFlag,client_id);
		}
		else if("Sales Return".equals(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getSalesReturnAccounts(DrCrFlag,client_id);
		}
		else if("Purchase Return".equals(vouchertypeflag)){
			
			voucherAccounts = (Object[]) transaction.getPurchaseReturnAccounts(DrCrFlag,client_id);
		}
		List<String> Accountlist = new ArrayList<String>();
		for(Object ac : voucherAccounts)
		{	
			Accountlist.add((String) ac);
		}	
		dataAdapter = new ArrayAdapter<String>(this,
    			android.R.layout.simple_spinner_item, Accountlist);
    	//set resource layout of spinner to that adapter
    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    
	}

	private void OnClickListener() { 
		// TODO Auto-generated method stub
    	Button btnSaveVoucher = (Button) findViewById( R.id.btnSaveVoucher );
    	btnSaveVoucher.setOnClickListener(new OnClickListener() {

		private String ac;
		private boolean  flag = false;

		@Override
		public void onClick(View v) {
			testAmountTally();
			EditText etRefNumber = (EditText)findViewById(R.id.etRefNumber);
			String refNumber = etRefNumber.getText().toString();
			
			if(totalDr == totalCr && !"".equals(refNumber)){
				if(totalDr == 0){
					Toast.makeText(context, "Please enter amount!", Toast.LENGTH_SHORT).show();
				}
				else{
					//main list
					paramsMaster = new ArrayList<ArrayList>(); 
					 ArrayList<String> accNames = new ArrayList();
					
					//first row
					List<String> paramFirstRow = new ArrayList<String>();
					String fistRowDrCr = DrCr.getSelectedItem().toString();
					
					String fistRowAccountName = account.getSelectedItem().toString();
					accNames.add(fistRowAccountName);
					
					EditText firstRowamount = (EditText) findViewById(R.id.etDrCrAmount);
					String firstRowAmount = firstRowamount.getText().toString();
					
					paramFirstRow.add(fistRowDrCr);
					paramFirstRow.add(fistRowAccountName);
					paramFirstRow.add(firstRowAmount);
					paramsMaster.add((ArrayList<String>) paramFirstRow);
					
					//remaining rows
					int tableRowCount = list.getChildCount();
					
					for(int i=0;i<(tableRowCount);i++){
						List<String> paramRow = new ArrayList<String>();
						
						View row = list.getChildAt(i);
						
						//drcr flag
						Spinner rowDrCr = (Spinner)((ViewGroup) row).getChildAt(1);
						String drcrFlag = rowDrCr.getSelectedItem().toString();
						
						//account name
						Spinner rowAccountName = (Spinner)((ViewGroup) row).getChildAt(3);
						String accountName = rowAccountName.getSelectedItem().toString();
						accNames.add(accountName);
						
						//amount edittext
						EditText etamount = (EditText)((ViewGroup) row).getChildAt(5);
						String rowAmount = etamount.getText().toString();
						System.out.println("amount is :"+ rowAmount);
						
						paramRow.add(drcrFlag);
						paramRow.add(accountName);
						paramRow.add(rowAmount);
						paramsMaster.add((ArrayList<String>) paramRow);
						
					}
					System.out.println("list is :"+ paramsMaster);
					System.out.println(accNames);
					
					for (int i = 0; i < accNames.size(); i++) {
						ac = accNames.get(i);
						System.out.println("accountname :"+ac);
						for (int j = 0; j < accNames.size(); j++)
						{
							if (i!=j)
							{
								System.out.println("next element"+accNames.get(j));
								if(ac.equals(accNames.get(j)))
								{
									System.out.println("equal");
									flag = true;
									break;
								}
								
							}
							else
							{
								flag = false;
								System.out.println("not equal");
							}
							
						}
						
						
					}
					if(flag == false)
					{
						//other voucher details...
						EditText etnarration = (EditText)findViewById(R.id.etVoucherNarration);
						String narration = etnarration.getText().toString();
						
						if("".equals(narration)){
							narration = ""; //need to find solution for null
						}
						System.out.println(narration);
						System.out.println("hi");
						
						
						System.out.println(vDate);
						System.out.println(vproject);
						
						Object[] params_master = new Object[]{refNumber,vDate,vouchertypeflag,vproject,narration};
						setVoucher = (Integer) transaction.setTransaction(params_master,paramsMaster,client_id);
						
						Toast.makeText(context, "Transaction added successfully!", Toast.LENGTH_SHORT).show();
						System.out.println("voucherset");
						
						etRefNumber.setText("");
						etnarration.setText("");
						
						TextView tvproject = (TextView)projetct_name.findViewById(R.id.tvSubItem1);
						tvproject.setText("No Project");
						
						DrCr.setSelection(0); 
						account.setSelection(0);
						firstRowamount.setText("0.00");
						
						actionButton.setSelection(0);
						sp1.setSelection(1);
						et.setText("0.00");
						
						
					}
					else{
						Toast.makeText(context, "Account name can not be repeated, please select another name!", Toast.LENGTH_SHORT).show();
					}
				}
				
			}
			else if(totalDr != totalCr){
				Toast.makeText(context, "Debit and Credit amount is not tally", Toast.LENGTH_SHORT).show();
			}
			else if("".equals(refNumber)){
				Toast.makeText(context, "Please enter voucher reference number", Toast.LENGTH_SHORT).show();
			}
		}
		}); 
	}

	private void setProject() {
		// TODO Auto-generated method stub
    	
		String[] abc = new String[] {"rowid", "col_1"};
		int[] pqr = new int[] { R.id.tvRowTitle1, R.id.tvSubItem1};
	
		List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("rowid", "" + "Select project");
		map.put("col_1", "" + "No Project");
		fillMaps.add(map);
		
		projectAdapter = new SimpleAdapter(this, fillMaps, R.layout.child_row1, abc, pqr);
		projetct_name.setAdapter(projectAdapter);
		
		vproject = "No Project";
		
		projetct_name.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				projetct_name.setCacheColorHint(color.transparent);
				if(position == 0){
					projectName = (TextView)view.findViewById(R.id.tvSubItem1);
					
					//call the getAllProjects method to get all projects
					Object[] projectnames = (Object[]) organisation.getAllProjects(client_id);
					// create new array list of type String to add gropunames
					List<String> projectnamelist = new ArrayList<String>();
					projectnamelist.add("No Project");
					for(Object pn : projectnames)
					{	
						Object[] p = (Object[]) pn;
						projectnamelist.add((String) p[1]); //p[0] is project code & p[1] is projectname
					}	
					
					System.out.println(projectnamelist);
					
					/*
					 * 'builder.setItems' takes Charsequence Array as a parameter, 
					we have to convert List<Address> to List<String> and 
					then use list.toarray() 
					*/
 
					final CharSequence[] allProjectNames = projectnamelist.toArray(new String[0]);
					System.out.println(allProjectNames);
					
					
					//creating a dialog box for popup
			        AlertDialog.Builder builder = new AlertDialog.Builder(context);
			        //setting title
			        builder.setTitle("Select project");
			        //adding allProjectNames
			        builder.setItems(allProjectNames, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int pos) {
							// set project name in project field
							projectName.setText(allProjectNames[pos]);
							vproject = allProjectNames[pos].toString();
						}
					});
			        //building a complete dialog
					dialog=builder.create();
					dialog.show();
					
				}
			}
			
		});
	}

	

	private void setVoucherDate() {
		// TODO Auto-generated method stub
		financialFromDate =Startup.getfinancialFromDate();
		String dateParts[] = financialFromDate.split("-");
	   	String fromday  = dateParts[0];
	   	String frommonth = dateParts[1];
	   	String fromyear = dateParts[2];
	   	financialToDate = Startup.getFinancialToDate();
	   	
	   	
    	year = Integer.parseInt(fromyear);
		month = Integer.parseInt(frommonth);
		day = Integer.parseInt(fromday);
		
		String[] abc = new String[] {"rowid", "col_1"};
		int[] pqr = new int[] { R.id.tvRowTitle1, R.id.tvSubItem1};
	
		List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("rowid", "" + "Voucher Date");
		map.put("col_1", "" + mFormat.format(Double.valueOf(day))+"-"+mFormat.format(Double.valueOf(month))+"-"+year);
		fillMaps.add(map);
		
		dateAdapter = new SimpleAdapter(this, fillMaps, R.layout.child_row1, abc, pqr);
		voucher_date.setAdapter(dateAdapter);
		
		vDate = mFormat.format(Double.valueOf(day))+"-"+mFormat.format(Double.valueOf(month))+"-"+year;
		System.out.println("date are...");
		System.out.println(day+"-"+month+"-"+year);
		voucher_date.setOnItemClickListener(new OnItemClickListener() {

			
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				voucher_date.setCacheColorHint(color.transparent);
					
				if(position == 0)
				{
					showDialog(VOUCHER_DATE_DIALOG_ID);
				}	
			}
		});
	}
    
    @Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case VOUCHER_DATE_DIALOG_ID:
			// set 'from date' date picker as current date
			System.out.println(day+"-"+month+"-"+year);
			   return new DatePickerDialog(this, fromdatePickerListener, 
	                         year, month-1,day);
		}
		return null;
	}
	
	private DatePickerDialog.OnDateSetListener fromdatePickerListener 
    = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
			int selectedMonth, int selectedDay) {
		year = selectedYear;
		month = selectedMonth;
		day = selectedDay;
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        	Date date1 = sdf.parse(financialFromDate);
        	Date date2 = sdf.parse(financialToDate);
        	Date date3 = sdf.parse(mFormat.format(Double.valueOf(day))+"-"
        					+mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(month))))+ 1))+"-"
        					+mFormat.format(Double.valueOf(year)));
        	Calendar cal1 = Calendar.getInstance(); //financial from date
        	Calendar cal2 = Calendar.getInstance(); //financial to date
        	Calendar cal3 = Calendar.getInstance(); //voucher date
        	
        	//24-10-2012 23-10-2013 23-10-2012

        	
        	cal1.setTime(date1);
        	cal2.setTime(date2);
        	cal3.setTime(date3);
        	
        	System.out.println(financialFromDate+financialToDate+mFormat.format(Double.valueOf(day))+"-"
        					+mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(month))))+ 1))+"-"
        					+mFormat.format(Double.valueOf(year)));
        	
        	
        	if((cal3.after(cal1) && cal3.before(cal2)) || cal3.equals(cal1) || cal3.equals(cal2)){
        		voucherDate =  (TextView)findViewById(R.id.tvSubItem1); 
        		
        		// set selected date into textview
        		voucherDate.setText(new StringBuilder()
        		.append(mFormat.format(Double.valueOf(day))).append("-")
        		.append(mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(month))))+ 1)))
        		.append("-").append(year));	
        		vDate = mFormat.format(Double.valueOf(day))+"-"
        				+(mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(month))))+ 1)))+"-"
        				+year;
        	}
        	else{
        		Toast.makeText(context, "please enter proper voucher date!", Toast.LENGTH_SHORT).show();
        	}
        	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		}
};

	/***
     * Gets all the information necessary to delete itself from the constructor.
     * Deletes itself when the button is pressed.
     */
    private static class RowRemover implements OnClickListener {
    	private TableLayout list;
    	private TableRow rowToBeRemoved;
    	
    	/***
    	 * @param list	The list that the button belongs to
    	 * @param row	The row that the button belongs to
    	 */
    	public RowRemover( TableLayout list, TableRow row ) {
    		this.list = list;
    		this.rowToBeRemoved = row;
    	}
    	
    	public void onClick( View view ) {
    		int tableRowCount = list.getChildCount();
            System.out.println("total Child " +tableRowCount);
            if (tableRowCount == 1){
                //Toast.makeText(context, "There should be atleast two rows ", Toast.LENGTH_SHORT).show();
            }else{
                list.removeView( rowToBeRemoved );
            }
    	}
    }
    
    public void addButton() {
    	newRow = new TableRow( list.getContext() );
    	newRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
    	//newRow.addView(child, width, height)
    	
    	TextView tvac = new TextView(newRow.getContext());
      	tvac.setText( "Account Type " );
      	tvac.setTextSize(16); //*****
      	tvac.setTextColor(Color.WHITE);
      	
      	sp1 = new Spinner( newRow.getContext() );
        
        
    	TextView tv = new TextView(newRow.getContext());
    	tv.setText("        Account Name");
    	tv.setTextSize(16); //for emulator 14
    	tv.setTextColor(Color.WHITE);
    	
    	actionButton = new Spinner( newRow.getContext() );
        actionButton.setMinimumWidth(259); //for emulator keep 283
        
        
        OnDrCrItemSelectedListenerFirstRow();
        
        TextView tv1 = new TextView(newRow.getContext());
    	tv1.setText( "        Amount" );
    	tv1.setTextSize(16); //****
    	tv1.setTextColor(Color.WHITE);
    	
    	//tv1.setWidth(100);
    	et = new EditText(newRow.getContext());
    	et.setText( "0.00" );
    	et.setWidth(100); //for emulator 80
    	et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    	
    	//actionButton.setText( "Action: " + ++rowsSoFar );
    	Button removeSelfButton = new Button( newRow.getContext() );
    	removeSelfButton.setText( "   -   " ); //for tablet ***** add  space
    	
    	
    	//removeSelfButton.setBackgroundResource(R.drawable.button_plus_green);
    	//removeSelfButton.setBackgroundColor(color)
    	// pass on all the information necessary for deletion
    	removeSelfButton.setOnClickListener( new RowRemover( list, newRow ));
    	newRow.addView(tvac);
    	newRow.addView(sp1);
    	newRow.addView(tv);
    	newRow.addView( actionButton );
    	newRow.addView(tv1);
    	newRow.addView(et);
    	newRow.addView( removeSelfButton );
    	list.addView(newRow);
    	
    }
}