package com.capgemini.stockmarket.strategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.capgemini.stockmarket.exceptions.LackOfDataInDatabaseException;
import com.capgemini.stockmarket.objects.Transaction;
import com.capgemini.stockmarket.service.BrokersOffice;
import com.capgemini.stockmarket.service.StockMarket;

public abstract class WeeklyTendencyStrategy implements InvestmentStrategy {
	private StockMarket stockMarket;
	private BrokersOffice brokersOffice;

	public WeeklyTendencyStrategy(StockMarket stockMarket, BrokersOffice brokersOffice) {
		this.stockMarket = stockMarket;
		this.brokersOffice = brokersOffice;
	}

	public List<Transaction> choosePurchaseTransactions(BigDecimal money) {
		List<Transaction> transactions = new ArrayList<Transaction>();

		BigDecimal moneyToSpend = new BigDecimal(money.toString());

		stockMarket.getCompanies().forEach(company -> {
			try {
				if (checkTendency(stockMarket, company)) {
					Long amount = 300L;
					BigDecimal value = brokersOffice.calculatePurchasingCost(company, amount);
					Transaction transaction = new Transaction(company, amount, value);
					if (checkInvestorHasEnoughMoney(transaction, moneyToSpend)) {
						transactions.add(transaction);
					}
				}
			} catch (LackOfDataInDatabaseException e) {
			}
		});

		return transactions;
	}

	public List<Transaction> chooseSaleTransactions(Map<String, Long> stocks) {
		List<Transaction> transactions = new ArrayList<Transaction>();

		stocks.keySet().forEach(company -> {
			try {
				if (!checkTendency(stockMarket, company)) {
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

	abstract protected boolean checkTendency(StockMarket stockMarket, String company)
			throws LackOfDataInDatabaseException;
}
