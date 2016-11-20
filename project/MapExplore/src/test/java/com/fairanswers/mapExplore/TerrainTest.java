package com.fairanswers.mapExplore;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TerrainTest {

	Terrain ta;
	Terrain tb;
	Map map;
	Location center = new Location(2,2);
	Location upLeft = new Location(0,4);
	Location loRight = new Location(4,0);

	String tstring = "ABC3456789012345678901xyz";
	@Before
	public void setUp() throws Exception {
		Map map = new Map(5, 5);
		ta = new Terrain(map, Terrain.GRASS);
		ta.setTerrain(1, 1, Terrain.PAVED);
		tb = new Terrain(map, Terrain.UNKNOWN);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWeight() {
		Location weight = ta.weight(center, Terrain.PAVED);
		assertTrue("one paved", weight.equals(-1, -1) );
	}
	

	@Test
	public void testWeightNeighborhood() {
		Location here = new Location(0, 0);
		Location next = tb.weightNeighborhood(here, Terrain.UNKNOWN, 1);
		assertTrue(next.getX()==2.0 && next.getY()==2.0);
		tb.setTerrain(0, 1, Terrain.PAVED);
		next = tb.weightNeighborhood(here, Terrain.UNKNOWN, 1);
		assertTrue(next.getX()==2.0 && next.getY()==1.0);
		tb.setTerrain(0, 2, Terrain.PAVED);
		tb.setTerrain(0, 3, Terrain.PAVED);
		tb.setTerrain(0, 4, Terrain.PAVED);
		next = tb.weightNeighborhood(here, Terrain.UNKNOWN, 5);
		assertTrue(next.getX()==20.0 && next.getY()==16.0);
		tb.setTerrain(2, 0, Terrain.PAVED);
		tb.setTerrain(3, 0, Terrain.PAVED);
		tb.setTerrain(4, 0, Terrain.PAVED);
		next = tb.weightNeighborhood(here, Terrain.UNKNOWN, 5);
		assertTrue(next.getX()==17.0 && next.getY()==16.0);
		
	}

	@Test
	public void testCoverage() {
		//Tries two different maps
		double cov = tb.getCoverage();
		assertTrue("blank", cov == 0);
		tb.setTerrain(1, 2, Terrain.CLIFF);
		cov = tb.getCoverage();
		assertTrue("some", cov == 4.0);
		cov = ta.getCoverage();
		assertTrue("all", cov == 100.0);
	}
	
	@Test
	public void testWeight2() {
		Location loc = ta.weight(center, Terrain.GRASS);
		assertTrue("rest grass", loc.equals(1, 1) );
	}
	@Test
	public void testCompare() {
		assertTrue(ta.compare(10, 9) == 1);
		assertTrue(ta.compare(10, 10) == 0);
		assertTrue(ta.compare(10.1, 10.9) == 0);
		assertTrue(ta.compare(9, 10) == -1);
	}
	
//	@Test
//	public void testFromString() throws Exception{
//		Map map = new Map(5, 5);
//		map.setTerrain(Terrain.getTerrainFromString(map, tstring, .1));
//		System.out.println(map);
//		assertEquals("A", map.getViewAt(0, 4));//Upper Left
//		assertEquals("z", map.getViewAt(4, 0));//Lower Right
//	}
	
//	@Test
//	public void testToFileString() throws Exception{
//		Map map = new Map(5, 5);
//		map.setTerrain(Terrain.getTerrainFromString(map, tstring, .1));
//		System.out.println(map.getTerrain().getSaveString() );
//		assertEquals("A", map.getViewAt(0, 4));//Upper Left
//		assertEquals("z", map.getViewAt(4, 0));//Lower Right
//	}

	@Test
	public void testLoadHandmadeFile() throws Exception{
		String filename = "./maps/map1.txt";
		Map map = new Map(10, 10);
		Terrain loadTer = Terrain.load(filename, 1);
		map.setTerrain(loadTer);
		assertEquals("....................................................................................................", loadTer.getSaveString());
		filename = "./maps/map2.txt";
		map = new Map(8, 58);
		loadTer = Terrain.load(filename, 1);
		map.setTerrain(loadTer);
		String s = loadTer.getSaveString();
		System.out.println(loadTer);
	}

}
