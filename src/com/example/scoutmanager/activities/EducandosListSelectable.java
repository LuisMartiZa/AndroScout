package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EducandosListSelectable extends Activity {
	
	private String[] educandos;
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
				// TODO Auto-generated catch block
				e.printStackTrace();
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
    }
}
