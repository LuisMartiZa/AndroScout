package com.example.scoutmanager.model.entities;

import java.util.ArrayList;
import java.util.List;

import com.example.scoutmanager.R;
import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.*;

@Table(name = "tTutor")
public class Tutor extends Entity {
	
	@TableField(name= "nombreTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	@Databinding(ViewId= R.id.nombreTutor)
	public String Nombre = "";
	
	@TableField(name= "telefonoTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	@Databinding(ViewId= R.id.telefonoTutor)
	public String Telefono = "";
	
	@TableField(name= "emailTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	@Databinding(ViewId= R.id.emailTutor)
	public String Email = "";
	
	@TableField(name= "tipoTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	@Databinding(ViewId= R.id.tipoTutor)
	public String Tipo = "";
	
	@TableField(name= "hijosTutor", datatype= Entity.DATATYPE_ENTITY_LINK)
    public List<Educando> hijos= new ArrayList<Educando>();

	
	 /*
     * Methods of Usuario class.
     */
    
    //CONSTRUCTORS
    public Tutor(){
    	super();
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
    
    public String getTelefono()
    {
    	return Telefono;
    }
    
    public void setTelefono(String telefono)
    {
    	this.Telefono=telefono;
    }
    
    public String getEmail()
    {
    	return Email;
    }
    
    public void setEmail(String email)
    {
    	this.Email=email;
    }

    public String getTipo()
    {
    	return Tipo;
    }
    
    public void setTipo(String tipo)
    {
    	this.Tipo=tipo;
    }
    
    public List<Educando> getHijos()
    {
    	return hijos;
    }
    
    public void addHijoTutor(Educando educando)
    {
    	this.hijos.add(educando);
    }
}