package com.example.scoutmanager.model.entities;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tEvento")
public class Evento extends Entity {
	
	@TableField(name= "nombreEvento", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	public String Nombre = "";
	
	@TableField(name= "lugarEvento", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	public String Lugar = "";
	
	@TableField(name= "tipoEvento", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	public String Tipo = "";
	
}