package com.example.scoutmanager.model.entities;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tEducandos")
public class Educando extends Entity {
	
	@TableField(name= "nombreEducando", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	private String Nombre = "";
	
	@TableField(name= "apellidoEducando", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	private String Apellidos = "";
	
	@TableField(name= "nacimientoEducando", datatype= Entity.DATATYPE_DATE_BINARY, required= true)
	private Date F_Nacimiento = new Date();
	
	@TableField(name= "fotografiaEducando", datatype= Entity.DATATYPE_TEXT)
    private String fotografia;
    
    @TableField(name= "seccionEducando", datatype= Entity.DATATYPE_ENTITY_LINK)
    private Seccion seccion;
    
    @TableField(name= "etapaEducando", datatype= Entity.DATATYPE_ENTITY_LINK)
    private Etapa etapa;
    
    @TableField(name= "tutorEducando", datatype= Entity.DATATYPE_ENTITY_LINK)
    private List<Tutor> tutores= new ArrayList<Tutor>();
    
    @TableField(name= "comunicacionEducando", datatype= Entity.DATATYPE_ENTITY)
    private List<Comunicaciones> comunicaciones = new ArrayList<Comunicaciones>();
    
    @TableField(name= "insigniasEducando", datatype= Entity.DATATYPE_ENTITY_LINK)
    private List<Insignias> insignias = new ArrayList<Insignias>();
    
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
    
    public void setFotografia(String foto)
    {
    	this.fotografia=foto;
    }
    
    public String getFotografia()
    {
    	return fotografia;
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
    
    public List<Tutor> getTutorEducando(String nombreTutor)
    {
    	return tutores;
    }
    
    public void addTutorEducando(Tutor tutor)
    {
    	this.tutores.add(tutor);
    }
    
    public List<Comunicaciones> getComunicacionesEducando()
    {
    	return comunicaciones;
    }
    
    public void addComunicacionEducando(Comunicaciones com)
    {
    	this.comunicaciones.add(com);
    }
    
    public List<Insignias> getInsigniasEducando()
    {
    	return insignias;
    }
    
    public void addInsigniaEducando(Insignias insignia)
    {
    	this.insignias.add(insignia);
    }
}
