package com.fairanswers.mapExplore.optimizers;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import com.fairanswers.mapExplore.Agent;
import com.fairanswers.mapExplore.Map;
import com.fairanswers.mapExplore.Terrain;
import com.fairanswers.mapExplore.fsm.Model;

public class MapExploreProblem extends AbstractDoubleProblem{
	
	

	private Map map;
	private int multiplier;
	private Agent agent;

	public MapExploreProblem() {
		super();
		// Variables x, y, dirWiggle, chanceFwd, ticks, Hevily populated map , seed
	    setNumberOfVariables(2);
	    setNumberOfObjectives(1);
	    setNumberOfConstraints(0);
	    setName("MapExplore");

	    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    for (int i = 0; i < getNumberOfVariables(); i++) {
	      lowerLimit.add(0.0);		// initial x
	      upperLimit.add(100.0);	// initial y
	    }

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);
	}

	@Override
	public void evaluate(DoubleSolution solution) {
		multiplier = 100;
		map = new Map(multiplier, multiplier);
		map.setTerrain(new Terrain(map, 1.0, 1L) );
		agent = new Agent("A", solution.getVariableValue(0), solution.getVariableValue(1), 45, .9, map);
		agent.setDir(0);
		agent.setUnExploredWeight(1);
		map.getAgents().add(agent);
		for (int i = 0; i < multiplier*multiplier; i++) {
			map.tick();
		}
		solution.setObjective(0, 0-agent.getTer().getCoverage() );
		//System.out.println(agent);
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

}
