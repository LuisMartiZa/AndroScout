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
	
	@TableField(name= "participantesActividad", datatype= Entity.DATATYPE_TEXT, required= true)
	@Databinding(ViewId= R.id.participantesActividad)
	public String Participantes;
	
	@TableField(name= "descripcionActividad", datatype= Entity.DATATYPE_TEXT, required= true)
	@Databinding(ViewId= R.id.descripActividad)
	public String Descripcion = "";
	
	@TableField(name= "tipoActividad", datatype= Entity.DATATYPE_TEXT, required= true)
	public String Tipo = "";
	
	@TableField(name= "urlActividad", datatype= Entity.DATATYPE_TEXT, required= true)
	public String URL = "";
	

	/*
    * Methods of Actividades class.
    */
   
   //CONSTRUCTORS
   public Actividades(){
   	super();
   }
   
   public Actividades(String nombre, String participantes, String descripcion, String tipo, String url)
   {
   	super();
   	
   	this.Nombre=nombre;
   	this.Participantes=participantes;
   	this.Descripcion=descripcion;
   	this.Tipo=tipo;
   	this.URL=url;
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
   
   public String getParticipantes()
   {
   	return Participantes;
   }
   
   public void setParticipantes(String participantes)
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
   
   public String getURLVideo()
   {
   	return URL;
   }
   
   public void setURLVideo(String url)
   {
   	this.URL=url;
   }


}
