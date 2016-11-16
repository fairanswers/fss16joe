package com.fairanswers.mapExplore.fsm;

import java.util.ArrayList;
import java.util.Collections;

public class State {
	String name;
	int visits=0;
	boolean stop = false;
	transient ArrayList<Guard> guards = new ArrayList<Guard>();
	
	public State(String name, boolean stop){
		this.name = name;
		this.stop = stop;
	}
	public void arrive(){
		
	}
	public void trans(){
		
	}
	public State next() {
		Collections.shuffle(guards, Model.getRandomGenerator()); //Shuffle in place
		for(Guard g:guards){
			if(g.isTrue() ){
				System.out.println("Trans from "+name+" via "+g.getName()+" to "+g.getTo().getName() );
				return g.getTo();
			}
		}
		//If nothing else, stay where we are.
		System.out.println("Staying at "+name);
		return this;
	}
	
	public void addGuard(Guard g){
		guards.add(g);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getVisits() {
		return visits;
	}
	public void setVisits(int visits) {
		this.visits = visits;
	}
	public ArrayList<Guard> getGuards() {
		return guards;
	}
	public void setGuards(ArrayList<Guard> guards) {
		this.guards = guards;
	}
	public boolean isStop() {
		return stop;
	}
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	@Override
	public String toString() {
		return "State [name=" + name + ", visits=" + visits + ", stop=" + stop + ", guards=" + guards + "]";
	}
	
}
