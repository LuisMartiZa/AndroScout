package com.example.scoutmanager.activities;

import java.util.ArrayList;
import java.util.List;

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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EducandosListSelectable extends Activity {
	
	private ArrayList<String> educandos;
	private ArrayList<String> educandosSelected;
	private List<Integer> educandosSelectedID;
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
			
			educandos.add(educando.getNombre()+" "+educando.getApellidos());
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
    			
		educandosSelectedID = new ArrayList<Integer>();
    	
    	StringBuffer sb = new StringBuffer("");
    	for (int i=0; i < a.size(); i++){
    		 
            if (a.valueAt(i)) {
            	int idx = a.keyAt(i);
            	 
                if (sb.length() > 0)
                    sb.append(", ");
     
                String s = (String)educandosListView.getAdapter().getItem(idx);
                educandosSelectedID.add(idx);
                sb.append(s);
                
            }
        }
	
    }
    
    public void executeSaveSelected(){
    	
    	getItemsSelected();
    	
    	Bundle intentExtras = this.getIntent().getExtras();
    	
    	if(intentExtras.getBoolean("tutoresView")){
        	Intent intent = new Intent(this, TutoresDetailActivity.class);
        	
        	 Bundle educandosID = new Bundle();
        	 
        	 Log.v("LISTEDUCANDOS", "ID's " + educandosSelectedID);
         	
         	educandosID.putIntegerArrayList("educandosID", (ArrayList<Integer>) educandosSelectedID);
             intent.putExtras(educandosID);
             
             // Add the bundle to the intent.
             setResult(RESULT_OK,intent);
             finish();
    	}
		
       
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.list_selectable_action, menu);
	    return super.onCreateOptionsMenu(menu);
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.accept:
	            executeSaveSelected();
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

        }
        return super.onKeyDown(keyCode, event);
    }
    
    
}
