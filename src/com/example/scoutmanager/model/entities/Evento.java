package com.example.scoutmanager.model.entities;

import java.util.Date;

import com.example.scoutmanager.R;
import com.example.scoutmanager.parsers.DateParser;
import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Databinding;
import com.mobandme.ada.annotations.RequiredFieldValidation;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tEvento")
public class Evento extends Entity {
	
	@TableField(name= "nombreEvento", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	@Databinding(ViewId = R.id.nameEventoField)
	@RequiredFieldValidation(message = "El nombre del evento es requerido")
	public String Nombre = "";
	
	@TableField(name= "lugarEvento", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	@Databinding(ViewId = R.id.lugarEventoField)
	@RequiredFieldValidation(message = "El lugar del evento es requerido")
	public String Lugar = "";
	
	@TableField(name= "fechaEvento", datatype= Entity.DATATYPE_DATE_BINARY, required= true)
	@Databinding(ViewId = R.id.fechaEventoField, parser = DateParser.class)
	@RequiredFieldValidation(message = "La fecha del evento es requerida")
	public Date F_Evento = new Date();
	
	 /*
     * Methods of Evento class.
     */
    
    //CONSTRUCTORS
    public Evento(){
    	super();
    }
    
    public Evento(String nombre, String lugar, String tipo, Date fecha)
    {
    	super();
    	
    	this.Nombre=nombre;
    	this.Lugar=lugar;
    	this.F_Evento=fecha;
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
    
    public String getLugar()
    {
    	return Lugar;
    }
    
    public void setLugar(String lugar)
    {
    	this.Lugar=lugar;
    }
    
    public Date getFechaEvento()
    {
    	return F_Evento;
    }
    
    public void setFechaEvento(Date f_evento)
    {
    	this.F_Evento=f_evento;
    }

}