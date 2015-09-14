package com.capgemini.stockmarket.investor.impl;

import java.math.BigDecimal;
import java.util.Map;

import com.capgemini.stockmarket.exceptions.RemoveStocksFromWalletException;
import com.capgemini.stockmarket.investor.Investor;
import com.capgemini.stockmarket.objects.Transaction;
import com.capgemini.stockmarket.objects.Wallet;
import com.capgemini.stockmarket.strategy.InvestmentStrategy;

public class StockInvestor implements Investor {
	private final static BigDecimal INITIAL_MONEY = new BigDecimal("10000");
	private Wallet wallet;
	private InvestmentStrategy investmentStrategy;

	public StockInvestor(InvestmentStrategy investmentStrategy) {
		wallet = new Wallet(INITIAL_MONEY);
		this.investmentStrategy = investmentStrategy;
	}

	public void makeTransactionsWithStrategy() {
		investmentStrategy.chooseSaleTransactions(wallet.getStocks())
				.forEach(transaction -> makeSaleTransaction(transaction));

		investmentStrategy.choosePurchaseTransactions(wallet.getMoney())
				.forEach(transaction -> makePurchaseTransaction(transaction));
	}
	
	private void makeSaleTransaction(Transaction transaction) {
		try {
			removeStocksFromWallet(transaction.getCompany(), transaction.getAmount());
		} catch (RemoveStocksFromWalletException e) {
			return;
		}

		BigDecimal saleProfit = transaction.getValue();
		BigDecimal moneyAfterTransaction = wallet.getMoney().add(saleProfit);

		wallet.setMoney(moneyAfterTransaction);
	}

	private void makePurchaseTransaction(Transaction transaction) {
		addStocksToWallet(transaction.getCompany(), transaction.getAmount());

		BigDecimal purchasingCost = transaction.getValue();
		BigDecimal moneyAfterTransaction = wallet.getMoney().subtract(purchasingCost);

		wallet.setMoney(moneyAfterTransaction);
	}

	private void addStocksToWallet(String company, Long amount) {
		Map<String, Long> stocks = wallet.getStocks();
		if (stocks.containsKey(company)) {
			Long oldAmount = stocks.get(company);
			stocks.put(company, oldAmount + amount);
		} else {
			stocks.put(company, amount);
		}
	}

	private void removeStocksFromWallet(String company, Long amount) throws RemoveStocksFromWalletException {
		Map<String, Long> stocks = wallet.getStocks();
		if (stocks.containsKey(company)) {
			Long oldAmount = stocks.get(company);
			if ((oldAmount - amount) >= 0) {
				stocks.put(company, oldAmount - amount);
				if (stocks.get(company) == 0) {
					stocks.remove(company);
				}
			}
			return;
		}
		throw new RemoveStocksFromWalletException();
	}

	public Wallet getWallet() {
		return wallet;
	}
}
