package com.example.scoutmanager.model.entities;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tSeccion")
public class Seccion extends Entity {
	
	@TableField(name= "nombreSeccion", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	private String Nombre = "";

	 /*
     * Methods of Usuario class.
     */
    
    //CONSTRUCTORS
    public Seccion(){
    	super();
    }
    
    public Seccion(String nombre)
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