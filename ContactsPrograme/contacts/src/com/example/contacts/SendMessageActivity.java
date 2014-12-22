package com.example.contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessageActivity extends Activity {

	private String keywords;
	private EditText ed1 = null;
	private String strPhoneNumber;
	private String message = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_msg);
		keywords = getIntent().getExtras().getString("name");

		Builder bulider = new AlertDialog.Builder(this);
		bulider.setTitle("我操！快选择啊");
		bulider.setMessage("你特么真的要给他发送短信么？");
		bulider.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Uri allcontacts = ContactsContract.Contacts.CONTENT_URI;
				String[] projection = new String[] {
						ContactsContract.Contacts._ID,
						ContactsContract.Contacts.DISPLAY_NAME,
						ContactsContract.Contacts.HAS_PHONE_NUMBER };

				ContentResolver cr = getContentResolver();
				 Cursor c; 
			        {
			        	//---Honeycomb and later---
			            CursorLoader cursorLoader = new CursorLoader(
			            		SendMessageActivity.this, 
			            		allcontacts, 
			            		projection, 
			                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?",
			                    new String[] {"%"+keywords+"%"}, 
			                    ContactsContract.Contacts.DISPLAY_NAME + " ASC");
			            c = cursorLoader.loadInBackground();        	
			        }

				while (c.moveToNext()) {

		        	String str = "";
		            message = "";
		            int nameFieldColumnIndex = c.getColumnIndex(PhoneLookup.DISPLAY_NAME);   
		            String name = c.getString(nameFieldColumnIndex);   
		            str = str + name;

		            
		            String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));   
		            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,  ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "  
		                    + contactId, null, null); 

		            while (phone.moveToNext())   
		            {   
		                strPhoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));   
		                str = str + " : " + strPhoneNumber;
		                
		                //sendSMS(strPhoneNumber, message);
		                
		                Toast.makeText(SendMessageActivity.this, str, Toast.LENGTH_LONG).show();
		                //Toast.makeText(SendMessageActivity.this, message, Toast.LENGTH_LONG).show();
		            }   
		            
		            phone.close();   
		        }   
		        c.close();  
		        Toast.makeText(SendMessageActivity.this, "短信发送完毕", Toast.LENGTH_LONG).show();

			}

		});
		bulider.setNegativeButton("不发送短信",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						Log.i("", "setNegativeButton");
					}
				});

		bulider.setCancelable(false);

		Dialog dialog = bulider.create();
		dialog.show();

	}
	private void sendSMS(String phoneNumber, String message){
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, null, null);
	}
	
	public void send_msg(View view){
		ed1 = (EditText) findViewById(R.id.send_editText1);
        String message = "";
        String temp = ed1.getText().toString();
        Toast.makeText(SendMessageActivity.this, temp, Toast.LENGTH_LONG).show();
        message = message + ed1.getText().toString();
		sendSMS(strPhoneNumber, message);
		Toast.makeText(SendMessageActivity.this, message, Toast.LENGTH_LONG).show();
	}
	public void back(View view){
		Intent i = new Intent();
		i.setClass(SendMessageActivity.this, LoginActivity.class);
		startActivity(i);
	}
}
