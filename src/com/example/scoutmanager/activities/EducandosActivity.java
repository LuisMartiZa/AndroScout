package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;
import com.example.scoutmanager.fragments.EducandosListFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class EducandosActivity extends FragmentActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
        	
        	setContentView(R.layout.educandos_list_activity);
        	
        } catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.educando_list_action, menu);
	    return super.onCreateOptionsMenu(menu);
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.new_educando:
	            executeAddNewCommand();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
    public void executeAddNewCommand() {
    	try {
	    	
    		Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.EducandosListFragment);
	    	if (fragment != null) {
	    		((EducandosListFragment)fragment).executeAddNewCommand();
	    	}
	    	
    	} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
    }

}
