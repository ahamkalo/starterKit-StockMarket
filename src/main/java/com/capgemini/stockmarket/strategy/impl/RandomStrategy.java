package com.capgemini.stockmarket.strategy.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.capgemini.stockmarket.exceptions.LackOfDataInDatabaseException;
import com.capgemini.stockmarket.objects.Transaction;
import com.capgemini.stockmarket.service.BrokersOffice;
import com.capgemini.stockmarket.service.StockMarket;
import com.capgemini.stockmarket.strategy.InvestmentStrategy;

public class RandomStrategy implements InvestmentStrategy {
	private StockMarket stockMarket;
	private BrokersOffice brokersOffice;
	Random random;

	public RandomStrategy(StockMarket stockMarket, BrokersOffice brokersOffice) {
		this.stockMarket = stockMarket;
		this.brokersOffice = brokersOffice;
		random = new Random();
	}
	@Override
	public List<Transaction> choosePurchaseTransactions(BigDecimal money) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		BigDecimal moneyToSpend = new BigDecimal(money.toString());
		
		List<String> companies = stockMarket.getCompanies();
		String company = companies.get(random.nextInt(companies.size()));
		Long amount = new Long(random.nextInt(100));
		
		try {
			BigDecimal value = brokersOffice.calculatePurchasingCost(company, amount);
			Transaction transaction = new Transaction(company, amount, value);
			if (checkInvestorHasEnoughMoney(transaction, moneyToSpend)) {
				transactions.add(transaction);
			}
		} catch (LackOfDataInDatabaseException e) {
		}
		
		return transactions;
	}

	@Override
	public List<Transaction> chooseSaleTransactions(Map<String, Long> stocks) {
		List<Transaction> transactions = new ArrayList<Transaction>();

		stocks.keySet().forEach(company -> {
			try {
				if (random.nextBoolean() && random.nextBoolean() && random.nextBoolean()) {
					Long amount = stocks.get(company);
					BigDecimal value = brokersOffice.calculateSaleProfit(company, amount);
					Transaction transaction = new Transaction(company, amount, value);
					transactions.add(transaction);
				}
			} catch (Exception e) {
			}
		});

		return transactions;
	}
	
	private boolean checkInvestorHasEnoughMoney(Transaction transaction, BigDecimal moneyToSpend) {
		moneyToSpend = moneyToSpend.subtract(transaction.getValue());

		return moneyToSpend.compareTo(new BigDecimal(1000)) > 0;
	}
}
