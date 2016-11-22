package com.fairanswers.mapExplore.optimizers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.solution.DoubleSolution;

public class MapExploreProblemTest extends MapExploreProblem {

	private MapExploreProblem prob;

	@Before
	public void setUp() throws Exception {
		prob = MapExploreProblem.create(1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEvaluate() {
		DoubleSolution sol = prob.createSolution();
		prob.evaluate(sol);
	}

}
