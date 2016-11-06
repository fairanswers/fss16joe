package com.fairanswers.mapExplore;

import java.util.ArrayList;
import java.util.Collections;

public class Map {
	int len;
	int wid;
	Terrain terrain;
	MapState state;
	ArrayList<Agent> agents = new ArrayList<Agent>();
	private int tick=0;
	public final String end = System.getProperty("line.separator");
	
	public Map(int wid, int len){
		this.wid = wid;
		this.len = len;
		terrain = new Terrain(this);
		state = new MapState(this);
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("["+tick+"]"+end);
		for(int y=0; y<len; y++){
			for(int x=0; x<wid; x++){
							sb.append(getTerrainAt(x, y) );
			}
			sb.append(end);
		}
		return sb.toString();
	}

	public String getTerrainAt(int x, int y) {
		//Check if an agent is at this location
		for(Agent a:agents){
			if(a.getLoc().equals(x,y)){
				return a.getAscii();
			}
			
		}
		return terrain.get(x,y);
	}
	
	//Represents one time tick
	public int tick(){
		tick++;
		//Shuffle the agents and let them move
		Collections.shuffle(agents);
		for(Agent a:agents){
			a.tick(tick);
		}
		return this.tick;
		
	}

	public int getWid() {
		return wid;
	}

	public void setWid(int wid) {
		this.wid = wid;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}

	public MapState getState() {
		return state;
	}

	public void setState(MapState state) {
		this.state = state;
	}

	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}

	public int getTick() {
		return tick;
	}

	public void setTick(int tick) {
		this.tick = tick;
	}
	
	
}
