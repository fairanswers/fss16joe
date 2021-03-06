package com.fairanswers.mapExplore;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.io.FileUtils;

import com.fairanswers.mapExplore.fsm.Model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/* Wrapper for ecosystem including:
 *  - Terrain for map
 *  - Agents
 *  - Functionality for running agents around the map
 *  
 *  Origin is (0,0) to (len, wid) in lower left corner.  North (zero degrees) is up. 
 *
 */
public class Map {
	private static final Object BORDER = "M";
	public static final double CLOSE_ENOUGH = .5;
	int wid;  // x dimension
	int len;  // y dimension
	Terrain terrain;
	ArrayList<Agent> agents = new ArrayList<Agent>();
	private int tick=0;
	public final String end = System.getProperty("line.separator");
	public static final DecimalFormat numFormat = new DecimalFormat("#.00");	
	public boolean complete=false;
	
	public Map(int wid, int len){
		this.wid = wid;
		this.len = len;
		terrain = new Terrain(this);
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("["+tick+"]"+end);
		for(Agent a:agents){
			sb.append(a.toString(true)+end);
		}
		sb.append(BORDER);
		for(int x=0; x<wid; x++){
			sb.append(x%10);
		}
		sb.append(BORDER);
		sb.append(end);
		for(int y=len-1; y>=0; y--){
			sb.append(y%10);
			for(int x=0; x<wid; x++){
				sb.append(getViewAt(x, y) );
			}
			sb.append(y%10);
			sb.append(end);
		}
		sb.append(BORDER);
		for(int x=0; x<wid; x++){
			sb.append(x%10);
		}
		sb.append(BORDER);
		sb.append(end);
		return sb.toString();
	}

	public String getViewAt(int x, int y) {
		//Check if an agent is at this location
		for(Agent a:agents){
			if(a.getLoc().equals(x,y)){
				return a.getAscii();
			}
			
		}
		return terrain.get(x,y);
	}
	
	public String getViewAt(double x, double y) {
		return getViewAt((int)x, (int)y);
	}
	
	//Represents one time tick
	public int tick(){
		tick++;
		//Shuffle the agents and let them move
		Collections.shuffle(agents, Model.getRandomGenerator() );
		boolean done = true;
		for(Agent a:agents){
			if(!a.isComplete()){
				done =false;
				a.tick(tick);
			}
		}
		if(done){
			System.out.println("Earliy termination at tick "+tick);
			complete = true;
		}
		return this.tick;
	}
	

	public double getEnergy(){
		double e=0;
		for(Agent a: agents){
			e += a.getEnergy();
		}
		return e;
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

	public boolean isValid(double x, double y) {
		if(x < 0.0 || y < 0.0){
			return false;
		}
		return isValid((int)x, (int)y);
	}
	public boolean isValid(int x, int y) {
		if(x>=0 && x<wid && y>=0 && y<len){
			return true;
		}else{
			return false;
		}
	}

	public boolean isCliff(double x, double y) {
		String t = getViewAt(x, y);
		if(t.equals(Terrain.CLIFF)){
			//System.out.println("Turning from a cliff.");
			return true;
		}
		return false;
	}

	public String getSaveString() {
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}

	public static Map load(String filename) throws IOException {
		String s = FileUtils.readFileToString(new File(filename), "UTF-8");
		Gson gson = new Gson();
		Map map = gson.fromJson(s, Map.class );
		for(Agent a:map.getAgents() ){
			a.setMap(map);
		}
		return map;
	}

	public void save(String filename) throws IOException {
		Gson gson =new GsonBuilder().setPrettyPrinting().create();
		String str = gson.toJson(this);
		FileUtils.writeStringToFile(FileUtils.getFile(filename), str, "UTF-8");
		
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
}
