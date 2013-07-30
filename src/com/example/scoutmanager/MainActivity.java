package com.example.scoutmanager;

//import java.util.Calendar;
//import java.util.Date;

//import com.example.scoutmanager.model.datacontexts.ApplicationDataContext;
//import com.example.scoutmanager.model.entities.Educando;
import java.util.ArrayList;

import com.example.scoutmanager.activities.ControlAsistencia;
import com.example.scoutmanager.activities.LateralMenu;
import com.example.scoutmanager.adapters.LateralMenuAdapter;
import com.example.scoutmanager.model.entities.Menu_items;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

//import android.widget.Toast;

public class MainActivity extends SlidingActivity {
	
	private ListView listView;

	//private ApplicationDataContext dataContext;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.activity_menu);
	
		getSlidingMenu().setBehindOffset(100);
		getSlidingMenu().setSlidingEnabled(true);
        getSlidingMenu().setShadowDrawable(R.drawable.shadow);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		setSlidingActionBarEnabled(false);
		
		ActionBar actionbar;
		actionbar= getActionBar();
		actionbar.setTitle("MENU");
		
		this.setLateralMenu();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
		return false;
        
    }
    
    public void setLateralMenu()
    {
    	this.listView = (ListView) findViewById(R.id.listView);

		ArrayList<Menu_items> items = new ArrayList<Menu_items>();
		items.add(new Menu_items(R.drawable.ic_launcher, "Control Asistencia"));
		items.add(new Menu_items(R.drawable.ic_launcher, "Eventos"));
		items.add(new Menu_items(R.drawable.ic_launcher, "Insignias"));
		items.add(new Menu_items(R.drawable.ic_launcher, "Actividades"));
		items.add(new Menu_items(R.drawable.ic_launcher, "Ley y Promesa"));
		items.add(new Menu_items(R.drawable.ic_launcher, "Educandos"));
		// Sets the data behind this ListView
		//this.listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		this.listView.setAdapter(new LateralMenuAdapter(this, items));

		// Register a callback to be invoked when an item in this AdapterView
		// has been clicked
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg) {
				
				Context context = getApplicationContext();
		        CharSequence text;
		        int duration = Toast.LENGTH_SHORT;
		        Toast toast;
		        
				switch (position) {
			    case 0:
			        getSlidingMenu().toggle(true);
			        text = "Ha pulsado control de asistencia";

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        
			        Intent myIntent = new Intent(view.getContext(), ControlAsistencia.class);
			        startActivity(myIntent);
			        
			        break;
			        
			    case 1:
			    	getSlidingMenu().toggle(true);
			        text = "Ha pulsado eventos";

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        break;
			        
			    case 2:
			    	getSlidingMenu().toggle(true);
			        text = "Ha pulsado insignias";

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        break;
			        
			    case 3:
			    	getSlidingMenu().toggle(true);
			        text = "Ha pulsado actividades";

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        break;
			        
			    case 4:
			    	getSlidingMenu().toggle(true);
			        text = "Ha pulsado ley y promesa";

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        break;
			        
			    case 5:
			    	getSlidingMenu().toggle(true);
			        text = "Ha pulsado educandos";

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        break;
			   }

			}
		});
    }
    
}
