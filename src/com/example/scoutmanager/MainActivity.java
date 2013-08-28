package com.example.scoutmanager;

import java.util.ArrayList;

import com.example.scoutmanager.activities.ControlAsistencia;
import com.example.scoutmanager.activities.LeyYPromesa;
import com.example.scoutmanager.adapters.LateralMenuAdapter;
import com.example.scoutmanager.model.DataBase;
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

public class MainActivity extends SlidingActivity {
	
	private ListView listView;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		DataBase.initialize(this);

        
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.activity_menu);
	
		getSlidingMenu().setBehindOffset(100);
		getSlidingMenu().setSlidingEnabled(true);
        getSlidingMenu().setShadowWidthRes(R.dimen.shadow_width);

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
			        text = "Ha pulsado control de asistencia";

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        
			        Intent myIntent = new Intent(view.getContext(), ControlAsistencia.class);
			        startActivity(myIntent);
			        
			        getSlidingMenu().toggle(true);
			        
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
			        
			        Intent myIntent1 = new Intent(view.getContext(), LeyYPromesa.class);
			        startActivity(myIntent1);

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

/*
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeActivity();
	}
	
	private void initializeActivity() {
		try {
		
			setContentView(R.layout.activity_main);
			
			DataBase.initialize(this);
			
		} catch (Exception e) {
			ExceptionsHelper.manage(e);
		}
	}
	
	public void executeCommand(View pView) {
		try {
			
			if (pView != null) {
				switch (pView.getId()) {
					case R.id.Command_NewCompany:
						createNewCompany();
						break;
					case R.id.Command_NewWorker:
						createNewWorker();
						break;
					case R.id.Command_NewDirective:
						createNewDirective();
						break;
				}
			}
			
			Toast.makeText(this, getString(R.string.message_save_ok), Toast.LENGTH_SHORT).show();
			
		} catch (Exception e) {
			ExceptionsHelper.manage(this, e);
		}
	}
	
	private Address createNewAddress(String pStreet) {
		Address address = new Address();
		address.Street = pStreet;
		address.Number = 25;
		address.Floor = 7;
		address.Door = "A";
		address.City = "Bilbao";
		address.Province = "Bizkaia";
		//Find the Country into DataBase if it does not exist, create.
		address.Country = DataBase.Context.CountriesSet.getByName("España", true);
		
		return address;
	}
	
	private void createNewCompany() throws AdaFrameworkException {
		Company company = new Company();
		company.Name    = "Mob&Me";
		company.Address = createNewAddress("C/ Mob&Me Street");
		
		//METHOD 1
		//This method save all instances with changes contained into the ObjectSet.
		DataBase.Context.CompaniesSet.add(company);
		DataBase.Context.CompaniesSet.save();
		
		//METHOD 2
		//This is an alternative method to save only one entity.
		//DataBase.Context.CompaniesSet.save(company);
	}
	
	private void createNewWorker() throws AdaFrameworkException {
		Worker worker = new Worker();
		worker.Name = "Txus";
		worker.Surname = "Ballesteros";
		worker.UserName = "user1234";
		worker.Password = "pass1234";
		worker.SalaryRate = 45.5f;
		worker.Active = true;
		//If the CompaniesSet is empty, create a new Company.
		if (DataBase.Context.CompaniesSet.size() == 0) {
			createNewCompany();
		}
		//Get the first company from CompaniesSet. 
		worker.Company = DataBase.Context.CompaniesSet.get(0);
		worker.Address.add(createNewAddress("C/ Worker Address 1"));
		worker.Address.add(createNewAddress("C/ Worker Address 2"));
		
		//METHOD 1
		//This method save all instances with changes contained into the ObjectSet.
		DataBase.Context.WorkersSet.add(worker);
		DataBase.Context.WorkersSet.save();
		
		//METHOD 2
		//This is an alternative method to save only one entity.
		//DataBase.Context.WorkersSet.save(worker);
	}
	
	private void createNewDirective() throws AdaFrameworkException {
		Directive directive = new Directive();
		directive.Name = "Eric";
		directive.Surname = "Schmidt";
		directive.UserName = "user4321";
		directive.Password = "pass4321";
		directive.Active = true;
		//If the CompaniesSet is empty, create a new Company.
		if (DataBase.Context.CompaniesSet.size() == 0) {
			createNewCompany();
		}
		//Get the first company from CompaniesSet. 
		directive.Company = DataBase.Context.CompaniesSet.get(0);
		directive.Address.add(createNewAddress("C/ Directive Address 1"));
		directive.Address.add(createNewAddress("C/ Directive Address 2"));
		//If the WorkersSet is empty, create a new Worker.
		if (DataBase.Context.WorkersSet.size() == 0) {
			createNewWorker();
		}
		directive.Workers.add(DataBase.Context.WorkersSet.get(0));
		
		//METHOD 1
		//This method save all instances with changes contained into the ObjectSet.
		DataBase.Context.DirectivesSet.add(directive);
		DataBase.Context.DirectivesSet.save();
		
		//METHOD 2
		//This is an alternative method to save only one entity.
		//DataBase.Context.DirectivesSet.save(directive);
	}
}
*/