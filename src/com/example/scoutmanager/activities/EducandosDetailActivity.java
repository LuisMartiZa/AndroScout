package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;
import com.example.scoutmanager.fragments.EducandosDetailFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class EducandosDetailActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
        	
        	setContentView(R.layout.educandos_detail_activity);
        	
        } catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.educando_detail_action, menu);
	    return super.onCreateOptionsMenu(menu);
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.accept_educando:
	            executeSaveCommand();
	            return true;
	            
	        case R.id.discard_educando:
	            executeDeleteCommand();
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void executeDeleteCommand() {
		try {
			Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.EducandosDetailFragment);
	    	if (fragment != null) {
	    		((EducandosDetailFragment)fragment).executeDeleteCommand();
	    	}
	    	
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	public void executeSaveCommand() {
		try {
			
			Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.EducandosDetailFragment);
	    	if (fragment != null) {
	    		((EducandosDetailFragment)fragment).executeSaveCommand();
	    	}
			
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}

}