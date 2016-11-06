package com.fairanswers.mapExplore.fsm;

public class Maybe extends Guard {

	public Maybe(String name, State to) {
		super(name, to);
	}

	@Override
	public boolean isTrue(){
		return Model.getRandom() > .5;
			
	}
}
