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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class EducandosDetailFragment extends Fragment {
	
	private View fragmentView;
	private Spinner etapas;
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
			
		} catch (Exception e) {
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	private void initializeFragment(View pView) throws AdaFrameworkException {
		Bundle intentExtras = getActivity().getIntent().getExtras();
		if (intentExtras != null) {
			executeShowCommand(intentExtras.getInt("educandoID"));
			List<String> SpinnerArray =  new ArrayList<String>();
		    SpinnerArray.add("Integraci—n");
		    SpinnerArray.add("Participaci—n");
		    SpinnerArray.add("Animaci—n");

		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, SpinnerArray);
		    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    etapas = (Spinner) getView().findViewById(R.id.spinnerEtapa);
		    etapas.setAdapter(adapter);		    
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

}