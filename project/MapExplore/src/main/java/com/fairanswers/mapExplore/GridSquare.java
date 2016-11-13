package com.fairanswers.mapExplore;

public class GridSquare {

	public static final String BLANK = " ";
	String view = BLANK;
	private double friction = 0;
	
	public GridSquare() {
		
	}

	public GridSquare(String view, double friction) {
			this.view = view;
			this.friction = friction;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public double getFriction() {
		return friction;
	}

	public void setFriction(double friction) {
		this.friction = friction;
	}

}
