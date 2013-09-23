package com.example.scoutmanager.fragments;

import java.util.ArrayList;
import java.util.List;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
import com.mobandme.ada.DataBinder;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class EducandosDetailFragment extends Fragment {
	
	private View fragmentView;
	private Spinner etapas;
	private Spinner seccion;
	private ArrayAdapter<String> etapasAdapter;
	private Educando educando = new Educando();
	
	private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> pParent, View pView, int pPosition, long id) {
			try {
				List<String> etapasArray =  new ArrayList<String>();
				
			    switch (pPosition) {
		        case 0:
		        	etapasArray.add("");
		            break;
		        case 1:
		        	etapasArray.add("");
		        	etapasArray.add("Castor sin paletas");
				    etapasArray.add("Castor con paletas");
				    etapasArray.add("Castor Keeo");
		            break;
		        case 2:
		        	etapasArray.add("");
		        	etapasArray.add("Huella de Akela");
				    etapasArray.add("Huella de Baloo");
				    etapasArray.add("Huella de Bagheera");
		            break;
		        default:
		        	etapasArray.add("");
		        	etapasArray.add("Integraci—n");
				    etapasArray.add("Participaci—n");
				    etapasArray.add("Animaci—n");
				    break;
			    }
			    
			    etapasAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, etapasArray);
			    etapasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    etapas = (Spinner) getView().findViewById(R.id.spinnerEtapa);
			    etapas.setAdapter(etapasAdapter);
	        	
	        } catch (Exception e) {
				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
    };
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.educandos_detail_fragment, container, true);
	}
	
	@Override
	public void onViewCreated(View pView, Bundle savedInstanceState) {
		try {
			
			fragmentView = pView;
			initializeFragment(pView);
			
		} catch (Exception e) {
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	private void initializeFragment(View pView) throws AdaFrameworkException {
		Bundle intentExtras = getActivity().getIntent().getExtras();
		if (intentExtras != null) {
			executeShowCommand(intentExtras.getInt("educandoID"));
			initializeSpinners();
					    
		}
	}
	
	public void executeShowCommand(int pIndex) {
		try {

			educando = DataBase.Context.EducandosSet.get(pIndex);
			educando.setStatus(Entity.STATUS_UPDATED);
			educando.bind(fragmentView);
			
		} catch (Exception e) {
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
 	public void executeDeleteCommand(View pView) {
		try {
			
			if (educando.getID() != null) {
			
				educando.setStatus(Entity.STATUS_DELETED);
				DataBase.Context.EducandosSet.save();
				
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
			}
			
		} catch (Exception e) {
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	public void executeSaveCommand(View pView) {
		try {
			
			educando.bind(fragmentView, DataBinder.BINDING_UI_TO_ENTITY);
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
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	public void initializeSpinners()
	{
		List<String> seccionesArray =  new ArrayList<String>();
		seccionesArray.add("");
	    seccionesArray.add("Castores");
	    seccionesArray.add("Manada");
	    seccionesArray.add("Tropa");
	    seccionesArray.add("Unidad");
	    seccionesArray.add("Clan");

	    ArrayAdapter<String> seccionesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, seccionesArray);
	    seccionesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    seccion = (Spinner) getView().findViewById(R.id.spinnerSeccion);
	    seccion.setAdapter(seccionesAdapter);
	    seccion.setOnItemSelectedListener(itemSelectedListener);
	}

}