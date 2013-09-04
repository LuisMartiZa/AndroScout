package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;
import com.example.scoutmanager.fragments.EducandosListFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
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
    
    public void executeAddNewCommand(View pView) {
    	try {
	    	
    		Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.EducandosListFragment);
	    	if (fragment != null) {
	    		((EducandosListFragment)fragment).executeAddNewCommand(pView);
	    	}
	    	
    	} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
    }

}
