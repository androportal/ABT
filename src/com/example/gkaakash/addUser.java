package com.example.gkaakash;

import com.gkaakash.controller.Startup;
import com.gkaakash.controller.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class addUser extends Activity {
	TextView tvheader,tvwarning,tvmessage;
	Button btncancel,btndone;
	TableRow truserrole,transwer,trquestion,radiouserole;
	EditText eusername,epassword,econfpassword;
	RadioGroup radiogender,radiorole;
	String gender,username,password,confpassword,userrole;
	int rbRoleCheckedId,rbGenderCheckedId,client_id;
	RadioButton rbRoleChecked,rbGenderChecked;
	module module;
	User user;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the layout signup view to this content
		setContentView(R.layout.sign_up);
		// get the id of signup.xml header 
		tvheader = (TextView) findViewById(R.id.tvalertHead1);
		tvheader.setVisibility(View.GONE); // set visibility gone of header 
		// get the id of table row of user role and visible it
		truserrole = (TableRow) findViewById(R.id.trUserRole);
		truserrole.setVisibility(View.VISIBLE);
		// get the id of cancel button and change the text to Reset
		btncancel =  (Button) findViewById(R.id.btnCancel);
		btncancel.setText("Reset");
		// get the id of question row and answer row and invisible it
		transwer = (TableRow) findViewById(R.id.trAnswer);
		trquestion = (TableRow) findViewById(R.id.trQuestion);
		transwer.setVisibility(View.GONE);
		trquestion.setVisibility(View.GONE);
		// get all widget id's to use 
		btndone = (Button) findViewById(R.id.btnSignUp);
		eusername =(EditText) findViewById(R.id.eUserName);
		epassword =(EditText) findViewById(R.id.ePassword);
		econfpassword =(EditText) findViewById(R.id.eConfPassword);
		radiogender =(RadioGroup) findViewById(R.id.radioGender);
		radiorole  = (RadioGroup) findViewById(R.id.radioRole);
		radiouserole =(TableRow) findViewById(R.id.trUserRole);
		tvwarning =(TextView)findViewById(R.id.tvWarning);
		tvmessage = (TextView) findViewById(R.id.tvSignUp);
		rbRoleCheckedId = radiorole.getCheckedRadioButtonId();
		rbRoleChecked = (RadioButton) findViewById(rbRoleCheckedId);
		rbGenderCheckedId = radiogender.getCheckedRadioButtonId();
		rbGenderChecked = (RadioButton) findViewById(rbGenderCheckedId);
		// create instance of module class to get isEmpty method
		module = new module();
		// create instance of user class to call setUser method
		user = new User();
		// get the client_id from startup
		
		client_id = Startup.getClient_id();
		// call addClickListenerOnButton
		addOnClickListnereOnButton();
	}// end onCreate method
	

	public void addOnClickListnereOnButton()
	{
		btndone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gender = rbGenderChecked.getText().toString();
				username = eusername.getText().toString();
				password = epassword.getText().toString();
				confpassword= econfpassword.getText().toString();
				userrole = rbRoleChecked.getText().toString();
				tvwarning.setVisibility(TextView.GONE);
				Object[] params = new Object[]{username,password,gender,userrole,"null","null"};

				if(module.isEmpty(params)||module.isEmpty(new Object[]{confpassword}))
				{
					String message = "please fill blank field";
					tvwarning.setVisibility(TextView.VISIBLE);
					tvwarning.setText(message);

				}else if(!password.equals(confpassword))
				{
					epassword.setText("");
					econfpassword.setText("");
					String message = "Please enter correct password";
					tvwarning.setVisibility(TextView.VISIBLE);
					tvwarning.setText(message);
				}else   
				{
					boolean unique = user.isUserUnique(new Object[]{username},client_id);
					if(unique==true)
					{	 
						String setuser = user.setUser(params, client_id);
						AlertDialog.Builder builder = new AlertDialog.Builder(addUser.this);
				        builder.setMessage(username+" added successfully as "+userrole);
				        AlertDialog alert = builder.create();
				        alert.show();
				        alert.setCancelable(true);
				        alert.setCanceledOnTouchOutside(true);
						reset();
					}
					else
					{
						eusername.setText("");
						String message = "username is already exist";
						tvwarning.setVisibility(TextView.VISIBLE);
						tvwarning.setText(message);
					}
				} 
			}	
		}); // end of btndone Onclick
		
		// call onClickListner on reset button to reset all field
		btncancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				reset();
				
			}// end of btncancel Onclick
			
		});// end btnCancel setOnclickListener
		
	}// end addOnClickListnereOnButton()

	// reset will reset all fields after set user successfully
	void reset()
	{
		eusername.setText("");
		epassword.setText("");
		econfpassword.setText("");
	}

}// end addUser class