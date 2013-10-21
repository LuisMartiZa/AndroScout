package com.example.scoutmanager.fragments;

import com.example.scoutmanager.R;
import com.example.scoutmanager.activities.EducandosDetailActivity;
import com.example.scoutmanager.adapters.EducandosListAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EducandosListFragment extends Fragment {
	
	private ListView educandosListView;
    private ArrayAdapter<Educando> educandosListViewAdapter; 
    
    private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> pParent, View pView, int pPosition, long id) {
			try {
	        	
				Intent deatailIntent = new Intent(getActivity(), EducandosDetailActivity.class);
				deatailIntent.putExtra("educandoID", pPosition);
				startActivityForResult(deatailIntent, 1);
	        	
	        } catch (Exception e) {
				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
			}
		}
    };
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.educandos_list_fragment, container);
	}
	
	@Override
	public void onViewCreated(View pView, Bundle pSavedInstanceState) {
		try {
			initializeFragment(pView);
			
		} catch (Exception e) {
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	
	private void initializeFragment(View pView) throws AdaFrameworkException {
				
    	this.educandosListView = (ListView)pView.findViewById(R.id.EducandosListView);
    	
    	if (this.educandosListView != null) {
    		this.educandosListView.setOnItemClickListener(itemClickListener);
    		this.educandosListViewAdapter= new EducandosListAdapter(getActivity(), R.layout.educandos_row);
    		this.educandosListView.setAdapter(this.educandosListViewAdapter);
    		
    		DataBase.Context.EducandosSet.setAdapter(this.educandosListViewAdapter);
    		DataBase.Context.EducandosSet.fill();
    	}
    }
    
    public void executeAddNewCommand() {
    	try {
    		
    		Intent detailIntent = new Intent(getActivity(), EducandosDetailActivity.class);
    		startActivityForResult(detailIntent, 1);
    		
    	} catch (Exception e) {
 			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
 		}
    }

}
