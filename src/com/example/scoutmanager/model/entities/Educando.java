package com.example.scoutmanager.model.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.scoutmanager.R;
import com.example.scoutmanager.parsers.DateParser;
import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Databinding;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tEducandos")
public class Educando extends Entity {
	
	@TableField(name= "nombreEducando", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	@Databinding(ViewId= R.id.nameEducando)
	public String Nombre = "";
	
	@TableField(name= "apellidoEducando", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	@Databinding(ViewId= R.id.surnameEducando)
	public String Apellidos = "";
	
	@TableField(name= "direccionEducando", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	@Databinding(ViewId= R.id.dirEducando)
	public String Direccion = "";
	
	@TableField(name= "nacimientoEducando", datatype= Entity.DATATYPE_DATE_BINARY)
	@Databinding(ViewId = R.id.educandoBirthday, parser = DateParser.class)
	public Date F_Nacimiento = new Date();
	
	@TableField(name= "emailEducando", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	@Databinding(ViewId= R.id.educandoEmail)
	public String Email = "";
	
	@TableField(name= "telefonoEducando", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	@Databinding(ViewId= R.id.telefonoEducando)
	public String Telefono = "";
	
    @TableField(name= "imagenEducando", datatype= Entity.DATATYPE_TEXT)
	public String imagen = "";
	
    @TableField(name= "seccionEducando", datatype= Entity.DATATYPE_ENTITY_LINK)
    public Seccion seccion = new Seccion();
    
    @TableField(name= "etapaEducando", datatype= Entity.DATATYPE_ENTITY_LINK)
    public Etapa etapa = new Etapa();
    
    @TableField(name= "tutorEducando", datatype= Entity.DATATYPE_ENTITY_LINK)
    public List<Tutor> tutores= new ArrayList<Tutor>();
    
    
    /*
     * Methods of Educando class.
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
    	//this.F_Nacimiento=f_nac;
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
    
    public String getDireccion()
    {
    	return Direccion;
    }
    
    public void setDireccion(String direccion)
    {
    	this.Direccion=direccion;
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
    
    public List<Tutor> getTutorEducando()
    {
    	return tutores;
    }
    
    public void addTutorEducando(Tutor tutor)
    {
    	this.tutores.add(tutor);
    }
    
    public String getEmail()
    {
    	return Email;
    }
    
    public void setEmail(String email)
    {
    	this.Email=email;
    }
    
    public String getTelefono()
    {
    	return Telefono;
    }
    
    public void setTelefono(String telefono)
    {
    	this.Telefono=telefono;
    }
    
    public void setImagen(String imagen)
    {
    	this.imagen=imagen;
    }
    
    public String getImagen()
    {
    	return imagen;
    }
    
}
