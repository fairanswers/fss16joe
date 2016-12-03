package com.fairanswers.mapExplore.optimizers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
	private static int ITERATIONS;
	private static int POPULATION;
	private static int EVALS;
	private static int THREADS;
	String problemName = "com.fairanswers.mapExplore.optimizers.MapExploreProblem";
	String startTime = ""+new Date().getTime();

	public static void main(String[] args) throws JMetalException, IOException {
		long mainStart = new Date().getTime();
		// SmallRuns
		if(true){
			ITERATIONS=1;
			POPULATION=50; //Must be even for DE
			EVALS=1000;
			THREADS=1;
		}
		Runner run = new Runner();
		for(int i=0; i < ITERATIONS; i++){
			System.out.println("Starting iteration"+i+" pop="+ POPULATION+ " evals=" +EVALS);
			run.nsgii();
			//run.de();  // 5 minutes with 1/50/1000
			
		}
		System.out.println("* * * End maintime ="+new Date().getTime() +" elaspsed mainTime ="+(new Date().getTime() - mainStart )/1000);
	}

	public void nsgii() throws JMetalException, IOException {
		long nsgiiTime = new Date().getTime();
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREADS);
        for (int i = 0; i < THREADS; i++) 
        {
            System.out.println("Starting NSGIIProblem #" + i);
    		NSGIIProblem prob = new NSGIIProblem();
    	    prob.setEvals(EVALS);
    	    prob.setPopulation(POPULATION);
    	    prob.setStartTime(startTime);
    	    prob.setRunner(this);
            executor.execute(prob);
        }
        System.out.println("Maximum threads inside pool " + executor.getMaximumPoolSize());
        executor.shutdown();
		System.out.println("* * * End nsgiitime ="+new Date().getTime() +" elaspsed nsgiiTime ="+(new Date().getTime() - nsgiiTime )/1000);
	}

	private void de() throws IOException {
		long deTime = new Date().getTime();
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREADS);
        for (int i = 0; i < THREADS; i++) 
        {
            System.out.println("Starting DEProblem #" + i);
    		DEProblem prob = new DEProblem();
    	    prob.setEvals(EVALS);
    	    prob.setPopulation(POPULATION);
    	    prob.setStartTime(startTime);
    	    prob.setRunner(this);
            executor.execute(prob);
        }
        System.out.println("Maximum threads inside pool " + executor.getMaximumPoolSize());
        executor.shutdown();
        System.out.println("DE Finised at "+new Date().getTime() );
		System.out.println("* * * End detime ="+new Date().getTime() +" elaspsed deTime ="+(new Date().getTime() - deTime )/1000);

	}


	public String report(String optType, String startHack, ArrayList<String> results) throws IOException {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i< results.size(); i++){
			sb.append(results.get(i) );
			sb.append(" ");
		}
		sb.append("\r\n");
		File f = new File("tmp/"+optType+"."+startHack+"."+new Date().getTime()+".jdat" );
		FileUtils.writeStringToFile(f, sb.toString() );
		System.out.println(sb.toString() );
		return sb.toString();
	}

	public void random() throws FileNotFoundException {
		Problem<DoubleSolution> problem;
		Algorithm<List<DoubleSolution>> algorithm;
		String referenceParetoFront = "";
		String problemName = "com.fairanswers.mapExplore.optimizers.MapExploreProblem";
		problem = new MapExploreProblem("random");
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
}
