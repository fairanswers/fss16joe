package com.fairanswers.mapExplore.fsm;

public class Guard { //aka "Gaurd"
	String name;
	State to;
	
	public Guard(String name, State to) {
		this.name = name;
		this.to = to;
	}

	public boolean isTrue(){
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getTo() {
		return to;
	}

	public void setTo(State to) {
		this.to = to;
	}
	
	

}
