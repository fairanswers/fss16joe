package com.fairanswers.mapExplore.fsm;

public class Always extends Guard {

	public Always(String name, State to) {
		super(name, to);
	}
	@Override
	public boolean isTrue(){
		return true;
	}

}
