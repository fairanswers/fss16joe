package com.fairanswers.mapExplore.fsm;

public class Trans {

	State from;
	Guard guard;
	
	public Trans(State from, Guard guard){
		this.from=from;
		this.guard=guard;
	}

	public State getFrom() {
		return from;
	}

	public void setFrom(State from) {
		this.from = from;
	}

	public Guard getGuard() {
		return guard;
	}

	public void setGuard(Guard guard) {
		this.guard = guard;
	}

	@Override
	public String toString() {
		return "Trans [from=" + from + ", guard=" + guard + "]";
	}
}
