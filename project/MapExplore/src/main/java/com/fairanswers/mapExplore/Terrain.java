package com.fairanswers.mapExplore;

public class Terrain {

	int wid;
	int len;
	String [][] state;
	
	public Terrain(Map map) {
		this.wid = map.getWid();
		this.len = map.getLen();
		state = new String[wid][len];
		for(int x=0; x<wid; x++){
			for(int y=0; y<len; y++){
				state[x][y] = ".";
			}
		}	
	}
	
	public String get(int x, int y){
		return state[x][y];
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

	public String[][] getState() {
		return state;
	}

	public void setState(String[][] state) {
		this.state = state;
	}
	
	
}
