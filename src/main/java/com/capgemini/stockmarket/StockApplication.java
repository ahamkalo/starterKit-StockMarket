package com.capgemini.stockmarket;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.capgemini.stockmarket.data.DataProvider;
import com.capgemini.stockmarket.data.impl.DataProviderImpl;
import com.capgemini.stockmarket.investor.Investor;
import com.capgemini.stockmarket.investor.impl.StockInvestor;
import com.capgemini.stockmarket.service.BrokersOffice;
import com.capgemini.stockmarket.service.StockMarket;
import com.capgemini.stockmarket.service.impl.BrokersOfficeImpl;
import com.capgemini.stockmarket.service.impl.StockMarketImpl;
import com.capgemini.stockmarket.strategy.InvestmentStrategy;
import com.capgemini.stockmarket.strategy.impl.BuyWhenPricesFallStrategy;
import com.capgemini.stockmarket.strategy.impl.BuyWhenPricesRiseStrategy;
import com.capgemini.stockmarket.strategy.impl.RandomStrategy;

public class StockApplication {
	private final static String PATH = "/dane.csv";
	static DataProvider dataProvider;
	static StockMarket stockMarket;
	static BrokersOffice brokersOffice;
	
	public static void main(String[] args) {
		dataProvider = new DataProviderImpl(PATH);
		stockMarket = new StockMarketImpl(dataProvider.getStocksMap());
		brokersOffice = new BrokersOfficeImpl(stockMarket);

		List<InvestmentStrategy> investmentStrategies = initializeStrategies();
		List<Investor> investors = initializeInvestors(investmentStrategies);

		System.out.println("Stan przed pierwszym dniem: ");
		displaySummary(investors);

		while (stockMarket.getStockDateHandler().isNotLastDate()) {
			stockMarket.getStockDateHandler().moveToNextDate();
			investors.forEach(i -> i.makeTransactionsWithStrategy());
		}

		System.out.println("Stan po ostatnim dniu: ");
		displaySummary(investors);
	}

	private static List<Investor> initializeInvestors(List<InvestmentStrategy> investmentStrategies) {
		List<Investor> investors = new LinkedList<Investor>();
		investmentStrategies.forEach(i -> investors.add(new StockInvestor(i)));

		return investors;
	}
	
	private static List<InvestmentStrategy> initializeStrategies() {
		InvestmentStrategy buyWhenPricesRiseStrategy = new BuyWhenPricesRiseStrategy(stockMarket, brokersOffice);
		InvestmentStrategy buyWhenPricesFallStrategy = new BuyWhenPricesFallStrategy(stockMarket, brokersOffice);
		InvestmentStrategy randomStrategy = new RandomStrategy(stockMarket, brokersOffice);
		
		return Arrays.asList(buyWhenPricesRiseStrategy, buyWhenPricesFallStrategy, randomStrategy);
	}
	
	private static void displaySummary(List<Investor> investors) {
		System.out.println();
		System.out.println("*********** Investor with BuyWhenPricesRiseStrategy ***********");
		getFinancialBalance(investors.get(0), countInvestorsFund(investors.get(0)));
		System.out.println("*********** Investor with BuyWhenPricesFallStrategy ***********");
		getFinancialBalance(investors.get(1), countInvestorsFund(investors.get(1)));
		System.out.println("*********** Investor with RandomStrategy ***********");
		getFinancialBalance(investors.get(2), countInvestorsFund(investors.get(2)));
	}
	
	private static BigDecimal countInvestorsFund(Investor investor) {
		BigDecimal stocksValue = investor.getWallet().getStocks().entrySet().stream()
				.map(entry -> stockMarket.getActualStockPrice(entry.getKey())
						.multiply(new BigDecimal(entry.getValue().toString())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return investor.getWallet().getMoney().add(stocksValue);
	}
	
	private static void getFinancialBalance(Investor investor, BigDecimal investorsFund) {
		System.out.println(
				"Posiadane pieniądze: " + investor.getWallet().getMoney().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		System.out.println("Posiadane akcje: ");
		investor.getWallet().getStocks().keySet().forEach(company -> {
			Long amount = investor.getWallet().getStocks().get(company);
			System.out.println("    " + company + " - liczba akcji: " + amount + " wartość: "
					+ stockMarket.getActualStockPrice(company).multiply(new BigDecimal(amount.toString())).setScale(2,
							BigDecimal.ROUND_HALF_EVEN));
		});

		System.out.println("Posiadane fundusze (pieniądze + wartość akcji): "
				+ investorsFund.setScale(2, BigDecimal.ROUND_HALF_EVEN));
		System.out.println();
	}
}
