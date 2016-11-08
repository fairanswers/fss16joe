package com.fairanswers.mapExplore;

public class Terrain {
	public final static String UNKNOWN = " ";
	public final static String DEFAULT = ".";
	public final static String AGENT   = "A";
	private static final String TERRAIN_CORNER = "T";
	public final String end = System.getProperty("line.separator");

	int wid;
	int len;
	String [][] detail;
	public Terrain(Map map) {
		this(map, DEFAULT);
	}

	public Terrain(Map map, String fill) {
		this.wid = map.getWid();
		this.len = map.getLen();
		detail = new String[wid][len];
		for(int x=0; x<wid; x++){
			for(int y=0; y<len; y++){
				detail[x][y] = fill;
			}
		}	
	}
	
	public String get(int x, int y){
		return detail[x][y];
	}

	//http://stackoverflow.com/questions/6468730/converting-double-to-integer-in-java
	public String get(double x, double y){
		return get((int)x,(int)y);
	}

	public static Terrain createAgentTerrain(Map map){
		return new Terrain(map, UNKNOWN);
	}

	public void setTerrain(int x, int y, String terrainAt) {
		detail[x][y] = terrainAt;
	}
	public void setTerrain(double x, double y, String terrainAt) {
		setTerrain((int)x, (int)y, terrainAt);
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(TERRAIN_CORNER);
		for(int x=0; x<wid; x++){
			sb.append(x%10);
		}
		sb.append(TERRAIN_CORNER);
		sb.append(end);
		for(int y=len-1; y>=0; y--){
			sb.append(y%10);
			for(int x=0; x<wid; x++){
				sb.append(detail[x][y]);
			}
			sb.append(y%10);
			sb.append(end);
		}
		sb.append(TERRAIN_CORNER);
		for(int x=0; x<wid; x++){
			sb.append(x%10);
		}
		sb.append(TERRAIN_CORNER);
		return sb.toString();
	}

	/////////////////
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

	public String[][] getDetail() {
		return detail;
	}

	public void setDetail(String[][] detail) {
		this.detail = detail;
	}

	
}
