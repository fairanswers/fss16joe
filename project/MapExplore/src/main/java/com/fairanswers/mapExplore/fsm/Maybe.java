package com.fairanswers.mapExplore.fsm;

public class Maybe extends Guard {

	private double probability;

	public Maybe(String name, State to, double likelihood) {
		super(name, to);
		this.probability = likelihood;
	}

	@Override
	public boolean isTrue(){
		return Model.getRandom() < probability;
			
	}

	@Override
	public String toString() {
		return "Maybe [probability=" + probability + ", name=" + name + ", to=" + to + "]";
	}
	
}
