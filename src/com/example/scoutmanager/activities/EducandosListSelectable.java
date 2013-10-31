package com.example.scoutmanager.activities;

import java.util.ArrayList;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EducandosListSelectable extends Activity {
	
	private ArrayList<String> educandos;
	private ArrayList<String> educandosSelected;
	private ListView educandosListView;
	private ArrayAdapter<String> adapter;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		this.setContentView(R.layout.educandos_selectable_list);
		
		ActionBar actionbar;
		actionbar= getActionBar();
		actionbar.setTitle("SELECCIÓN EDUCANDOS");
		
		this.educandosListView =(ListView)findViewById(R.id.educandosSelectableList);
		
			try {
				fillListView();
			} catch (AdaFrameworkException e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
			}

     }
    
    public void fillListView() throws AdaFrameworkException
    {
    	
    	
		DataBase.Context.EducandosSet.fill();

		int neducando = DataBase.Context.EducandosSet.size();
		Educando educando;
		
		educandos = new ArrayList<String>();
				
		for(int n=0; n< neducando; n++){
			educando= DataBase.Context.EducandosSet.get(n);
			
			educandos.add(educando.getNombre());
		}
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, educandos);
		
        this.educandosListView.setItemsCanFocus(false);
        this.educandosListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		this.educandosListView.setAdapter(adapter);
		
		Bundle intentExtras = this.getIntent().getExtras();
		if (intentExtras != null)
			educandosSelected = intentExtras.getStringArrayList("selectedEducandos");
		
		
		for(int i=0; i < educandosListView.getCount(); i++)
		{
			for(int j=0; j < educandosSelected.size(); j++){

				if(educandosSelected.get(j).equals((String)educandosListView.getItemAtPosition(i)))
				{
					educandosListView.setItemChecked(i, true);
				}
					
			}
		}
    }
    
    private void getItemsSelected(){
    
    	SparseBooleanArray a= educandosListView.getCheckedItemPositions();
    	
		educandosSelected = new ArrayList<String>();
    	
    	StringBuffer sb = new StringBuffer("");
    	for (int i=0; i < a.size(); i++){
    		 
            if (a.valueAt(i)) {
            	int idx = a.keyAt(i);
            	 
                if (sb.length() > 0)
                    sb.append(", ");
     
                
                String s = (String)educandosListView.getAdapter().getItem(idx);
                educandosSelected.add(s);
                sb.append(s);
                
            }
        }
	
    }
    
    public void executeSaveSelected(){
    	
    	getItemsSelected();
    	
    	Intent intent = new Intent(this, EventosDetailActivity.class);
		
		// Create a bundle object
        Bundle b = new Bundle();
        b.putStringArrayList("educandosSelected", educandosSelected);
        
        Log.w("POGGGGQUE","Value " + educandosSelected);
 
        // Add the bundle to the intent.
        intent.putExtras(b);
        finish();
        startActivity(intent);
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
	            executeSaveSelected();
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
    
    
}
