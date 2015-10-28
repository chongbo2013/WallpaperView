package com.example.administrator.cleanviewactivity;

import java.io.Serializable;

import android.graphics.Color;
import android.graphics.Path;

public class Fllower implements Serializable{
	private int width;
	private Path path;
	private float value;

	public int getWidth(){
		return width;
	}
	public void setWidth(int width){
		this.width=width;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}


	private int color= Color.GREEN;
	public void setFllowerColor(int color) {
			this.color=color;
	}
	public int getFllowerColor(){

		return color;
	}
}
