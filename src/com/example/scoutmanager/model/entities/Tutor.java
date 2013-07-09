package com.example.scoutmanager.model.entities;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.*;

@Table(name = "tTutor")
public class Tutor extends Entity {
	
	@TableField(name= "nombreTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	public String Nombre = "";
	
	@TableField(name= "apellidoTutor", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	public String Apellidos = "";

}