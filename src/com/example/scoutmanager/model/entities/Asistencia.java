package com.example.scoutmanager.model.entities;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tAsistencia")
public class Asistencia extends Entity {
	
	@TableField(name= "asiste", datatype= Entity.DATATYPE_BOOLEAN, required= true, maxLength= 100)
	public Boolean Asiste;
	
}