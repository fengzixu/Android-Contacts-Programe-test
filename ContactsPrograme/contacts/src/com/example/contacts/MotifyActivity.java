package com.example.contacts;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MotifyActivity extends Activity{
	
	private EditText ed1 = null;
	private EditText ed2 = null;
	private String keywords = null;
	private String name = null;
	private String phone = null;
	private int _id = 0;
    private String NewName = null;
    private String NewPhone =null;
	Uri uri = Uri.parse("content://com.android.contacts/data");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.motify);
	    String NewName = null;
	    String NewPhone =null;
		ed1 = (EditText)findViewById(R.id.input_name_editText1);
		ed2 = (EditText)findViewById(R.id.updatehaha_editText1); 
		keywords = getIntent().getExtras().getString("modify_id");
		name = getIntent().getExtras().getString("motify_name");
		phone = getIntent().getExtras().getString("motify_phone");
//		Toast toast1 = Toast.makeText(this, "phone="+name+" is selected.", Toast.LENGTH_SHORT);  
//        toast1.show();
		_id = Integer.parseInt(keywords);
		
	}

	public void update_data(View view){

		NewName = ed1.getText().toString();
		NewPhone = ed2.getText().toString();
		//Toast toast1 = Toast.makeText(this, "phone="+NewPhone+" is selected.", Toast.LENGTH_SHORT);  
        //toast1.show();
//        Toast toast2 = Toast.makeText(this, "phone="+NewName+" is selected.", Toast.LENGTH_SHORT);  
//        toast2.show();
		ContentResolver resolver = this.getBaseContext().getContentResolver();  
	    ContentValues values = new ContentValues();  
	    ContentValues values2 = new ContentValues(); 
	    values.put("data1", NewPhone);
	    values2.put("data1", NewName);
	    
	    resolver.update(uri, values, "mimetype=? and raw_contact_id=?", new String[]{"vnd.android.cursor.item/phone_v2",_id+""});
	    resolver.update(uri, values2, "mimetype=? and raw_contact_id=?", new String[]{"vnd.android.cursor.item/name",_id+""});
	    Toast toast1 = Toast.makeText(this, "name="+name+"已经更新为"+NewName, Toast.LENGTH_SHORT);  
        toast1.show();
	    Toast toast2 = Toast.makeText(this, "phone="+phone+"已经更新为"+NewPhone, Toast.LENGTH_SHORT);  
        toast2.show();
	}
	
	
	public void delete_data(View view){
		NewName = ed1.getText().toString();
		NewPhone = ed2.getText().toString();
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts"); 
		ContentResolver resolver = this.getBaseContext().getContentResolver();  
	    Cursor cursor = resolver.query(uri, new String[]{Data._ID},"display_name=?", new String[]{name}, null); 
	    if(cursor.moveToFirst()){  
	        int id = cursor.getInt(0);  
	        //根据id删除data中的相应数据  
	        resolver.delete(uri, "display_name=?", new String[]{name});  
	        uri = Uri.parse("content://com.android.contacts/data");  
	        resolver.delete(uri, "raw_contact_id=?", new String[]{id+""});
	      Toast toast1 = Toast.makeText(this, "name="+name+" 删除成功.", Toast.LENGTH_SHORT);  
	      toast1.show();
	    }
	}
	
	public void back(View view){
		Intent i = new Intent();
		i.setClass(MotifyActivity.this, LoginActivity.class);
		startActivity(i);
	}
}
