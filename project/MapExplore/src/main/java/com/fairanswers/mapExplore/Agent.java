package com.fairanswers.mapExplore;

import com.fairanswers.mapExplore.fsm.Model;

public class Agent extends Model {
	String name; //UID
	Location loc;
	double speed=1; // distance per tick
	double dir = 0; //Heading
	double see = 1; //Visibility
	Terrain ter; //Known terrain
	Map map;
	
	public Agent(){
	}
	
	public Agent(String name){
		this.name = name;
	}
	
	public Agent(String string, double x, double y, Map map) {
		this.name = name;
		this.loc = new Location(x, y);
		this.map = map;
		this.ter = new Terrain(map, Terrain.UNKNOWN);// Gets blank copy the same size.
		ter.setTerrain(x, y, map.getViewAt(x, y) );
	}
	
	public void move(double dir, double speed) {
		double xDir = getXTravel(dir);
		double yDir = getYTravel(dir);
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
	
	private double turnRandom(double i) {
		if(getRandom() < .5)
			return turnRight(i);
		else
			return turnLeft(i);
	}

	public double turnRight(double i) {
		return (dir+i)%360;
		
	}

	public double turnLeft(double i) {
		return Math.abs(dir-i) %360;
		
	}

	private double getXTravel(double dir2) {
		switch( (int)dir2){
		case  90: return  1*speed;
		case 270: return -1*speed;
		default : return  0;
		}
			
	}

	private double getYTravel(double dir2) {
		switch( (int)dir2){
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

	public void look(double xCenter, double yCenter, Map map) {
		for(double y=yCenter-see; y<=yCenter+see; y++){
			for(double x=xCenter-see; x<=xCenter+see; x++){
				if(map.isValid(x,y) ){
					ter.setTerrain(x, y, map.getTerrain().get(x, y) );
				}
			}
		}
	}

	private double decideDir() {
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
	public void setLoc(double x, double y) {
		this.loc = new Location(x,y);
	}
	public String getAscii() {
		return Terrain.AGENT;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getDir() {
		return dir;
	}

	public void setDir(double dir) {
		this.dir = dir;
	}

	public double getSee() {
		return see;
	}

	public void setSee(double see) {
		this.see = see;
	}

	public Terrain getTer() {
		return ter;
	}

	public void setTer(Terrain ter) {
		this.ter = ter;
	}
	
}
