package com.capgemini.stockmarket.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import com.capgemini.stockmarket.objects.Stock;

public interface StockMarket {
	BigDecimal getActualStockPrice(String company);
	BigDecimal getStockPrice(String company, Date date);
	List<String> getCompanies();
	
	DateHandler getStockDateHandler();
	SortedMap<Date, List<Stock>> getStocks();
}
