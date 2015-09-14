package com.capgemini.stockmarket.objects;

import java.math.BigDecimal;

public class Transaction {

	private Long amount;
	private String company;
	private BigDecimal value;

	public Transaction(String company, Long amount, BigDecimal value) {
		this.amount = amount;
		this.company = company;
		this.value = value;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}
