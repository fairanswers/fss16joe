package com.fairanswers.mapExplore.optimizers;

import java.io.FileNotFoundException;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.runner.AbstractAlgorithmRunner;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

public class Runner extends AbstractAlgorithmRunner {
	String problemName = "org.uma.jmetal.problem.multiobjective.zdt.ZDT1";

	public static void main(String[] args) throws JMetalException, FileNotFoundException {
		Runner run = new Runner();
		run.random();
		//run.nsgii();
	}

	public void nsgii() throws JMetalException, FileNotFoundException {
		Problem<DoubleSolution> problem;
		Algorithm<List<DoubleSolution>> algorithm;
		CrossoverOperator<DoubleSolution> crossover;
		MutationOperator<DoubleSolution> mutation;
		SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;
		String referenceParetoFront = "";

		problem = ProblemUtils.<DoubleSolution>loadProblem(problemName);

		double crossoverProbability = 0.9;
		double crossoverDistributionIndex = 20.0;
		crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);

		double mutationProbability = 1.0 / problem.getNumberOfVariables();
		double mutationDistributionIndex = 20.0;
		mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);

		selection = new BinaryTournamentSelection<DoubleSolution>(
				new RankingAndCrowdingDistanceComparator<DoubleSolution>());

		algorithm = new NSGAIIBuilder<DoubleSolution>(problem, crossover, mutation).setSelectionOperator(selection)
				.setMaxEvaluations(25000).setPopulationSize(100).build();

		AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

		List<DoubleSolution> population = algorithm.getResult();
		long computingTime = algorithmRunner.getComputingTime();

		JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

		new SolutionListOutput(population).setSeparator("\t")
				.setVarFileOutputContext(new DefaultFileOutputContext("me_nsgiiVAR.tsv"))
				.setFunFileOutputContext(new DefaultFileOutputContext("me_nsgiiFUN.tsv")).print();

		JMetalLogger.logger.info("Random seed: " + JMetalRandom.getInstance().getSeed());
	}

	public void random() throws FileNotFoundException {
		Problem<DoubleSolution> problem;
		Algorithm<List<DoubleSolution>> algorithm;
		String referenceParetoFront = "";
		String problemName = "com.fairanswers.mapExplore.optimizers.MapExploreProblem";
		problem = new MapExploreProblem();
		algorithm = new RandomSearchBuilder<DoubleSolution>(problem).setMaxEvaluations(10).build();

		AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

		List<DoubleSolution> population = algorithm.getResult();
		long computingTime = algorithmRunner.getComputingTime();

		JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

		new SolutionListOutput(population).setSeparator("\t")
				.setVarFileOutputContext(new DefaultFileOutputContext("me_randomVAR.tsv"))
				.setFunFileOutputContext(new DefaultFileOutputContext("me_randomFUN.tsv")).print();

		JMetalLogger.logger.info("Random seed: " + JMetalRandom.getInstance().getSeed());
	}
	// public static void main(String [] args) throws IOException{
	// //args = new String[1];
	// //args[0]="ZDTStudy";
	// //ZDTStudy.main(args);
	// MapExploreProblem prob = new MapExploreProblem();
	// Algorithm<List<DoubleSolution>> search = new
	// RandomSearch<DoubleSolution>(prob, 10);
	// search.run();
	// List<DoubleSolution> result = search.getResult();
	// System.out.println(result);
	// System.out.println(prob.getMap().getAgents().get(0));
	// }
	//
}
