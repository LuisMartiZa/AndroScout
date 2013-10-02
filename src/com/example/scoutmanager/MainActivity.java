package com.example.scoutmanager;

import java.util.ArrayList;

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
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
		
		Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Light.ttf");
        
        TextView main1 = (TextView) findViewById(R.id.main1);
        TextView main2 = (TextView) findViewById(R.id.main2);
        TextView main3 = (TextView) findViewById(R.id.main3);

        main1.setTypeface(tf);
        main2.setTypeface(tf);
        main3.setTypeface(tf);

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
		items.add(new Menu_items(R.drawable.ic_launcher, "Eventos"));
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
			        getSlidingMenu().toggle(true);
			        text = "Ha pulsado eventos";

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        break;
			        
			    case 1:
			    	getSlidingMenu().toggle(true);
			        text = "Ha pulsado actividades";

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        break;
			        
			    case 2:
			    	getSlidingMenu().toggle(true);
			        text = "Ha pulsado ley y promesa";
			        
			        Intent leypromesa = new Intent(view.getContext(), LeyYPromesa.class);
			        startActivity(leypromesa);

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        break;
			        
			    case 3:
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