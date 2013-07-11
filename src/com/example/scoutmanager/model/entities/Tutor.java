package com.example.scoutmanager.model.entities;

import java.util.Date;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.*;

@Table(name = "tTutor")
public class Tutor extends Entity {
	
	@TableField(name= "nombreTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	private String Nombre = "";
	
	@TableField(name= "apellidoTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	private String Apellidos = "";

	
	 /*
     * Methods of Usuario class.
     */
    
    //CONSTRUCTORS
    public Tutor(){
    	super();
    }
    
    public Tutor(String nombre, String apellidos)
    {
    	super();
    	
    	this.Nombre=nombre;
    	this.Apellidos=apellidos;
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
    
    public String getApellidos()
    {
    	return Apellidos;
    }
    
    public void setApellidos(String apellidos)
    {
    	this.Apellidos=apellidos;
    }

}