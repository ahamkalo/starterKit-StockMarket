package com.capgemini.stockmarket.strategy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.capgemini.stockmarket.objects.Transaction;

public interface InvestmentStrategy {
	List<Transaction> choosePurchaseTransactions(BigDecimal money);
	List<Transaction> chooseSaleTransactions(Map<String, Long> stocks);
}
