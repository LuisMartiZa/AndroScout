package com.example.scoutmanager.activities;

import java.util.ArrayList;

import com.example.scoutmanager.R;
import com.example.scoutmanager.adapters.LateralMenuAdapter;
import com.example.scoutmanager.model.entities.Menu_items;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class LateralMenu extends Activity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.activity_menu);

		this.listView = (ListView) findViewById(R.id.listView);

		ArrayList<Menu_items> items = new ArrayList<Menu_items>();
		items.add(new Menu_items(R.drawable.ic_launcher, "Control Asistencia"));
		items.add(new Menu_items(R.drawable.ic_launcher, "Eventos"));
		items.add(new Menu_items(R.drawable.ic_launcher, "Insignias"));
		items.add(new Menu_items(R.drawable.ic_launcher, "Actividades"));
		items.add(new Menu_items(R.drawable.ic_launcher, "Ley y Promesa"));
		items.add(new Menu_items(R.drawable.ic_launcher, "Educandos"));
		// Sets the data behind this ListView
		this.listView.setAdapter(new LateralMenuAdapter(this, items));

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
