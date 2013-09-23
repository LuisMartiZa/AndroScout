package com.example.scoutmanager;

import java.util.ArrayList;

import com.example.scoutmanager.activities.ControlAsistencia;
import com.example.scoutmanager.activities.EducandosActivity;
import com.example.scoutmanager.activities.LeyYPromesa;
import com.example.scoutmanager.adapters.LateralMenuAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Etapa;
import com.example.scoutmanager.model.entities.Menu_items;
import com.example.scoutmanager.model.entities.Seccion;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

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
		try {
			fillObjectSets();
		} catch (AdaFrameworkException e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
        
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
    
    public void fillObjectSets() throws AdaFrameworkException
    {	
    	//Fill Etapas object set
    	Etapa castorsinpaletas = new Etapa("Castor sin paletas");
    	Etapa castorconpaletas = new Etapa("Castor con paletas");
    	Etapa castorkeeo = new Etapa("Castor Keeo");
    	
    	Etapa huellaakela = new Etapa("Huella de Akela");
    	Etapa huellabaloo = new Etapa("Huella de Baloo");
    	Etapa huellabagheera = new Etapa("Huella de Bagheera");
    	
    	Etapa integracion = new Etapa("Integraci—n");
    	Etapa participacion = new Etapa("Participaci—n");
    	Etapa animacion = new Etapa("Animaci—n");
    	
    	DataBase.Context.EtapasSet.add(castorsinpaletas);
    	DataBase.Context.EtapasSet.add(castorconpaletas);
    	DataBase.Context.EtapasSet.add(castorkeeo);
    	DataBase.Context.EtapasSet.add(huellaakela);
    	DataBase.Context.EtapasSet.add(huellabaloo);
    	DataBase.Context.EtapasSet.add(huellabagheera);
    	DataBase.Context.EtapasSet.add(integracion);
    	DataBase.Context.EtapasSet.add(participacion);
    	DataBase.Context.EtapasSet.add(animacion);
    	
		DataBase.Context.EtapasSet.save();

    	
    	//Fill Seccion object set
    	Seccion castores = new Seccion("Castores");
    	castores.addEtapaSeccion(castorconpaletas);
    	castores.addEtapaSeccion(castorsinpaletas);
    	castores.addEtapaSeccion(castorkeeo);
    	
    	Seccion manada = new Seccion("Manada");
    	manada.addEtapaSeccion(huellaakela);
    	manada.addEtapaSeccion(huellabaloo);
    	manada.addEtapaSeccion(huellabagheera);
    	
    	Seccion tropa = new Seccion("Tropa");
    	tropa.addEtapaSeccion(integracion);
    	tropa.addEtapaSeccion(participacion);
    	tropa.addEtapaSeccion(animacion);
    	
    	Seccion unidad = new Seccion("Unidad");
    	unidad.addEtapaSeccion(integracion);
    	unidad.addEtapaSeccion(participacion);
    	unidad.addEtapaSeccion(animacion);
    	
    	Seccion clan = new Seccion("Clan");
    	clan.addEtapaSeccion(integracion);
    	clan.addEtapaSeccion(participacion);
    	clan.addEtapaSeccion(animacion);
    	
    	DataBase.Context.SeccionsSet.add(castores);
    	DataBase.Context.SeccionsSet.add(manada);
    	DataBase.Context.SeccionsSet.add(tropa);
    	DataBase.Context.SeccionsSet.add(unidad);
    	DataBase.Context.SeccionsSet.add(clan);
    	
		DataBase.Context.SeccionsSet.save();


    	
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
			        
			        Intent controlAsistencia = new Intent(view.getContext(), ControlAsistencia.class);
			        startActivity(controlAsistencia);
			        
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
			        
			        Intent leypromesa = new Intent(view.getContext(), LeyYPromesa.class);
			        startActivity(leypromesa);

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        break;
			        
			    case 5:
			    	getSlidingMenu().toggle(true);
			        text = "Ha pulsado educandos";
			        
			        Intent educandos = new Intent(view.getContext(), EducandosActivity.class);
			        startActivity(educandos);

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        break;
			   }

			}
		});
    }
    
}


/*		private Address createNewAddress(String pStreet) {
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