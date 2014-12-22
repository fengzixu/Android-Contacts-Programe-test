package com.example.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	}
	
	public void add_members(View view){
		
		Intent i = new Intent();
		i.setClass(LoginActivity.this, AddContactsActivity.class);
		startActivity(i);
	}
	
	public void back_main(View view){
		Intent i = new Intent();
		i.setClass(LoginActivity.this, MainActivity.class);
		startActivity(i);
	}
	
	public void show_members(View view){
		
		Intent i = new Intent();
		i.setClass(LoginActivity.this, ShowContactsActivity.class);
		startActivity(i);
	}

	public void input_search_members(View view){
		Intent i = new Intent();
		i.setClass(LoginActivity.this, InputMembersActivity.class);
		startActivity(i);
	}
}
