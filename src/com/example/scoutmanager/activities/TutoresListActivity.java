package com.example.scoutmanager.activities;

import java.util.ArrayList;

import com.example.scoutmanager.R;
import com.example.scoutmanager.activities.EducandosDetailActivity;
import com.example.scoutmanager.adapters.TutoresListAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
import com.example.scoutmanager.model.entities.Tutor;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
				deatailIntent.putExtra("tutorID", pPosition);
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

        try {
        	Bundle intentExtras = this.getIntent().getExtras();
        	
        	if (intentExtras != null)
				Log.v("EducandoID", "ID " + intentExtras.getLong("educandoID"));
        		fillArrayListTutores(intentExtras.getLong("educandoID"));
        	
        	initializeListView();
			ActionBar actionbar;
			actionbar= getActionBar();
			actionbar.setTitle("TUTORES");
        	
        } catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
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
    
    public void executeAddNewCommand() {
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
	
	private void fillArrayListTutores(Long educandoID) throws AdaFrameworkException{
		
		DataBase.Context.EducandosSet.fill();
		Educando educando= DataBase.Context.EducandosSet.getElementByID(educandoID);
		
		Log.v("EDUCANDO", "EducandoID" + educando.getNombre());
		
		arrayListTutores= new ArrayList<Tutor>();
		arrayListTutores= (ArrayList<Tutor>) educando.getTutorEducando();
	}

}
