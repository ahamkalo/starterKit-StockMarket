package com.capgemini.stockmarket.service.impl;

import java.math.BigDecimal;

import com.capgemini.stockmarket.exceptions.LackOfDataInDatabaseException;
import com.capgemini.stockmarket.service.BrokersOffice;
import com.capgemini.stockmarket.service.StockMarket;

public class BrokersOfficeImpl implements BrokersOffice {
	private final static BigDecimal MARGIN_VALUE = new BigDecimal("0.005");
	private StockMarket stockMarket;
	
	public BrokersOfficeImpl(StockMarket stockMarket) {
		this.stockMarket = stockMarket;
	}

	public BigDecimal calculatePurchasingCost(String company, Long amount) throws LackOfDataInDatabaseException {
		BigDecimal stockPrice = getActualStockPrice(company);

		stockPrice = stockPrice.add(stockPrice.multiply(MARGIN_VALUE));
		BigDecimal purchasingCost = stockPrice.multiply(new BigDecimal(amount));

		return purchasingCost;
	}

	public BigDecimal calculateSaleProfit(String company, Long amount) throws LackOfDataInDatabaseException {
		BigDecimal stockPrice = getActualStockPrice(company);

		stockPrice = stockPrice.subtract(stockPrice.multiply(MARGIN_VALUE));
		BigDecimal saleCost = stockPrice.multiply(new BigDecimal(amount));

		return saleCost;
	}

	private BigDecimal getActualStockPrice(String company) throws LackOfDataInDatabaseException {
		BigDecimal stockPrice;
		try {
			stockPrice = stockMarket.getActualStockPrice(company);
		} catch (Exception e) {
			throw new LackOfDataInDatabaseException();
		}

		return stockPrice;
	}
}
