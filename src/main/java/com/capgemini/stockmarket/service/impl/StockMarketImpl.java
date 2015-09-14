package com.capgemini.stockmarket.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import com.capgemini.stockmarket.objects.Stock;
import com.capgemini.stockmarket.service.DateHandler;
import com.capgemini.stockmarket.service.StockMarket;

public class StockMarketImpl implements StockMarket {
	private SortedMap<Date, List<Stock>> stocks;
	private DateHandler dateHandler;

	public StockMarketImpl(SortedMap<Date, List<Stock>> stocks) {
		this.stocks = stocks;
		dateHandler = new StockDateHandler(stocks.keySet());
	}

	public BigDecimal getActualStockPrice(String company) {
		try{
			return stocks.get(dateHandler.getActualDate()).stream().filter(stock -> stock.getCompany().equals(company))
					.findFirst().get().getValue();
		}catch(Exception e){
			return null;
		}
	}

	public BigDecimal getStockPrice(String company, Date date) {
		try{
			return stocks.get(date).stream().filter(stock -> stock.getCompany().equals(company)).findFirst().get()
					.getValue();
		}catch(Exception e){
			return null;
		}
		
	}
	
	public List<String> getCompanies() {
		List<String> companies = new ArrayList<String>();
		stocks.get(dateHandler.getActualDate()).stream().forEach(stock -> companies.add(stock.getCompany()));
					
		return companies;
	}

	public DateHandler getStockDateHandler() {
		return dateHandler;
	}
	
	public SortedMap<Date, List<Stock>> getStocks(){
		return stocks;
	}
}
