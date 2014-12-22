package com.example.contacts;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.EditText;

public class InputMembersActivity extends Activity{
	
	private EditText ed1;
	private EditText ed2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input_search);
		ed1 = (EditText)findViewById(R.id.input_name_editText1);
	}
	
	public void search(View view){
		
		Intent i = new Intent();
		i.setClass(InputMembersActivity.this, SearchMemberActivity.class);
		String name = ed1.getText().toString();
		
		if(!name.equals(""))
		{
			i.putExtra("name", name);
			startActivity(i);
		}
	}
	
	public void back(View view){
		
		Intent i = new Intent();
		i.setClass(InputMembersActivity.this, LoginActivity.class);
		startActivity(i); 
	}
	public void send_msg(View view){
		Intent i = new Intent();
		i.setClass(this, SendMessageActivity.class);
		
		String name = ed1.getText().toString();
		if (!name.equals("")){
			i.putExtra("name", name);
			startActivity(i);
		}
	}
}
