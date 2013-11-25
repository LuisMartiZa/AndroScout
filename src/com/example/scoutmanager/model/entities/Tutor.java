package com.example.scoutmanager.model.entities;

import com.example.scoutmanager.R;
import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.*;

@Table(name = "tTutor")
public class Tutor extends Entity {
	
	@TableField(name= "nombreTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	@Databinding(ViewId= R.id.nombreTutor)
	public String Nombre = "";
	
	@TableField(name= "apellidoTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	@Databinding(ViewId= R.id.apellidosTutor)
	public String Apellidos = "";
	
	@TableField(name= "telefonoTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	@Databinding(ViewId= R.id.telefonoTutor)
	public String Telefono = "";
	
	@TableField(name= "emailTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	@Databinding(ViewId= R.id.emailTutor)
	public String Email = "";

	
	 /*
     * Methods of Usuario class.
     */
    
    //CONSTRUCTORS
    public Tutor(){
    	super();
    }
    
    public Tutor(String nombre, String apellidos)
    {
    	super();
    	
    	this.Nombre=nombre;
    	this.Apellidos=apellidos;
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
    
    public String getApellidos()
    {
    	return Apellidos;
    }
    
    public void setApellidos(String apellidos)
    {
    	this.Apellidos=apellidos;
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

}