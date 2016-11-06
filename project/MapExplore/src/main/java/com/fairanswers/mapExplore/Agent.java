package com.fairanswers.mapExplore;

import com.fairanswers.mapExplore.fsm.Model;

public class Agent extends Model {
	String name;
	Location loc;
	
	public Agent(){
	}
	
	public Agent(String name){
		this.name = name;
	}
	
	public Agent(String string, int x, int y) {
		this.name = name;
		this.loc = new Location(x, y);
	}

	public void move() {
		loc.setX(loc.getX()+1);
	}
	
	@Override
	public void tick(int tick){
		move();
	}
////////////
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public Location getLoc() {
		return loc;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	public void setLoc(int x, int y) {
		this.loc = new Location(x,y);
	}
	public String getAscii() {
		return "A";
	}
	
}
