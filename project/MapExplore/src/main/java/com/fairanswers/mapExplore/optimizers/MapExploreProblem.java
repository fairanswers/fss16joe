package com.fairanswers.mapExplore.optimizers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import com.fairanswers.mapExplore.Agent;
import com.fairanswers.mapExplore.Map;
import com.fairanswers.mapExplore.Terrain;
import com.fairanswers.mapExplore.fsm.Model;

public class MapExploreProblem extends AbstractDoubleProblem{

	public static String name="nsgii";
	public MapExploreProblem() {
		this(name);
	}
	public MapExploreProblem(String name) {
		super();
		this.name=name;
		// Variables x, y, dirWiggle, chanceFwd, ticks, Hevily populated map , seed
	    setNumberOfVariables(5);
	    setNumberOfObjectives(2);
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
	    lowerLimit.add(0.0);		// laziness
	    upperLimit.add(.9);		// laziness
	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);
	}

	@Override
	public void evaluate(DoubleSolution solution) {
		int multiplier = 100;
		//private Agent agent;
		Map map = new Map(multiplier, multiplier);
		map.setTerrain(new Terrain(map, 1.0) );
		Agent agent = new Agent("A", solution.getVariableValue(0), solution.getVariableValue(1), solution.getVariableValue(2), solution.getVariableValue(3), solution.getVariableValue(4), map);
		agent.setDir(0);
		agent.setUnExploredWeight(1);
		map.getAgents().add(agent);
		for (int i = 0; i < multiplier*multiplier; i++) {
			//System.out.println(i);
			if(!agent.isComplete()){
				try{
				map.tick();
				}catch(Exception ex){
					//Death.  Don't tell me about death.
					agent.setComplete(true);
					map.setComplete(true);
				}
			}else{
				break;
			}
			//Check for completes
			if(agent.getTer().getCoverage() < -99.9 ){ //Check for finished
				finish(solution, agent);
				return;
			}
		}
		finish(solution, agent);
	}

	private void finish(DoubleSolution solution, Agent agent) {
		double cov = 100-agent.getTer().getCoverage() ;
		solution.setObjective(0, cov);
		double energy =agent.getEnergy()/1000;
		solution.setObjective(1, energy);
		System.out.println(name+"..Finished with unknown percent = "+Map.numFormat.format(cov)
				+" Energy = "+Map.numFormat.format(energy)
				+" DirWiggle = "+Map.numFormat.format(agent.getDirWiggle())
				+" ChanceFwd = "+Map.numFormat.format(agent.getChanceFwd())
				+" Laziness = "+Map.numFormat.format(agent.getLaziness() )
				+" terminateEarly = "+agent.isComplete()
				+" at "+new Date() );
	}

	public static MapExploreProblem create(int seed, String name) {
		Model.setRandomSeed(seed);
		return new MapExploreProblem(name);
	}

	public static String generateParetofront(List<DoubleSolution> init, List<DoubleSolution> pop) {
		Map map = new Map(100, 100);
		Terrain t = new Terrain(map, Terrain.UNKNOWN);
		for(int i=0; i< init.size(); i++){
			double unknown = init.get(i).getObjective(0);
			double energy = init.get(i).getObjective(1);
			if(unknown > 99){
				unknown = 99;
			}
			if( energy > 99){
				energy = 99;
			}
			t.setTerrain(unknown, energy, Terrain.INITIAL);
		}
		for(int i=0; i< pop.size(); i++){
			double unknown = pop.get(i).getObjective(0);
			double energy = pop.get(i).getObjective(1);
			if(unknown > 99){
				unknown = 99;
			}
			if( energy > 99){
				energy = 99;
			}
			if( energy < 0 ){
				energy = 0;
			}
			t.setTerrain(unknown, energy, Terrain.PARETO);
		}
		return t.toString();
	}
}
