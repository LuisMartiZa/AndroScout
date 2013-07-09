package com.example.scoutmanager.model.entities;

import java.util.Date;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tCalendario")
public class Calendario extends Entity {
	
	@TableField(name= "fechaCalendario", datatype= Entity.DATATYPE_DATE_BINARY, required= true, maxLength= 100)
	public Date Fecha = new Date();
	
}
