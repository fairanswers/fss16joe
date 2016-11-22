package com.fairanswers.mapExplore.optimizers;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import com.fairanswers.mapExplore.Agent;
import com.fairanswers.mapExplore.Map;
import com.fairanswers.mapExplore.Terrain;
import com.fairanswers.mapExplore.fsm.Model;

public class MapExploreProblem extends AbstractDoubleProblem{

	public MapExploreProblem() {
		super();
		// Variables x, y, dirWiggle, chanceFwd, ticks, Hevily populated map , seed
	    setNumberOfVariables(4);
	    setNumberOfObjectives(1);
	    //setNumberOfConstraints(0);
	    setName("MapExplore");

	    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    lowerLimit.add(0.0);		// initial x
	    upperLimit.add(99.0);		// initial x
	    lowerLimit.add(0.0);		// initial y
	    upperLimit.add(99.0);		// initial y
	    lowerLimit.add(0.0);		// dirWiggle
	    upperLimit.add(180.0);		// dirWiggle
	    lowerLimit.add(0.0);		// chanceFwd
	    upperLimit.add(1.0);		// chanceFwd
	    //TerrainSeed

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);
	}

	@Override
	public void evaluate(DoubleSolution solution) {
		int multiplier = 100;
		//private Agent agent;
		Map map = new Map(multiplier, multiplier);
		map.setTerrain(new Terrain(map, 1.0) );
		Agent agent = new Agent("A", solution.getVariableValue(0), solution.getVariableValue(1), solution.getVariableValue(2), solution.getVariableValue(3), map);
		agent.setDir(0);
		agent.setUnExploredWeight(1);
		map.getAgents().add(agent);
		for (int i = 0; i < multiplier*multiplier; i++) {
			//System.out.println(i);
			map.tick();
			//Check for completes
			if(agent.getTer().getCoverage() > 99.999){
				solution.setObjective(0, 0-agent.getTer().getCoverage() );
				System.out.println("Finished with percent = "+(0-agent.getTer().getCoverage() ) );
				return;
			}
		}
		solution.setObjective(0, 0-agent.getTer().getCoverage() );
		System.out.println("Finished with percent = "+(0-agent.getTer().getCoverage() ) );
		//System.out.println(agent);
	}

	public static MapExploreProblem create(int seed) {
		Model.setRandomSeed(seed);
		return new MapExploreProblem();
	}
}
