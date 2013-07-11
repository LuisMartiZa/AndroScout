package com.example.scoutmanager.model.entities;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tActividades")
public class Actividades extends Entity {
	
	@TableField(name= "tipoActividad", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	private String Tipo = "";
	
	@TableField(name= "descripcionActividad", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	private String Descripcion = "";
	
	

	/*
    * Methods of Actividades class.
    */
   
   //CONSTRUCTORS
   public Actividades(){
   	super();
   }
   
   public Actividades(String tipo, String descripcion)
   {
   	super();
   	
   	this.Tipo=tipo;
   	this.Descripcion=descripcion;
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
   
   public String getDescripcion()
   {
   	return Descripcion;
   }
   
   public void setDescripcion(String descripcion)
   {
   	this.Descripcion=descripcion;
   }


}
