package com.example.scoutmanager.model.entities;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tAsistencia")
public class Asistencia extends Entity {
	
	@TableField(name= "asistenciaRonda", datatype= Entity.DATATYPE_TEXT, required= true)
	public String Ronda;
	
	@TableField(name= "asistenciasEducando", datatype= Entity.DATATYPE_INTEGER, required= true)
	public int Asistencias;
	
	
	 /*
     * Methods of Usuario class.
     */
    
    //CONSTRUCTORS
    public Asistencia(){
    	super();
    }
    
    public Asistencia(String ronda, int asistencia)
    {
    	super();
    	this.Ronda= ronda;
    	this.Asistencias= asistencia;
    }
    
    //GETTERS AND SETTERS
    public int getAsistencias()
    {
    	return Asistencias;
    }
    
    public void setAsiste(int asistencias)
    {
    	this.Asistencias=asistencias;
    }
	
}