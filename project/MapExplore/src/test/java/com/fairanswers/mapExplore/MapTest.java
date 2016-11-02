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
	public void testMap() {
		Map map = new Map(10,4);
		assertTrue("Checking terrain", map.getTerrain().get(3, 3).equals("1") );
	}

	@Test
	public void testSimpleToString() {
		System.out.println(map);
	}

	@Test
	public void testAgentToString() {
		map.getAgents().add(new Agent("a1", 1, 1) );
		map.getAgents().add(new Agent("a1", 2, 2) );
		System.out.println(map);
	}
	
	@Test 
	public void testAgentMove(){
		Agent a = new Agent("a1", 1, 1) ;
		map.getAgents().add(a);
		System.out.println(map);
		for(int i=0; i< 8; i++){
			map.tick();
			System.out.println(map);
		}
		
		
	}
}
