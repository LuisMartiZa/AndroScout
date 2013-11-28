package com.example.scoutmanager.model.entities;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tAsistencia")
public class Asistencia extends Entity {
	
	@TableField(name= "asistencias", datatype= Entity.DATATYPE_INTEGER, required= true)
	private int Asistencias;
	
	@TableField(name= "asistenciaEducandos", datatype= Entity.DATATYPE_ENTITY)
    public Educando Educando= new Educando();

	
	 /*
     * Methods of Usuario class.
     */
    
    //CONSTRUCTORS
    public Asistencia(){
    	super();
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
    
    public Educando getEducandos()
    {
    	return Educando;
    }
    
    public void addEducando(Educando educando)
    {
    	this.Educando=educando;
    }
	
}