package com.capgemini.stockmarket.service.impl;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.capgemini.stockmarket.service.DateHandler;

public class StockDateHandlerTest {
	DateHandler dateHandler;
	static Set<Date> dates;
	
	@BeforeClass
	public static void setUpBeforeClass() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		dates = new HashSet<Date>();
		dates.add(dateFormat.parse("20130101"));
		dates.add(dateFormat.parse("20130102"));

	}
	@Before
	public void setUp() throws ParseException {
		dateHandler = new StockDateHandler(dates);
	}

	@Test
	public void shouldReturnActualDate() {
		assertEquals("Tue Jan 01 00:00:00 CET 2013", dateHandler.getActualDate().toString());
	}
	@Test
	public void shouldMoveToNextDate() {
		dateHandler.moveToNextDate();
		assertEquals("Wed Jan 02 00:00:00 CET 2013", dateHandler.getActualDate().toString());
	}
	@Test
	public void shouldReturnFirstDate() {
		dateHandler.moveToNextDate();
		assertEquals("Tue Jan 01 00:00:00 CET 2013", dateHandler.getDateEarlierAboutOneWeek().toString());
	}
	@Test
	public void shouldReturnThatItIsLastDate() {
		dateHandler.moveToNextDate();
		assertFalse(dateHandler.isNotLastDate());
	}
}
