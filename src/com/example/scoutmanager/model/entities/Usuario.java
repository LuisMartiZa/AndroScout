package com.example.scoutmanager.model.entities;

import java.util.Date;

import android.widget.ImageView;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tUsuario")
public class Usuario extends Entity {
	
	/*
	 * Properties of Usuario class.
	 */
	
	@TableField(name= "nombreUsuario", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	private String Nombre = "";
	
	@TableField(name= "apellidoUsuario", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	private String Apellidos = "";
	
	@TableField(name= "nacimientoUsuario", datatype= Entity.DATATYPE_DATE_BINARY, required= true, maxLength= 100)
	private Date F_Nacimiento = new Date();
	
	@TableField(name = "psswordUsuario", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 10, encripted= true)
	private String Pssword = "";
	
    private ImageView fotografiaUsuario;
    
    @TableField(name= "seccionUsuario", datatype= Entity.DATATYPE_ENTITY_LINK)
    private Seccion seccion;
    
    /*
     * Methods of Usuario class.
     */
    
    //CONSTRUCTORS
    public Usuario(){
    	super();
    }
    
    public Usuario(String nombre, String apellidos, Date f_nac, String passwd)
    {
    	super();
    	
    	this.Nombre=nombre;
    	this.Apellidos=apellidos;
    	this.F_Nacimiento=f_nac;
    	this.Pssword=passwd;
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
    
    public Date getFechaNacimiento()
    {
    	return F_Nacimiento;
    }
    
    public void setFechaNacimiento(Date f_nac)
    {
    	this.F_Nacimiento=f_nac;
    }
    
    public void setPassword(String psswd)
    {
    	this.Pssword=psswd;
    }
    
    public Seccion getSeccionUsuario()
    {
    	return seccion;
    }
    
    public void setSeccionUsuario(Seccion seccion)
    {
    	this.seccion=seccion;
    }
	
}