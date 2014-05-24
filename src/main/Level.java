package main;

import javax.swing.JFrame;

public class Level {
	
	public int milliseconds;
	public String name;
	
	public Level (String name, int milliseconds){
		this.milliseconds = milliseconds;
		this.name = name;
	}
	
	@Override
	public String toString(){
		return name;
	}

}
