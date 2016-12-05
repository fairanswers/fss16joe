package com.fairanswers.mapExplore.optimizers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.TournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.qualityindicator.impl.Hypervolume;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.qualityindicator.impl.hypervolume.util.WfgHypervolumeFront;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import com.fairanswers.mapExplore.fsm.Model;

public class NSGIIProblem implements Runnable{

	private String startTime;
	private int population;
	private int evals;
	private Runner runner;
	private int runs=1;

	@Override
	public void run() {
		Problem<DoubleSolution> problem;
		NSGAII<DoubleSolution> algorithm;
		CrossoverOperator<DoubleSolution> crossover;
		MutationOperator<DoubleSolution> mutation;
		SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;
		String referenceParetoFront = "";

		String problemName = "com.fairanswers.mapExplore.optimizers.MapExploreProblem";
		problem = ProblemUtils.<DoubleSolution>loadProblem(problemName );

		int seed = Model.getRandomIntRange(0, 1000000);
		double crossoverProbability = 0.9;
		double crossoverDistributionIndex = 20.0;
		crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);

		double mutationProbability = 1.0 / problem.getNumberOfVariables();
		double mutationDistributionIndex = 20.0;
		mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);
		
		selection = new TournamentSelection<DoubleSolution>(
				new RankingAndCrowdingDistanceComparator<DoubleSolution>(), 5);

		algorithm = new NSGAIIBuilder<DoubleSolution>(problem, crossover, mutation).setSelectionOperator(selection)
				.setMaxEvaluations(evals).setPopulationSize(population).build();

		List<DoubleSolution> initalPopulation = new ArrayList<>();
		for(int i=0; i<population; i++){
			DoubleSolution sol = problem.createSolution();
			problem.evaluate(sol);
			initalPopulation.add(sol);
		}
		algorithm.setPopulation(initalPopulation);
		ArrayList<String> results = new ArrayList<>();
		String runName = "NSGII.crossoverProbability=" +crossoverProbability
				+"_crossoverDistributionIndex="+crossoverDistributionIndex
				+"_mutationProbability="+mutationProbability
				+"_mutationDistributionIndex="+mutationDistributionIndex
				+"_pop="+population+"_evals="+evals+"_seed"+seed;
		results.add(runName );
		for(int i=0; i< runs; i++){
			System.out.println("Starting run "+i);
			AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
			List<DoubleSolution> population = algorithm.getResult();
			for(int j=0; j<initalPopulation.size(); j++){
				results.add(" ["+Double.toString(initalPopulation.get(j).getObjective(0) )+", " + Double.toString(initalPopulation.get(j).getObjective(1) )+"], " );
			}
			results.add("\n" );
			for(int j=0; j<population.size(); j++){
				results.add(" ["+Double.toString(population.get(j).getObjective(0) )+", " + Double.toString(population.get(j).getObjective(1) )+"], " );
			}
			String display = MapExploreProblem.generateParetofront(initalPopulation, population);
			results.add("\nPARETO:\n"+display);
		}

		//List<DoubleSolution> population = algorithm.getResult();
	    try {
			runner.report("NSGII", startTime, results);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JMetalLogger.logger.info("Random seed: " + JMetalRandom.getInstance().getSeed());

	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
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

	public Runner getRunner() {
		return runner;
	}

	public void setRunner(Runner runner) {
		this.runner = runner;
	}

	public void setRuns(int runs) {
		this.runs=runs;
	}
}
