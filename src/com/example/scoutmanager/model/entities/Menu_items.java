package com.example.scoutmanager.model.entities;

public class Menu_items {

	private Integer image;
	private String title;

	public Menu_items() {
		super();
	}

	public Menu_items(Integer image, String title) {
		super();
		this.image = image;
		this.title = title;
	}

	public Integer getImage() {
		return image;
	}

	public void setImage(Integer image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}