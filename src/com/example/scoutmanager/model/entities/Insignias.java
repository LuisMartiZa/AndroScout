package com.example.scoutmanager.model.entities;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tInsignias")
public class Insignias extends Entity {
	
	@TableField(name= "nombreInsignia", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	public String Nombre = "";
	
	@TableField(name= "tipoInsignia", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	public String Tipo = "";
	
}