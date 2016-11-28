package com.fairanswers.mapExplore;

import static org.junit.Assert.*;

import java.io.IOException;

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
		map = new Map(80, 10);
		agent = new Agent("a2", 1, 1, 1, 1, 1, map);
		agent.setDir(0);
		agent.setUnExploredWeight(1);
		map.getAgents().add(agent);
		Model.setRandomSeed(1L);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDecideDir() {
		agent.setLoc(10, 10);
		agent.setDir(10);
		agent.setUnExploredWeight(.3);
		agent.getTer().setTerrain(0, 0, Terrain.GRASS);
		agent.getTer().setTerrain(1, 0, Terrain.GRASS);
		agent.getTer().setTerrain(2, 0, Terrain.GRASS);
		double dir = agent.decideDir();
		assertTrue(dir - 98 < 2);
		agent.getTer().setTerrain(3, 4, Terrain.GRASS);
		agent.getTer().setTerrain(4, 4, Terrain.GRASS);
		agent.getTer().setTerrain(5, 4, Terrain.GRASS);
		dir = agent.decideDir();
		assertTrue(dir - 98.76 < .1);
	}

	@Test
	public void testExploredWeight() {
		agent.setUnExploredWeight(0);
		for (int i = 0; i < 8; i++) {
			map.tick();
			System.out.println(map);
		}
		agent.turnLeft(90);
		agent.setUnExploredWeight(.3);
		for (int i = 0; i < 6; i++) {
			map.tick();
			System.out.println(map);
		}
		for (double wander = 0; wander <= 1; wander = wander + .1) {
			agent.setUnExploredWeight(wander);
			for (int i = 0; i < 20; i++) {
				map.tick();
				//System.out.println(map);
			}
		}
		//System.out.println("* * * Reducing Wander");
		for (double wander = 1; wander >=0; wander = wander - .1) {
			agent.setUnExploredWeight(wander);
			for (int i = 0; i < 20; i++) {
				map.tick();
				//System.out.println(map);
			}
		}
		System.out.println(agent);
		System.out.println(map);
	}

//	@Test
//	public void testMultipleBigMaps(){
//		for(int i=0; i < 1000; i++){
//			testBigMap();
//			if(i%100==0){
//				System.out.println(i);
//			}
//				
//		}
//	}
	@Test
	public void testBigMap() {
		int multiplier = 100;
		map = new Map(multiplier, multiplier);
		map.setTerrain(new Terrain(map, 1.0) );
		//agent = new Agent("a3", 1, 1, 30, .9, map); //70%
		//agent = new Agent("a3", 1, 1, 50, .9, map); //77.3
		agent = new Agent("a3", 1, 1, 1, 1, 1, map); //82.2
		
		map.getAgents().add(agent);
		Model.setRandomSeed(1L);
		
		//for (int i = 0; i < 100*multiplier; i++) {
		for (int i = 0; i < multiplier * multiplier; i++) {
			map.tick();
			//System.out.println(map);
			if(map.getTick() % 1000== 0 ){
				//System.out.println(map.getTick());
				//agent.setUnExploredWeight(Model.getRandom() * .5);
				//System.out.println(agent);
				
			}
		}
		System.out.println(agent);
		//System.out.println(map);
	}

	@Test
	public void test2Agents() {
		int multiplier = 10;
		map = new Map(10*multiplier, 10*multiplier);
		map.setTerrain(new Terrain(map, 1.0) );
		Agent a = new Agent("a", 0, 0, 30, .2, 0, map);
		Agent b = new Agent("b", map.getWid()-1, map.getLen()-1, 30, .2, 0, map);
		b.setTer(a.getTer());
		map.getAgents().add(a);
		map.getAgents().add(b);
		Model.setRandomSeed(1L);
		
		for (int i = 0; i < 1000*multiplier; i++) {
			map.tick();
			if(map.getTick() % 100== 0 ){
				System.out.println(map.getTick());
				//agent.setUnExploredWeight(Model.getRandom() * .5);
				
			}
		}
		System.out.println(a);
		System.out.println(b);
		//System.out.println(map);
	}
	
	@Test
	public void test2AgentsPaintBallWall() throws IOException, Exception {
		Terrain t = Terrain.load("./maps/map3.txt", 1);
		Map map = new Map(t.getWid(), t.getLen() );
		map.setTerrain(t);
		Agent a = new Agent("a", 1.0, 5.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.1, map);
		a.setDir(0);
		Agent b = new Agent("a", map.getWid()-1, 5.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.1, map);
		b.setDir(180);
		b.setTer(a.getTer());
		map.getAgents().add(a);
		map.getAgents().add(b);
		Model.setRandomSeed(1L);
		
		for (int i = 0; i < 1000; i++) {
			map.tick();
//			System.out.println(a);
			if(map.getTick() % 1000== 0 ){
				System.out.println(map.getTick());
			}
		}
		System.out.println(map);
		System.out.println(a);
		//System.out.println(b);
		//System.out.println(map);
	}

	@Test
	public void test2AgentsPaintBall() throws IOException, Exception {
		Terrain t = Terrain.load("./maps/map2.txt", 1);
		Map map = new Map(t.getWid(), t.getLen() );
		map.setTerrain(t);
		Agent a = new Agent("a", 1.0, 5.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.1, map);
		a.setDir(0);
		Agent b = new Agent("a", map.getWid()-1, 5.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.1, map);
		b.setDir(180);
		b.setTer(a.getTer());
		map.getAgents().add(a);
		map.getAgents().add(b);
		Model.setRandomSeed(1L);
		
		for (int i = 0; i < 100; i++) {
			map.tick();
//			System.out.println(a);
			if(map.getTick() % 10000== 0 ){
				System.out.println(map.getTick());
			}
		}
		System.out.println(map);
		System.out.println(a);
		//System.out.println(b);
		//System.out.println(map);
	}

	@Test
	public void testAllAgentsSettings() {
		int multiplier = 10;
		map = new Map(10*multiplier, 10*multiplier);
		map.setTerrain(new Terrain(map, 1.0) );
		Agent a = new Agent("a", 1, 2, 1, 0, 3, 10, .9, .01, map);
		map.getAgents().add(a);
		Model.setRandomSeed(1L);
		
		for (int i = 0; i < 1000*multiplier; i++) {
			map.tick();
			if(map.getTick() % 100== 0 ){
				System.out.println(map.getTick());
				//agent.setUnExploredWeight(Model.getRandom() * .5);
				
			}
		}
		System.out.println(a);
		//System.out.println(map);
	}

	@Test
	public void testSubtractAngles() {
		assertTrue(agent.subtractAngles(10, 5) == 5);
		assertTrue(agent.subtractAngles(5, 10) == -5);
		assertTrue(agent.subtractAngles(350, 10) == -20);
		assertTrue(agent.subtractAngles(10, 350) == 20);

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
	public void testDirFromWeight() {
		assertEquals(356, agent.dirFromWeight(new Location(650, -50)));
		assertEquals(90, agent.dirFromWeight(new Location(0, 1)));
		assertEquals(45, agent.dirFromWeight(new Location(1, 1)));
		assertEquals(0, agent.dirFromWeight(new Location(1, 0)));
		assertEquals(180, agent.dirFromWeight(new Location(-1, 0)));
		assertEquals(270, agent.dirFromWeight(new Location(0, -1)));
		// assertEquals(0, agent.dirFromWeight(new Location(0,0)));
	}

	@Test
	public void testAgentMove() {
		agent.setName("testAgentMove");
		agent.setDir(0);
		System.out.println(map);
		for (int i = 0; i < 8; i++) {
			map.tick();
			System.out.println(map);
		}
		System.out.println(agent);
		// assertEquals("Checking for agent at 5x2", Terrain.AGENT,
		// map.getViewAt(5,2) );
	}

	@Test
	public void testAgentBumpAndTurnBoring() {
		agent.setName("testAgentBumpAndTurnBoring");
		Model.setRandomSeed(1L);
		agent.setDir(90);
		System.out.println(map);
		for (int i = 0; i < 500; i++) {
			map.tick();
			System.out.println(map);
		}
		System.out.println(agent);
		// assertEquals("Checking for agent at 0x1", Terrain.AGENT,
		// map.getViewAt(0,1) );
	}

	@Test
	public void testAgentBumpAndTurnExciting() {
		map.agents.remove(0);
		agent = new Agent("testAgentBumpAndTurnExciting", 1, 1, 10, .60,0 , map);
		map.agents.add(agent);
		Model.setRandomSeed(1L);
		agent.setDir(90);
		System.out.println(map);
		for (int i = 0; i < 500; i++) {
			map.tick();
			System.out.println(map);
		}
		System.out.println(agent);
	}

	@Test
	public void testAgentCircle() {
		Model.setRandomSeed(1L);
		agent.setDir(0);
		agent.setUnExploredWeight(0);
		// Right
		for (int i = 0; i < 8; i++) {
			map.tick();
			System.out.println(map);
		}
		// Up
		agent.turnLeft(90);
		for (int i = 0; i < 8; i++) {
			map.tick();
			System.out.println(map);
		}
		// left
		agent.turnLeft(90);
		for (int i = 0; i < 8; i++) {
			map.tick();
			System.out.println(map);
		}
		// Down
		agent.turnLeft(90);
		for (int i = 0; i < 8; i++) {
			map.tick();
			System.out.println(map);
		}
		System.out.println(map);
		System.out.println(agent);
		assertEquals("Checking for agent at 1x1", Terrain.AGENT, map.getViewAt(1, 1));
	}


	@Test
	public void testLazy() {
		agent.setDir(0);
		
	}

	@Test
	public void testTurn() {
		agent.setDir(90);
		agent.setDir(100);
		agent.setDir(agent.turnRight(80));
	}

	@Test
	public void testTravel() {
		double deg = 0.0;
		double rad = toRadians(deg);
		double c = sin(rad);
		double s = cos(rad);
		double x = agent.getXTravel(deg, 10.0);
		double y = agent.getYTravel(deg, 10.0);
		assertTrue("Testing for close enough ", x - 10 < .01); // Equals is too
																// hard. Settle
																// for 1%
		assertTrue(abs(y) - 10.0 < .01); // Equals is too hard. Settle for 1%
		deg = 270;
		x = agent.getXTravel(deg, 10.0);
		y = agent.getYTravel(deg, 10.0);
		assertTrue("Testing for close enough ", x < .01); // Equals is too hard.
															// Settle for 1%
		assertTrue("Testing for close enough ", y + 10 < .01); // Equals is too
																// hard. Settle
																// for 1%
	}
}
