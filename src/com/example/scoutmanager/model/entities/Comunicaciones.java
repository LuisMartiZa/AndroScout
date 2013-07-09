package com.example.scoutmanager.model.entities;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.*;

@Table(name = "tComunicaciones")
public class Comunicaciones extends Entity {
	
	@TableField(name= "tipoComunicacion", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	public String Tipo = "";
	
	@TableField(name= "datoComunicaci—n", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	public String Datos = "";

}


