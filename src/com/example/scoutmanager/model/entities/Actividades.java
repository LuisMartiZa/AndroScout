package com.example.scoutmanager.model.entities;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tActividades")
public class Actividades extends Entity {
	
	@TableField(name= "tipoActividad", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	public String Tipo = "";
	
	@TableField(name= "descripcionActividad", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	public String Descripcion = "";

}
