package com.example.scoutmanager.model.entities;

import com.example.scoutmanager.R;
import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.*;

@Table(name = "tTutor")
public class Tutor extends Entity {
	
	@TableField(name= "nombreTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	@Databinding(ViewId= R.id.nombreTutor)
	@RequiredFieldValidation(message = "El nombre del tutor/a es requerido")
	public String Nombre = "";
	
	@TableField(name= "apellidosTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	@Databinding(ViewId= R.id.apellidosTutor)
	@RequiredFieldValidation(message = "Los apellidos del tutor/a son requeridos")
	public String Apellidos = "";
	
	@TableField(name= "telefonoTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	@Databinding(ViewId= R.id.telefonoTutor)
	@RequiredFieldValidation(message = "El t�lefono del tutor/a es requerido")
	public String Telefono = "";
	
	@TableField(name= "emailTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	@Databinding(ViewId= R.id.emailTutor)
	@RequiredFieldValidation(message = "El e-mail del tutor/a es requerido")
	public String Email = "";
	
	@TableField(name= "tipoTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	@Databinding(ViewId= R.id.tipoTutor)
	@RequiredFieldValidation(message = "El tipo de tutor/a es requerido")
	public String Tipo = "";

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

    public String getTipo()
    {
    	return Tipo;
    }
    
    public void setTipo(String tipo)
    {
    	this.Tipo=tipo;
    }
}