package com.example.scoutmanager.fragments;

import com.example.scoutmanager.R;
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
	
	private ListView employeesListView;
    private ArrayAdapter<Educando> educandosListViewAdapter; 
    
    private OnItemClickListener itemClickLitener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> pParent, View pView, int pPosition, long id) {
			try {
	        	
				Intent deatailIntent = new Intent(getActivity(), employeeDetailActivity.class);
				deatailIntent.putExtra("educandoID", pPosition);
				startActivityForResult(deatailIntent, 1);
	        	
	        } catch (Exception e) {
				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
			}
		}
    };
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.employee_list_fragment_layout, container);
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
		
		ApplicationDataContext.DataBase = new DataContext(getActivity());
		
		
    	this.employeesListView = (ListView)pView.findViewById(R.id.EmployeesListView);
    	
    	if (this.employeesListView != null) {
    		this.employeesListView.setOnItemClickListener(itemClickLitener);
    		this.employeesListViewAdapter = new ArrayAdapter<Employee>(getActivity(), android.R.layout.simple_list_item_1);
    		
    		ApplicationDataContext.DataBase.EmployeesSet.fill();
    		ApplicationDataContext.DataBase.EmployeesSet.setAdapter(this.employeesListViewAdapter);
    		this.employeesListView.setAdapter(this.employeesListViewAdapter);
    	}
    }
    
    public void executeAddNewCommand(View pView) {
    	try {
    		
    		Intent deatailIntent = new Intent(getActivity(), employeeDetailActivity.class);
    		startActivityForResult(deatailIntent, 1);
    		
    	} catch (Exception e) {
 			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
 		}
    }

}
