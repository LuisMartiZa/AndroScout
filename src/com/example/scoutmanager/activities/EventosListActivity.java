package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;
import com.example.scoutmanager.adapters.EventosListAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Evento;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EventosListActivity extends Activity {
	
	private ListView eventosListView;
    private ArrayAdapter<Evento> eventosListViewAdapter; 
    
    private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> pParent, View pView, int pPosition, long id) {
			try {
	        	
				Intent detailIntent = new Intent(pView.getContext(), EventosDetailActivity.class);
				detailIntent.putExtra("eventoID", pPosition);
				detailIntent.putExtra("new", false);
				detailIntent.putExtra("edited", false);

				
				startActivity(detailIntent);
			
				
	        } catch (Exception e) {
				Toast.makeText(pView.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
			}
		}
    };
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.eventos_list_activity);
		
		try {
			initializeActivity();
			
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
	
	 public void executeAddNewCommand() {
    	try {
    		Intent detailIntent = new Intent(this, EventosDetailActivity.class);
    		detailIntent.putExtra("newEvent", true);
			detailIntent.putExtra("edited", false);
    		
    		startActivity(detailIntent);
    		
    	} catch (Exception e) {
 			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
 		}
    }
	
	private void initializeActivity() throws AdaFrameworkException {
				
    	this.eventosListView = (ListView) this.findViewById(R.id.EventosListView);
    	
    	if (this.eventosListView != null) {
    		this.eventosListView.setOnItemClickListener(itemClickListener);
    		this.eventosListViewAdapter= new EventosListAdapter(this, R.layout.eventos_row);
    		this.eventosListView.setAdapter(this.eventosListViewAdapter);
    		
    		DataBase.Context.EventosSet.setAdapter(this.eventosListViewAdapter);
    		DataBase.Context.EventosSet.fill();
    		
    		ActionBar actionbar;
    		actionbar= getActionBar();
    		actionbar.setTitle("EVENTOS");
    	}
    }
}
