package com.fairanswers.mapExplore.fsm;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		State one = new State("one", false);
		State two = new State("two", true);
		Guard always = new Guard("always", two);
		
		ArrayList<Trans> t = new ArrayList<Trans>();
		t.add(new Trans(one, always, two) );
		Model m = new Model(t);
		m.run();
		
	}

}
