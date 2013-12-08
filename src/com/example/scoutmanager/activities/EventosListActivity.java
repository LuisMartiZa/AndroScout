package com.example.scoutmanager.activities;

import java.util.ArrayList;

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
	private ArrayList<Evento> arrayListEventos= new ArrayList<Evento>();

    
    private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> pParent, View pView, int pPosition, long id) {
			try {
				Intent detailIntent = new Intent(EventosListActivity.this, EventosDetailActivity.class);
				detailIntent.putExtra("eventoID", arrayListEventos.get(pPosition).getID());
				detailIntent.putExtra("modo", "editar");

				startActivityForResult(detailIntent,1);
				
	        } catch (Exception e) {
				Toast.makeText(pView.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
			}
		}
    };
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eventos_list_activity);
		
		arrayListEventos = new ArrayList<Evento>();

        try {
        	fillArrayListEventos();
        	initializeListView();
        	//initializePopUp();
			ActionBar actionbar;
			actionbar= getActionBar();
			actionbar.setTitle("EVENTOS");
        	
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
	
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {	 
         if(resultCode == RESULT_OK){
       	  	Intent refresh = new Intent(this, EventosListActivity.class);
            startActivity(refresh);
            this.finish();
         }
	 }
	
	private void initializeListView() throws AdaFrameworkException {
				
	   	this.eventosListView = (ListView) findViewById(R.id.EventosListView);
	   	
	   	if (eventosListView != null) {
	   		eventosListView.setOnItemClickListener(itemClickListener);
	   		eventosListViewAdapter= new EventosListAdapter(EventosListActivity.this, R.layout.eventos_row, arrayListEventos);
	   		eventosListView.setAdapter(eventosListViewAdapter);
	   	}
	}
	
	private void fillArrayListEventos() throws AdaFrameworkException{
		
		DataBase.Context.EventosSet.fill();
		int nEventos= DataBase.Context.EventosSet.size();
		
		arrayListEventos= new ArrayList<Evento>();
		
		for(int i=0; i<nEventos;++i){
			Evento evento= DataBase.Context.EventosSet.get(i);
			
			arrayListEventos.add(evento);
		}
	}
	
	 public void executeAddNewCommand() {
    	try {
    		Intent detailIntent = new Intent(this, EventosDetailActivity.class);
			detailIntent.putExtra("modo", "nuevo");

    		startActivityForResult(detailIntent,1);    		
    		
    	} catch (Exception e) {
 			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
 		}
    }
}
