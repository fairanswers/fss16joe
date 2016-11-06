package com.fairanswers.mapExplore.fsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

// Inspired by Tim Menzies https://github.com/txt/ase16/blob/master/src/fsm.py

public class Model {
	private static Random randomNumberGenerator = new Random();
	static Long randomSeed = 1L;
	HashMap<String,State>states = new HashMap<String,State>();
	ArrayList<Trans>trans = new ArrayList<Trans>();
	State first = null;
	State here = null;
	
	
	public Model(){
	}

	public Model(ArrayList<Trans> trans){
		this(trans, randomSeed);
	}
	
	public Model(ArrayList<Trans> trans, Long seed){
		setRandomSeed(seed);
		createModel(trans);
	}

	private void createModel(ArrayList<Trans> trans) {
		this.first=trans.get(0).getFrom();
		this.here = first;
		this.trans = trans;
		for(Trans t:trans){
			t.getFrom().addGuard(t.getGuard());
			
		}
	}

	public void run() {
		System.out.println("Starting: seed = "+randomSeed);
		while(here.isStop() == false){
			here = here.next();
		}
	}
	
	//abstract?
	public void tick(int tick){
		here = here.next();
	}
	
	public static int getRandomInt(int top){
		return randomNumberGenerator.nextInt(top);
	}
	public static int getRandomIntRange(int bottom, int top){
		return randomNumberGenerator.nextInt(top-bottom)+bottom;
	}

	public static Double getRandom(){
		return randomNumberGenerator.nextDouble();
	}
	
	public static Double getRandomDouble(Double bottom, Double top){
		return randomNumberGenerator.nextDouble()*(top-bottom)+bottom;
	}

	public static void setRandomSeed(long seed) {
		randomSeed= seed;
		System.out.println("Setting seed "+seed);
		randomNumberGenerator.setSeed(seed);
		
	}

	public static Random getRandomGenerator() {
		return randomNumberGenerator;
	}
}
