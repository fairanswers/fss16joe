package com.fairanswers.mapExplore;

import com.fairanswers.mapExplore.fsm.Model;

public class Agent extends Model {
	String name; //UID
	Location loc;
	int speed=1; // distance per tick
	int dir = 0; //Heading
	int see = 1; //Visibility
	Terrain ter; //Known terrain
	Map map;
	
	public Agent(){
	}
	
	public Agent(String name){
		this.name = name;
	}
	
	public Agent(String string, int x, int y, Map map) {
		this.name = name;
		this.loc = new Location(x, y);
		this.map = map;
		this.ter = new Terrain(map, Terrain.UNKNOWN);// Gets blank copy the same size.
		ter.setTerrain(x, y, map.getViewAt(x, y) );
	}
	
	public void move(int dir, int speed) {
		int xDir = getXTravel(dir);
		int yDir = getYTravel(dir);
		loc.setX(loc.getX()+xDir);
		if(!map.isValid(loc.getX(), loc.getY())){
			loc.setX(loc.getX() - xDir);
			setDir( turnRandom(90) );  
		}
		loc.setY(loc.getY()+yDir);
		if(!map.isValid(loc.getX(), loc.getY())){
			loc.setY(loc.getY() - yDir);
			setDir( turnRandom(90) );  
		}

	}
	
	private int turnRandom(int i) {
		if(getRandom() < .5)
			return turnRight(i);
		else
			return turnLeft(i);
	}

	public int turnRight(int i) {
		return (dir+i)%360;
		
	}

	public int turnLeft(int i) {
		return Math.abs(dir-i) %360;
		
	}

	private int getXTravel(int dir2) {
		switch( dir2){
		case  90: return  1*speed;
		case 270: return -1*speed;
		default : return  0;
		}
			
	}

	private int getYTravel(int dir2) {
		switch( dir2){
		case   0: return  1*speed;
		case 180: return -1*speed;
		default : return  0;
		}

	}

	@Override
	public void tick(int tick){
		
		move(decideDir(), speed);
		look(loc.getX(), loc.getY(), map);
	}

	public void look(int xCenter, int yCenter, Map map) {
		for(int y=yCenter-see; y<=yCenter+see; y++){
			for(int x=xCenter-see; x<=xCenter+see; x++){
				if(map.isValid(x,y) ){
					ter.setTerrain(x, y, map.getTerrain().get(x, y) );
				}
			}
		}
	}

	private int decideDir() {
		//Mostly go forward
		if(getRandom() < .7){
			return dir;
		}else{
			return turnRandom(90);
		}
	}

	@Override
	public String toString() {
		return "Agent [name=" + name + ", loc=" + loc + ", speed=" + speed + ", dir=" + dir + ", see=" + see + ", ter=\n"
				+ ter + "\n]";
	}

	////////////
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public Location getLoc() {
		return loc;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	public void setLoc(int x, int y) {
		this.loc = new Location(x,y);
	}
	public String getAscii() {
		return Terrain.AGENT;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public int getSee() {
		return see;
	}

	public void setSee(int see) {
		this.see = see;
	}

	public Terrain getTer() {
		return ter;
	}

	public void setTer(Terrain ter) {
		this.ter = ter;
	}
	
}
