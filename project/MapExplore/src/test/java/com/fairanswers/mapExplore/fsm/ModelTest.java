package com.fairanswers.mapExplore.fsm;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {

	@Before
	public void setUp() throws Exception {
		Model.setRandomSeed(1L);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSimple() {
		State one = new State("one", false);
		State two = new State("two", true);
		Guard always = new Always("always", two);
		ArrayList<Trans> t = new ArrayList<Trans>();
		t.add(new Trans(one, always) );
		Model m = new Model(t);
		m.run();
	}

	@Test
	public void testLooping() {
		State one = new State("one", false);
		State two = new State("two", true);
		Guard always = new Maybe("likely", one, .9);
		Guard maybe = new Maybe("maybe", two, .1);
		ArrayList<Trans> t = new ArrayList<Trans>();
		t.add(new Trans(one, always) );
		t.add(new Trans(one, maybe) );
		Model m = new Model(t);
		Model.setRandomSeed(1L);
		m.run();
	}
	
	@Test
	public void testRandom(){
		Model.setRandomSeed(1L);
		for(int i=0; i<500; i++){
			assertTrue("Checking random", Model.getRandom() < 1.0);
			assertTrue("Checking randomInt", Model.getRandomInt(11) < 11);
			Double dub = Model.getRandomDouble(9999.11, 999999.22);
			assertTrue("Checking randomDouble bottom "+dub, dub > 9999.11);
			assertTrue("Checking randomDouble top "+dub, dub < 999999.22);
			int in = Model.getRandomIntRange(11, 111);
			assertTrue("Checking randomIntRange bottom for int "+in, in >= 11 );
			assertTrue("Checking randomIntRange bottom for int "+in, in <= 111 );
		}
	}
	
}
