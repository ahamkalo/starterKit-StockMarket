package com.capgemini.stockmarket.investor.impl;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import com.capgemini.stockmarket.investor.Investor;
import com.capgemini.stockmarket.objects.Transaction;
import com.capgemini.stockmarket.strategy.InvestmentStrategy;

public class StockInvestorTest {
	InvestmentStrategy investmentStrategyMock;
	Investor investor;

	
	@Test
	public final void quickTestBD() {
		BigDecimal bd1 = new BigDecimal("1.0");
		doSomething(bd1);
		assertEquals("11.0", bd1.toString());
	}
	
	public void doSomething(BigDecimal bdX) {
		bdX = bdX.add(BigDecimal.TEN);
	}
	
	@Test
	public final void shouldProperlyMakeTransactions() {
		investmentStrategyMock = Mockito.mock(InvestmentStrategy.class);
		investor = new StockInvestor(investmentStrategyMock);

		makePurchaseTransactions(Arrays.asList(new Transaction("one", 15L, new BigDecimal("2000"))));

		assertEquals("8000", investor.getWallet().getMoney().toString());
		assertEquals(15L, investor.getWallet().getStocks().get("one").longValue());

		makeSaleTransactions(Arrays.asList(new Transaction("one", 10L, new BigDecimal("1000"))));

		assertEquals("9000", investor.getWallet().getMoney().toString());
		assertEquals(5L, investor.getWallet().getStocks().get("one").longValue());

		Mockito.verify(investmentStrategyMock, Mockito.times(2)).choosePurchaseTransactions(Mockito.any());
		Mockito.verify(investmentStrategyMock, Mockito.times(2)).chooseSaleTransactions(Mockito.any());
	}

	private void makePurchaseTransactions(List<Transaction> purchaseTransactions) {
		Mockito.when(investmentStrategyMock.chooseSaleTransactions(Mockito.any()))
				.thenReturn(new ArrayList<Transaction>());
		Mockito.when(investmentStrategyMock.choosePurchaseTransactions(Mockito.any())).thenReturn(purchaseTransactions);
		investor.makeTransactionsWithStrategy();
	}

	private void makeSaleTransactions(List<Transaction> saleTransactions) {
		Mockito.when(investmentStrategyMock.chooseSaleTransactions(Mockito.any())).thenReturn(saleTransactions);
		Mockito.when(investmentStrategyMock.choosePurchaseTransactions(Mockito.any()))
				.thenReturn(new ArrayList<Transaction>());
		investor.makeTransactionsWithStrategy();
	}
}
