package com.fairanswers.mapExplore;

import java.beans.Transient;
import java.util.ArrayList;

import com.fairanswers.mapExplore.fsm.Always;
import com.fairanswers.mapExplore.fsm.Guard;
import com.fairanswers.mapExplore.fsm.Maybe;
import com.fairanswers.mapExplore.fsm.Model;
import com.fairanswers.mapExplore.fsm.State;
import com.fairanswers.mapExplore.fsm.Trans;

public class Agent {
	private static final String EXCITED = "EXCITED";
	private static final String BORED = "BORED";
	private static final String DONE = "DONE";
	private static final String LAZY = "LAZY";
//	private static final Location ZERO_ZERO = new Location(0, 0);
	String name; // UID
	Location loc;
	double speed = 1; // distance per tick
	double dir = 0; // Heading
	double see = 1; // Visibility
	Terrain ter; // Known terrain
	transient Map map; // Maps save agents. infinite recursion on save.
	double dirWiggle = 1;
	private Double chanceFwd = .99;
	int tick = 0;
	private double unExploredWeight = 1;
	private int boredCounter;
	private int boredLimit = 100;
	private Model model; // Can't json because of abstract guard and recursion
	private State excitedState;
	private State boredState;
	private State doneState;
	private State lazyState;
	private double boredDirection;
	private double energy;
	private boolean complete=false;
	private double laziness=.1;
	private Location boredCorner;

	public Agent() {
	}

	public Agent(String name) {
		this.name = name;
		createModel();
	}

	public Agent(String name, double x, double y, Map map) {
		this.name = name;
		this.loc = new Location(x, y);
		this.map = map;
		this.ter = new Terrain(map, Terrain.UNKNOWN);// Gets blank copy same size
		if(!ter.isValid(x, y)){
			throw new RuntimeException("Can not set x,y to "+x+","+y);
		}
		ter.setTerrain(x, y, map.getViewAt(x, y));
		createModel();
	}

	public Agent(String name, double x, double y, double dirWiggle, double chanceFwd, double laziness, Map map) {
		this(name, x, y, map);
		this.dirWiggle = dirWiggle;
		this.chanceFwd = chanceFwd;
		this.laziness = laziness;
	}

	public Agent(String name, double x2, double y2, double speed, double dir, double see, double dirWiggle,
			Double chanceFwd, double unExploredWeight, Map map) {
		this.name = name;
		this.loc = new Location(x2, y2);
		this.speed = speed;
		this.dir = dir;
		this.see = see;
		this.dirWiggle = dirWiggle;
		this.chanceFwd = chanceFwd;
		this.unExploredWeight = unExploredWeight;
		this.map = map;
		this.ter = new Terrain(map, Terrain.UNKNOWN);// Gets blank copy the same
		createModel();
	}


//	public double decideDir() {
//Works but not lazy
		//		double tmpDir = this.dir;// Where we're currently headed
//		if(model.getHere()==excitedState){
//			double exploreDir = unexploredDir();
//			exploreDir = subtractAngles(exploreDir, this.dir) 
//					* getUnExploredWeight(); // Away from known places
//			tmpDir = getAbsoluteDegrees(tmpDir + exploreDir);
//		}
//
//		if(model.getHere()==boredState){
//			//Keep moving in the direction we were going when we got bored.
//			double boredDir = subtractAngles(boredDirection, this.dir) 
//					* getUnExploredWeight(); 
//			tmpDir = getAbsoluteDegrees(tmpDir + boredDir);
//		}
//		// Mostly go forward
//		if (Model.getRandom() < chanceFwd) {
//			Double wiggle = Model.getRandomDouble(0 - dirWiggle, dirWiggle);
//			return tmpDir + wiggle;
//		} else {
//			return turnRandom(90);
//		}
//	}

	// Zero degrees is East
	public double decideDir() {
		double tmpDir = this.dir;// Where we're currently headed in absolute, positive degrees
		double exploreDir = unexploredDir();// Absolute deg
		//tmpDir = subtractAngles(exploreDir, this.dir) * getUnExploredWeight();  //This isn't right.
		//tmpDir = exploreDir;
//		tmpDir = getAbsoluteDegrees(tmpDir + exploreDir);
		if (model.getHere() == excitedState) {
			//return tmpDir;
		} else {  // Try something new.
			model.tick(tick);
			//If we're at the corner, get excited.
			if(closeToBoredCorner()){
				model.setHere(excitedState);
			}
			if (model.getHere() == boredState) {
				// Go the other way
				tmpDir = decideWhileBored(tmpDir);
//				double boredDir = subtractAngles(boredDirection, this.dir) ;
//				tmpDir = getAbsoluteDegrees(tmpDir + boredDir);
			}

			if (model.getHere() == lazyState) {
				tmpDir = decideWhileBored(tmpDir);
				double boredDir = findLazyDir(boredDirection);
				tmpDir = getAbsoluteDegrees(tmpDir + boredDir);
			}

			// Mostly go forward
			if (Model.getRandom() < chanceFwd) {
				Double wiggle = Model.getRandomDouble(0 - dirWiggle, dirWiggle);
				tmpDir += wiggle;
			} else {
				tmpDir= turnRandom(90);
			}
		}
		return tmpDir;
	}

public boolean closeToBoredCorner() {
	double closeX = map.getLen()*.05;
	double closeY = map.getWid()*.05;
	if(boredCorner == null || loc == null){
		System.out.println("Something Null"+this.toString());

		System.out.println("Something Null"+this.toString());
	}
	double xdiff = Math.abs(boredCorner.getX() - loc.getX());
	double ydiff = Math.abs(boredCorner.getY() - loc.getY());
	if(closeX > xdiff && closeY > ydiff){
		return true;
	}else{
		return false;
	}
}

public double decideWhileBored(double tmpDir) {
	double rise = boredCorner.getY()-loc.getY();
	double run  = boredCorner.getX()-loc.getX();
	double tmpBoredDir = getDegreesFromSlope(rise, run);
	//tmpBoredDir = subtractAngles(tmpBoredDir, this.dir);
	//tmpBoredDir = getAbsoluteDegrees(tmpBoredDir);
	return tmpBoredDir;
}

	private double findLazyDir(double boredDir) {
		double left = findFriction(getAbsoluteDegrees(-45 + boredDir), speed);
		double right = findFriction(getAbsoluteDegrees(45 + boredDir), speed);
		double center = findFriction(boredDir, speed);
		if(left < right && left < center){
			return left;
		}else if(right<left && right < center){
			return right;
		}else{
			return center;
		}
	}

	private double findFriction(double boredDir, double speed2) {
		double xTravel = getXTravel(dir, speed);
		double yTravel = getYTravel(dir, speed);
		if (!map.isValid(loc.getX() + xTravel, loc.getY() + yTravel)) {
			return 100;
		}
		return map.getTerrain().getFriction(loc.getX()+xTravel, loc.getY()+yTravel);
	}

	// Returns positive or negative of difference.
	public double subtractAngles(double first, double second) {
		double a = first - second;
		a = getAbsoluteDegrees(a);
		return a;
	}

	private double getAbsoluteDegrees(double a) {
		a = ((a + 180) % 360) - 180;
		if (a > 180)
			a = a - 360;
		if (a < -180)
			a = a + 360;
		return a;
	}
	
	// Return absolute degrees
	public double getDegreesFromSlope(double rise, double run){
		if(rise == 0.0){
			rise = .0001;
		}
		if(run == 0.0){
			run = .0001;
		}
		double degrees = Math.toDegrees( Math.atan2(rise, run) );
		degrees = getAbsoluteDegrees(degrees);
//		degrees = -1*degrees + 360;
		degrees = getAbsoluteDegrees(degrees);
		if(degrees < 0.0){
			degrees = 360.0 + degrees;
		}
		return degrees;
	}


	// Behavior needs to change based on state
	public int unexploredDir() {
		Terrain t = getTer();
		Location l = t.weightNeighborhood(loc, Terrain.UNKNOWN, see);
		if (l.equals(0.0,0.0)) {
			int range = t.getWid();
			// Find if x or y is bigger
			if (t.getLen() > t.getWid()) {
				range = t.getLen();
			}
			// If no local spots, search wider: 
			double increment = .1;
			double pctMap = increment;
			do {
				l = t.weightNeighborhood(loc, Terrain.UNKNOWN, range * pctMap);
				pctMap += increment;
			} while (pctMap < 100.0 && l.equals(0.0, 0.0));
		}
		if(l.equals(0.0, 0.0) ){
			System.out.println("Map Is Complete!");
			alertMapIsComplete();
		}
		return dirFromWeight(l);
	}

	private void alertMapIsComplete() {
		this.complete = true;
	}

	public int dirFromWeight(Location l) {
		if (l.equals(0, 0)) {
			return 0;
		}
		//return (int) getDegreesFromSlope(l.getY(), l.getX() );
		int d = (int) Math.toDegrees(Math.atan2(l.getY(), l.getX()));
		if (d < 0)
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

	public void createModel() {
		excitedState = new State(EXCITED, false);
		boredState = new State(BORED, false);
		lazyState = new State(LAZY, false);
		doneState = new State(DONE, false);
		ArrayList<Trans> t = new ArrayList<Trans>();
		t.add(new Trans(excitedState, new Always(EXCITED, excitedState)));
		//t.add(new Trans(boredState, new Always(EXCITED, excitedState)));
		t.add(new Trans(boredState, new Maybe(LAZY, lazyState, laziness)));
		t.add(new Trans(lazyState, new Maybe(BORED, excitedState, laziness)));
		model = new Model(t);
	}

	public void move(double dir, double speed) {
		double xTravel = getXTravel(dir, speed);
		double yTravel = getYTravel(dir, speed);
		if (!map.isValid(loc.getX() + xTravel, loc.getY() + yTravel)) {
			setDir(turnRandom(90));
			return;
		}
		if (map.isCliff(loc.getX() + xTravel, loc.getY() + yTravel)) {
			setDir(turnRandom(90));
			move(getDir(), speed);
			return;
		}
		setDir(dir);
		loc.setX(loc.getX() + xTravel);
		loc.setY(loc.getY() + yTravel);
	}

	private double turnRandom(double i) {
		if (Model.getRandom() < .5)
			return turnRight(i);
		else
			return turnLeft(i);
	}

	public double turnRight(double i) {
		// System.out.println(" * * * RIGHT TURN");
		setDir(dir - i);
		return this.dir;

	}

	public double turnLeft(double i) {
		// System.out.println(" * * * LEFT TURN");
		setDir(dir + i);
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

	public void tick(int tick) {
		this.tick = tick;
		if(!complete){
			move(decideDir(), speed);
			int found = look(loc.getX(), loc.getY(), map);
			energy += ter.getFriction(loc.getX(), loc.getY());
			checkState(found);
		}

	}

	private void checkState(int found) {
		if (found > 0) {
			boredCounter = 0;
			boredCorner = null;
			model.setHere(excitedState);
		} else {
			boredCounter++;
			if (boredCounter == boredLimit) {
				boredCorner = pickCorner();
				model.setHere(boredState);
			}
		}

	}

	public Location pickCorner() {
		double x, y;
		//Pick X
		if(.5 < Model.getRandom() ){
			x = 0;
		}else{
			x=map.getWid()-1;
		}
		//Pick Y
		if(.5 < Model.getRandom() ){
			y = 0;
		}else{
			y=map.getLen()-1;
		}
		return new Location(x,y);
	}

	public int look(double xCenter, double yCenter, Map map) {
		int found = 0;
		for (double y = yCenter - see; y <= yCenter + see; y = y + 1.0) {
			for (double x = xCenter - see; x <= xCenter + see; x = x + 1.0) {
				if (map.isValid(x, y)) {
					if (ter.get(x, y).equals(ter.UNKNOWN)) {
						found++;
					}
					ter.setTerrain(x, y, map.getTerrain().get(x, y));
				}
			}
		}
		return found;
	}

	public String toString(boolean brief) {
		if (!brief)
			return toString();
		else
			return "Agent [name=" + name + ", loc=" + loc + ", speed=" + Map.numFormat.format(speed) + ", dir="
					+ Map.numFormat.format(dir) + ", see=" + Map.numFormat.format(see) + "tick=" + tick;
	}

	@Override
	public String toString() {
		return "Agent [name=" + name + ", loc=" + loc + ", dir=" + Map.numFormat.format(dir) + ", speed=" + speed + ", see=" + see
				+ ", dirWiggle=" + Map.numFormat.format(dirWiggle) + ", chanceFwd=" + Map.numFormat.format(chanceFwd) + ", tick=" + tick + ", ter=" + map.end + ter
				+ map.end + "]";
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

	public int getBoredCounter() {
		return boredCounter;
	}

	public void setBoredCounter(int boredCounter) {
		this.boredCounter = boredCounter;
	}

	public int getBoredLimit() {
		return boredLimit;
	}

	public void setBoredLimit(int boredLimit) {
		this.boredLimit = boredLimit;
	}

	public double getBoredDirection() {
		return boredDirection;
	}

	public void setBoredDirection(double boredDirection) {
		this.boredDirection = boredDirection;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public double getLaziness() {
		return laziness;
	}

	public void setLaziness(double laziness) {
		this.laziness = laziness;
	}

	public Location getBoredCorner() {
		return boredCorner;
	}

	public void setBoredCorner(Location boredCorner) {
		this.boredCorner = boredCorner;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public State getExcitedState() {
		return excitedState;
	}

	public void setExcitedState(State excitedState) {
		this.excitedState = excitedState;
	}

	public State getBoredState() {
		return boredState;
	}

	public void setBoredState(State boredState) {
		this.boredState = boredState;
	}

	public State getDoneState() {
		return doneState;
	}

	public void setDoneState(State doneState) {
		this.doneState = doneState;
	}

	public State getLazyState() {
		return lazyState;
	}

	public void setLazyState(State lazyState) {
		this.lazyState = lazyState;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

}
