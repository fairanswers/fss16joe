package com.fairanswers.mapExplore;

public class Location {

	int x;
	int y;
	public Location(int x2, int y2) {
		this.x=x2;
		this.y=y2;
	}
	
	@Override
	public String toString() {
		return "Location [" + x + ", " + y + "]";
	}


	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean equals(int x2, int y2) {
		if(x2==x && y2 == y){
			return true;
		}
		return false;
	}
}
