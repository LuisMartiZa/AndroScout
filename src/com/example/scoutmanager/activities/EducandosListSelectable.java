package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EducandosListSelectable extends Activity {
	
	private String[] educandos;
	private String[] educandosSelected;
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
		
		educandos= new String[neducando];
		
		for(int n=0; n< neducando; n++){
			educando= DataBase.Context.EducandosSet.get(n);
			
			educandos[n]=educando.getNombre();
		}
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, educandos);
		
        this.educandosListView.setItemsCanFocus(false);
        this.educandosListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		this.educandosListView.setAdapter(adapter);
		
		Bundle intentExtras = this.getIntent().getExtras();
		if (intentExtras != null)
			educandosSelected = intentExtras.getStringArray("selectedEducandos");
		
		
		for(int i=0; i < educandosListView.getCount(); i++)
		{
			for(int j=0; j < educandosSelected.length; j++){
				
				Log.w("LOCURA",(String) educandosListView.getItemAtPosition(i));
				Log.w("LOCURA2",educandosSelected[j]);

				if(educandosSelected[j].equals((String)educandosListView.getItemAtPosition(i)))
				{
					educandosListView.setItemChecked(i, true);
				}
					
			}
		}
		
    }
}
