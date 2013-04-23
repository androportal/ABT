package com.example.gkaakash;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class User_table extends Activity {
	String[] ColumnNameList;
	TableRow tr;
	TextView label;
	LinearLayout Ll;
	TableLayout manager_table;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the layout signup view to this content
		setContentView(R.layout.user_table);
		manager_table = (TableLayout) findViewById(R.id.manager_table);
		addTable();
	}// end onCreate method
	
	
	private void addTable(){
		addHeader();
		// Add the TableRow to the TableLayout
		manager_table.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
	}
		
	void addHeader() {
		ColumnNameList = new String[] { "Username", "Last login time" };
		 tr = new TableRow(this);
		 for (int k = 0; k < ColumnNameList.length; k++) {
				/** Creating a TextView to add to the row **/
				addRow(ColumnNameList[k], k);
				tr.setClickable(false);
				label.setBackgroundColor(Color.parseColor("#348017"));
				label.setGravity(Gravity.CENTER);
			}

	}


	private void addRow(String param, int k) {
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

}// end User_table class