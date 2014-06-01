package com.example.contactretrieveloaderanonymos;



import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends ListActivity  
{
	private final static String TAG = MainActivity.class.getSimpleName();//  <==> "MainActivity";

	private CursorAdapter adapter;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>()
        {

        	@SuppressLint("NewApi")
			@Override
        	public Loader<Cursor> onCreateLoader(int id, Bundle args) 
        	{
        		// Run query
        		 Uri uri = ContactsContract.Contacts.CONTENT_URI;
        	        
        	        Log.d(TAG, uri.toString());
        	        
        	        String[] projection = new String[] { ContactsContract.Contacts._ID,
        	            ContactsContract.Contacts.DISPLAY_NAME };
        	        
//        	        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
//        	            + ("1") + "'";
//        	        
        	        
        	        String[] selectionArgs = null;
        	        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
        	            + " COLLATE LOCALIZED ASC";
        		
        		
        		CursorLoader cursorLoader = 
        				new CursorLoader(MainActivity.this, uri, projection, null, null, null);
        		
        		return cursorLoader;
        	}
        	
        		@Override
        		public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        		
        	    adapter = new CursorAdapter(MainActivity.this, cursor, true)
                {

        			@Override
        			public void bindView(View view, Context arg1, Cursor cursor) 
        			{
        				TextView textView = (TextView)view;
        				String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        				textView.setText(displayName);
        			}

        			@Override
        			public View newView(Context arg0, Cursor arg1, ViewGroup arg2) 
        			{
        				return new TextView(MainActivity.this);
        			}
                	
                };

                
                getListView().setAdapter(adapter);
                adapter.swapCursor(cursor);
        		
        		
        	}

        	@SuppressLint("NewApi")
			@Override
        	public void onLoaderReset(Loader<Cursor> loader) {
        		adapter.swapCursor(null);
        		
        	}
					
        		}); 
        
        setListAdapter(adapter);
    }

}
