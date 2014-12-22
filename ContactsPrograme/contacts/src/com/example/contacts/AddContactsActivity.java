package com.example.contacts;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Contacts.Data;
import android.provider.ContactsContract.RawContacts;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactsActivity extends Activity{

	private Button buttonadd = null;
	private Button buttoncancel = null;
	private EditText editName = null;
	private EditText editMobile = null;
	private EditText editAddress = null;
	private EditText editEmail = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contacts);
		
	}
	public void back_login(View view){
		Intent i = new Intent();
		i.setClass(AddContactsActivity.this, LoginActivity.class);
		startActivity(i);
	}
	public void add(View view){
		//获取控件
		editName = (EditText)findViewById(R.id.name_editText1);
		editMobile = (EditText)findViewById(R.id.mobile_editText1);
		//editAddress = (EditText)findViewById(R.id.address_editText2);
		editEmail = (EditText)findViewById(R.id.email_editText2);
		buttonadd = (Button)findViewById(R.id.add_button1);
		buttoncancel = (Button)findViewById(R.id.cancel_button2);
		
		String member_name = editName.getText().toString();
		String member_mobile = editMobile.getText().toString();
		String member_email = editEmail.getText().toString();
		
		ContentValues values = new ContentValues();
		Uri rawContactUri = this.getBaseContext().getContentResolver().insert(RawContacts.CONTENT_URI, values);
		long rawContactId = ContentUris.parseId(rawContactUri);
	    //插入姓名
		values.clear();
        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
        Toast.makeText(AddContactsActivity.this,member_name,1).show();
        values.put(StructuredName.GIVEN_NAME, member_name);
        this.getBaseContext().getContentResolver().insert(
        android.provider.ContactsContract.Data.CONTENT_URI, values);
        //插入电话号码
        values.clear();
        values.put(android.provider.ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
        values.put(Phone.NUMBER, member_mobile);
        values.put(Phone.TYPE, Phone.TYPE_MOBILE);
        this.getBaseContext().getContentResolver().insert(
        android.provider.ContactsContract.Data.CONTENT_URI, values);
        //插入邮箱
        values.clear();
        values.put(android.provider.ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
        values.put(Email.DATA, member_email);
        values.put(Email.TYPE, Email.TYPE_WORK);
        this.getBaseContext().getContentResolver().insert(
        android.provider.ContactsContract.Data.CONTENT_URI, values);
	}
}
