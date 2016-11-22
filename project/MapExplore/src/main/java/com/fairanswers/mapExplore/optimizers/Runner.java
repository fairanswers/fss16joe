package com.fairanswers.mapExplore.optimizers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.algorithm.singleobjective.differentialevolution.DifferentialEvolutionBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.operator.impl.selection.TournamentSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.runner.AbstractAlgorithmRunner;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.ProblemUtils;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.MultithreadedSolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

import com.fairanswers.mapExplore.fsm.Model;

public class Runner extends AbstractAlgorithmRunner {
	private static final int RUNS = 50;
	private static final int ITERATIONS = 50;
	private static final int POPULATION = 30;
	private static final int EVALS = 250;
	String problemName = "com.fairanswers.mapExplore.optimizers.MapExploreProblem";
	String startTime = ""+new Date().getTime();

	public static void main(String[] args) throws JMetalException, IOException {
		Runner run = new Runner();
		for(int i=0; i < ITERATIONS; i++){
			System.out.println("Starting iteration"+i);
			run.nsgii();
			run.de();
			
		}
		//run.random();
		//run.nsgii();
		//run.de();
	}

	private void de() throws IOException {
	    DoubleProblem problem;
	    Algorithm<DoubleSolution> algorithm;
	    DifferentialEvolutionSelection selection;
	    DifferentialEvolutionCrossover crossover;
	    SolutionListEvaluator<DoubleSolution> evaluator ;

	    double cr = .5;
	    double f = .5;
	    String variant = "rand/1/bin";
	    int seed = Model.getRandomIntRange(0, 10000);
	    		
	    problem = MapExploreProblem.create(seed);

	    int numberOfCores =8;

	    if (numberOfCores == 1) {
	      evaluator = new SequentialSolutionListEvaluator<DoubleSolution>() ;
	    } else {
	      evaluator = new MultithreadedSolutionListEvaluator<DoubleSolution>(numberOfCores, problem) ;
	    }

	    crossover = new DifferentialEvolutionCrossover(cr,f,variant) ;
	    selection = new DifferentialEvolutionSelection();

	    algorithm = new DifferentialEvolutionBuilder(problem)
	        .setCrossover(crossover)
	        .setSelection(selection)
	        .setSolutionListEvaluator(evaluator)
	        .setMaxEvaluations(EVALS)
	        .setPopulationSize(POPULATION)
	        .build() ;

	    ArrayList<String> results = new ArrayList<>();
		results.add("CoverageDE.cr="+cr+"_f="+f+"_variant="+variant+"_pop="+POPULATION+"_evals="+EVALS+"_seed"+seed );
	    for(int i=0; i< RUNS; i++){
		    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
			        .execute() ;
			    DoubleSolution solution = algorithm.getResult() ;
			    results.add(Double.toString(solution.getObjective(0) ) );
	    }
	    report("CoverageDE", startTime, results);
//	    long computingTime = algorithmRunner.getComputingTime() ;
//
//	    List<DoubleSolution> population = new ArrayList<>(1) ;
//	    population.add(solution) ;
//	    new SolutionListOutput(population)
//	        .setSeparator("\t")
//	        .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
//	        .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
//	        .print();
//
//	    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
//	    JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
//	    JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");

	    evaluator.shutdown();
		
	}

	private String report(String optType, String startHack, ArrayList<String> results) throws IOException {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i< results.size(); i++){
			sb.append(results.get(i) );
			sb.append(" ");
		}
		sb.append("\r\n");
		File f = new File(optType+"."+startHack+"."+new Date().getTime()+".jdat" );
		FileUtils.writeStringToFile(f, sb.toString() );
		System.out.println(sb.toString() );
		return sb.toString();
	}

	public void nsgii() throws JMetalException, IOException {
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

		
		selection = new TournamentSelection<DoubleSolution>(
				new RankingAndCrowdingDistanceComparator<DoubleSolution>(), 5);

		algorithm = new NSGAIIBuilder<DoubleSolution>(problem, crossover, mutation).setSelectionOperator(selection)
				.setMaxEvaluations(EVALS).setPopulationSize(POPULATION).build();

		ArrayList<String> results = new ArrayList<>();
		results.add("NSGII" );
		for(int i=0; i< ITERATIONS; i++){
			System.out.println("Starting run "+i);
			AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
			List<DoubleSolution> population = algorithm.getResult();
			results.add(Double.toString(population.get(0).getObjective(0) ) );
		}

		//List<DoubleSolution> population = algorithm.getResult();
	    report("NSGII", startTime, results);
		

		//JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

//		new SolutionListOutput(population).setSeparator("\t")
//				.setVarFileOutputContext(new DefaultFileOutputContext("me_nsgiiVAR.tsv"))
//				.setFunFileOutputContext(new DefaultFileOutputContext("me_nsgiiFUN.tsv")).print();

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
