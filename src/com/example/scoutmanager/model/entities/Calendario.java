package com.example.scoutmanager.model.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tCalendario")
public class Calendario extends Entity {
	
	@TableField(name= "fechaCalendario", datatype= Entity.DATATYPE_DATE_BINARY, required= true, maxLength= 100)
	private Date Fecha = new Date();
	
	@TableField(name= "asistenciaEducando", datatype= Entity.DATATYPE_ENTITY_LINK)
    private List<Educando> asistenciaReunion = new ArrayList<Educando>();
	
	@TableField(name= "eventoCalendario", datatype= Entity.DATATYPE_ENTITY_LINK)
    private Evento evento;
	
	 /*
     * Methods of Calendario class.
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
    
    public List<Educando> getAsistenciaEducando()
    {
    	return this.asistenciaReunion;
    }
    
    public void setAsistenciaEducando(Educando educando)
    {
    	this.asistenciaReunion.add(educando);
    }
	
    public Evento getEventoCalendario()
    {
    	return evento;
    }
    
    public void EventoCalendario(Evento evento)
    {
    	this.evento=evento;
    }
}
