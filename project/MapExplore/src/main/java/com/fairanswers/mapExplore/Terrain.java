package com.fairanswers.mapExplore;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.uma.jmetal.util.experiment.component.GenerateFriedmanTestTables;

import com.fairanswers.mapExplore.fsm.Model;

public class Terrain {
	public final static String UNKNOWN = " ";
	public final static double UNKNOWN_WT = 0;
	public final static String DEFAULT = ".";
	public final static double DEFAULT_WT = 1;
	public final static String AGENT = "A";
	private static final String TERRAIN_CORNER = "T";
	public final String end = System.getProperty("line.separator");

	HashMap<String, Double> weight = new HashMap<>();
	public final static String PAVED = ".";
	public final static double PAVED_WT = 1;
	public final static String GRASS = ",";
	public final static double GRASS_WT = 5;
	public final static String SLOPE = "o";
	public final static double SLOPE_WT = 25;
	public final static String CLIFF = "X";
	public final static double CLIFF_WT = 100;
	public static final String INITIAL = "I";
	public static final String PARETO = "*";

	int wid;
	int len;
	GridSquare[][] detail;
//	private long seed;
	private double varianceFactor;

	public Terrain(Map map) {
		this(map, DEFAULT);
	}
	
//	public static Terrain getTerrainFromString(Map map, String details, double friction) throws Exception {
//		Terrain t = new Terrain(map);
//		int charCounter =0;
//		for (int y = map.getLen()-1; y >= 0; y--) {
//			for (int x = 0; x < map.getWid(); x++) {
//				try{
//					t.getDetail()[x][y] = new GridSquare(details.substring(charCounter, charCounter+1), friction);
//					charCounter++;
//				}catch(Exception e){
//					throw new Exception("Error loading terrain at x="+x+" y="+y+" for string "+details, e);
//				}
//			}
//		}
//		return t;
//	}

	public String getSaveString() throws Exception {
		StringBuffer sb = new StringBuffer();
		int charCounter = 0;
		for (int y = getLen()-1; y >= 0; y--) {
			for (int x = 0; x < getWid(); x++) {
				try{
					sb.append(getDetail()[x][y].getView());
					charCounter++;
				}catch(Exception e){
					throw new Exception("Error saving terrain at x="+x+" y="+y+" for "+this, e);
				}
			}
		}
		return sb.toString();
	}
	
	public void save(String filename) throws IOException, Exception{
		StringBuffer data = new StringBuffer();
		for(int y=len-1; y>=0; y--){
			for(int x=0; x<wid; x++){
				data.append(detail[x][y].getView() );
			}
			data.append(end);
		}
		FileUtils.writeStringToFile(FileUtils.getFile(filename), data.toString(), "UTF-8");
	}

	public static Terrain load(String filename, double friction) throws IOException, Exception{
		List<String> s = FileUtils.readLines(new File(filename), "UTF-8");
		Terrain t = Terrain.getTerrainFromStringList(new Map(s.get(0).length() ,s.size() ), s);
		return t;
		
	}

	private static Terrain getTerrainFromStringList(Map map, List<String> s) throws Exception {
		Terrain t = new Terrain(map);
		int charCounter =0;
		for (int y = map.getLen()-1; y >= 0; y--) {
			for (int x = 0; x < map.getWid(); x++) {
				try{
					String view=s.get(y).substring(x,x+1);
					double friction = t.getWeight().get(view);
					t.getDetail()[x][y] = new GridSquare(view, friction);
					charCounter++;
				}catch(Exception e){
					throw new Exception("Error loading terrain at x="+x+" y="+y+" for string "+s.get(y), e);
				}
			}
		}
		return t;

	}

	public Terrain(Map map, String fill) {
		weight.put(PAVED, PAVED_WT);
		weight.put(GRASS, GRASS_WT);
		weight.put(SLOPE, SLOPE_WT);
		weight.put(CLIFF, CLIFF_WT);
		weight.put(UNKNOWN, UNKNOWN_WT);
		weight.put(INITIAL, UNKNOWN_WT);
		weight.put(PARETO, UNKNOWN_WT);
		this.wid = map.getWid();
		this.len = map.getLen();
		detail = new GridSquare[wid][len];
		for (int x = 0; x < wid; x++) {
			for (int y = 0; y < len; y++) {
				double friction = weight.get(fill);
				detail[x][y] = new GridSquare(fill, friction);
			}
		}
	}

	public double getCoverage(){
		double found = 0;
		for (int x = 0; x < wid; x++) {
			for (int y = 0; y < len; y++) {
				if(!detail[x][y].getView().equals(UNKNOWN)){
					found++;
				}
			}
		}
		double total = found * 100 / (double)(len * wid);
		return total;
	}
	
	public Location weight(Location loc, String item) {
		Location weight = new Location(0, 0);
		for (double x = 0; x < wid; x++) {
			for (double y = 0; y < len; y++) {
				if (item.equals(detail[(int) x][(int) y].getView())) {
					// Add one if right of loc, subtract if left of loc
					weight.setX(weight.getX()+compare(x, loc.getX()));
					// Add one if above loc, subtract if below loc
					weight.setY(weight.getY()+compare(y, loc.getY()));
				}
			}
		}
		return weight;
	}

	public Location weightNeighborhood(Location loc, String item, double range) {
		Location weight = new Location(0, 0);
		for (double x = loc.getX()-range;  x <= loc.getX()+range; x++) {
			for (double y = loc.getY()-range; y <= loc.getY()+range; y++) {
				if (isValid(x,y) && item.equals(detail[(int) x][(int) y].getView())) {
					// Add one if right of loc, subtract if left of loc
					weight.setX(weight.getX()+compare(x, loc.getX()));
					// Add one if above loc, subtract if below loc
					weight.setY(weight.getY()+compare(y, loc.getY()));
				}
			}
		}
		return weight;
	}

	public double compare(double x1, double x2) {
		if ((int) x1 == (int) x2) {
			return 0;
		}
		if (x1 < x2) {
			return -1;
		}else{
			return  1;
		}
	}

	public boolean isValid(double x, double y) {
		if (x < 0.0 || y < 0.0 || x > wid || y > len ) {
			return false;
		}
		return isValid((int) x, (int) y);
	}

	public boolean isValid(int x, int y) {
		if (x >= 0 && x < wid && y >= 0 && y < len) {
			return true;
		} else {
			return false;
		}
	}

	// public GridSquare[][] look(Location loc , int see) {
	//
	// int topX = see*2+1;
	// int topY = see*2+1;
	// GridSquare [][] gs = new GridSquare[topX][topY];
	// for(int x=-see; x<=see; x++){
	// for(int y=-see, y<=see; y++){
	// double tx = loc.getX()+x;
	// double ty = loc.getY()+y;
	// gs[]][loc.getY()+y];
	//
	// }
	// }
	// }

	// Terrain Factor [0,1] determines how many cliff, slopes, grass, etc
	// if rand < factor * .1 -> cliff
	// if rand < factor * .3 -> slope
	// if rand < factor * .5 -> grass
	// otherwise, paved
	//
	// TODO Can also use an array to determine probabilities.
	public Terrain(Map map, double factor) {
		//this.seed = seed;
		this.varianceFactor = factor;
		//Model.setRandomSeed(seed);
		this.wid = map.getWid();
		this.len = map.getLen();
		detail = new GridSquare[wid][len];
		for (int x = 0; x < wid; x++) {
			for (int y = 0; y < len; y++) {
				Double rand = Model.getRandom() * factor;
				if (rand < .1) {
					detail[x][y] = new GridSquare(CLIFF, CLIFF_WT);
				} else if (rand < .3) {
					detail[x][y] = new GridSquare(SLOPE, SLOPE_WT);
				} else if (rand < .5) {
					detail[x][y] = new GridSquare(GRASS, GRASS_WT);
				} else {
					detail[x][y] = new GridSquare(PAVED, PAVED_WT);
				}
			}
		}
	}
	// public void loadTerrainString(String setup){
	// for(int x=0; x<wid; x++){
	// for(int y=0; y<len; y++){
	// detail[x][y] = new String(setup.charAt(x+y*wid ) );
	// }
	// }
	// }

	public String get(int x, int y) {
		return detail[x][y].getView();
	}

	// http://stackoverflow.com/questions/6468730/converting-double-to-integer-in-java
	public String get(double x, double y) {
		return get((int) x, (int) y);
	}

	public static Terrain createAgentTerrain(Map map) {
		return new Terrain(map, UNKNOWN);
	}

	public void setTerrain(int x, int y, String terrainAt) {
		detail[x][y].setView(terrainAt);
		detail[x][y].setFriction(weight.get(terrainAt));
	}

	public void setTerrain(double x, double y, String terrainAt) {
		setTerrain((int) x, (int) y, terrainAt);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Terrain: variance=" + getVarianceFactor() + " covered=" + getCoverage() + "%"+ end);
		sb.append(TERRAIN_CORNER);
		for (int x = 0; x < wid; x++) {
			sb.append(x % 10);
		}
		sb.append(TERRAIN_CORNER);
		sb.append(end);
		for (int y = len - 1; y >= 0; y--) {
			sb.append(y % 10);
			for (int x = 0; x < wid; x++) {
				if (detail[x][y] == null) {
					sb.append("E");
				} else {
					sb.append(detail[x][y].getView());
				}
			}
			sb.append(y % 10);
			sb.append(end);
		}
		sb.append(TERRAIN_CORNER);
		for (int x = 0; x < wid; x++) {
			sb.append(x % 10);
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

	public GridSquare[][] getDetail() {
		return detail;
	}

	public void setGridSquare(GridSquare[][] detail) {
		this.detail = detail;
	}

	public void setDetail(GridSquare[][] detail) {
		this.detail = detail;
	}

	public double getVarianceFactor() {
		return varianceFactor;
	}

	public void setVarianceFactor(double varianceFactor) {
		this.varianceFactor = varianceFactor;
	}

	public HashMap<String, Double> getWeight() {
		return weight;
	}

	public void setWeight(HashMap<String, Double> weight) {
		this.weight = weight;
	}

	public String getEnd() {
		return end;
	}

	public double getFriction(double x, double y) {
		return detail[(int) x][(int) y].getFriction();
	}

}
