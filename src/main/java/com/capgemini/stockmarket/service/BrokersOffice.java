package com.capgemini.stockmarket.service;

import java.math.BigDecimal;

import com.capgemini.stockmarket.exceptions.LackOfDataInDatabaseException;

public interface BrokersOffice {
	BigDecimal calculatePurchasingCost(String company, Long amount) throws LackOfDataInDatabaseException;
	BigDecimal calculateSaleProfit(String company, Long amount) throws LackOfDataInDatabaseException;
}
