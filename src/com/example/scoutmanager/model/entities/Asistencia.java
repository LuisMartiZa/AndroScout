package com.example.scoutmanager.model.entities;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tAsistencia")
public class Asistencia extends Entity {
	
	@TableField(name= "asiste", datatype= Entity.DATATYPE_BOOLEAN, required= true, maxLength= 100)
	private Boolean Asiste;
	
	 /*
     * Methods of Usuario class.
     */
    
    //CONSTRUCTORS
    public Asistencia(){
    	super();
    }
    
    public Asistencia(Boolean asiste)
    {
    	super();
    	
    	this.Asiste=asiste;
    }
    
    //GETTERS AND SETTERS
    public Boolean getAsiste()
    {
    	return Asiste;
    }
    
    public void setAsiste(Boolean asiste)
    {
    	this.Asiste=asiste;
    }
	
}