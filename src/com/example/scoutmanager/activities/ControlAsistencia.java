package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ControlAsistencia extends SlidingActivity {

	private ListView listView;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.activity_asistencia);
        setBehindContentView(R.layout.activity_menu);


		this.listView = (ListView) findViewById(R.id.listAsistencia);
		
		String[] pruebas = {"Juaquin el vizco", "Mari la locati","Estebana la enana","locati de cadi",
				"locati de jere","locati de chiclana","locati de bilbado"};
		
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pruebas);

		
		// Sets the data behind this ListView
		this.listView.setAdapter(adaptador);

		// Register a callback to be invoked when an item in this AdapterView
		// has been clicked
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg) {

			}
		});

	}
}
