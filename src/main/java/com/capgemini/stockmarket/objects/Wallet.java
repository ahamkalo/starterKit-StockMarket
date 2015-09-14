package com.capgemini.stockmarket.objects;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
	
	private Map<String, Long> stocks = new HashMap<String, Long>();
	private BigDecimal money;

	public Wallet(BigDecimal money) {
		this.money = money;
	}

	public Map<String, Long> getStocks() {
		return stocks;
	}

	public void setStocks(Map<String, Long> stocks) {
		this.stocks = stocks;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
}
