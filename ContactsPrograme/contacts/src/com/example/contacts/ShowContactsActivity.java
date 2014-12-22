package com.example.contacts;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShowContactsActivity extends ListActivity{
	
   Context mContext = null;
   private static final String[] PHONES_PROJECTION = new String[] {  
        Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };
    /**联系人显示名称**/  
   private static final int PHONES_DISPLAY_NAME_INDEX = 0;  
     
   /**电话号码**/  
   private static final int PHONES_NUMBER_INDEX = 1;  
     
   /**头像ID**/  
   private static final int PHONES_PHOTO_ID_INDEX = 2;  
    
   /**联系人的ID**/  
   private static final int PHONES_CONTACT_ID_INDEX = 3;
   
   //存储联系人名字
   private ArrayList<String> mContextName = new ArrayList<String>();
   
   //存储联系人电话
   private ArrayList<String> mContextNumber = new ArrayList<String>();
   
   //存储联系人头像
   private ArrayList<Bitmap> mContextPhoto = new ArrayList<Bitmap>();
   
   
   
   ListView mListView = null;  
   MyListAdapter myAdapter = null;  
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//setContentView(R.layout.show_members);
//		Uri uri = Uri.parse("content://com.android.contacts/data/phones");	
		mContext = this;
		mListView = this.getListView();
		
		getPhoneContacts();
		myAdapter = new MyListAdapter(this);
		setListAdapter(myAdapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> adapterView, View view,
			    int position, long id) {
			Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri
				.parse("tel:" + mContextNumber.get(position)));
			startActivity(dialIntent);
		    }
		});
		
		super.onCreate(savedInstanceState);
	}
   
   private void getPhoneContacts(){
	   
	   ContentResolver resolver = mContext.getContentResolver();
	   Cursor phonecursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
	   
	   if(phonecursor != null){
		   while(phonecursor.moveToNext()){
			   
			   String phoneNumber = phonecursor.getString(PHONES_NUMBER_INDEX);
			   if(TextUtils.isEmpty(phoneNumber))
				   continue;
			   
			   String contactName = phonecursor.getString(PHONES_DISPLAY_NAME_INDEX);
			   
			   long contactid = phonecursor.getLong(PHONES_CONTACT_ID_INDEX);
			   
			 //  long photoid = phonecursor.getLong(PHONES_PHOTO_ID_INDEX);
			   
			  // Bitmap contactPhoto = null;
			   
			 //  if(photoid > 0){
//				   
//				   Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
//				   InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
//				   contactPhoto = BitmapFactory.decodeStream(input);
//			   }
//			   else
//			   {
//				   contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.contactphoto);
//			   }
			   
			   mContextName.add(contactName);
			   mContextNumber.add(phoneNumber);
			  // mContextPhoto.add(contactPhoto);
			   }
		   
		   phonecursor.close();
	   }
   }
   
   
   class MyListAdapter extends BaseAdapter{
	   
	   public MyListAdapter(Context context){
		   mContext = context;
	   }
	   
	   public int getCount(){
		   
		   return mContextName.size();
	   }

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public boolean areAllItemsEnabled(){
		return false;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView image = null;
		TextView title = null;
		TextView text = null;
		if(convertView == null || position < mContextNumber.size()){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.show_members, null);
		title = (TextView)convertView.findViewById(R.id.contactName);
		text = (TextView)convertView.findViewById(R.id.contact_mobile);
		}
		title.setText(mContextName.get(position));
		text.setText(mContextNumber.get(position));
		
		return convertView;
		
	}
   }

}
