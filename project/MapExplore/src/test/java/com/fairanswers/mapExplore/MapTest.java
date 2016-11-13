package com.fairanswers.mapExplore;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fairanswers.mapExplore.fsm.Model;

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
	
	@Test
	public void testTerrainVaried(){
		Model.setRandomSeed(1L);
		Map map = new Map(10,4);
		Terrain t = new Terrain(map, 1.0, 1L);
		map.setTerrain(t);
		System.out.println(t);
	}

	@Test
	public void testTerrainBoring(){
		Map map = new Map(10,4);
		Terrain t = new Terrain(map, 10, 1L);
		map.setTerrain(t);
		System.out.println(map);
		System.out.println(t);
//		assertTrue("Checking terrain", map.getTerrain().get(3, 3).equals(Terrain.DEFAULT) );
	}
}
