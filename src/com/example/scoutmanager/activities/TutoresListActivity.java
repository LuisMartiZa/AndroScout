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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class TutoresListActivity extends Activity implements SearchView.OnQueryTextListener {

    private SearchView mSearchView;
	
	private ListView tutoresListView;
    private ArrayAdapter<Tutor> tutoresListViewAdapter;
	private ArrayList<Tutor> arrayListTutores= new ArrayList<Tutor>();
	    
    private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> pParent, View pView, int pPosition, long id) {
			try {
	        	
				Intent deatailIntent = new Intent(TutoresListActivity.this, TutoresDetailActivity.class);
				deatailIntent.putExtra("tutorID", arrayListTutores.get(pPosition).getID());
				deatailIntent.putExtra("modo", "editar");
				startActivityForResult(deatailIntent, 1);
	        	
	        } catch (Exception e) {
				Toast.makeText(TutoresListActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
			}
		}
    };
    
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		
		super.onCreate(savedInstanceState);
    	getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

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
			detailIntent.putExtra("modo", "nuevo");

    		startActivityForResult(detailIntent, 1);
    		
    	} catch (Exception e) {
 			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
 		}
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    
	    inflater.inflate(R.menu.searchview_in_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);
	    
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
	
	private void setupSearchView(MenuItem searchItem) {

        if (isAlwaysExpanded()) {
            mSearchView.setIconifiedByDefault(false);
        } else {
            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }

        mSearchView.setOnQueryTextListener(this);
    }

    public boolean onQueryTextChange(String newText) {
        tutoresListViewAdapter.getFilter().filter(newText);
        return false;
    }

    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    public boolean onClose() {
        return false;
    }

    protected boolean isAlwaysExpanded() {
        return false;
    }

}
