package com.example.scoutmanager.model.entities;


import java.util.Date;

import android.widget.ImageView;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.*;

@Table(name = "tEducandos")
public class Educando extends Entity {
	
	@TableField(name= "nombreEducando", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	private String Nombre = "";
	
	@TableField(name= "apellidoEducando", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	private String Apellidos = "";
	
	@TableField(name= "nacimientoEducando", datatype= Entity.DATATYPE_DATE_BINARY, required= true, maxLength= 100)
	private Date F_Nacimiento = new Date();
	
    private ImageView fotografiaEducando;
    
    @TableField(name= "seccionEducando", datatype= Entity.DATATYPE_ENTITY)
    private Seccion seccion;
    
    @TableField(name= "etapaEducando", datatype= Entity.DATATYPE_ENTITY)
    private Etapa etapa;
    
    
    /*
     * Methods of Usuario class.
     */
    
    //CONSTRUCTORS
    public Educando(){
    	super();
    }
    
    public Educando(String nombre, String apellidos, Date f_nac)
    {
    	super();
    	
    	this.Nombre=nombre;
    	this.Apellidos=apellidos;
    	this.F_Nacimiento=f_nac;
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
    
    public Etapa getEtapaEducando()
    {
    	return etapa;
    }
    
    public void setEtapaEducando(Etapa etapa)
    {
    	this.etapa=etapa;
    }
    
    public Seccion getSeccionEducando()
    {
    	return seccion;
    }
    
    public void setSeccionEducando(Seccion seccion)
    {
    	this.seccion=seccion;
    }
}
