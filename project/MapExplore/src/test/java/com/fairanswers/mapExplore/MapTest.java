package com.fairanswers.mapExplore;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MapTest {

	private Map map;

	@Before
	public void setUp() throws Exception {
		map = new Map(10,4);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAgentStateDriven() {
		map.getAgents().add(new Agent("a1", 1, 1, map) );
		map.getAgents().add(new Agent("a1", 2, 2, map) );
		System.out.println(map);
	}

	@Test
	public void testMap() {
		Map map = new Map(10,4);
		assertTrue("Checking terrain", map.getTerrain().get(3, 3).equals(Terrain.DEFAULT) );
	}

	@Test
	public void testSimpleToString() {
		System.out.println(map);
	}

	@Test
	public void testValid(){
		assertTrue(map.isValid(0, 0));
		assertFalse(map.isValid(0, map.getLen() ) );
		assertFalse(map.isValid(map.getWid(), 0));
		assertFalse(map.isValid(map.getWid(), map.getLen()));
	}
}
