package com.example.scoutmanager.model.entities;

import java.util.ArrayList;
import java.util.List;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tEvento")
public class Evento extends Entity {
	
	@TableField(name= "nombreEvento", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	private String Nombre = "";
	
	@TableField(name= "lugarEvento", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	private String Lugar = "";
	
	@TableField(name= "tipoEvento", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	private String Tipo = "";
	
	@TableField(name= "educandosEvento", datatype= Entity.DATATYPE_ENTITY_LINK)
    private List<Educando> educandosEvento = new ArrayList<Educando>();
	 /*
     * Methods of Usuario class.
     */
    
    //CONSTRUCTORS
    public Evento(){
    	super();
    }
    
    public Evento(String nombre, String lugar, String tipo)
    {
    	super();
    	
    	this.Nombre=nombre;
    	this.Lugar=lugar;
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
    
    public String getLugar()
    {
    	return Lugar;
    }
    
    public void setLugar(String lugar)
    {
    	this.Lugar=lugar;
    }
    
    public String getTipo()
    {
    	return Tipo;
    }
    
    public void setTipo(String tipo)
    {
    	this.Tipo=tipo;
    }
	
    public List<Educando> getEducandosEvento()
    {
    	return this.educandosEvento;
    }
    
    public void addEducandoEvento(Educando educando)
    {
    	this.educandosEvento.add(educando);
    }
}