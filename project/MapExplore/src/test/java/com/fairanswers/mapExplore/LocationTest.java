package com.fairanswers.mapExplore;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LocationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEqualsDoubleDouble() {
		Location a = new Location(10, 10);
		assertTrue(a.equals(10,10));
		a=new Location(-10, -10);
		assertTrue(a.equals(-10,-10));
		assertFalse(a.equals(-1,-1));
	}

}
