package com.example.scoutmanager.model.entities;

import java.util.Date;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.*;

@Table(name = "tEducandos")
public class Educando extends Entity {
	
	@TableField(name= "nombreEducando", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	public String Nombre = "";
	
	@TableField(name= "apellidoEducando", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	public String Apellidos = "";
	
	@TableField(name= "nacimientoEducando", datatype= Entity.DATATYPE_DATE_BINARY, required= true, maxLength= 100)
	public Date F_Nacimiento = new Date();
	

}
