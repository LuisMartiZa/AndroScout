package com.example.scoutmanager.activities;

import java.util.ArrayList;

import com.example.scoutmanager.R;
import com.example.scoutmanager.adapters.TutoresListAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Tutor;
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

public class TutoresListActivity extends Activity {
	
	private ListView tutoresListView;
    private ArrayAdapter<Tutor> tutoresListViewAdapter;
	private ArrayList<Tutor> arrayListTutores= new ArrayList<Tutor>();
	    
    private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> pParent, View pView, int pPosition, long id) {
			try {
	        	
				Intent deatailIntent = new Intent(TutoresListActivity.this, TutoresDetailActivity.class);
				deatailIntent.putExtra("tutorID", arrayListTutores.get(pPosition).getID());
				startActivityForResult(deatailIntent, 1);
	        	
	        } catch (Exception e) {
				Toast.makeText(TutoresListActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
			}
		}
    };
    
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.tutores_list_activity);
    	
    	arrayListTutores= new ArrayList<Tutor>();

        try {
        	fillArrayListTutores();
        	initializeListView();
        	//initializePopUp();
			ActionBar actionbar;
			actionbar= getActionBar();
			actionbar.setTitle("TUTORES");
        	
        } catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {	 
          /*if (requestCode == NEW_REQUEST && resultCode == RESULT_OK) {
        	try {
				DataBase.Context.TutoresSet.fill();
	      		Tutor tutor= DataBase.Context.TutoresSet.get(DataBase.Context.TutoresSet.size()-1);
	      		
	      		arrayListTutores.add(tutor);
	      		
	      		initializeListView();
			} catch (AdaFrameworkException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          }
          if (requestCode == SELECT_REQUEST && resultCode == RESULT_OK) {
        	  //TODO: Recuperar el intent y coger cada id y meterlo en el arraylisttutores.
        	  tutoresID = intentExtras.getIntegerArrayList("tutoresID");
        	  
        	  for(int i=0; i< tutoresID.size(); ++i){
        		  
        	  }
        	  
          }*/
          
          if(resultCode == RESULT_OK){
        	  Intent refresh = new Intent(this, TutoresListActivity.class);
              startActivity(refresh);
              this.finish();
          }
	 }
	
	private void initializeListView() throws AdaFrameworkException {
				
    	this.tutoresListView = (ListView) findViewById(R.id.tutoresListView);
    	
    	if (this.tutoresListView != null) {
    		this.tutoresListView.setOnItemClickListener(itemClickListener);
    		this.tutoresListViewAdapter= new TutoresListAdapter(TutoresListActivity.this, R.layout.tutores_row, arrayListTutores);
    		this.tutoresListView.setAdapter(this.tutoresListViewAdapter);
    	}
    }
    
    private void executeAddNewCommand() {
    	try {
    		
    		Intent detailIntent = new Intent(TutoresListActivity.this, TutoresDetailActivity.class);
    		startActivityForResult(detailIntent, 1);
    		
    	} catch (Exception e) {
 			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
 		}
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.list_action, menu);
	    return super.onCreateOptionsMenu(menu);
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.newAction:
	        	executeAddNewCommand();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void fillArrayListTutores() throws AdaFrameworkException{
		
		DataBase.Context.TutoresSet.fill();
		int nTutores= DataBase.Context.TutoresSet.size();
		
		arrayListTutores= new ArrayList<Tutor>();
		
		for(int i=0; i<nTutores;++i){
			Tutor aux= DataBase.Context.TutoresSet.get(i);
			
			arrayListTutores.add(aux);
		}
		

	}

}
