package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;
import com.example.scoutmanager.fragments.EducandosDetailFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
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
	
	public void executeDeleteCommand(View pView) {
		try {
			Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.EducandosDetailFragment);
	    	if (fragment != null) {
	    		((EducandosDetailFragment)fragment).executeDeleteCommand(pView);
	    	}
	    	
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	public void executeSaveCommand(View pView) {
		try {
			
			Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.EducandosDetailFragment);
	    	if (fragment != null) {
	    		((EducandosDetailFragment)fragment).executeSaveCommand(pView);
	    	}
			
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}

}