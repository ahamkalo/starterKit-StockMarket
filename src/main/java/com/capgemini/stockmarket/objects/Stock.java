package com.capgemini.stockmarket.objects;

import java.math.BigDecimal;

public class Stock {
	private String company;
	private BigDecimal value;

	public Stock(String company, BigDecimal value) {
		this.company = company;
		this.value = value;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}
