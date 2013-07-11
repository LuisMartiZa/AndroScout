package com.example.scoutmanager.model.entities;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.*;

@Table(name = "tComunicaciones")
public class Comunicaciones extends Entity {
	
	@TableField(name= "tipoComunicacion", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	private String Tipo = "";
	
	@TableField(name= "datoComunicaci—n", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	private String Datos = "";

	
	 /*
     * Methods of Usuario class.
     */
    
    //CONSTRUCTORS
    public Comunicaciones(){
    	super();
    }
    
    public Comunicaciones(String tipo, String datos)
    {
    	super();
    	
    	this.Tipo=tipo;
    	this.Datos=datos;
    }
    
    //GETTERS AND SETTERS
    public String getTipo()
    {
    	return Tipo;
    }
    
    public void setTipo(String tipo)
    {
    	this.Tipo=tipo;
    }
    
    public String getDatos()
    {
    	return Datos;
    }
    
    public void setDatos(String datos)
    {
    	this.Datos=datos;
    }

}


