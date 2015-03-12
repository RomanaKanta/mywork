package com.ftfl.icare.modelclass;

public class DrawerModel {
	private String mTitle;
	private int mIcon;
	
	public DrawerModel(){}

	public DrawerModel(String eTitle, int eIcon){
		this.mTitle = eTitle;
		this.mIcon = eIcon;
	}
	
	public String getTitle(){
		return this.mTitle;
	}
	
	public int getIcon(){
		return this.mIcon;
	}
	
	public void setTitle(String title){
		this.mTitle = title;
	}
	
	public void setIcon(int icon){
		this.mIcon = icon;
	}
}
