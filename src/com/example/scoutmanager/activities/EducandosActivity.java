package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;
import com.example.scoutmanager.activities.EducandosDetailActivity;
import com.example.scoutmanager.adapters.EducandosListAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EducandosActivity extends Activity {
	
	private ListView educandosListView;
    private ArrayAdapter<Educando> educandosListViewAdapter; 
    
    private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> pParent, View pView, int pPosition, long id) {
			try {
	        	
				Intent deatailIntent = new Intent(EducandosActivity.this, EducandosDetailActivity.class);
				deatailIntent.putExtra("educandoID", pPosition);
				startActivityForResult(deatailIntent, 1);
	        	
	        } catch (Exception e) {
				Toast.makeText(EducandosActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
			}
		}
    };
    
	@Override
	public void onCreate(Bundle savedInstanceState) {   
		
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.educandos_list_activity);

        try {        	
        	initializeFragment();
			ActionBar actionbar;
			actionbar= getActionBar();
			actionbar.setTitle("EDUCANDOS");
        	
        } catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	private void initializeFragment() throws AdaFrameworkException {
				
    	this.educandosListView = (ListView) findViewById(R.id.EducandosListView);
    	
    	if (this.educandosListView != null) {
    		this.educandosListView.setOnItemClickListener(itemClickListener);
    		this.educandosListViewAdapter= new EducandosListAdapter(EducandosActivity.this, R.layout.educandos_row);
    		this.educandosListView.setAdapter(this.educandosListViewAdapter);
    		this.educandosListView.setFooterDividersEnabled(true);
    		this.educandosListView.setHeaderDividersEnabled(true);
    		
    		DataBase.Context.EducandosSet.setAdapter(this.educandosListViewAdapter);
    		DataBase.Context.EducandosSet.fill();
    	}
    }
    
    public void executeAddNewCommand() {
    	try {
    		
    		Intent detailIntent = new Intent(EducandosActivity.this, EducandosDetailActivity.class);
    		startActivityForResult(detailIntent, 1);
    		
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
	            executeAddNewCommand();;
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
