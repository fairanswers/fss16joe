package com.fairanswers.mapExplore;

import static org.junit.Assert.*;
import static java.lang.Math.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fairanswers.mapExplore.fsm.Model;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fairanswers.mapExplore.fsm.Model;

public class AgentTest {

	private Map map;
	private Agent agent;

	@Before
	public void setUp() throws Exception {
		map = new Map(80,10);
		//agent = new Agent("a1", 1, 1, map) ;
		agent = new Agent("a2", 1, 1, 1, .99, map);
		agent.setDir(90);
		map.getAgents().add(agent);
		Model.setRandomSeed(1L);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLook() {
		agent.setName("testLook");
		String t = agent.getTer().get(0, 1);
		assertTrue(t.equals(Terrain.UNKNOWN));
		map.tick();
		t = map.getAgents().get(0).getTer().get(1, 1);
		assertTrue(t.equals(Terrain.DEFAULT));
		// Terrain should NOT show agent, but the terrain underneath
		t = map.getAgents().get(0).getTer().get(1, 2);
		assertFalse(t.equals(Terrain.AGENT));
		t = map.getAgents().get(0).getTer().get(2, 3);
		assertFalse(t.equals(Terrain.DEFAULT));
		System.out.println(agent.getTer());
	}

	@Test 
	public void testAgentMove(){
		agent.setName("testAgentMove");
		agent.setDir(90.0);
		System.out.println(map);
		for(int i=0; i< 8; i++){
			map.tick();
			System.out.println(map);
		}
		System.out.println(agent);
//		assertEquals("Checking for agent at 5x2", Terrain.AGENT, map.getViewAt(5,2) );
	}

	@Test 
	public void testAgentBumpAndTurnBoring(){
		agent.setName("testAgentBumpAndTurnBoring");
		Agent.setRandomSeed(1L);
		agent.setDir(90);
		System.out.println(map);
		for(int i=0; i< 500; i++){
			map.tick();
			System.out.println(map);
		}
		System.out.println(agent);
		//assertEquals("Checking for agent at 0x1", Terrain.AGENT, map.getViewAt(0,1) );
	}
	
	@Test 
	public void testAgentBumpAndTurnExciting(){
		map.agents.remove(0);
		agent = new Agent("testAgentBumpAndTurnExciting", 1, 1, 10, .60, map);
		map.agents.add(agent);
		Agent.setRandomSeed(1L);
		agent.setDir(90);
		System.out.println(map);
		for(int i=0; i< 500; i++){
			map.tick();
			System.out.println(map);
		}
		System.out.println(agent);
		//assertEquals("Checking for agent at 0x1", Terrain.AGENT, map.getViewAt(0,1) );
	}
	@Test 
	public void testAgentCircle(){
		Agent.setRandomSeed(1L);
		agent.setDir(0);
		//Right
		for(int i=0; i< 8; i++){
			map.tick();
			System.out.println(map);
		}
		//Up
		agent.turnRight(90);
		for(int i=0; i< 8; i++){
			map.tick();
			System.out.println(map);
		}
		//left
		agent.turnRight(90);
		for(int i=0; i< 8; i++){
			map.tick();
			System.out.println(map);
		}
		//Down
		agent.turnRight(90);
		for(int i=0; i< 8; i++){
			map.tick();
			System.out.println(map);
		}
		System.out.println(agent);
		assertEquals("Checking for agent at 0x0", Terrain.AGENT, map.getViewAt(0,0) );
	}

	@Test 
	public void testTurn(){
		agent.setDir(90);
		agent.setDir(100);
		agent.setDir(agent.turnRight(80));
	}
	
	@Test
	public void testTravel(){
		double deg=180.0;
		double rad = toRadians(deg);
		double c = cos(rad);
		double s = sin(rad);
		double x = agent.getXTravel(180, 10.0);
		assertTrue(abs(x+10.0) < .01); // Equals is too hard.  Settle for 1%
		double y = agent.getYTravel(270, 10.0);
		assertTrue(abs(y+10.0) < .01); // Equals is too hard.  Settle for 1%
	}
}
