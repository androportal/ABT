package com.example.gkaakash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.gkaakash.controller.PdfGenaretor;
import com.gkaakash.controller.Report;
import com.gkaakash.controller.Startup;
import com.gkaakash.controller.Transaction;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.log.SysoLogger;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ledger extends Activity {
	TableLayout ledgertable;
	TableRow tr;
	TextView label, tvaccontName, tvfinancialFromDate, tvfinancialToDate,
			infoTxtCredits;
	ArrayList<ArrayList> ledgerGrid;
	ArrayList<ArrayList> ledgerGrid_with_voucherCode, ledgerGrid_with_header;
	static Object[] ledgerResult;
	static Integer client_id;
	private Report report;
	ArrayList<String> ledgerResultList, ledgerResult_for_voucherCode;
	private ArrayList accountlist;
	String[] pdf_params;
	Boolean updown = false;
	boolean checked;
	DecimalFormat formatter = new DecimalFormat("#,##,##,###.00");
	String colValue, financialFromDate, financialToDate;
	Boolean alertdialog = false;
	ObjectAnimator animation2;
	boolean reportmenuflag;
	int oneTouch = 1;
	String OrgName;
	TableLayout floating_heading_table;
	LinearLayout Ll;
	ScrollView sv;
	Object[] ledger_params;
	static String[] ColumnNameList;
	String accountName, projectName, fromDate, toDate;
	Font smallBold, smallNormal, bigBold;
	String OrgPeriod, LedgerPeriod;
	String Ledger_project, sFilename;
	static String name;
	static Boolean cloneflag = false;
	static String IPaddr;
	final Context context = this;
	static String vouchertypeflag;
	static Object[] voucherAccounts;
	private Transaction transaction;
	static List<String> Accountlist;
	static ArrayList<String> DrAccountlist;
	static ArrayList<String> CrAccountlist;
	static module m;
	ArrayList<String> columnValue;
	static String code;
	static String get_extra_flag;
	String msg;
	ArrayList<String> stringList;
	private int group1Id = 1;
	int PDF = Menu.FIRST;
	int CSV = Menu.FIRST + 1;
	int len;
	AlertDialog dialog;
	Date date;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(group1Id, PDF, PDF, "Export as PDF");
		menu.add(group1Id, CSV, CSV, "Export as CSV");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			String password = m.setPasswordForPdfFile(context,inflater, R.layout.sign_up,0, pdf_params, ledgerGrid, null);
			return true;

		case 2:
			m.csv_writer(pdf_params,ledgerGrid_with_header);
			m.toastValidationMessage(ledger.this, "CSV exported");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ledger_table);
		IPaddr = MainActivity.IPaddr;
	    System.out.println("in createorg"+IPaddr);
		report = new Report(IPaddr);
		transaction = new Transaction(IPaddr);
		client_id = Startup.getClient_id();
		m = new module();
		msg = "At lease 2 accounts require to enter transaction, please create account!";
		reportmenuflag = MainActivity.reportmenuflag;
		// customizing title bar
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
//				R.layout.bank_recon_title);
		smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 10);
		smallBold.setStyle(Font.UNDERLINE);
		smallBold.setStyle(Font.BOLD);
		bigBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
		smallNormal = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

		try {
			floating_heading_table = (TableLayout) findViewById(R.id.floating_heading_table);
			floating_heading_table.setVisibility(TableLayout.GONE);
			sv = (ScrollView) findViewById(R.id.ScrollLedger);

			financialFromDate = Startup.getfinancialFromDate();
			financialToDate = Startup.getFinancialToDate();

			Bundle extras = getIntent().getExtras();
			if (extras == null) {
			} else {

				get_extra_flag = extras.getString("flag");

			}
			System.out.println("content:" + get_extra_flag);

			if (get_extra_flag == null) {

				accountName = reportMenu.selectedAccount;
				projectName = reportMenu.selectedProject;
				checked = reportMenu.cheched;
				fromDate = reportMenu.givenfromDateString;
				toDate = reportMenu.givenToDateString;

			} else if (get_extra_flag.equalsIgnoreCase("from_trialBal")) {
				accountName = trialBalance.acc_name;
				other_details();
				System.out.println("m in extra");

			} else if (get_extra_flag.equalsIgnoreCase("from_projStatement")) {
				accountName = projectStatement.acc_name1;
				other_details();
				System.out.println("m in extra1");
			} else if (get_extra_flag.equalsIgnoreCase("from_cashflow")) {
				accountName = cashFlow.acc_name;
				other_details();
				System.out.println("m in extra1");
			} else if (get_extra_flag.equalsIgnoreCase("from_balanceSheet")) {
				accountName = balanceSheet.acc_name1;
				other_details();
				System.out.println("m in extra1");
			}else if (get_extra_flag.equalsIgnoreCase("from_IncExp")) {
				accountName = incomeExpenditure.acc_name;
				other_details();
				System.out.println("m in extra1");
			}else if(get_extra_flag.equalsIgnoreCase("from_cashbook")) {   
				accountName = cashBook.acc_name;
				//other_details();
				projectName = "No Project";
				checked = true;
				fromDate = cashBook.fromDateString;
				toDate = cashBook.toDateString;
				System.out.println("m in extra1");
			}

			tvaccontName = (TextView) findViewById(R.id.tvaccountName);
			tvfinancialToDate = (TextView) findViewById(R.id.tvfinancialToDate);
			tvaccontName.setText("Ledger for: " + accountName.trim());

			// to get month in words
			SimpleDateFormat read = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat write = new SimpleDateFormat("dd-MMM-yyyy");
			String str_fromDate = write.format(read.parse(fromDate));
			String str_toDate = write.format(read.parse(toDate));

			tvfinancialToDate.setText("Period : " + str_fromDate + " to "
					+ str_toDate);

			if (!projectName.equalsIgnoreCase("No Project")) {
				TextView tvProjectName = (TextView) findViewById(R.id.tvProjectName);
				tvProjectName.setVisibility(View.VISIBLE);
				tvProjectName.setText("Project: " + projectName);
				Ledger_project = "for the " + projectName + " project";
			} else {
				Ledger_project = "No Project";
			}
			
			/**
			 * call to the ledger report and collect input parameters for the same 
			 */
			ledger_params = new Object[] { accountName, financialFromDate,fromDate, toDate, projectName };
			ledgerResult = (Object[]) report.getLedger(ledger_params, client_id);

			/**
			 * below variables are to providing information to PDF genartion header
			 */
			OrgName = MainActivity.organisationName;
			
			
			/**
			 * get the result of ledger from rpc and display it to tables accordingly
			 */
			ledgerGrid = new ArrayList<ArrayList>();
			ledgerGrid_with_voucherCode = new ArrayList<ArrayList>();
			ledgerGrid_with_header = new ArrayList<ArrayList>();
			for (Object tb : ledgerResult) {

				Object[] t = (Object[]) tb;
				ledgerResultList = new ArrayList<String>();

				ledgerResult_for_voucherCode = new ArrayList<String>();

				for (int i = 0; i < (t.length); i++) {
					ledgerResult_for_voucherCode.add((String) t[i].toString());

				}
				ledgerGrid_with_voucherCode.add(ledgerResult_for_voucherCode);

				for (int i = 0; i < (t.length - 1); i++) {
					if (i == 5) { // ****************
						if (checked == true) {
							ledgerResultList.add((String) t[i].toString());
						}

					} else {
						ledgerResultList.add((String) t[i].toString());
					}
				}

				ledgerGrid.add(ledgerResultList);

			}

			ledgertable = (TableLayout) findViewById(R.id.maintable);
			addTable();

			//set title
			TextView org = (TextView)findViewById(R.id.org_name);
			org.setText(OrgName + ", "+reportMenu.orgtype);
			TextView tvdate = (TextView)findViewById(R.id.date);
			tvdate.setText(m.changeDateFormat(financialFromDate)+" To "+m.changeDateFormat(financialToDate));
			floatingHeader();

			createMenuOptions();
			
			changeInputs();
			
		} catch (Exception e) {
			AlertDialog.Builder builder = new AlertDialog.Builder(ledger.this);
			builder.setMessage("Please try again")
					.setCancelable(false)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

								}
							});

			AlertDialog alert = builder.create();
			alert.show();

		}
	}

	private void changeInputs() {
		Button btn_changeInputs = (Button)findViewById(R.id.btn_changeInputs);
		btn_changeInputs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reportMenu reportM = new reportMenu();
				reportM.callLedgerOrCashFlowOrBankRecon(context,"L",ledger.class); 
			}
		});
	}

	public void createMenuOptions() {
		Button btn_optionsMenu = (Button)findViewById(R.id.btn_optionsMenu);
		
		btn_optionsMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				date = new Date();
				String date_format = new SimpleDateFormat("dMMMyyyy_HHmmss").format(date);
				OrgPeriod = "Financial Year: " + financialFromDate + " to "+ financialToDate;
				LedgerPeriod = fromDate + " to " + toDate;
				String account = accountName.replace(" ", "");
				sFilename = "L" +"_"+ OrgName.replace(" ", "")+"_" + account + "_" +financialFromDate.substring(8)+"-"+financialToDate.substring(8)+"_"+ date_format;
				pdf_params = new String[] { "L", sFilename, OrgName, OrgPeriod,"Ledger for: " + accountName, LedgerPeriod,"Project: " + Ledger_project, };
				CharSequence[] items = new CharSequence[]{ "Export as PDF","Export as CSV"};
				

				//creating a dialog box for popup
				AlertDialog.Builder builder = new AlertDialog.Builder(ledger.this);
				//setting title
				builder.setTitle("Select");
				//adding items
				builder.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int pos) {
						if(pos == 0){
							LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
							String password = m.setPasswordForPdfFile(context,inflater, R.layout.sign_up,0, pdf_params, ledgerGrid, null);
						}else if(pos == 1){
							m.csv_writer(pdf_params,ledgerGrid_with_header);
							m.toastValidationMessage(ledger.this, "CSV exported please see at /mnt/sdcard/"+pdf_params[1]);
						}
					}
				});
				dialog=builder.create();
				((Dialog) dialog).show();
			}
		});
		
	}

	private void other_details() {
		projectName = "No Project";
		checked = true;
		fromDate = financialFromDate;
		toDate = financialToDate;

	}

	/*
	 * this method adds the floating header to the table on touching it. In this
	 * case, we have a main table which includes table rows and a header at the
	 * load time. and another table(for floating header) is invisible at load
	 * time which is located at the top of main table. on the very first touch
	 * of the main table, we will add floating header columns and make it
	 * visible. at the same time we will set width 0 for the main table header
	 * to avoid double headers at the same time.
	 */
	private void floatingHeader() {
		ledgertable.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (oneTouch == 1) {
					floating_heading_table.setVisibility(TableLayout.VISIBLE);

					// System.out.println("we are in if");

					int rowcount = ledgertable.getChildCount();
					View row = ledgertable.getChildAt(rowcount - 1);

					final SpannableString rsSymbol = new SpannableString(ledger.this.getText(R.string.Rs));
					/** Create a TableRow dynamically **/
					String[] ColumnNameList = new String[] { "Date","Particulars", "Reference no.",rsSymbol + " Debit", rsSymbol + " Credit","Narration" };

					tr = new TableRow(ledger.this);

					int len;
					if (checked == true) {
						len = ColumnNameList.length;
					} else {
						len = ColumnNameList.length - 1;
					}

					for (int k = 0; k < len; k++) {
						/** Creating a TextView to add to the row **/
						addRow(ColumnNameList[k], k);
						label.setBackgroundColor(Color.WHITE);
						label.setTextColor(Color.BLACK); //blue theme
						label.setGravity(Gravity.CENTER);
						tr.setClickable(false);
						LinearLayout l = (LinearLayout) ((ViewGroup) row).getChildAt(k);
						label.setWidth(l.getWidth());
						// System.out.println("size is"+l.getWidth());
					}

					// Add the TableRow to the TableLayout
					floating_heading_table.addView(tr,new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.MATCH_PARENT));

					// ledgertable.removeViewAt(0);
					ledgertable.getChildAt(0).setVisibility(View.INVISIBLE);

					View firstrow = ledgertable.getChildAt(0);
					for (int k = 0; k < len; k++) {
						LinearLayout l = (LinearLayout) ((ViewGroup) firstrow).getChildAt(k);
						TextView tv = (TextView) l.getChildAt(0);
						tv.setHeight(0);
						l.getLayoutParams().height = 0;
					}
					// ledgertable.getChildAt(0).setLayoutParams(new
					// TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
					// 0));

				}
				oneTouch++;

				return false;
			}
		});
	}

	private void addTable() {
		addHeader();
		// System.out.println("ledgerGrid."+ledgerGrid);

		/** Create a TableRow dynamically **/
		for (int i = 0; i < ledgerGrid.size(); i++) {
			columnValue = new ArrayList<String>();
			columnValue.addAll(ledgerGrid.get(i));
			tr = new TableRow(this);
			Integer lastIndex = ledgerGrid.size() - 1;
			Boolean click = true;
			if ("Opening Balance b/f".equalsIgnoreCase(columnValue.get(1).toString())) {
				// System.out.println("we are in new if");
				click = false;
			}
			for (int j = 0; j < columnValue.size(); j++) {
				addRow(columnValue.get(j), i);

				if (click == false) {
					tr.setClickable(false);
				} else {
					tr.setClickable(true);
				}

				System.out.println("llll:" + columnValue.get(1));

				if ((i + 1) % 2 == 0)
					label.setBackgroundColor(Color.parseColor("#085e6b")); //blue theme
				else
					label.setBackgroundColor(Color.parseColor("#2f2f2f")); //gray theme
				Integer secondlastRow = lastIndex - 1;
				Integer thirdlastRow = lastIndex - 2;

				if (secondlastRow.equals(i) || thirdlastRow.equals(i)) {
					tr.setClickable(false);

				}
				if (lastIndex.equals(i)) {
					tr.setClickable(false);
					label.setBackgroundColor(Color.parseColor("#218d9d"));
				}

				if (j == 3 || j == 4) {
					label.setGravity(Gravity.RIGHT);

					if (columnValue.get(j).length() > 0) {

						colValue = columnValue.get(j);
						if (!"".equals(colValue)) {
							// System.out.println("m in ");
							if (!"0.00".equals(colValue)) {
								// for checking multiple \n and pattern matching
								Pattern pattern = Pattern.compile("\\n");
								Matcher matcher = pattern.matcher(colValue);
								boolean found = matcher.find();
								// System.out.println("value:"+found);
								if (found == false) {
									double amount = Double.parseDouble(colValue);
									label.setText(formatter.format(amount));
								} else {
									label.setText(colValue);
								}

							} else {
								label.setText(colValue);
							}
						}
					}
				} else {
					label.setGravity(Gravity.CENTER);
				}
			}

			// Add the TableRow to the TableLayout
			ledgertable.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));

		}

	}

	void addHeader() {
		// For adding rupee symbol
		final SpannableString rsSymbol = new SpannableString(
				ledger.this.getText(R.string.Rs));
		/** Create a TableRow dynamically **/
		ColumnNameList = new String[] { "Date", "Particulars", "Reference no.",
				rsSymbol + " Debit", rsSymbol + " Credit", "Narration" };

		tr = new TableRow(this);

		if (checked == true) {
			len = ColumnNameList.length;
		} else {
			len = ColumnNameList.length - 1;
		}

		for (int k = 0; k < len; k++) {
			/** Creating a TextView to add to the row **/
			addRow(ColumnNameList[k], k);
			tr.setClickable(false);
			label.setBackgroundColor(Color.WHITE);
			label.setTextColor(Color.BLACK); //blue theme
			label.setGravity(Gravity.CENTER);
		}

		// for adding header in the main grid
		stringList = new ArrayList<String>();
		for (String s : ColumnNameList) {
			stringList.add(s);
		}

		ledgerGrid_with_header.add(stringList);
		ledgerGrid_with_header.addAll(ledgerGrid);
		System.out.println("ledgerGrid_with_header1:" + ledgerGrid_with_header);

		// Add the TableRow to the TableLayout
		ledgertable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
	}

	void addRow(String param, final int i) {

		tr.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// fade the row color(black/gray to orange) when clicked
				View row = ledgertable.getChildAt(i + 1);

				for (int j = 0; j < len; j++) {
					LinearLayout l = (LinearLayout) ((ViewGroup) row).getChildAt(j);
					TextView t = (TextView) l.getChildAt(0);
					ColorDrawable drawable = (ColorDrawable)t.getBackground();
					System.out.println("color:"+drawable.getColor());

					ObjectAnimator colorFade = ObjectAnimator.ofObject(t, "backgroundColor", new ArgbEvaluator(), Color.parseColor("#FBB117"),drawable.getColor());
					colorFade.setDuration(100);
					colorFade.start();
				}

				MainActivity.nameflag = true;

				name = "Voucher details";

				MainActivity.searchFlag = true;

				Object[] params = new Object[] { "Dr" };
				Accountlist = new ArrayList<String>();

				code = ledgerGrid_with_voucherCode.get(i).get(6).toString();
				String abc = ledgerGrid.get(i).get(1).toString();

				Object[] params1 = new Object[] { code };

				Object[] VoucherMaster = (Object[]) transaction.getVoucherMaster(params1, client_id);
				// System.out.println("i am new object"+VoucherMaster);

				ArrayList otherdetailsrow = new ArrayList();
				for (Object row1 : VoucherMaster) {
					Object a = (Object) row1;
					otherdetailsrow.add(a.toString());// getting vouchermaster
														// details
				}

				String vtf = otherdetailsrow.get(2).toString();
				System.out.println("vtf"+vtf);
				System.out.println("params "+params);
				IntentToVoucher(vtf, params);

				Intent intent = new Intent(ledger.this, transaction_tab.class);
				intent.putExtra("flag", "from_ledger");
				// To pass on the value to the next page
				startActivity(intent);
				// Toast.makeText(context,"name"+name,Toast.LENGTH_SHORT).show();
				return false;

			}
		});

		label = new TextView(this);
		label.setText(param);
		label.setTextColor(Color.WHITE);
		label.setTextSize(18);
		// label.setBackgroundColor(Color.);
		label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT));
		label.setBackgroundColor(Color.BLACK);
		label.setPadding(2, 2, 2, 2);

		Ll = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.setMargins(1, 1, 1, 1);
		// Ll.setPadding(10, 5, 5, 5);
		Ll.addView(label, params);
		tr.addView((View) Ll);

	}

	void IntentToVoucher(String vtf, Object[] params) {
		// for "Contra" voucher
		if (vtf.equalsIgnoreCase("Contra")) {
			contraJournal(vtf, params);

		}
		if (vtf.equalsIgnoreCase("Journal")) {

			contraJournal(vtf, params);
		}
		if (vtf.equalsIgnoreCase("Payment")) {

			exceptContraJournal(vtf, params);
		}
		if (vtf.equalsIgnoreCase("Receipt")) {
			exceptContraJournal(vtf, params);
		}
		if (vtf.equalsIgnoreCase("Credit Note")) {
			exceptContraJournal(vtf, params);
		}
		if (vtf.equalsIgnoreCase("Debit Note")) {
			exceptContraJournal(vtf, params);
		}
		if (vtf.equalsIgnoreCase("Sales")) {
			exceptContraJournal(vtf, params);
		}
		if (vtf.equalsIgnoreCase("Sales Return")) {
			exceptContraJournal(vtf, params);
		}
		if (vtf.equalsIgnoreCase("Purchase")) {
			exceptContraJournal(vtf, params);

		}
		if (vtf.equalsIgnoreCase("Purchase Return")) {
			exceptContraJournal(vtf, params);
		}

	}

	void exceptContraJournal(String vtf, Object[] params) {
		vouchertypeflag = vtf;

		DrAccountlist = new ArrayList<String>();
		Object[] paramDr = new Object[] { "Dr" };
		m.getAccountsByRule(paramDr, vouchertypeflag, context);
		Accountlist = module.Accountlist;
		
		DrAccountlist.addAll(Accountlist);
		
		CrAccountlist = new ArrayList<String>();
		Object[] paramCr = new Object[] { "Cr" };
		m.getAccountsByRule(paramCr, vouchertypeflag, context);
		Accountlist = module.Accountlist;
		CrAccountlist.addAll(Accountlist);
		

		if (DrAccountlist.size() < 1 || CrAccountlist.size() < 1) {
			m.toastValidationMessage(ledger.this, msg);
		} else {
			System.out.println("..paramCr go to transaction tab");
			Intent intent = new Intent(context, transaction_tab.class);
			// To pass on the value to the next page
			startActivity(intent);
		}

	}

	void contraJournal(String vtf, Object[] params) {
		vouchertypeflag = vtf;
		m.getAccountsByRule(params, vouchertypeflag, context);

		Accountlist = module.Accountlist;
		System.out.println("Accountlist Contra"+Accountlist);
		if (Accountlist.size() < 2) {
			m.toastValidationMessage(ledger.this, msg);
		} else {
			System.out.println("listtt to pass transaction");
			Intent intent = new Intent(context, transaction_tab.class);
			// To pass on the value to the next page
			startActivity(intent);
		}

	}

	public void onBackPressed() {
		if (get_extra_flag == null) {

			MainActivity.nameflag = false;
			Intent intent = new Intent(getApplicationContext(),reportMenu.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

		} else if (get_extra_flag.equalsIgnoreCase("from_trialBal")) {
			get_extra_flag = null;// so that on backpress it will to reportmenu
									// page
			Intent intent = new Intent(getApplicationContext(),trialBalance.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else if (get_extra_flag.equalsIgnoreCase("from_projStatement")) {
			get_extra_flag = null;// so that on backpress it will to reportmenu
									// page
			Intent intent = new Intent(getApplicationContext(),projectStatement.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else if (get_extra_flag.equalsIgnoreCase("from_cashflow")) {
			get_extra_flag = null;// so that on backpress it will to reportmenu
									// page
			Intent intent = new Intent(getApplicationContext(), cashFlow.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else if (get_extra_flag.equalsIgnoreCase("from_balanceSheet")) {
			get_extra_flag = null;// so that on backpress it will to reportmenu
									// page
			Intent intent = new Intent(getApplicationContext(),  balanceSheet.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}else if (get_extra_flag.equalsIgnoreCase("from_IncExp")) {
			get_extra_flag = null;// so that on backpress it will to reportmenu
			// page
			Intent intent = new Intent(getApplicationContext(), incomeExpenditure.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}else if(get_extra_flag.equalsIgnoreCase("from_cashbook")) {
			get_extra_flag = null;// so that on backpress it will to reportmenu
									// page
			Intent intent = new Intent(getApplicationContext(), cashBook.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	}

}