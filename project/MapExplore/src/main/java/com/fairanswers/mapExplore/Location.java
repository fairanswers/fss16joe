package com.fairanswers.mapExplore;

public class Location {

	double x;
	double y;
	public Location(double x2, double y2) {
		this.x=x2;
		this.y=y2;
	}
	
	@Override
	public String toString() {
		return "Location [" + x + ", " + y + "]";
	}


	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public boolean equals(double x2, double y2) {
		if(x2==x && y2 == y){
			return true;
		}
		return false;
	}
}
