package com.fairanswers.mapExplore.fsm;

import java.util.ArrayList;
import java.util.HashMap;

// Inspired by Tim Menzies https://github.com/txt/ase16/blob/master/src/fsm.py

public class Model {
	int seed = 1;
	HashMap<String,State>states = new HashMap<String,State>();
	ArrayList<Trans>trans = new ArrayList<Trans>();
	State first = null;
	State here = null;
	
	public Model(ArrayList<Trans> trans){
		this(trans, 1);
	}
	
	public Model(ArrayList<Trans> trans, int seed){
		this.first=trans.get(0).getFrom();
		this.here = first;
		this.seed = seed;
		this.trans = trans;
		for(Trans t:trans){
			t.getFrom().addGuard(t.getGuard());
			
		}
	}

	public void run() {
		System.out.println("Starting: seed = "+seed);
		while(here.isStop() == false){
			here = here.next();
		}
	}

}
