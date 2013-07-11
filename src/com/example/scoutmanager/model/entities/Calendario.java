package com.example.scoutmanager.model.entities;

import java.util.Date;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tCalendario")
public class Calendario extends Entity {
	
	@TableField(name= "fechaCalendario", datatype= Entity.DATATYPE_DATE_BINARY, required= true, maxLength= 100)
	private Date Fecha = new Date();
	
	 /*
     * Methods of Usuario class.
     */
    
    //CONSTRUCTORS
    public Calendario(){
    	super();
    }
    
    public Calendario(Date fecha)
    {
    	super();
    	
    	this.Fecha=fecha;
    }
    
    //GETTERS AND SETTERS
    public Date getFecha()
    {
    	return Fecha;
    }
    
    public void setFecha(Date fecha)
    {
    	this.Fecha=fecha;
    }
	
}
