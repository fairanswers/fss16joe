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
		return "Location [" + Map.numFormat.format(x) + ", " + Map.numFormat.format(y) + "]";
	}

	public boolean equals(double x2, double y2) {
		if((int)x2-(int)x == 0  && (int)y2-(int)y== 0){
			return true;
		}
		return false;
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

	public Location mirror() {
		return new Location(-1*getX(), -1*getY());
	}
}
