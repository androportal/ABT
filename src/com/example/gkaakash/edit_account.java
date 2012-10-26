package com.example.gkaakash;

import java.util.ArrayList;
import java.util.List;

import com.gkaakash.controller.Account;
import com.gkaakash.controller.Startup;

import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class edit_account extends Activity{
   
    private ListView lvitem;
    private EditText etSerach;
    private ArrayList<String> array_sort= new ArrayList<String>();
    int textlength=0;
    static Integer client_id;
    private Account account;
    private Object[] accountnames;
    List accname_list;
   
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_acc_tab);
       
        lvitem = (ListView) findViewById(R.id.ltAccname);
        etSerach = (EditText) findViewById(R.id.etSearch);
        lvitem.setCacheColorHint(color.transparent);
      
       
        account = new Account();
        client_id = Startup.getClient_id();
        System.out.println("client_id :"+client_id);
        getAllAccountNames();
        //attaching listener to textView
        etSerach.addTextChangedListener(new TextWatcher()
        {
        public void beforeTextChanged(CharSequence s,
        int start, int count, int after)
        {
        // Abstract Method of TextWatcher Interface.
        }
        public void onTextChanged(CharSequence s,
        int start, int before, int count)
        {
        //for loop for search
        textlength = etSerach.getText().length();
        array_sort.clear();
       
        for (Object acc : accname_list)
        {
            System.out.println("acc :"+acc);
            if (textlength <= acc.toString().length())
            {
                if(etSerach.getText().toString().equalsIgnoreCase((String) ((String) acc).subSequence(0,textlength)))
                {
                    array_sort.add((String)acc);
                }
            }
        }
       
        lvitem.setAdapter(new ArrayAdapter<String>(edit_account.this,android.R.layout.simple_list_item_1, array_sort));
        }
        @Override
        public void afterTextChanged(Editable arg0) {
            // Abstract Method of ArrayAdapter Interface
        }
        });
 }
    //for getting all acc_names
    void getAllAccountNames(){
        accountnames = (Object[])account.getAllAccountNames(client_id);
        accname_list = new ArrayList();
        System.out.println("accountnames:"+accountnames);
        for(Object an : accountnames)
        {   
            accname_list.add(an); //acc_names
        }   
         lvitem.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, accname_list));
       
       
    }
   
}