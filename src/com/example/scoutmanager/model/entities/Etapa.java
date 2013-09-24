package com.example.scoutmanager.model.entities;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tEtapa")
public class Etapa extends Entity {
	
	@TableField(name= "nombreEtapa", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	public String Nombre = "";
	
	
	/*
     * Methods of Usuario class.
     */
    
    //CONSTRUCTORS
    public Etapa(){
    	super();
    }
    
    public Etapa(String nombre)
    {
    	super();
    	
    	this.Nombre=nombre;
    }
    
    //GETTERS AND SETTERS
    public String getNombre()
    {
    	return Nombre;
    }
    
    public void setNombre(String nombre)
    {
    	this.Nombre=nombre;
    }
    
}