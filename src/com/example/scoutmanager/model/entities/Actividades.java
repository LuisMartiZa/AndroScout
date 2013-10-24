package com.example.scoutmanager.model.entities;

import com.example.scoutmanager.R;
import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Databinding;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tActividades")
public class Actividades extends Entity {
	
	@TableField(name= "nombreActividad", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	@Databinding(ViewId= R.id.nameActividad)
	public String Nombre = "";
	
	@TableField(name= "participantesActividad", datatype= Entity.DATATYPE_INTEGER, required= true)
	@Databinding(ViewId= R.id.participantesActividad)
	public int Participantes;
	
	@TableField(name= "descripcionActividad", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	@Databinding(ViewId= R.id.descripActividad)
	public String Descripcion = "";
	

	/*
    * Methods of Actividades class.
    */
   
   //CONSTRUCTORS
   public Actividades(){
   	super();
   }
   
   public Actividades(String nombre, int participantes, String descripcion)
   {
   	super();
   	
   	this.Nombre=nombre;
   	this.Participantes=participantes;
   	this.Descripcion=descripcion;
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
   
   public int getParticipantes()
   {
   	return Participantes;
   }
   
   public void setParticipantes(int participantes)
   {
   	this.Participantes=participantes;
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
