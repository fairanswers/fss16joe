package com.fairanswers.mapExplore.optimizers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;

import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3Builder;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.differentialevolution.DifferentialEvolution;
import org.uma.jmetal.algorithm.singleobjective.differentialevolution.DifferentialEvolutionBuilder;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.MultithreadedSolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import com.fairanswers.mapExplore.fsm.Model;

public class DEProblem implements Runnable{

	private int runs=1;
	private int population;
	private int evals;
	private String startTime;
	Runner runner;

	@Override
	public void run() {
		DoubleProblem problem;
	    //Algorithm<DoubleSolution> algorithm;
	    DifferentialEvolutionSelection selection;
	    DifferentialEvolutionCrossover crossover;
	    SolutionListEvaluator<DoubleSolution> evaluator ;

	    double cr = .3;
	    double f = .9;
	    String variant = "rand/1/bin";
	    int seed = Model.getRandomIntRange(0, 10000);
	    		
	    String name="DE.cr="+cr+"_f="+f+"_variant="+variant+"_pop="+population+"_evals="+evals+"_seed"+seed;
	    problem = MapExploreProblem.create(seed, name);
	    	    int numberOfCores =8;

	    if (numberOfCores == 1) {
	      evaluator = new SequentialSolutionListEvaluator<DoubleSolution>() ;
	    } else {
	      evaluator = new MultithreadedSolutionListEvaluator<DoubleSolution>(numberOfCores, problem) ;
	    }

	    crossover = new DifferentialEvolutionCrossover(cr,f,variant) ;
	    selection = new DifferentialEvolutionSelection();

//	    DifferentialEvolution algorithm = new DifferentialEvolutionBuilder(problem)
//	        .setCrossover(crossover)
//	        .setSelection(selection)
//	        .setSolutionListEvaluator(evaluator)
//	        .setMaxEvaluations(evals )
//	        .setPopulationSize(population)
//	        .build() ;

	    GDE3 algorithm = new GDE3Builder(problem)
	      .setCrossover(crossover)
	      .setSelection(selection)
	      .setMaxEvaluations(evals)
	      .setPopulationSize(population)
	      .build() ;


	    ArrayList<String> results = new ArrayList<>();
		results.add(name);
	    for(int i=0; i< runs; i++){
		    List<DoubleSolution> initalPopulation = new ArrayList<>();
			for(int j=0; j<population; j++){
				DoubleSolution sol = problem.createSolution();
				problem.evaluate(sol);
				initalPopulation.add(sol);
			}
			
		    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
			        .execute() ;
			List<DoubleSolution> fpop = algorithm.getPopulation() ;
			for(int j=0; j<initalPopulation.size(); j++){
				results.add(" ["+Double.toString(initalPopulation.get(j).getObjective(0) )+", " + Double.toString(initalPopulation.get(j).getObjective(1) )+"], " );
			}
			results.add("\n");
			for(int j=0; j<fpop.size(); j++){
				results.add(" ["+Double.toString(fpop.get(j).getObjective(0) )+", " + Double.toString(fpop.get(j).getObjective(1) )+"], " );
			}
		    	
		    String display = MapExploreProblem.generateParetofront(initalPopulation, fpop);
			results.add("\nPARETO:\n"+display);

	    }
	    evaluator.shutdown();
	    try {
			runner.report(name, startTime, results);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getRuns() {
		return runs;
	}

	public void setRuns(int runs) {
		this.runs = runs;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public int getEvals() {
		return evals;
	}

	public void setEvals(int evals) {
		this.evals = evals;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public Runner getRunner() {
		return runner;
	}

	public void setRunner(Runner runner) {
		this.runner = runner;
	}
	

}
