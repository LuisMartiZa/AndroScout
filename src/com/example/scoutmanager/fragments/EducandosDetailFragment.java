package com.example.scoutmanager.fragments;

import java.util.ArrayList;
import java.util.List;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
import com.example.scoutmanager.model.entities.Etapa;
import com.example.scoutmanager.model.entities.Seccion;
import com.mobandme.ada.DataBinder;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EducandosDetailFragment extends Fragment {
	
	private View fragmentView;
	private Spinner etapas;
	private Spinner seccion;
	private ArrayAdapter<String> etapasAdapter;
	private ArrayAdapter<String> seccionesAdapter;
	private Educando educando = new Educando();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.educandos_detail_fragment, container, true);
	}
	
	@Override
	public void onViewCreated(View pView, Bundle savedInstanceState) {
		try {
			
			fragmentView = pView;
			initializeFragment(pView);
			
			ActionBar actionbar;
			actionbar= getActivity().getActionBar();
			actionbar.setTitle("EDUCANDO");
			
			Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
	                "fonts/Roboto-Light.ttf");
	        //TEXTVIEW
	        TextView name = (TextView) getView().findViewById(R.id.nameText);
	        TextView surname = (TextView) getView().findViewById(R.id.surnameText);
	        TextView dir = (TextView) getView().findViewById(R.id.dirText);
	        TextView birthday = (TextView) getView().findViewById(R.id.birthdayText);
	        TextView seccion = (TextView) getView().findViewById(R.id.sectionText);
	        TextView etapa = (TextView) getView().findViewById(R.id.etapaText);
	        TextView padres = (TextView) getView().findViewById(R.id.padresText);

	        name.setTypeface(tf);
	        surname.setTypeface(tf);
	        dir.setTypeface(tf);
	        birthday.setTypeface(tf);
	        seccion.setTypeface(tf);
	        etapa.setTypeface(tf);
	        padres.setTypeface(tf);
	        
	        //EDITTEXT
	        EditText nameField = (EditText) getView().findViewById(R.id.nameEducando);
	        EditText surnameField = (EditText) getView().findViewById(R.id.surnameEducando);
	        EditText dirField = (EditText) getView().findViewById(R.id.dirEducando);
	        EditText birthdayField = (EditText) getView().findViewById(R.id.educandoBirthday);
	        
	        nameField.setTypeface(tf);
	        surnameField.setTypeface(tf);
	        dirField.setTypeface(tf);
	        birthdayField.setTypeface(tf);
	        
	        ImageView imgFavorite = (ImageView) pView.findViewById(R.id.imageEducando);
	        imgFavorite.setClickable(true);
	        imgFavorite.setOnClickListener(new OnClickListener() {
	                    @Override
	                    public void onClick(View v) {
	                        dispatchTakePictureIntent(1);
	                    }
	                });
	        
			
		} catch (Exception e) {
			Log.v("ONVIEWCREATED", "Mensaje "+e);

		}
	}
	
	private void dispatchTakePictureIntent(int actionCode) {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    startActivityForResult(takePictureIntent, actionCode);
	}
	
	private void initializeFragment(View pView) throws AdaFrameworkException {
		Bundle intentExtras = getActivity().getIntent().getExtras();
		if (intentExtras != null) {
			initializeSpinners();
			executeShowCommand(intentExtras.getInt("educandoID"));			    
		}else{
		
			initializeSpinners();
		}
	}
	
	public void executeShowCommand(int pIndex) {
		try {

			educando = DataBase.Context.EducandosSet.get(pIndex);
			educando.setStatus(Entity.STATUS_UPDATED);
			educando.bind(fragmentView);
			
			int seccionPosition = this.seccionesAdapter.getPosition(educando.getSeccionEducando().getNombre());
			
			//set the default according to value
			this.seccion.setSelection(seccionPosition,true);
						
			int etapaPosition = this.etapasAdapter.getPosition(educando.getEtapaEducando().getNombre());

			//set the default according to value
			this.etapas.setSelection(etapaPosition,true);
			
		} catch (Exception e) {
			Log.v("EXECUTESHOWCOMMAND", "Mensaje "+e);

		}
	}
	
 	public void executeDeleteCommand() {
		try {
			
			if (educando.getID() != null) {
			
				educando.setStatus(Entity.STATUS_DELETED);
				DataBase.Context.EducandosSet.save();
				
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
			}
			
		} catch (Exception e) {
			Log.v("DELETECOMMAND", "Mensaje "+e);

		}
	}
	
	public void executeSaveCommand() {
		try {
			
			educando.bind(fragmentView, DataBinder.BINDING_UI_TO_ENTITY);

			int nseccion = DataBase.Context.SeccionsSet.size();
			Seccion seccionAux;
			
			for(int n=0; n< nseccion; n++){
				seccionAux= DataBase.Context.SeccionsSet.get(n);

				if(seccionAux.getNombre().equals(seccion.getSelectedItem()))
				{
					educando.setSeccionEducando(seccionAux);

				}
					
			}
			
			int netapa = DataBase.Context.EtapasSet.size();
			Etapa etapaAux;
			
			for(int n=0; n< netapa; n++){
				etapaAux= DataBase.Context.EtapasSet.get(n);
				
				if(etapaAux.getNombre().equals(etapas.getSelectedItem()))
				{
					educando.setEtapaEducando(etapaAux);
				}
				
			}
			if (educando.validate(getActivity())) {
				
				if (educando.getID() == null) {
					DataBase.Context.EducandosSet.add(educando);
				}
				DataBase.Context.EducandosSet.save();
				
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
				
			} else {
				Toast.makeText(getActivity(), educando.getValidationResultString("-"), Toast.LENGTH_SHORT).show();
			}
			
		} catch (Exception e) {
			Log.v("SAVECOMMAND", "Mensaje "+e);

		}
	}
	
	public void initializeSpinners()
	{
		List<String> seccionesArray =  new ArrayList<String>();
	    seccionesArray.add("Castores");
	    seccionesArray.add("Manada");
	    seccionesArray.add("Tropa");
	    seccionesArray.add("Unidad");
	    seccionesArray.add("Clan");

	    this.seccionesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, seccionesArray){

	         public View getView(int position, View convertView, ViewGroup parent) {
	                 View v = super.getView(position, convertView, parent);

	                 Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
	                 ((TextView) v).setTypeface(externalFont);

	                 return v;
	         }


	         public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
	                  View v =super.getDropDownView(position, convertView, parent);

	                 Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
	                 ((TextView) v).setTypeface(externalFont);

	                 return v;
	         }
	 };
	    this.seccionesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    this.seccion = (Spinner) getView().findViewById(R.id.spinnerSeccion);
	    this.seccion.setAdapter(seccionesAdapter);
	    this.seccion.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> pParent, View pView, int pPosition, long id) {
				try {
					List<String> etapasArray =  new ArrayList<String>();
					
				    switch (pPosition) {
			        case 0:
			        	etapasArray.add("Castor sin paletas");
					    etapasArray.add("Castor con paletas");
					    etapasArray.add("Castor Keeo");
			            break;
			        case 1:
			        	etapasArray.add("Huella de Akela");
					    etapasArray.add("Huella de Baloo");
					    etapasArray.add("Huella de Bagheera");
			            break;
			        default:
			        	etapasArray.add("Integraci—n");
					    etapasArray.add("Participaci—n");
					    etapasArray.add("Animaci—n");
					    break;
				    }
				    
				    etapasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, etapasArray){

				         public View getView(int position, View convertView, ViewGroup parent) {
				                 View v = super.getView(position, convertView, parent);

				                 Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
				                 ((TextView) v).setTypeface(externalFont);

				                 return v;
				         }


				         public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
				                  View v =super.getDropDownView(position, convertView, parent);

				                 Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
				                 ((TextView) v).setTypeface(externalFont);

				                 return v;
				         }
				 };
				    etapasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				    etapas = (Spinner) getView().findViewById(R.id.spinnerEtapa);
				    etapas.setAdapter(etapasAdapter);
		        	
		        } catch (Exception e) {
					Log.v("ONITEMSELECTED", "Mensaje "+e);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
	    });
	}

}