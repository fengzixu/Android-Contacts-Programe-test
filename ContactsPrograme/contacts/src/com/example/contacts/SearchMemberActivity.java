package com.example.contacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SearchMemberActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_list);
		String keywords = getIntent().getExtras().getString("name");
		Uri allcontacts = ContactsContract.Contacts.CONTENT_URI;

		String[] projection = new String[] { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts.HAS_PHONE_NUMBER };
		Cursor c;
		{
			CursorLoader cursorLoader = new CursorLoader(this, allcontacts,
					projection, ContactsContract.Contacts.DISPLAY_NAME
							+ " LIKE ?", new String[] { "%" + keywords + "%" },
					ContactsContract.Contacts.DISPLAY_NAME + " ASC");
			c = cursorLoader.loadInBackground();
		}

		String[] columns = new String[] {
				ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts._ID };
		int[] views = new int[] { R.id.contactName, R.id.contact_mobile };
//		SimpleCursorAdapter adapter;
//
//		{
//	   adapter = new SimpleCursorAdapter(this, 
//		R.layout.search_list,
//		c,
//		columns, 
//		views,
//					CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//
//		}
//		this.setListAdapter(adapter);
        setListAdapter(new SimpleAdapter(this,   
                getData(" "),   
                R.layout.search_list,   
                new String[]{"name", "phone"}, 
                views));
		//PrintContacts(c);
	}

	/** 
     * ¹¹ÔìSimpleAdapterµÄµÚ¶þ¸ö²ÎÊý£¬ÀàÐÍÎªList<Map<?,?>> 
     * @param style 
     * @return 
     */  
    private List<Map<String, String>> getData(String style) {  
        List<Map<String, String>> listData = new ArrayList<Map<String, String>>();  
        
        //---get all diary---
//        db.open();
//        Cursor c = db.getAllContacts();
        String name = "";
        String phone = "";
        Map<String, String> map = new HashMap<String, String>();
		String keywords = getIntent().getExtras().getString("name");
		Uri allcontacts = ContactsContract.Contacts.CONTENT_URI;

		String[] projection = new String[] { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.Contacts.HAS_PHONE_NUMBER };
		Cursor c;
		{
			CursorLoader cursorLoader = new CursorLoader(this, allcontacts,
					projection, ContactsContract.Contacts.DISPLAY_NAME
							+ " LIKE ?", new String[] { "%" + keywords + "%" },
					ContactsContract.Contacts.DISPLAY_NAME + " ASC");
			c = cursorLoader.loadInBackground();
		}

        if (c.moveToFirst())
        {
        	do {
				String contactid = c.getString(c
						.getColumnIndex(ContactsContract.Contacts._ID));
				String contactdisplayname = c
						.getString(c
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				Log.v("Content Providers", contactid + ", "
						+ contactdisplayname);

				int hasPhone = c
						.getInt(c
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				if (hasPhone == 1) {
					Cursor phoneCursor = getContentResolver().query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = " + contactid, null, null);
					while (phoneCursor.moveToNext()) {
						phone = phoneCursor.getString(phoneCursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						Log.v("content providers",phone);

					}
					phoneCursor.close();
				}
				
	        	map.put("name", c.getString(1));
	        	map.put("phone", phone);
	        	map.put("_id", contactid);
	        	listData.add(map);
			} while (c.moveToNext());
        }
        
 
        return listData;  
    }
	
    protected void onListItemClick(ListView listView, View v, int position, long id) {  
        Map map = (Map)listView.getItemAtPosition(position);
        String str_id = map.get("_id").toString(); 
        String str_name = map.get("name").toString();
        String str_phone = map.get("phone").toString();
        Toast toast = Toast.makeText(this, "id="+str_id+" is selected.", Toast.LENGTH_SHORT);  
        toast.show();  
        
        Intent i = new Intent();
    	i.setClass(SearchMemberActivity.this, MotifyActivity.class);
    	i.putExtra("modify_id", str_id);
    	i.putExtra("motify_name", str_name);
    	i.putExtra("motify_phone", str_phone);
    	startActivity(i);
    }  
	
}
