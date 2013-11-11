package com.example.scoutmanager.model.context;

import android.content.Context;

import com.mobandme.ada.ObjectContext;
import com.mobandme.ada.ObjectSet;
import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.example.scoutmanager.model.entities.Actividades;
import com.example.scoutmanager.model.entities.Educando;
import com.example.scoutmanager.model.entities.Evento;
import com.example.scoutmanager.model.entities.Seccion;
import com.example.scoutmanager.model.entities.Etapa;

/**
 * Application Data Context.
 * @author   Luis Mart’nez (@LuisMartiZa)
 * @category Data Context
 * @version  1.0
 */

public class ApplicationDataContext extends ObjectContext {
	
	public static final String DEFAULT_SORT = "Name ASC";

	/**************************************************/
	/*      		OBJECTSETS DEFINITION 			  */
	/**************************************************/
	public ObjectSet<Educando> EducandosSet;
	public ObjectSet<Seccion> SeccionsSet;
	public ObjectSet<Etapa> EtapasSet;
	public ObjectSet<Actividades> ActividadesSet;
	public ObjectSet<Evento> EventosSet;
	
	/**************************************************/
	/*      		CONSTRUCTORS 		 			  */
	/**************************************************/
	public ApplicationDataContext(Context pContext) {
		super(pContext);
		
		//Initialize the ObjectContext
		initializeContext();
	}
	
	/**************************************************/
	/*      		PRIVATE METHODS   	 			  */
	/**************************************************/
	
	private void initializeContext() {
		try {
			
			//Enable DataBase Transactions to be used by the Save process.
			this.setUseTransactions(true);

			//Enable the creation of DataBase table indexes.
			this.setUseTableIndexes(true);
			
			//Set a custom encryption algorithm.
			this.setEncryptionAlgorithm("AES");
			
			//Set a custom encryption master pass phrase.
			this.setMasterEncryptionKey("com.example.scoutmanager");
			
			//Initialize ObjectSets instances.
			initializeObjectSets();
			
		} catch (Exception e) {
		}
	}
	
	private void initializeObjectSets() throws AdaFrameworkException {
		
		EducandosSet = new ObjectSet<Educando>(Educando.class, this);
		EtapasSet = new ObjectSet<Etapa>(Etapa.class, this);
		SeccionsSet = new ObjectSet<Seccion>(Seccion.class, this);
		ActividadesSet = new ObjectSet<Actividades>(Actividades.class, this);
		EventosSet = new ObjectSet<Evento>(Evento.class, this);
	}
}
