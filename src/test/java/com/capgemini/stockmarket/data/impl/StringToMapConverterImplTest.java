package com.capgemini.stockmarket.data.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.stockmarket.data.LinesToMapConverter;
import com.capgemini.stockmarket.objects.Stock;

public class StringToMapConverterImplTest {
	LinesToMapConverter converter;

	@Before
	public void setUP() {
		converter = new LinesToMapConverterImpl();
	}

	@Test
	public void shouldConvertWithSuccessForStandardData() {
		List<String> lines = new ArrayList<String>();
		lines.addAll(Arrays.asList("PKOBP,20130102,37.35", "KGHM,20130102,193.1", "PKOBP,20130103,37.1",
				"KGHM,20130103,193.5"));
		SortedMap<Date, List<Stock>> stocks = converter.convert(lines);

		assertFalse(stocks.isEmpty());
		assertEquals(2, stocks.keySet().size());

		Date firstDateInMap = stocks.keySet().stream().findFirst().get();
		String date = firstDateInMap.toString();

		assertEquals("Jan 02 2013", date.substring(4, 10) + date.substring(date.lastIndexOf('T') + 1));
		assertEquals(2, stocks.get(firstDateInMap).size());
		assertEquals("KGHM", stocks.get(firstDateInMap).get(1).getCompany());
	}

	@Test
	public void shouldConvertWithSuccessForMixedLines() {
		List<String> lines = new ArrayList<String>();
		lines.addAll(Arrays.asList("PKOBP,20130102,37.35", "PKOBP,20130103,37.1", "KGHM,20130103,193.5",
				"KGHM,20130102,193.1"));
		SortedMap<Date, List<Stock>> stocks = converter.convert(lines);

		assertFalse(stocks.isEmpty());
		assertEquals(2, stocks.keySet().size());

		Date firstDateInMap = stocks.keySet().stream().findFirst().get();
		String date = firstDateInMap.toString();

		assertEquals("Jan 02 2013", date.substring(4, 10) + date.substring(date.lastIndexOf('T') + 1));
		assertEquals(2, stocks.get(firstDateInMap).size());
		assertEquals("KGHM", stocks.get(firstDateInMap).get(1).getCompany());
	}

	@Test
	public void shouldIgnoreLineWithWrongDate() {
		List<String> lines = new ArrayList<String>();
		lines.addAll(Arrays.asList("PKOBP,20130,37.35"));
		SortedMap<Date, List<Stock>> stocks = converter.convert(lines);

		assertTrue(stocks.isEmpty());
	}

	@Test
	public void shouldIgnoreLineWithLessOrMoreThan3Words() {
		List<String> lines = new ArrayList<String>();
		lines.addAll(Arrays.asList("20130102,37.35", "PKOBP,20130102,37.35,37.35"));
		SortedMap<Date, List<Stock>> stocks = converter.convert(lines);

		assertTrue(stocks.isEmpty());
	}

	@Test
	public void shouldIgnoreLineWithWrongValue() {
		List<String> lines = new ArrayList<String>();
		lines.addAll(Arrays.asList("PKOBP,20130102,wrongValue"));
		SortedMap<Date, List<Stock>> stocks = converter.convert(lines);

		assertTrue(stocks.isEmpty());
	}
}
