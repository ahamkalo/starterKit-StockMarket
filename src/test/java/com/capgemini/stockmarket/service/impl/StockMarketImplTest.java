package com.capgemini.stockmarket.service.impl;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.capgemini.stockmarket.objects.Stock;
import com.capgemini.stockmarket.service.StockMarket;

public class StockMarketImplTest {
	StockMarket stockMarket;
	static SortedMap<Date, List<Stock>> stocks;

	@BeforeClass
	public static void setUpBeforeClass() throws ParseException {
		initializeStocksMap();
	}

	@Before
	public void setUp() {
		stockMarket = new StockMarketImpl(stocks);
	}

	@Test
	public void shouldReturnRightActualStockPrice() {
		assertEquals(1, stockMarket.getActualStockPrice("one").intValue());
		stockMarket.getStockDateHandler().moveToNextDate();
		assertEquals(2, stockMarket.getActualStockPrice("one").intValue());
	}

	@Test
	public void shouldReturnRightStockPrice() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		assertEquals(1, stockMarket.getStockPrice("one", dateFormat.parse("20130101")).intValue());
		assertEquals(2, stockMarket.getStockPrice("one", dateFormat.parse("20130102")).intValue());
	}
	@Test
	public void shouldReturnAllCompanies() throws ParseException {
		assertEquals("one", stockMarket.getCompanies().get(0));
		assertEquals("two", stockMarket.getCompanies().get(1));
	}
	
	private static void initializeStocksMap() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date1 = dateFormat.parse("20130101");
		Date date2 = dateFormat.parse("20130102");
		Stock stockOne1 = new Stock("one", new BigDecimal("1"));
		Stock stockOne2 = new Stock("one", new BigDecimal("2"));
		Stock stockTwo3 = new Stock("two", new BigDecimal("3"));
		Stock stockTwo4 = new Stock("two", new BigDecimal("4"));

		List<Stock> ones = Arrays.asList(stockOne1, stockTwo3);
		List<Stock> twos = Arrays.asList(stockOne2, stockTwo4);

		stocks = new TreeMap<Date, List<Stock>>();
		stocks.put(date1, ones);
		stocks.put(date2, twos);
	}
}
