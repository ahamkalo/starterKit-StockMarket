package com.capgemini.stockmarket.data.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.capgemini.stockmarket.data.LineMatcher;

public class LineMatcherImplTest {

	@Test
	public final void shouldReturnTrueForStandardData() {
		LineMatcher lineMatcher = new LineMatcherImpl();
		String line = "PKOBP,20130401,373.5";
		
		assertTrue(lineMatcher.matchTheStockDataPattern(line));
	}
	
	@Test
	public final void shouldReturnTrueForStandardDataWithNaturalStockValue() {
		LineMatcher lineMatcher = new LineMatcherImpl();
		String line = "PKOBP,20130401,3735";
		
		assertTrue(lineMatcher.matchTheStockDataPattern(line));
	}

	@Test
	public final void shouldReturnFalseIfThereIsLackOfOnePart() {
		LineMatcher lineMatcher = new LineMatcherImpl();
		String line = "PKOBP,20130401";
		
		assertFalse(lineMatcher.matchTheStockDataPattern(line));
	}
	
	@Test
	public final void shouldReturnFalseIfThereIsLetterInStockValue() {
		LineMatcher lineMatcher = new LineMatcherImpl();
		String line = "PKOBP,20130401,37K3.5";
		
		assertFalse(lineMatcher.matchTheStockDataPattern(line));
	}
	
	@Test
	public final void shouldReturnFalseIfThereIsLetterInDate() {
		LineMatcher lineMatcher = new LineMatcherImpl();
		String line = "PKOBP,2013K0401,373.5";
		
		assertFalse(lineMatcher.matchTheStockDataPattern(line));
	}
	
	@Test
	public final void shouldReturnFalseIfDataIsInvalid() {
		LineMatcher lineMatcher = new LineMatcherImpl();
		String line = "PKOBP,20130434,373.5";
		
		assertFalse(lineMatcher.matchTheStockDataPattern(line));
	}
}
