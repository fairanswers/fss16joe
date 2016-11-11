package opt;

import java.util.List;

import org.apache.commons.math3.analysis.solvers.NewtonSolver;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearch;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.problem.multiobjective.Schaffer;
import org.uma.jmetal.solution.DoubleSolution;

public class BasicProblem {
	public static void main(String []  args){
		Schaffer prob = new Schaffer();
		RandomSearch rand = new RandomSearchBuilder(prob).build();
		RandomSearch<DoubleSolution>  two = new RandomSearch<>(prob, 2000000);
		two.run();
		List<DoubleSolution> result = two.getResult();
		System.out.println(result);
	}
	
}
