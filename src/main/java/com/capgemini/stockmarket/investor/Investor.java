package com.capgemini.stockmarket.investor;

import com.capgemini.stockmarket.objects.Wallet;

public interface Investor {
	Wallet getWallet();
	void makeTransactionsWithStrategy();
}
