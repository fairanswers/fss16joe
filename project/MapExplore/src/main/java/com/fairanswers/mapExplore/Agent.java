package com.fairanswers.mapExplore;

import com.fairanswers.mapExplore.fsm.Model;

public class Agent extends Model {
	String name; // UID
	Location loc;
	double speed = 1; // distance per tick
	double dir = 0; // Heading
	double see = 1; // Visibility
	Terrain ter; // Known terrain
	Map map;
	double dirWiggle = 1;
	private Double chanceFwd = .99;
	int tick=0;
	private double unExploredWeight=1;

	public Agent() {
	}

	public Agent(String name) {
		this.name = name;
	}

	public Agent(String name, double x, double y, double dirWiggle, double chanceFwd, Map map) {
		this(name, x, y, map);
		this.dirWiggle = dirWiggle;
		this.chanceFwd = chanceFwd;
	}

	public Agent(String name, double x, double y, Map map) {
		this.name = name;
		this.loc = new Location(x, y);
		this.map = map;
		this.ter = new Terrain(map, Terrain.UNKNOWN, Terrain.UNKNOWN_WT);// Gets blank copy the same
														// size.
		ter.setTerrain(x, y, map.getViewAt(x, y));
	}

	public double decideDir() {
		double tmpDir = dir //Where we're currently headed
				//+ dirToPOI() * weightOfPOI() //Dir to POI (if known)
				+ subtractAngles(unexploredDir(), dir ) * getUnExploredWeight() // Away from known places
				;
		//+ getDir() * weightOfMomentum();
				
		// Mostly go forward
		if (getRandom() < chanceFwd) {
			Double wiggle = getRandomDouble(0 - dirWiggle, dirWiggle);
			return tmpDir + wiggle;
		} else {
			return turnRandom(90);
		}
	}

	public double subtractAngles(double first, double second) {
//		if(second - first < 0){
//			second += 360;
//		}
		double a = first - second;
		a = ((a+180) % 360 ) -180;
		if(a > 180)
			a = a - 360;
		if(a < -180)
			a = a + 360;
		return a;
	}

	public int unexploredDir() {
		Terrain t = getTer();
		Location l = t.weight(loc, Terrain.UNKNOWN);
		return dirFromWeight(l);
	}

	public int dirFromWeight(Location l) {
		if(l.equals(0,0)){
			return 0;
		}
		int d=  (int) Math.toDegrees(Math.atan2(l.getY(), l.getX() ) ) ;
		if(d < 0)
			d = d + 360;
		return d;
	}

	private double weightOfMomentum() {
		return 1;
	}

	private int weightOfPOI() {
		return 1;
	}

	private int dirToPOI() {
		return 45;
	}

	public void move(double dir, double speed) {
		setDir(dir);
		double xTravel = getXTravel(dir, speed);
		double yTravel = getYTravel(dir, speed);
		loc.setX(loc.getX() + xTravel);
		loc.setY(loc.getY() + yTravel);
		if (!map.isValid(loc.getX()+xTravel, loc.getY()+yTravel) ) {
			setDir(turnRandom(90));
			return;
		}
		if (map.isCliff(loc.getX()+xTravel, loc.getY()+yTravel) ) {
			setDir(turnRandom(90));
			return;
		}

	}

	private double turnRandom(double i) {
		if (getRandom() < .5)
			return turnRight(i);
		else
			return turnLeft(i);
	}

	public double turnRight(double i) {
		//System.out.println(" * * * RIGHT TURN");
		setDir(dir - i );
		return this.dir;

	}

	public double turnLeft(double i) {
		//System.out.println(" * * * LEFT TURN");
		setDir( dir + i);
		return dir;

	}

	public double getXTravel(double dir2, double spd) {
		double travel = Math.cos(Math.toRadians(dir2)) * spd;
		return travel;
	}

	public double getYTravel(double dir2, double spd) {
		double travel = Math.sin(Math.toRadians(dir2)) * spd;
		return travel;
	}

	@Override
	public void tick(int tick) {
		this.tick = tick;
		move(decideDir(), speed);
		look(loc.getX(), loc.getY(), map);
	}

	public void look(double xCenter, double yCenter, Map map) {
		for (double y = yCenter - see; y <= yCenter + see; y=y+1.0) {
			for (double x = xCenter - see; x <= xCenter + see; x=x+1.0) {
				if (map.isValid(x, y)) {
					ter.setTerrain(x, y, map.getTerrain().get(x, y));
				}
			}
		}
	}
	
	public String toString(boolean brief) {
		if (!brief)
			return toString();
		else
			return "Agent [name=" + name + ", loc=" + loc + ", speed=" + Map.numFormat.format(speed) + ", dir="
					+ Map.numFormat.format(dir) + ", see=" + Map.numFormat.format(see)+ "tick="+tick;
	}

	@Override
	public String toString() {
		return "Agent [name=" + name + ", loc=" + loc + ", dir=" + dir + ", speed=" + speed + ", see=" + see
				+ ", dirWiggle=" + dirWiggle + ", chanceFwd=" + chanceFwd + ", tick=" + tick + ", ter=" +  map.end + ter +  map.end + "]";
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
		this.loc = new Location(x, y);
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
		if (dir < 0) {
			dir = 360 + dir;
		}
		this.dir = dir % 360;
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

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public double getDirWiggle() {
		return dirWiggle;
	}

	public void setDirWiggle(double dirWiggle) {
		this.dirWiggle = dirWiggle;
	}

	public Double getChanceFwd() {
		return chanceFwd;
	}

	public void setChanceFwd(Double chanceFwd) {
		this.chanceFwd = chanceFwd;
	}

	public int getTick() {
		return tick;
	}

	public void setTick(int tick) {
		this.tick = tick;
	}

	public double getUnExploredWeight() {
		return unExploredWeight;
	}

	public void setUnExploredWeight(double unExploredWeight) {
		this.unExploredWeight = unExploredWeight;
	}

}
