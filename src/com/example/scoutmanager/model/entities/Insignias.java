package com.example.scoutmanager.model.entities;

import java.util.ArrayList;
import java.util.List;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tInsignias")
public class Insignias extends Entity {
	
	@TableField(name= "nombreInsignia", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	private String Nombre = "";
	
	@TableField(name= "tipoInsignia", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	private String Tipo = "";
	
	/*
     * Methods of Insignias class.
     */
    
    //CONSTRUCTORS
    public Insignias(){
    	super();
    }
    
    public Insignias(String nombre, String tipo)
    {
    	super();
    	
    	this.Nombre=nombre;
    	this.Tipo=tipo;
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
    
    public String getTipo()
    {
    	return Tipo;
    }
    
    public void setTipo(String tipo)
    {
    	this.Tipo=tipo;
    }

}