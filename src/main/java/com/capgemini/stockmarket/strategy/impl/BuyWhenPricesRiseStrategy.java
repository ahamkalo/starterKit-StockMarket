package com.capgemini.stockmarket.strategy.impl;

import java.math.BigDecimal;

import com.capgemini.stockmarket.exceptions.LackOfDataInDatabaseException;
import com.capgemini.stockmarket.service.BrokersOffice;
import com.capgemini.stockmarket.service.StockMarket;
import com.capgemini.stockmarket.strategy.WeeklyTendencyStrategy;

public class BuyWhenPricesRiseStrategy extends WeeklyTendencyStrategy {
	
	public BuyWhenPricesRiseStrategy(StockMarket stockMarket, BrokersOffice brokersOffice) {
		super(stockMarket, brokersOffice);
	}

	@Override
	protected boolean checkTendency(StockMarket stockMarket, String company) throws LackOfDataInDatabaseException {
		BigDecimal actualPrice = stockMarket.getActualStockPrice(company);
		BigDecimal oldPrice = stockMarket.getStockPrice(company,
				stockMarket.getStockDateHandler().getDateEarlierAboutOneWeek());
		if(actualPrice == null || oldPrice == null){
			throw new LackOfDataInDatabaseException();
		}

		return actualPrice.compareTo(oldPrice) > 0;
	}
}
