package com.example.scoutmanager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.scoutmanager.activities.EducandosGridActivity;
import com.example.scoutmanager.activities.EventosListActivity;
import com.example.scoutmanager.activities.Ley;
import com.example.scoutmanager.activities.ActividadesActivity;
import com.example.scoutmanager.activities.TutoresListActivity;
import com.example.scoutmanager.adapters.LateralMenuAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Actividades;
import com.example.scoutmanager.model.entities.Etapa;
import com.example.scoutmanager.model.entities.Menu_items;
import com.example.scoutmanager.model.entities.Seccion;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
//import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
//import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private ListView listView;
	
	private SlidingMenu menu;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		DataBase.initialize(this);
		
		try {
			DataBase.Context.SeccionsSet.fill();
			if (DataBase.Context.SeccionsSet.size() == 0)
				fillObjectSets();
		} catch (AdaFrameworkException e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
        
        setContentView(R.layout.activity_main);
        
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.activity_menu);
        menu.setBehindOffset(200);
        
		ActionBar actionbar;
		actionbar= getActionBar();
		actionbar.setTitle("MENU");
		actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5d2f89")));
		actionbar.setHomeButtonEnabled(true);
		
		this.setLateralMenu();
		try {
			DataBase.Context.ActividadesSet.fill();
			if (DataBase.Context.ActividadesSet.size() == 0)
				this.populateActividades();
		} catch (AdaFrameworkException e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
		
		
		
		/*Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Light.ttf");
        
        TextView main1 = (TextView) findViewById(R.id.main1);
        TextView main2 = (TextView) findViewById(R.id.main2);
        TextView main3 = (TextView) findViewById(R.id.main3);

        main1.setTypeface(tf);
        main2.setTypeface(tf);
        main3.setTypeface(tf);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
		return false;
        
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	menu.toggle();
	        default:
	            return super.onOptionsItemSelected(item);
	    }
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
    
    public SlidingMenu getSlidingMenu(){
    	 return this.menu;
    }
    
    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("actividades.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
    
    public void populateActividades(){
		try{
			String data = null;
			
			data = this.loadJSONFromAsset();
			
			if (data != null && !data.trim().equals("")) {
								
				JSONObject actividades = new JSONObject(data);

				JSONArray juegos = new JSONArray(actividades.getJSONArray("Juegos").toString());
				
				for(int i = 0; i < juegos.length(); i++){
				   JSONObject juego = juegos.getJSONObject(i);
				
				   String nombre = juego.getString("Nombre");
				   String participantes = juego.getString("Participantes");
				   String descripcion = juego.getString("Descripcion");
				   String tipo = juego.getString("Tipo");
				   String URL = juego.getString("URL");

				   Actividades aux = new Actividades(nombre, participantes, descripcion,tipo,URL);
				   DataBase.Context.ActividadesSet.add(aux);
				}
				DataBase.Context.ActividadesSet.save();
			}
			
		}catch(Exception e)
		{
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
    	
    }
    
    public void setLateralMenu()
    {
    	this.listView = (ListView) findViewById(R.id.listView);

		ArrayList<Menu_items> items = new ArrayList<Menu_items>();
		items.add(new Menu_items(R.drawable.asistencia, "Asistencia"));
		items.add(new Menu_items(R.drawable.events, "Eventos"));
		items.add(new Menu_items(R.drawable.actividades, "Actividades"));
		items.add(new Menu_items(R.drawable.estrella, "Ley Scout"));
		items.add(new Menu_items(R.drawable.educandos, "Educandos"));
		items.add(new Menu_items(R.drawable.educandos, "Padres/Tutores"));

		
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
			        menu.toggle(true);
			        text = "Ha pulsado asistencia";
			        
			        Intent asistencia = new Intent(view.getContext(), EventosListActivity.class);
			        startActivity(asistencia);

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        break;
			        
			    case 1:
			        menu.toggle(true);
			        text = "Ha pulsado eventos";
			        
			        Intent eventos = new Intent(view.getContext(), EventosListActivity.class);
			        startActivity(eventos);

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        break;
			        
			    case 2:
			    	menu.toggle(true);
			        text = "Ha pulsado actividades";
			        
			        Intent actividades = new Intent(view.getContext(), ActividadesActivity.class);
			        startActivity(actividades);

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        break;
			        
			    case 3:
			    	menu.toggle(true);
			        text = "Ha pulsado ley y promesa";
			        
			        Intent leypromesa = new Intent(view.getContext(), Ley.class);
			        startActivity(leypromesa);

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        break;
			        
			    case 4:
			    	menu.toggle(true);
			        text = "Ha pulsado educandos";
			        
			        Intent educandos = new Intent(view.getContext(), EducandosGridActivity.class);
			        startActivity(educandos);

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        break;
			        
			    case 5:
			    	menu.toggle(true);
			        text = "Ha pulsado educandos";
			        
			        Intent tutores = new Intent(view.getContext(), TutoresListActivity.class);
			        startActivity(tutores);

			        toast = Toast.makeText(context, text, duration);
			        toast.show();
			        break;
			   }

			}
		});
    }
    
}