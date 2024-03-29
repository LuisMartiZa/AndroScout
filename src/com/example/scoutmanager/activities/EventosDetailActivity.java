package com.example.scoutmanager.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.scoutmanager.R;
import com.example.scoutmanager.adapters.EducandosListAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
import com.example.scoutmanager.model.entities.Evento;
import com.mobandme.ada.DataBinder;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventosDetailActivity extends Activity {
	
	private Evento ev = new Evento();
	private ListView educandosListView;
    private ArrayAdapter<Educando> educandosListViewAdapter;
	private ArrayList<Educando> arrayListEducandos= new ArrayList<Educando>();
	private ArrayList<Educando> arrayListEducandosOlder= new ArrayList<Educando>();
	private ArrayList<String> educandosSelected;
	private ImageButton addEducando;
	private String modo= "";
		
	private static final int SELECT_REQUEST= 188;
	
	private AlertDialog.Builder popUpShare;
	private AlertDialog.Builder popUpNo;
	
	
	private View.OnClickListener onClick =  new View.OnClickListener() {
		
		public void onClick(View v) {
			executeShowListSelectable();
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.eventos_detail_activity1);
		
		try {
			Bundle intentExtras = this.getIntent().getExtras();
			
			ActionBar actionbar;

			actionbar= this.getActionBar();
			actionbar.setTitle("EVENTOS");
			
			if (intentExtras.getString("modo").equals("editar"))
				actionbar.setSubtitle("Editar evento");
			else
				actionbar.setSubtitle("Nuevo evento");
			
			actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5d2f89")));
			actionbar.setHomeButtonEnabled(true);
			
			educandosListView =(ListView)findViewById(R.id.listEducandosEvent);
			
			addEducando= (ImageButton)findViewById(R.id.addEducandoEventoButton);
			addEducando.setOnClickListener(onClick);
			
			modo=intentExtras.getString("modo");
			
			initializeActivity();
			
			initializeTypeface();

			
		} catch (AdaFrameworkException e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {	 
		if (requestCode == SELECT_REQUEST && resultCode == RESULT_OK) {
			try {
				DataBase.Context.EducandosSet.fill();
			} catch (AdaFrameworkException e) {
				e.printStackTrace();
			}
					  
			Bundle intentExtras = data.getExtras();
					  
			arrayListEducandos=  new ArrayList<Educando>();
							  
			ArrayList<Integer> educandosID = intentExtras.getIntegerArrayList("educandosID");
					  
			for(int i=0; i< educandosID.size(); i++){
				arrayListEducandos.add(DataBase.Context.EducandosSet.get(educandosID.get(i)));
			}
			
			try {
				initializeListView();
			} catch (AdaFrameworkException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.evento_detail_action, menu);
	    return super.onCreateOptionsMenu(menu);
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.accept:
	   	        executeSaveCommand(false);
	        	return true;
	            
	        case R.id.discard:
	            executeDeleteCommand();
	            return true;
	            
	        case R.id.share:
	            executeShareCommand();
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void initializeActivity() throws AdaFrameworkException {
		Bundle intentExtras = this.getIntent().getExtras();
		if (intentExtras.getString("modo").equals("editar")){
			executeShowCommand(intentExtras.getLong("eventoID"));
		}else{
			initializeListView();
		}
	}
	
	
	private void executeShowCommand(Long pIndex) {
		try {

			DataBase.Context.EventosSet.fill();
			ev = DataBase.Context.EventosSet.getElementByID(pIndex);
			ev.setStatus(Entity.STATUS_UPDATED);
			ev.bind(this);
			
			fillArrayListEducandos();
			initializeListView();
			
		} catch (Exception e) {
			Log.v("EXECUTESHOWCOMMAND", "Mensaje "+e);
		}
	}
	
 	private void executeDeleteCommand() {
		
		if (ev.getID() != null) {
		
			ev.setStatus(Entity.STATUS_DELETED);
			
			String wherePattern = "tEvento_ID = ?";
			try {
				List<Educando> educandosList= new ArrayList<Educando>();
				
			    educandosList = DataBase.Context.EducandosSet.search(Educando.TABLE_EDUCANDOS_JOIN_EVENTOS, false, null, wherePattern, new String[] { ev.getID().toString() }, "tEvento_ID ASC", null, null, null, null);
			    
			    for(int i=0; i<educandosList.size();i++)
			    {	for(int j=0; j<educandosList.get(i).getEventos().size();j++){
			    		if(educandosList.get(i).getEventos().get(j).getID() == ev.getID()){
			    			Educando educando = educandosList.get(i);
			    			educando.getEventos().remove(j);
			    			educando.setStatus(Entity.STATUS_UPDATED);
			    			
			    			DataBase.Context.EducandosSet.save(educando);
			    		}
	
			    	}
			    }
			} catch (Exception e) {
				Log.v("DELETECOMMAND", "Mensaje "+e);
	
			}
							
			try {
				DataBase.Context.EventosSet.save(ev);
			} catch (AdaFrameworkException e) {
				e.printStackTrace();
			}
	
			setResult(Activity.RESULT_OK);
			finish();
		}
	}
	
	
	private void executeSaveCommand(boolean assing) {
		try {
			
			ev.bind(this, DataBinder.BINDING_UI_TO_ENTITY);
			
			if (ev.validate(this)) {
				
				if (ev.getID() == null) {
					DataBase.Context.EventosSet.add(ev);
				}
				DataBase.Context.EventosSet.save(ev);
				
				setResult(Activity.RESULT_OK);
				
				Educando educando= new Educando();
				DataBase.Context.EventosSet.fill();
				
				if(arrayListEducandos.size()>0){
					Log.v("EVENTOS", "1");
					for(int n=0; n< arrayListEducandos.size(); n++){
						educando= arrayListEducandos.get(n);
						
						educando.setStatus(Entity.STATUS_UPDATED);

						if(modo.equals("editar")){

							for(int i=0;i<arrayListEducandosOlder.size();i++){
								Educando older= arrayListEducandosOlder.get(i);
								if(older.getID() != educando.getID()){
									older.setStatus(Entity.STATUS_UPDATED);
									for(int j=0; j<older.getEventos().size(); j++){
										if(older.getEventos().get(j).getID() == ev.getID())
											older.getEventos().remove(j);
									}

									DataBase.Context.EducandosSet.save(older);
								}
							}
							educando.addEvento(DataBase.Context.EventosSet.getElementByID(ev.getID()));
							
						}else{
							educando.addEvento(DataBase.Context.EventosSet.get(DataBase.Context.EventosSet.size()-1));

						}
						
						DataBase.Context.EducandosSet.save(educando);
						
					}
				}else if (arrayListEducandos.size()==0) {
					for(int i=0;i<arrayListEducandosOlder.size();i++){
						Educando older= arrayListEducandosOlder.get(i);
						older.setStatus(Entity.STATUS_UPDATED);
						for(int j=0; j<older.getEventos().size(); j++){
							if(older.getEventos().get(j).getID() == ev.getID())
								older.getEventos().remove(j);
						}

						DataBase.Context.EducandosSet.save(older);
					}
				}
				
				
				if(!assing)
					finish();
				
			} else {
				Toast.makeText(this, ev.getValidationResultString("-"), Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.v("SAVECOMMAND", "Mensaje "+e);

		}
	}
	
	private void executeShareCommand() {
		popUpShare = new AlertDialog.Builder(this);
    	
		popUpShare.setCancelable(true);
   	    popUpShare.setMessage("�Desea comunicar a los Padres, mediante un correo, acerca del evento?")
   	    .setTitle("ENVIAR CORREO")
   	    .setPositiveButton("SI", new DialogInterface.OnClickListener()  {
   	           public void onClick(DialogInterface dialog, int id) {
   	        	   
   	        	   if(ev.getID() == null){
   	        		   Log.v("PRUEBA", "ID NULO");
   	        		   
	   	        		popUpNo = new AlertDialog.Builder(EventosDetailActivity.this);
	   	         	
	   	        		popUpNo.setCancelable(true);
	   	        	    popUpNo.setMessage("Para poder enviar el correo el evento debe estar creado.")
	   	        	    .setTitle("IMPOSIBLE ENVIAR")
	   	        	    .setPositiveButton("OK", new DialogInterface.OnClickListener()  {
	   	        	           public void onClick(DialogInterface dialog, int id) {
	   	        	            	dialog.cancel();
	   	        	           }
	   	        	    });
	   	        	           
	   	        	    popUpNo.show();
   	        		   
   	        	   }else{
   	        		   
   	        		executeEmail();
   	            	dialog.cancel();
   	        	   }
   	            }
   	     })
   	           
   	    .setNegativeButton("NO",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});
   	    
   	    popUpShare.show();
	}
	
	private void executeEmail(){
		
		try {
			DataBase.Context.TutoresSet.fill();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<String> emailList = new ArrayList<String>();
		
		for(int i=0; i<DataBase.Context.TutoresSet.size(); i++){
			emailList.add(DataBase.Context.TutoresSet.get(i).getEmail());
			Log.v("EMAIL", DataBase.Context.TutoresSet.get(i).getEmail());
		}
		
		for (String s : emailList){
		    Log.v("My array list content: ", s);
		}
		
		int x = emailList.size();
        String[] appArray = new String[x];
        appArray = emailList.toArray(appArray);
		
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setType("message/rfc822");
		//emailIntent.putExtra(Intent.EXTRA_EMAIL, appArray);
		emailIntent.putExtra(Intent.EXTRA_BCC, appArray);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[SCOUTS] " + ev.getNombre() + ".");
		emailIntent.putExtra(Intent.EXTRA_TEXT, "Buenas me pongo en contacto con usted para comunicarle acerca del " + ev.getNombre() + " que tendr� lugar en " + ev.getLugar() +".\n\n Mensaje generado por AndroScout.");
		startActivity(Intent.createChooser(emailIntent, "Selecciona la aplicaci�n para env�o:"));
		
	}
	
	private void executeShowListSelectable() {
		try {
				getSelectedEducandos();

				Bundle educandosEvento = new Bundle();    
			    educandosEvento.putStringArrayList("selectedEducandos", educandosSelected);
				
				Intent detailIntent = new Intent(this, EducandosListSelectable.class);
			    detailIntent.putExtras(educandosEvento);
			    
				startActivityForResult(detailIntent, SELECT_REQUEST);
			} catch (Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
			}
	}
	
	private void initializeListView() throws AdaFrameworkException {
		    	
    	if (educandosListView != null) {
    		educandosListViewAdapter= new EducandosListAdapter(EventosDetailActivity.this, R.layout.educandos_row, arrayListEducandos);
    		educandosListView.setAdapter(this.educandosListViewAdapter);
    		
    		setListViewHeightBasedOnChildren(educandosListView);
    	}
    }
	
	private void setListViewHeightBasedOnChildren(ListView listView) {
        EducandosListAdapter listAdapter = (EducandosListAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
 
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
 
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
		
	private void fillArrayListEducandos() throws AdaFrameworkException {
		
		DataBase.Context.EducandosSet.fill();
	
		arrayListEducandos= new ArrayList<Educando>();
		arrayListEducandosOlder= new ArrayList<Educando>();
		
		String wherePattern = "tEvento_ID = ?";
        List<Educando> educandosList;
		try {
			educandosList = DataBase.Context.EducandosSet.search(Educando.TABLE_EDUCANDOS_JOIN_EVENTOS, false, null, wherePattern, new String[] { ev.getID().toString() }, "tEvento_ID ASC", null, null, null, null);
		} catch (AdaFrameworkException e) {
			e.printStackTrace();
			educandosList= new ArrayList<Educando>();
		}

		for(int i=0; i<educandosList.size();++i){
			Educando educando = educandosList.get(i);
			arrayListEducandos.add(educando);
		}
		
		arrayListEducandosOlder=arrayListEducandos;

	}
	
	private void getSelectedEducandos(){
		Educando educando;
		
		educandosSelected= new ArrayList<String>();
		
		for(int n=0; n< arrayListEducandos.size(); n++){
			educando= arrayListEducandos.get(n);
			
			educandosSelected.add(educando.getNombre()+" "+educando.getApellidos());
		}
	}
	
	private void initializeTypeface(){
		Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Light.ttf");
        //TEXTVIEW
        TextView nombre = (TextView) this.findViewById(R.id.nombreEventoText);
        TextView lugar = (TextView) this.findViewById(R.id.lugarEventoText);
        TextView fecha = (TextView) this.findViewById(R.id.fechaEventoText);
        TextView educandos = (TextView) this.findViewById(R.id.educandosEventoText);

        
        EditText nameField = (EditText) this.findViewById(R.id.nombreEvento);
        EditText lugarField = (EditText) this.findViewById(R.id.lugarEvento);
        EditText fechaField = (EditText) this.findViewById(R.id.fechaEvento);

        nombre.setTypeface(tf);
        nombre.setTextColor((Color.parseColor("#5d2f89")));
        nombre.setTextSize(20);
        
        lugar.setTypeface(tf);
        lugar.setTextColor((Color.parseColor("#5d2f89")));
        lugar.setTextSize(20);
        
        fecha.setTypeface(tf);
        fecha.setTextColor((Color.parseColor("#5d2f89")));
        fecha.setTextSize(20);
        
        educandos.setTypeface(tf);
        educandos.setTextColor((Color.parseColor("#5d2f89")));
        educandos.setTextSize(20);
        
        nameField.setTypeface(tf);
        lugarField.setTypeface(tf);
        fechaField.setTypeface(tf);

	}

}