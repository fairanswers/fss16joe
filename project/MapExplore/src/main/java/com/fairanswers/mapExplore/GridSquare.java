package com.fairanswers.mapExplore;

public class GridSquare {

	public static final String BLANK = " ";
	String view = BLANK;
	
	public GridSquare() {
		
	}

	public GridSquare(String view) {
		this.view = view;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}
}
