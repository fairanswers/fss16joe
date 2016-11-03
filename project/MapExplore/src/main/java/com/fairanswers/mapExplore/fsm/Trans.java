package com.fairanswers.mapExplore.fsm;

public class Trans {

	State from;
	Guard guard;
	State to;
	
	public Trans(State from, Guard guard, State to){
		this.from=from;
		this.guard=guard;
		this.to=to;
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

	public State getTo() {
		return to;
	}

	public void setTo(State to) {
		this.to = to;
	}
	
}
