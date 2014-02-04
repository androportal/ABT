package com.example.gkaakash;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gkaakash.controller.Organisation;
import com.gkaakash.controller.Report;
import com.gkaakash.controller.Startup;
import com.gkaakash.controller.Transaction;

public class createVoucher extends Activity {
	TableLayout table;
	int rowsSoFar = 0;
	static int tableRowCount;
	String amount, financialFromDate, financialToDate, drcramount;
	static String vouchertypeflag;
	AlertDialog dialog;
	String vouchernoExist, chequenoExist;
	final Context context = this;
	TextView tvTotalDebit, tvTotalCredit, projectName, cheque, tvpaymentmode;
	final List<String> dr_cr = new ArrayList<String>();
	final Calendar c = Calendar.getInstance();
	static int day, month, year;
	static final int VOUCHER_DATE_DIALOG_ID = 1;
	private SimpleAdapter dateAdapter, projectAdapter;
	static Integer client_id;
	private Transaction transaction;
	private Organisation organisation;
	static Object[] voucherAccounts;
	static Integer setVoucher;
	static Integer editVoucher;
	static ArrayAdapter<String> dataAdapter;
	protected String selDrCr;
	Spinner second_table_accountname_spinner, second_table_drcr_spinner;
	public static TableRow newRow;
	ArrayList<ArrayList> paramsMaster;
	float totalDr, totalCr;
	static String vDate, vproject;
	DecimalFormat mFormat;
	EditText etnarration, second_table_amount_et, second_table_closingbal_et;
	static EditText etRefNumber;
	private Object diffbal;
	Float drcrAmount, amountdrcr;
	boolean addRowFlag = true;
	List<String> accnames = new ArrayList<String>();
	List<String> DrAccountlist = new ArrayList<String>();
	List<String> CrAccountlist = new ArrayList<String>();
	static Boolean searchFlag;
	ArrayList otherdetailsrow;
	ArrayAdapter<String> da1;
	String proj, searchdate;
	static ArrayList<String> accdetails;
	static ArrayList<ArrayList<String>> accdetailsList;
	String Fsecond_spinner, Ssecond_spinner, Sacctype, Facctype;
	static int FaccnamePosition, SaccnamePosition, SacctypePosition, FacctypePosition;
	String vouchercode, voucherno;
	static Boolean cloneflag;
	boolean nameflag;
	static boolean edittabflag;
	String name, seletecd_val;
	static EditText e, e1;
	private Report reports;
	boolean voucher_code_flag;
	String from_report_flag;
	module m;
	Button removeSelfButton;
	Button btnResetVoucher;
	Button btnSaveVoucher;
	String from_trial, cheque_number;
	private EditText etvoucherno;
	static String IPaddr, checkvoucher_number;
	Button btnVoucherDate;
	Spinner sProjectNames;
	private TableRow rowToBeRemoved;
	EditText amount_first, amount_sec, closing_first, etcheque;
	Spinner first_account_name, first_dr_cr, second_dr_cr, second_account_name;
	View row123, row1;
	Spinner change_voucher_type;
	Boolean touch = false;
	Boolean add = false;
	RadioGroup rg;
	RadioButton cheque_, cash_;
	String flag_for_rollover;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_voucher);
		IPaddr = MainActivity.IPaddr;
		System.out.println("in create voucher" + IPaddr);
		transaction = new Transaction(IPaddr);
		organisation = new Organisation(IPaddr);
		m = new module();
		reports = new Report(IPaddr);
		client_id = Startup.getClient_id();
		
		table = (TableLayout) findViewById(R.id.Vouchertable);
		row1 = table.getChildAt(1);

		change_voucher_type = (Spinner) findViewById(R.id.sVouchertypes);
		btnSaveVoucher = (Button) findViewById(R.id.btnSaveVoucher);
		btnResetVoucher = (Button) findViewById(R.id.btnResetVoucher);

		cheque = (TextView) findViewById(R.id.tvcheque);
		tvpaymentmode = (TextView) findViewById(R.id.tvpaymentmode);
		etcheque = (EditText) findViewById(R.id.etcheque);
		cheque.setVisibility(TextView.GONE);
		etcheque.setVisibility(EditText.GONE);
		cheque_ = (RadioButton) findViewById(R.id.rbcheque);
		cash_ = (RadioButton) findViewById(R.id.rbcash);
		etvoucherno = (EditText) findViewById(R.id.etVoucherNumber);

		if (cash_.isChecked() == true) {
			etcheque.setText(""); // if cash radio is checked then set cheque number blank
		}

		rg = (RadioGroup) findViewById(R.id.radioRole);
		setRadioOnCheckListener(); // set listener on cheque and cash radio
		searchFlag = MainActivity.searchFlag;

		
		/***
		 * get extra values from other class
		 */
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			
		} else {
			from_report_flag = extras.getString("flag");
			
			flag_for_rollover= extras.getString("flag_for_rollover");
		}
	
		if (from_report_flag == null) {
				vouchertypeflag = transaction_tab.vouchertypeflag;
		} else if (from_report_flag.equalsIgnoreCase("from_ledger")) {
			
			vouchertypeflag = ledger.vouchertypeflag;
			from_trial = ledger.get_extra_flag;
		
		} else if (from_report_flag.equalsIgnoreCase("from_bankrecon")) {
		
			vouchertypeflag = bankReconciliation.vouchertypeflag;
		}else if (from_report_flag.equalsIgnoreCase("after_rollover"))	 {
			vouchertypeflag = transaction_tab.vouchertypeflag;
		}

		try {

				if (from_report_flag == null) {
					cloneflag = SearchVoucher.cloneflag;
				} else {
					cloneflag = false;
				}
		 
				etRefNumber = (EditText) findViewById(R.id.etRefNumber);
				name = SearchVoucher.name;
			
				voucherno = etvoucherno.getText().toString();
				etnarration = (EditText) findViewById(R.id.etVoucherNarration);
				sProjectNames = (Spinner) findViewById(R.id.sProjectNames);

				table = (TableLayout) findViewById(R.id.Vouchertable);
				
				/***
				 * searchFlag is false: refers values only for create voucher page
				 */
				if (searchFlag == false) {
					// get last reference number to set it to reference field
					String reff_no = transaction.getLastReferenceNumber(new Object[] { vouchertypeflag }, client_id);
					etRefNumber.setText(reff_no.toString());
				}

				/***
				 * searchFlag is True: getting values from either Edit or Clone in voucher details 
				 * 
				 */
				if (searchFlag == true) {
	
					addButton();
					// list coming from search voucher
					ArrayList<String> abc = SearchVoucher.value;
					if (from_report_flag == null) {
						vouchercode = abc.get(0);
						
					} else if (from_report_flag.equalsIgnoreCase("from_ledger")) {
						vouchercode = ledger.code;
					
					} else if (from_report_flag.equalsIgnoreCase("from_bankrecon")) {
						vouchercode = bankReconciliation.code;
					
					}else if (from_report_flag.equalsIgnoreCase("from_bankrecon")) {
						
					}
					Object[] params = new Object[] { vouchercode };
					Object[] VoucherMaster = (Object[]) transaction.getVoucherMaster(params, client_id);
					Object[] VoucherDetails = (Object[]) transaction.getVoucherDetails(params,client_id);
	
					otherdetailsrow = new ArrayList<Transaction>();
					for (Object row1 : VoucherMaster) {
						Object a = (Object) row1;
						otherdetailsrow.add(a.toString());// getting voucher master details
					}
	
					String refno = (String) otherdetailsrow.get(0);
					String narration = (String) otherdetailsrow.get(3);
					proj = (String) otherdetailsrow.get(4);
					searchdate = (String) otherdetailsrow.get(1);
					etnarration.setText(narration);
					etRefNumber.setText(refno);
					etvoucherno.setText(vouchercode);
					if (!cloneflag) {
						etvoucherno.setEnabled(false);
						etvoucherno.setTextColor(Color.parseColor("#AEC6CF"));
					}
	
					checkvoucher_number = vouchercode;
	
					accdetailsList = new ArrayList<ArrayList<String>>();
					for (Object row2 : VoucherDetails) {
						Object[] a2 = (Object[]) row2;
						accdetails = new ArrayList<String>();
						for (int i = 0; i < a2.length; i++) {
							accdetails.add((String) a2[i].toString());// getting voucher details
						}
						accdetailsList.add(accdetails);
					}
					
					if ("Journal".equals(vouchertypeflag) || 
							"Credit note".equals(vouchertypeflag)|| 
							"Debit note".equals(vouchertypeflag)) {
					
						tvpaymentmode.setVisibility(TextView.GONE);
						rg.setVisibility(EditText.GONE);
	
					} else {
	
						if (!"".equals(accdetailsList.get(0).get(3))) {
							cheque_.setChecked(true);
							etcheque.setText(accdetailsList.get(0).get(3));
							cheque_number = accdetailsList.get(0).get(3);
						
						}else{
							
							etcheque.setText("");
							cheque_number = "";
						}
					
					}
					// for filling 1st row amount
					View row = table.getChildAt(0);
					amount_first = (EditText) ((ViewGroup) row).getChildAt(6);
					amount_first.setText(accdetailsList.get(0).get(2));
					
				} //end of search flag true, coming from search to edit voucher

				// two digit date format for dd and mm
				mFormat = new DecimalFormat("00");
				mFormat.setRoundingMode(RoundingMode.DOWN);

			// add second row and set first & second row account names in spinner
			setFirstAndSecondRow();

			// for setting voucher date
			setVoucherDate();

			// for setting project
			setProject();

			if (from_report_flag != null && !(menu.userrole.equalsIgnoreCase("admin"))) {
			disable();
				
			}

		} catch (Exception ex) {

			m.toastValidationMessage(context, "Please try again");
		}
		// add all on click events in this method
		OnClickListener();

		// move focus from amount to reference number edit text
		OnAmountFocusChangeListener();

		// change voucher type
		changeVoucherType();

	}
	
	public void disable(){
		btnResetVoucher.setVisibility(View.GONE);
		btnSaveVoucher.setLayoutParams(new LinearLayout.LayoutParams(
		        LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1f));
		btnSaveVoucher.setText("Back");

		etnarration.setEnabled(false);
		etnarration.setTextColor(Color.parseColor("#AEC6CF"));
		etRefNumber.setEnabled(false);
		cheque_.setEnabled(false);
		cash_.setEnabled(false);
		etcheque.setEnabled(false);
		etcheque.setTextColor(Color.parseColor("#AEC6CF"));
		etRefNumber.setTextColor(Color.parseColor("#AEC6CF"));
		rg.setEnabled(false);
		btnResetVoucher.setEnabled(false);
		btnVoucherDate.setEnabled(false);
		btnVoucherDate.setTextColor(Color.parseColor("#AEC6CF"));
		sProjectNames.setEnabled(false);
		etvoucherno.setEnabled(false);
		etvoucherno.setTextColor(Color.parseColor("#AEC6CF"));

		tableRowCount = table.getChildCount();

		for (int i = 0; i < (tableRowCount); i++) {
			View row1 = table.getChildAt(i);

			second_table_drcr_spinner = (Spinner) ((ViewGroup) row1).getChildAt(0);
			second_table_drcr_spinner.setClickable(false);
			second_table_accountname_spinner = (Spinner) ((ViewGroup) row1).getChildAt(2);
			second_table_accountname_spinner.setClickable(false);
			second_table_amount_et = (EditText) ((ViewGroup) row1).getChildAt(6);
			second_table_amount_et.setEnabled(false);
			second_table_amount_et.setTextColor(Color.parseColor("#AEC6CF"));
			removeSelfButton = (Button) ((ViewGroup) row1).getChildAt(7);
			removeSelfButton.setVisibility(View.GONE);

		}
	}
	public void setRadioOnCheckListener()
	{
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				RadioButton selctedradio = (RadioButton) findViewById(arg1);
				seletecd_val = selctedradio.getText().toString();
				if (arg1 == R.id.rbcheque) {

					cheque.setVisibility(TextView.VISIBLE);
					etcheque.setVisibility(EditText.VISIBLE);
					etcheque.setText("");

				} else {
					cheque.setVisibility(TextView.GONE);
					etcheque.setVisibility(EditText.GONE);
					etcheque.setText("");

				}
			}
		});

	}
	private void resetFields() {
		
		name = "Create voucher";
		etRefNumber = (EditText) findViewById(R.id.etRefNumber);
		etRefNumber.setEnabled(true);
		etvoucherno.setEnabled(true);
		etvoucherno.setTextColor(Color.WHITE);
		String reff_no = transaction.getLastReferenceNumber(new Object[] { vouchertypeflag },client_id);
		etRefNumber.setText(reff_no.toString());
		etnarration = (EditText) findViewById(R.id.etVoucherNarration);
		etnarration.setText("");
		etvoucherno.setText("");
		etcheque.setText("");
		cash_.setChecked(true);
		second_table_closingbal_et.setText("");
		searchFlag = false;
		cloneflag = true;
		setVoucherDate();
		setProject();
	
		change_voucher_type.setEnabled(true);
		table.removeAllViews();
		DrAccountlist.clear();
		CrAccountlist.clear();
		accnames.clear();
		setFirstAndSecondRow();
		String tabname1 = transaction_tab.tabname;
		transaction_tab.tab.setText(tabname1);

	}

	private void changeVoucherType() {
		// adding voucher list items
		final String[] voucherTypes = new String[] { "Contra", "Journal", "Payment", "Receipt",
		        "Credit note", "Debit note", "Sales", "Sales return", "Purchase", "Purchase return" };
		// creating array adaptor to take list of vouchertypes
		final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
		        android.R.layout.simple_spinner_item, voucherTypes);
		// set resource layout of spinner to that adaptor
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		change_voucher_type.setAdapter(dataAdapter);

		change_voucher_type.setSelection(dataAdapter.getPosition(vouchertypeflag));

		if (searchFlag == true || from_report_flag != null) {
			change_voucher_type.setEnabled(false);
		}
		change_voucher_type.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				System.out.println("flags are" + searchFlag + from_report_flag);
				if (searchFlag == false && from_report_flag == null) {
					vouchertypeflag = voucherTypes[pos];

					if ("Journal".equals(vouchertypeflag) || 
							"Credit note".equals(vouchertypeflag)|| 
							"Debit note".equals(vouchertypeflag)) {
						tvpaymentmode.setVisibility(TextView.GONE);
						rg.setVisibility(EditText.GONE);
						etcheque.setText(" ");

					} else {
						tvpaymentmode.setVisibility(TextView.VISIBLE);
						rg.setVisibility(EditText.VISIBLE);

					}

					resetFields();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void OnAmountFocusChangeListener() {
		/*
		 * onfocuschange of amount edittext move focus to reference number
		 */
		tableRowCount = table.getChildCount();

		for (int i = 1; i < (tableRowCount); i++) {
			View row = table.getChildAt(i);
			// amount edit text
			e = (EditText) ((ViewGroup) row).getChildAt(6);
			e.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {

					e.setBackgroundResource(R.drawable.textfield_activated_holo_light);
					etvoucherno = (EditText) findViewById(R.id.etVoucherNumber);
					e.setNextFocusDownId(etvoucherno.getId());
				}
			});
		}

		View row = table.getChildAt(0);
		amount_first = (EditText) ((ViewGroup) row).getChildAt(6);

		amount_first.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasfocus) {
				amount_first.setBackgroundResource(R.drawable.textfield_activated_holo_light);
				int tableRowCount1 = table.getChildCount();
				String fist_row_amount = amount_first.getText().toString();
				if (tableRowCount1 == 2) {
					View row = table.getChildAt(1);
					amount_sec = (EditText) ((ViewGroup) row).getChildAt(6);
					amount_sec.setText(fist_row_amount);

				} else {
					
				}

			}
		});

		//get the first roe of table
		View row1 = table.getChildAt(0);
		closing_first = (EditText) ((ViewGroup) row).getChildAt(5);

		closing_first.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean hasfocus) {
				int tableRowCount1 = table.getChildCount();

				if (closing_first.hasFocus() == true) {
					if (tableRowCount1 == 2) {
						View row = table.getChildAt(1);
						
						amount_sec = (EditText) ((ViewGroup) row).getChildAt(6);
						amount_sec.setText(amount_first.getText());
					
					}
				}

			}
		});

	}

	public void testAmountTally() {
		/*
		 * this method calculate toatalDr and totalCr
		 */
		totalDr = 0;
		totalCr = 0;
		
		tableRowCount = table.getChildCount();

		for (int i = 0; i < (tableRowCount); i++) {
			View row = table.getChildAt(i);
			// dr cr spinner
			Spinner s = (Spinner) ((ViewGroup) row).getChildAt(0);
			String drcr = s.getSelectedItem().toString();
		
			// amount edit text
			EditText e = (EditText) ((ViewGroup) row).getChildAt(6);
			drcramount = e.getText().toString();

			if (drcramount.length() < 1) {
				drcramount = "0.00";
		
			}
			drcrAmount = Float.parseFloat(drcramount);

			if ("Dr".equals(drcr)) {
				totalDr = totalDr + drcrAmount;
			} else if ("Cr".equals(drcr)) {
				totalCr = totalCr + drcrAmount;
			}
		}
	}

	private void setFirstAndSecondRow() {
		/*
		 * this on load function takes the account name list from
		 * voucherMenu.java depending upon getAccountByRule sets first row
		 * account name spinner add the second row and set the account name in
		 * spinner
		 */

		if ("Contra".equals(vouchertypeflag) ||"Journal".equals(vouchertypeflag)) {
			if (from_report_flag == null) {
				
				Object[] params = new Object[] { "Dr" };
				m.getAccountsByRule(params, vouchertypeflag, context);
				accnames = module.Accountlist;
				
			} else if (from_report_flag.equalsIgnoreCase("from_ledger")) {
				accnames = ledger.Accountlist;
				
			} else if (from_report_flag.equalsIgnoreCase("from_bankrecon")) {
				accnames = bankReconciliation.Accountlist;
				
			}

			// set resource layout of spinner to that adapter
			if (searchFlag == false) {

				addButton();
				change_voucher_type.setEnabled(true);
				ArrayAdapter<String> da12 = new ArrayAdapter<String>(createVoucher.this,
				        android.R.layout.simple_spinner_dropdown_item, dr_cr);

				dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				        accnames);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				second_table_accountname_spinner.setAdapter(dataAdapter);
				second_table_drcr_spinner.setAdapter(da12);
				second_table_accountname_spinner.setAdapter(dataAdapter);
				removeSelfButton.setText("+");

				addButton();
  
				dr_cr.clear();
				dr_cr.add("Dr");
				dr_cr.add("Cr");
				da1 = new ArrayAdapter<String>(createVoucher.this,
				        android.R.layout.simple_spinner_item, dr_cr);
				da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				second_table_drcr_spinner.setAdapter(da1);
				second_table_drcr_spinner.setSelection(1);

				// set adaptor with account name list in second row spinner
				second_table_accountname_spinner.setAdapter(dataAdapter);

			} else {
				// for setting second row for editing
				System.out.println("In journel/contra true case");
				dr_cr.clear();
				// for setting 1st row's 2nd spinner
				Fsecond_spinner = accdetailsList.get(0).get(0);
				System.out.println("FirstS:" + Fsecond_spinner);

				System.out.println("names:" + accnames);
				// setting adapter
				dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				        accnames);

				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				FaccnamePosition = dataAdapter.getPosition(Fsecond_spinner);
				System.out.println("Position:" + FaccnamePosition);
				View row = table.getChildAt(0);
				// drcr flag
				first_account_name = (Spinner) ((ViewGroup) row).getChildAt(2);
				first_account_name.setAdapter(dataAdapter);
				first_account_name.setSelection(FaccnamePosition);

				// add second row
				addButton();

				removeSelfButton.setText("+");

				dr_cr.add("Dr");
				dr_cr.add("Cr");

				// for setting 1st spinner of 1st and 2nd row
				Sacctype = accdetailsList.get(1).get(1);
				Facctype = accdetailsList.get(0).get(1);
				da1 = new ArrayAdapter<String>(createVoucher.this,
				        android.R.layout.simple_spinner_item, dr_cr);
				da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				first_dr_cr = (Spinner) ((ViewGroup) row).getChildAt(0);

				first_dr_cr.setAdapter(da1);
				first_dr_cr.setSelection(FacctypePosition);// setting spinner
				// selection

				SacctypePosition = da1.getPosition(Sacctype);
				FacctypePosition = da1.getPosition(Facctype);

				View row1 = table.getChildAt(1);
				// drcr flag
				second_dr_cr = (Spinner) ((ViewGroup) row1).getChildAt(0);

				second_dr_cr.setAdapter(da1);
				second_dr_cr.setSelection(SacctypePosition);// setting spinner
				// selection acc to obtained value

				// for filling 2nd row amount
				amount_sec = (EditText) ((ViewGroup) row1).getChildAt(6);
				amount_sec.setText(accdetailsList.get(1).get(2));

				// for setting 2nd row's 2nd spinner
				second_account_name = (Spinner) ((ViewGroup) row1).getChildAt(2);
				Ssecond_spinner = accdetailsList.get(1).get(0);
				SaccnamePosition = dataAdapter.getPosition(Ssecond_spinner);
				second_account_name.setAdapter(dataAdapter);
				second_account_name.setSelection(SaccnamePosition);
			
				tableRowCount = table.getChildCount();

				// if row count of 2nd table(list) is more than 1 code bellow
				// will be executed

				// /////////////////////////////////////////////////////////////////////////////////////
				if (accdetailsList.size() > 2) {
					for (int i = 2; i < accdetailsList.size(); i++) {
						addButton();
						second_table_amount_et.setText(accdetailsList.get(i).get(2));
						Ssecond_spinner = accdetailsList.get(i).get(0);
						SaccnamePosition = dataAdapter.getPosition(Ssecond_spinner);
						second_table_accountname_spinner.setAdapter(dataAdapter);
						second_table_accountname_spinner.setSelection(SaccnamePosition);

						Sacctype = accdetailsList.get(i).get(1);
						da1 = new ArrayAdapter<String>(createVoucher.this,
						        android.R.layout.simple_spinner_item, dr_cr);
						da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						SacctypePosition = da1.getPosition(Sacctype);
						second_table_drcr_spinner.setAdapter(da1);
						second_table_drcr_spinner.setSelection(SacctypePosition);

					}
				}
			}
		} else {
			if (from_report_flag == null) {

				Object[] paramDr = new Object[] { "Dr" };
				m.getAccountsByRule(paramDr, vouchertypeflag, context);
				accnames = module.Accountlist;
				DrAccountlist.addAll(accnames);

				Object[] paramCr = new Object[] { "Cr" };
				m.getAccountsByRule(paramCr, vouchertypeflag, context);
				accnames = module.Accountlist;
				tableRowCount = table.getChildCount();

				CrAccountlist.addAll(accnames);

			} else if (from_report_flag.equalsIgnoreCase("from_ledger")) {
				if (ledger.vouchertypeflag.equalsIgnoreCase("Contra")
				        || ledger.vouchertypeflag.equalsIgnoreCase("Journal")) {
					
					accnames = ledger.Accountlist;
					DrAccountlist.addAll(accnames);
					CrAccountlist.addAll(accnames);
				} else {

					DrAccountlist = ledger.DrAccountlist;
					CrAccountlist = ledger.CrAccountlist;
				}

			} else if (from_report_flag.equalsIgnoreCase("from_bankrecon")) {
				
				DrAccountlist = bankReconciliation.DrAccountlist;
				CrAccountlist = bankReconciliation.CrAccountlist;

			}

			if (searchFlag == false) {
				change_voucher_type.setEnabled(true);
				addButton();
				removeSelfButton.setText("+");
				// set first row
				View row = table.getChildAt(0);
				// drcr flag
				first_dr_cr = (Spinner) ((ViewGroup) row).getChildAt(0);

				first_account_name = (Spinner) ((ViewGroup) row).getChildAt(2);

				ArrayAdapter<String> da12 = new ArrayAdapter<String>(createVoucher.this,
				        android.R.layout.simple_spinner_item, dr_cr);

				da12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				first_dr_cr.setAdapter(da12);

				dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				        DrAccountlist);
				// set resource layout of spinner to that adapter
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				first_account_name.setAdapter(dataAdapter);
				// add second row
				addButton();
				dr_cr.clear();
				dr_cr.add("Dr");
				dr_cr.add("Cr");

				View row1 = table.getChildAt(1);
				// drcr flag
				second_dr_cr = (Spinner) ((ViewGroup) row1).getChildAt(0);

				second_account_name = (Spinner) ((ViewGroup) row1).getChildAt(2);
				ArrayAdapter<String> da1 = new ArrayAdapter<String>(createVoucher.this,
				        android.R.layout.simple_spinner_item, dr_cr);
				da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				second_dr_cr.setAdapter(da1);
				second_dr_cr.setSelection(1);

				// set adaptor with account name list in second row spinner
				dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				        CrAccountlist);
				// set resource layout of spinner to that adapter
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// set adaptor with account name list in spinner
				second_account_name.setAdapter(dataAdapter);
			} else {
				// add second row
				addButton();
				removeSelfButton.setText("+");
				dr_cr.clear();
				dr_cr.add("Dr");
				dr_cr.add("Cr");

				Facctype = accdetailsList.get(0).get(1);

				Sacctype = accdetailsList.get(1).get(1);
			
				View row1 = table.getChildAt(1);
				second_dr_cr = (Spinner) ((ViewGroup) row1).getChildAt(0);

				View row = table.getChildAt(0);
				first_dr_cr = (Spinner) ((ViewGroup) row).getChildAt(0);

				da1 = new ArrayAdapter<String>(createVoucher.this,
				        android.R.layout.simple_spinner_item, dr_cr);
				da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				SacctypePosition = da1.getPosition(Sacctype);
				FacctypePosition = da1.getPosition(Facctype);
				second_dr_cr.setAdapter(da1);
				second_dr_cr.setSelection(SacctypePosition);

				first_dr_cr.setAdapter(da1);
				first_dr_cr.setSelection(FacctypePosition);
				System.out.println("dr acco");
				if ("Dr".equals(Facctype)) {// if acctype is DR
					dataAdapter = new ArrayAdapter<String>(this,
					        android.R.layout.simple_spinner_item, DrAccountlist);

				} else {// if acctype is CR
					dataAdapter = new ArrayAdapter<String>(this,
					        android.R.layout.simple_spinner_item, CrAccountlist);
				}
				Fsecond_spinner = accdetailsList.get(0).get(0);

				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				FaccnamePosition = dataAdapter.getPosition(Fsecond_spinner);

				first_account_name = (Spinner) ((ViewGroup) row).getChildAt(2);

				first_account_name.setAdapter(dataAdapter);
				first_account_name.setSelection(FaccnamePosition);

				second_table_amount_et.setText(accdetailsList.get(1).get(2));

				if ("Dr".equals(Sacctype)) {
					dataAdapter = new ArrayAdapter<String>(this,
					        android.R.layout.simple_spinner_item, DrAccountlist);
				} else {
					dataAdapter = new ArrayAdapter<String>(this,
					        android.R.layout.simple_spinner_item, CrAccountlist);

				}
			
				Ssecond_spinner = accdetailsList.get(1).get(0);

				SaccnamePosition = dataAdapter.getPosition(Ssecond_spinner);

				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				View row12 = table.getChildAt(1);

				second_account_name = (Spinner) ((ViewGroup) row12).getChildAt(2);

				second_account_name.setAdapter(dataAdapter);
				second_account_name.setSelection(SaccnamePosition);

				tableRowCount = table.getChildCount();
				if (accdetailsList.size() > 2) {
					for (int i = 2; i < accdetailsList.size(); i++) {
						addButton();
						second_table_amount_et.setText(accdetailsList.get(i).get(2));

						if ("Dr".equals(accdetailsList.get(i).get(1))) {
							dataAdapter = new ArrayAdapter<String>(this,
							        android.R.layout.simple_spinner_item, DrAccountlist);
						} else {
							dataAdapter = new ArrayAdapter<String>(this,
							        android.R.layout.simple_spinner_item, CrAccountlist);
						}

						Ssecond_spinner = accdetailsList.get(i).get(0);
					
						SaccnamePosition = dataAdapter.getPosition(Ssecond_spinner);
						dataAdapter
						        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						second_table_accountname_spinner.setAdapter(dataAdapter);
						second_table_accountname_spinner.setSelection(SaccnamePosition);
						dr_cr.clear();
						dr_cr.add("Dr");
						dr_cr.add("Cr");
						Sacctype = accdetailsList.get(i).get(1);
						da1 = new ArrayAdapter<String>(createVoucher.this,
						        android.R.layout.simple_spinner_item, dr_cr);
						da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						SacctypePosition = da1.getPosition(Sacctype);
						second_table_drcr_spinner.setAdapter(da1);
						second_table_drcr_spinner.setSelection(SacctypePosition);
					}
				}
			}
		}
	}

	private void OnDrCrItemSelectedListener() {
		/*
		 * to set account names in dropdown when Dr/Cr changed
		 */
		// for first row

		for (int i = 0; i < table.getChildCount(); i++) {
		
		
			second_table_drcr_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
					String drcr_value = parent.getItemAtPosition(position).toString();
					
					// when touch is false,the values the account name drop-down does not changes.
					//Once touch is true the account name drop-down get updated
					if (drcr_value != null) {
						if (searchFlag == false) {
//							Toast.makeText(context,"0",Toast.LENGTH_SHORT).show();
							Object[] params = new Object[] { drcr_value };
							m.getAccountsByRule(params, vouchertypeflag, context);
							dataAdapter = module.dataAdapter;
							View v1 = (View) parent.getParent();
							Spinner sp = (Spinner) ((ViewGroup) v1).getChildAt(2);
							sp.setAdapter(dataAdapter);
						} else if (searchFlag == true && touch == true && add == false) {
//							Toast.makeText(context,"1",Toast.LENGTH_SHORT).show();
							Object[] params = new Object[] { drcr_value };
							m.getAccountsByRule(params, vouchertypeflag, context);
							dataAdapter = module.dataAdapter;
							View v1 = (View) parent.getParent();
							Spinner sp = (Spinner) ((ViewGroup) v1).getChildAt(2);
							sp.setAdapter(dataAdapter);
						} else if (searchFlag == true && touch == false && add == true) {
//							Toast.makeText(context,"2",Toast.LENGTH_SHORT).show();
							Object[] params = new Object[] { drcr_value };
							m.getAccountsByRule(params, vouchertypeflag, context);
							dataAdapter = module.dataAdapter;
							View v1 = (View) parent.getParent();
							Spinner sp = (Spinner) ((ViewGroup) v1).getChildAt(2);
							sp.setAdapter(dataAdapter);
						}else {
							
							if(menu.existRollOver==true){
								disable();
								from_report_flag="abc";

							}
							
						}
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
		}
		//

		second_table_accountname_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				String accountname = parent.getItemAtPosition(position).toString();
				// Toast.makeText(createVoucher.this,
				// "account:"+ac,Toast.LENGTH_SHORT).show();
				Object[] params1 = new Object[] { accountname, financialFromDate,
				        financialFromDate, financialToDate };
				Object[] calculateBalance = (Object[]) reports.calculateBalance(params1, client_id);

				View v1 = (View) parent.getParent();
				EditText e3 = (EditText) ((ViewGroup) v1).getChildAt(5);
				e3.setText(calculateBalance[2].toString());
				if (calculateBalance[6].toString().equals("Dr")) {
					e3.setTextColor(Color.parseColor("#348017")); // green
				} else if (!calculateBalance[2].toString().equals("0.00")) {
					e3.setTextColor(Color.parseColor("#FF010f")); // red
				} else {// for 0 bal
					e3.setTextColor(Color.parseColor("#348017")); // green
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void OnClickListener() {
		/*
		 * on click method for add, save and reset button 1. add: Every time the
		 * "+" button is clicked, add a new row to the table 2. save: takes all
		 * necessary field values and calls transaction.setTransaction for
		 * adding transaction and resets all fileds after adding transaction 3.
		 * reset: resets all fields
		 */

		/*
		 * ======================================================================
		 * ======== Every time the "+" button is clicked, add a new row to the
		 * table
		 */
		// /////////////////////
		//

		second_table_drcr_spinner.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (searchFlag == true) {
					touch = true;
					add = false;
				
				}

				return false;
			}
		});

		tableRowCount = table.getChildCount();

		for (int i = 1; i < tableRowCount; i++) {
			final View row1 = table.getChildAt(i);

			removeSelfButton = (Button) ((ViewGroup) row1).getChildAt(7);

			removeSelfButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					RowRemover(table, (TableRow) row1);

					tableRowCount = table.getChildCount();

					if (tableRowCount == 2) {
						View row1 = table.getChildAt(0);
						// amount edit text
						amount_first = (EditText) ((ViewGroup) row1).getChildAt(6);

						View row12 = table.getChildAt(1);
						amount_sec = (EditText) ((ViewGroup) row12).getChildAt(6);
						amount_sec.setText(amount_first.getText());
					}

				}
			});

		}

		View row = table.getChildAt(0);
		removeSelfButton = (Button) ((ViewGroup) row).getChildAt(7);

		removeSelfButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {

				testAmountTally();
				if (totalDr == totalCr) {
					if (from_report_flag == null) {
						m.toastValidationMessage(context, "Debit and Credit amount is tally");
					}
				} else if (drcrAmount <= 0) {
					m.toastValidationMessage(context,
					        "No row can be added,Please fill the existing row");
				} else {
					for (int i = 0; i < (tableRowCount); i++) {
						View row = table.getChildAt(i);

						// amount edittext
						EditText e = (EditText) ((ViewGroup) row).getChildAt(6);
						drcramount = e.getText().toString();
						if (drcramount.length() < 1) {
							drcramount = "0.00";
						}
						amountdrcr = Float.parseFloat(drcramount);

						if (amountdrcr <= 0) {
							addRowFlag = false;
							break;
						} else {
							addRowFlag = true;
						}
					}

					if (addRowFlag == true) {
						touch = false;
						add = true;
						// add new row
						addButton();
						ArrayAdapter<String> da1 = new ArrayAdapter<String>(createVoucher.this,
						        android.R.layout.simple_spinner_item, dr_cr);
						da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						second_table_drcr_spinner.setAdapter(da1);

						// set totalDr and totalCr in textview
						String tvTotalDr = Float.toString(totalDr);
					
						String tvTotalCr = Float.toString(totalCr);
						
						// set Dr/Cr selected in dropdown according to the
						// condition and set amount in new row
						if (totalDr > totalCr) {
							diffbal = totalDr - totalCr;
							second_table_amount_et.setText(String.format("%.2f", diffbal));
							// set 'Cr' selected in Dr/Cr dropdown
							second_table_drcr_spinner.setSelection(1);

						} else {
							diffbal = totalCr - totalDr;
							second_table_amount_et.setText(String.format("%.2f", diffbal));
							// set 'Dr' selected in Dr/Cr dropdown
							second_table_drcr_spinner.setSelection(0);

						}

					} else {
						m.toastValidationMessage(context,
						        "No row can be added,Please fill the existing row");
					}
				}
			}
		});

		/***
		 * validation for Save Transaction
		 */
		btnSaveVoucher.setOnClickListener(new OnClickListener() {

			private String ac;
			private boolean flag = false;

			@Override
			public void onClick(View v) {
				if (from_report_flag == null) {   
					testAmountTally();

					String refNumber = etRefNumber.getText().toString();
					String strnarration = etnarration.getText().toString();
					voucherno = etvoucherno.getText().toString();
					String cheque_no = etcheque.getText().toString();
					
					Pattern pattern = Pattern.compile("(?=.*\\d)[a-zA-Z0-9]");
					
					vouchernoExist = transaction.voucherNoExist(new Object[] { voucherno },client_id);// exist then 1 else 0
					chequenoExist = transaction.chequeNoExist(new Object[] { cheque_no }, client_id); // exist then 1 else 0
					/***
					 * It will save voucher only if 
					 * Dr Cr amount is equal and following field should not be blank 
					 * reference and Narration and voucher and 
					 * and if cheque number is equal to cheque no coming from search voucher page 
					 * even if it is was blank OR if cash is selected then don't check for cheque number else
					 * cheque number should not be blank 
					 * 
					 */
					if ((totalDr == totalCr && !"".equals(refNumber) && !"".equals(strnarration)&&!"".equals(voucherno))&& 
					        (searchFlag == true && cloneflag == false&&cheque_.isChecked()==true&&cheque_number.equals(cheque_no)&&
					        	"".equals(cheque_no)||cash_.isChecked()==true||!"".equals(cheque_no))) {

						if (totalDr == 0) {
							if ("0.0".equals(Float.toString(totalDr))) {

								amount_first.requestFocus();
								amount_first.setBackgroundResource(R.drawable.btn_default_focused_holo_light);
							}
							m.toastValidationMessage(context, "Please enter amount");

						} else if(!pattern.matcher(voucherno).find()){

							//voucher number must contain at least one digit or alphanumeric
							m.toastValidationMessage(context, "Please enter valid voucher number");
							etvoucherno.requestFocus();
							etvoucherno.setBackgroundResource(R.drawable.btn_default_focused_holo_light);

						}else if ((searchFlag == true && cloneflag == false
						        && checkvoucher_number.equals(voucherno) && (cheque_number
						        .equals(cheque_no)|| (cash_.isChecked()==true||cheque_.isChecked()==true&&chequenoExist.equals("0"))))
						        || vouchernoExist.equals("0") && (cash_.isChecked()==true||cheque_.isChecked()==true&&chequenoExist.equals("0"))) {

							// main list
							paramsMaster = new ArrayList<ArrayList>();
							ArrayList<String> accNames = new ArrayList();

							boolean empty_spinner = false;
							int empty_account_row = 0;
							// check validations for all rows and if ok, save
							int tableRowCount = table.getChildCount();

							for (int i = 0; i < (tableRowCount); i++) {
								List<String> paramRow = new ArrayList<String>();

								View row1 = table.getChildAt(i);
								// drcr flag
								second_dr_cr = (Spinner) ((ViewGroup) row1).getChildAt(0);
								String drcrFlag = second_dr_cr.getSelectedItem().toString();

								// account name
								second_account_name = (Spinner) ((ViewGroup) row1).getChildAt(2);

								String accountName = "";
								try {
									accountName = second_account_name.getSelectedItem().toString();
									accNames.add(accountName);
								} catch (Exception e) {
									empty_spinner = true;
									empty_account_row = i;
									break;
								}

								// amount edittext
								amount_sec = (EditText) ((ViewGroup) row1).getChildAt(6);
								String rowAmount = amount_sec.getText().toString();
								if (searchFlag == false) {// for editing account
									paramRow.add(drcrFlag);
									paramRow.add(accountName);
									paramRow.add(rowAmount);
									paramsMaster.add((ArrayList<String>) paramRow);
									System.out.println("paramsMaster:" + paramsMaster);
								} else if (cloneflag == false) {// for editing
									// account
									if ("Dr".equals(drcrFlag)) {
										paramRow.add(accountName);
										paramRow.add(rowAmount);
										paramRow.add("0");
										paramsMaster.add((ArrayList<String>) paramRow);
									} else {
										paramRow.add(accountName);
										paramRow.add("0");
										paramRow.add(rowAmount);
										paramsMaster.add((ArrayList<String>) paramRow);
									}
								} else if (cloneflag == true) {// for clonning
									// account
									paramRow.add(drcrFlag);
									paramRow.add(accountName);
									paramRow.add(rowAmount);
									paramsMaster.add((ArrayList<String>) paramRow);
								}
							}

							int same_account_name_row = 0;
							for (int i = 0; i < accNames.size(); i++) {
								ac = accNames.get(i);
								for (int j = 0; j < accNames.size(); j++) {
									if (i != j) {
										if (ac.equals(accNames.get(j))) {
											flag = true;
											same_account_name_row = i;
											break;
										}

									} else {
										flag = false;
									}
									if (flag == true) {
										break;
									}
								}
								if (flag == true) {
									break;
								}
							}
							if (empty_spinner == false && flag == false) {
								// other voucher details...
								etnarration = (EditText) findViewById(R.id.etVoucherNarration);
								String narration = etnarration.getText().toString();

								if ("".equals(narration)) {
									narration = ""; // need to find solution for
									// null
								}
								if (searchFlag == false) {// for saving voucher
									// details

									Object[] params_master = null;
									if ("Cash".equals(seletecd_val)) {
										
										params_master = new Object[] { refNumber, vDate,
										        vouchertypeflag, vproject, narration, voucherno, "" };
									} else if ("Cheque".equals(seletecd_val)) {
										
										String val = etcheque.getText().toString();
										params_master = new Object[] { refNumber, vDate,
										        vouchertypeflag, vproject, narration, voucherno,
										        val };

									} else {
										System.out.println("3");
										params_master = new Object[] { refNumber, vDate,
										        vouchertypeflag, vproject, narration, voucherno, "" };
									}

									setVoucher = (Integer) transaction.setTransaction(params_master, paramsMaster, client_id);
									// for satisfying reset condition
									searchFlag = false;
									edittabflag = false;

								} else if (cloneflag == false) {// for saving
									// edited
									// transaction
									// account details
									String val = etcheque.getText().toString();
									Object[] params_master = new Object[] { vouchercode, vDate,
									        vproject, narration, refNumber, val };
									transaction.editVoucher(params_master, paramsMaster, client_id);
									edittabflag = true;

									MainActivity.nameflag = false;
									/*
									 * if we are coming from report and edited
									 * transaction then be on the same page else
									 * change the tab to search voucher
									 */
									if (from_report_flag == null) {
										// for satisfying reset condition
										searchFlag = false;

										// for changing the tab
										transaction_tab.tabHost.setCurrentTab(1);

										// for changing the tab name
										String tabname1 = transaction_tab.tabname;
										transaction_tab.tab.setText(tabname1);
									}

								} else if (cloneflag == true) {// for saving
									// cloned
									// details

									Object[] params_master = null;
									if ("Cash".equals(seletecd_val)) {
										
										params_master = new Object[] { refNumber, vDate,
										        vouchertypeflag, vproject, narration, voucherno, "" };
									} else if ("Cheque".equals(seletecd_val)) {
										
										String val = etcheque.getText().toString();

										params_master = new Object[] { refNumber, vDate,
										        vouchertypeflag, vproject, narration, voucherno,
										        val };

									} else {
										
										params_master = new Object[] { refNumber, vDate,
										        vouchertypeflag, vproject, narration, voucherno, "" };
									}
									setVoucher = (Integer) transaction.setTransaction(
									        params_master, paramsMaster, client_id);
									// for not getting reseted
									searchFlag = true;
									edittabflag = false;
								}

								AlertDialog.Builder builder = new AlertDialog.Builder(context);
								if (searchFlag == false && edittabflag == false) {
									builder.setMessage("Transaction added successfully");
								} else if (cloneflag == false && edittabflag == true) {
									builder.setMessage("Transaction edited successfully");
								} else if (cloneflag == true) {
									builder.setMessage("Transaction cloned successfully");
								}
								AlertDialog alert = builder.create();
								alert.setCancelable(true);
								alert.setCanceledOnTouchOutside(true);
								alert.show();

								// reset all fields
								if ((searchFlag == false || cloneflag == false)&& from_report_flag == null) {
									resetFields();
								}

							} else {

								if (empty_spinner) {
									m.toastValidationMessage(context, "Please create account");
									View row1 = table.getChildAt(empty_account_row);
									second_account_name = (Spinner) ((ViewGroup) row1).getChildAt(2);
									second_account_name.requestFocus();
									second_account_name.setBackgroundResource(R.drawable.spinner_focused_holo_dark);
									getCurrentFocus().clearFocus();
								} else {
									m.toastValidationMessage(context,"Account name can not be repeated, please select another account name");
									View row1 = table.getChildAt(same_account_name_row);
									second_account_name = (Spinner) ((ViewGroup) row1).getChildAt(2);
									second_account_name.requestFocus();
									second_account_name.setBackgroundResource(R.drawable.spinner_focused_holo_dark);
									getCurrentFocus().clearFocus();
								}
							}
						} else {

							System.out.println("i am in both exist voucher no" + voucherno);
							if (searchFlag == false || searchFlag == true && cloneflag == true) {
								if (vouchernoExist.equals("1")) {
									etvoucherno.requestFocus();
									etvoucherno
									        .setBackgroundResource(R.drawable.btn_default_focused_holo_light);
									m.toastValidationMessage(context, "Voucher no already exist");
								} else {
									if (chequenoExist.equals("1")) {
										etcheque.requestFocus();
										etcheque.setBackgroundResource(R.drawable.btn_default_focused_holo_light);
										m.toastValidationMessage(context, "Cheque no already exist");
									}
								}
							} else {

								if (chequenoExist.equals("1")) {
									etcheque.requestFocus();
									etcheque.setBackgroundResource(R.drawable.btn_default_focused_holo_light);
									m.toastValidationMessage(context, "Cheque no already exist");
								} else {

								}
							}

						}
					} else if (totalDr != totalCr) {
						if ("0.0".equals(Float.toString(totalDr))) {

							View row = table.getChildAt(0);
							amount_first = (EditText) ((ViewGroup) row).getChildAt(6);
							amount_first.requestFocus();
							amount_first.setBackgroundResource(R.drawable.btn_default_focused_holo_light);
						} else {
							View row = table.getChildAt(1);
							amount_sec = (EditText) ((ViewGroup) row).getChildAt(6);
							amount_sec.requestFocus();
							amount_sec.setBackgroundResource(R.drawable.btn_default_focused_holo_light);
						}

						m.toastValidationMessage(context, "Debit and Credit amount is not tally");

					} else if ("".equals(refNumber)) {
						etRefNumber.requestFocus();
						m.toastValidationMessage(context, "Please enter voucher reference number");
						etRefNumber.setBackgroundResource(R.drawable.btn_default_focused_holo_light);

					} else if ("".equals(voucherno)) {
						// voucherNoExist
						etvoucherno.requestFocus();
						m.toastValidationMessage(context, "Please enter voucher number");
						etvoucherno.setBackgroundResource(R.drawable.btn_default_focused_holo_light);

					} else if ("".equals(strnarration)) {
						etnarration.requestFocus();
						m.toastValidationMessage(context, "Please enter narration");
						etnarration.setBackgroundResource(R.drawable.btn_default_focused_holo_light);
					} else if (cheque_.isChecked()==true&&etcheque.getText().length()==0) {
						m.toastValidationMessage(context, "Please enter cheque no.");

					}
				} else {
					onBackPressed();
				}
			}
		});

		etvoucherno.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				etvoucherno.setBackgroundResource(R.drawable.textfield_activated_holo_light);

			}
		});

		etRefNumber.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				etRefNumber.setBackgroundResource(R.drawable.textfield_activated_holo_light);

			}
		});

		etnarration.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				etnarration.setBackgroundResource(R.drawable.textfield_activated_holo_light);

			}
		});

		/*
		 * ======================================================================
		 * ======== reset all fields
		 */

		btnResetVoucher.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage(
				        "Are you sure, you want reset all fields for creating a new voucher?")
				        .setCancelable(false)
				        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int id) {

						        resetFields();

					        }
				        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int id) {
						        dialog.cancel();
					        }
				        });
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}

	private void setProject() {
		/*
		 * set 'No Project' in the subtitle on load and when item is clicked,
		 * populates the list of project names present in database when
		 * item(project name) is selected, sets selected name in the subtitle
		 */

		// call the getAllProjects method to get all projects
		Object[] projectnames = (Object[]) organisation.getAllProjects(client_id);
		// create new array list of type String to add gropu names
		List<String> projectnamelist = new ArrayList<String>();
		projectnamelist.add("No Project");
		for (Object pn : projectnames) {
			Object[] p = (Object[]) pn;
			projectnamelist.add((String) p[1]); // p[0] is project code & p[1] is project name
		}

		// creating array adaptor to take list of voucher types
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
		        android.R.layout.simple_spinner_item, projectnamelist);
		// set resource layout of spinner to that adaptor
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sProjectNames.setAdapter(dataAdapter);

		if (searchFlag == true) {// this code will be executed while
			// cloning,editing
			sProjectNames.setSelection(dataAdapter.getPosition(proj));

		} else {// this code will be executed while creating account
			sProjectNames.setSelection(dataAdapter.getPosition("No Project"));
		}

		vproject = "No Project";

		sProjectNames.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
				vproject = parent.getItemAtPosition(pos).toString();
				// Toast.makeText(context, vproject, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void setVoucherDate() {
		/*
		 * set the financial year from date in the subtitle and when date is
		 * changed by user, sets date in the subtitle
		 */
		String fromday, frommonth, fromyear, LastFromDate;
		if (searchFlag == true) {
			// will get executed while clonning and editing
			financialFromDate = Startup.getfinancialFromDate();
			String dateParts[] = searchdate.split("-");
			fromday = dateParts[0];
			frommonth = dateParts[1];
			fromyear = dateParts[2];
		} else {
			// will get executed while creating account
			financialFromDate = Startup.getfinancialFromDate();
			LastFromDate = transaction.getLastReferenceDate(new Object[] { financialFromDate,
			        vouchertypeflag }, client_id);
			String dateParts[] = LastFromDate.split("-");
			fromday = dateParts[0];
			frommonth = dateParts[1];
			fromyear = dateParts[2];
		}

		financialToDate = Startup.getFinancialToDate();
		year = Integer.parseInt(fromyear);
		month = Integer.parseInt(frommonth);
		day = Integer.parseInt(fromday);

		btnVoucherDate = (Button) findViewById(R.id.btnVoucherDate);
		btnVoucherDate.setText(mFormat.format(Double.valueOf(day)) + "-"
		        + mFormat.format(Double.valueOf(month)) + "-" + year);

		vDate = mFormat.format(Double.valueOf(day)) + "-" + mFormat.format(Double.valueOf(month))
		        + "-" + year;
		btnVoucherDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(VOUCHER_DATE_DIALOG_ID);
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case VOUCHER_DATE_DIALOG_ID:
			// set 'from date' date picker as current date
			return new DatePickerDialog(this, fromdatePickerListener, year, month - 1, day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener fromdatePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date date1 = sdf.parse(financialFromDate);
				Date date2 = sdf.parse(financialToDate);
				Date date3 = sdf.parse(mFormat.format(Double.valueOf(day))
				        + "-"
				        + mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double
				                .valueOf(month)))) + 1)) + "-"
				        + mFormat.format(Double.valueOf(year)));
				Calendar cal1 = Calendar.getInstance(); // financial from date
				Calendar cal2 = Calendar.getInstance(); // financial to date
				Calendar cal3 = Calendar.getInstance(); // voucher date

				// 24-10-2012 23-10-2013 23-10-2012

				cal1.setTime(date1);
				cal2.setTime(date2);
				cal3.setTime(date3);
			
				if ((cal3.after(cal1) && cal3.before(cal2)) || cal3.equals(cal1)
				        || cal3.equals(cal2)) {

					// set selected date into textview
					btnVoucherDate.setText(new StringBuilder()
					        .append(mFormat.format(Double.valueOf(day)))
					        .append("-")
					        .append(mFormat.format(Double.valueOf(Integer.parseInt((mFormat
					                .format(Double.valueOf(month)))) + 1))).append("-")
					        .append(year));
					vDate = mFormat.format(Double.valueOf(day))
					        + "-"
					        + (mFormat.format(Double.valueOf(Integer.parseInt((mFormat
					                .format(Double.valueOf(month)))) + 1))) + "-" + year;
				} else {
					m.toastValidationMessage(context, "Please enter proper voucher date");
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	};

	
	public void RowRemover(TableLayout table, TableRow row) {
		this.table = table;
		this.rowToBeRemoved = row;
	
		if (table.getChildCount() == 2) {
		
		} else {
			table.removeView(rowToBeRemoved);
			View row1 = table.getChildAt(0);
		
			closing_first = (EditText) ((ViewGroup) row1).getChildAt(5);
			closing_first.requestFocus();
		
		}

	}

	public void addButton() {
		/*
		 * tableRowCount this method add the transaction row to the table
		 */
		newRow = new TableRow(table.getContext());
		newRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		        LayoutParams.WRAP_CONTENT));
		newRow.setBackgroundColor(Color.parseColor("#2f2f2f"));
		// newRow.addView(child, width, height)

		second_table_drcr_spinner = new Spinner(newRow.getContext(), Spinner.MODE_DIALOG);
		second_table_drcr_spinner.setBackgroundResource(R.drawable.spinner_background_holo_light);
		second_table_drcr_spinner.setPrompt("Select Account type");

		TextView tv = new TextView(newRow.getContext());
		tv.setText("        Account Name");
		tv.setTextSize(14); // for emulator 14
		tv.setTextColor(Color.WHITE);
		tv.setVisibility(TextView.GONE);

		second_table_accountname_spinner = new Spinner(newRow.getContext(), Spinner.MODE_DIALOG);
		second_table_accountname_spinner.setMinimumWidth(259);// for emulator
		// keep 283
		second_table_accountname_spinner
		        .setBackgroundResource(R.drawable.spinner_background_holo_light);
		second_table_accountname_spinner.setPrompt("Select Account name");

		TextView tv1 = new TextView(newRow.getContext());
		tv1.setText("        Amount");
		tv1.setTextSize(14); // ****
		tv1.setTextColor(Color.WHITE);
		tv1.setVisibility(TextView.GONE);

		TextView tv2 = new TextView(newRow.getContext());
		tv2.setText(R.string.Rs);
		tv2.setTextColor(Color.WHITE);
		tv2.setTextSize(19);
		tv2.setPadding(10, 0, 5, 0);
		tv2.setVisibility(TextView.GONE);

		second_table_amount_et = new EditText(newRow.getContext());
		second_table_amount_et.setBackgroundResource(R.drawable.edit_text_holo_light);
		second_table_amount_et.setText("");
		second_table_amount_et.setHint("Enter amount");
		second_table_amount_et.setInputType(InputType.TYPE_CLASS_NUMBER
		        | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		TableRow.LayoutParams paramsExample = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
		        LayoutParams.WRAP_CONTENT, 1.0f);
		paramsExample.width = 10;

		second_table_closingbal_et = new EditText(newRow.getContext());
		second_table_closingbal_et.setBackgroundResource(R.drawable.textfield_focused_holo_light);
		second_table_closingbal_et.setText("");
		second_table_closingbal_et.setInputType(InputType.TYPE_CLASS_NUMBER
		        | InputType.TYPE_NUMBER_FLAG_DECIMAL);

		second_table_closingbal_et.setEnabled(false);

		// second_table_accountname_spinner.setText( "Action: " + ++rowsSoFar );
		removeSelfButton = new Button(newRow.getContext(), null);
		removeSelfButton.setText("     -      "); // for tablet ***** add space
		removeSelfButton.setBackgroundResource(R.drawable.default_button_selector);
		
		removeSelfButton.setTextColor(Color.WHITE);

		newRow.addView(second_table_drcr_spinner, 105, 40);
		newRow.addView(tv);
		newRow.addView(second_table_accountname_spinner, 259, 40);
		newRow.addView(tv1);
		newRow.addView(tv2);

		newRow.addView(second_table_closingbal_et, 162, 40);
		newRow.addView(second_table_amount_et, 182, 40);
		newRow.addView(removeSelfButton, 60, 45);
		table.addView(newRow);
		OnAmountFocusChangeListener();
		System.out.println("flag:" + MainActivity.searchFlag);
		// if(MainActivity.searchFlag=false){
		OnDrCrItemSelectedListener();
		// }
		OnClickListener();

	}

	public void onBackPressed() {

		MainActivity.searchFlag = false;

		if (from_report_flag == null) {
			MainActivity.nameflag = false;
			Intent intent = new Intent(getApplicationContext(), menu.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else if (from_report_flag.equalsIgnoreCase("from_ledger")) {
			Intent intent = new Intent(getApplicationContext(), ledger.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else if (from_report_flag.equalsIgnoreCase("from_bankrecon")) {
			Intent intent = new Intent(getApplicationContext(), bankReconciliation.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

		}else {
			transaction_tab.flag_for_rollover=null;
			Intent intent = new Intent(getApplicationContext(), transaction_tab.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

		}

	}

	@Override
	protected void onResume() {
	
		super.onResume();
		if (SearchVoucher.deletedflag.equals(true)&& SearchVoucher.deleted_vouchercode.equals(vouchercode)) {
			resetFields();
		}
	}

}