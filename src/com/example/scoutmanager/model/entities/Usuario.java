package com.example.scoutmanager.model.entities;

import java.util.Date;

import android.provider.MediaStore.Images;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

@Table(name = "tUsuario")
public class Usuario extends Entity {
	
	@TableField(name= "nombreUsuario", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 100)
	public String Nombre = "";
	
	@TableField(name= "apellidoUsuario", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 200)
	public String Apellidos = "";
	
	@TableField(name= "nacimientoUsuario", datatype= Entity.DATATYPE_DATE_BINARY, required= true, maxLength= 100)
	public Date F_Nacimiento = new Date();
	
	@TableField(name = "psswordUsuario", datatype= Entity.DATATYPE_TEXT, required= true, maxLength= 10, encripted= true)
	public String Pssword = "";
	
	public Images ImagenUsuario = new Images();
	
}