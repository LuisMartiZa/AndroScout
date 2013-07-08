package com.example.scoutmanager.model.datacontexts;

import android.content.Context;

import com.example.scoutmanager.model.entities.Educando;
import com.mobandme.ada.ObjectContext;
import com.mobandme.ada.ObjectSet;

public class ApplicationDataContext extends ObjectContext {

	/*
	 * Este es el object set para la prueba de educandos.
	 */
	public ObjectSet<Educando> EducandosSet;
	public ApplicationDataContext(Context pContext) throws Exception {
		super(pContext);
		// TODO Auto-generated constructor stub
		this.EducandosSet = new ObjectSet<Educando>(Educando.class, this);
	}

}
