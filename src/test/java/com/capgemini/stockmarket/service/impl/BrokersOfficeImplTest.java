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

import com.capgemini.stockmarket.exceptions.LackOfDataInDatabaseException;
import com.capgemini.stockmarket.objects.Stock;
import com.capgemini.stockmarket.service.BrokersOffice;
import com.capgemini.stockmarket.service.StockMarket;

public class BrokersOfficeImplTest {
	static StockMarket stockMarket;
	BrokersOffice brokersOffice;
	
	@BeforeClass
	public static void setUpBeforeClass() throws ParseException{
		SortedMap<Date, List<Stock>> stocks = initializeStocksMap();
		
		stockMarket = new StockMarketImpl(stocks);
	}

	@Before
	public void setUp() {
		brokersOffice = new BrokersOfficeImpl(stockMarket);
	}

	@Test
	public void shouldProperlyCalculatePurchasingCost() throws LackOfDataInDatabaseException {
		assertEquals("301.500", brokersOffice.calculatePurchasingCost("two", 100L).toString());
	}
	
	@Test
	public void shouldProperlyCalculateSaleProfit() throws LackOfDataInDatabaseException {
		assertEquals("99.500", brokersOffice.calculateSaleProfit("one", 100L).toString());
	}
	
	@Test
	public void shouldThrowLackOfDataInDatabaseException() throws LackOfDataInDatabaseException {
		try{
			brokersOffice.calculateSaleProfit("three", 100L);
			fail("Not throw LackOfDataInDatabaseException");
		}catch(LackOfDataInDatabaseException e){
			
		}
		
	}

	private static SortedMap<Date, List<Stock>> initializeStocksMap() throws ParseException {
		SortedMap<Date, List<Stock>> stocks;
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
		return stocks;
	}
}
