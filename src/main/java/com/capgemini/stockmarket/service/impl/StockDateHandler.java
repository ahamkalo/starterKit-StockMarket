package com.capgemini.stockmarket.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import com.capgemini.stockmarket.service.DateHandler;

public class StockDateHandler implements DateHandler{
	private List<Date> dates = new LinkedList<Date>();
	private ListIterator<Date> dateIterator;
	private Date date;

	public StockDateHandler(Set<Date> dateSet) {
		dates.addAll(dateSet);
		dateIterator = dates.listIterator();
		date = dateIterator.next();
	}

	public Date getActualDate() {
		return date;
	}

	public void moveToNextDate() {
		if (dateIterator.hasNext()) {
			date = dateIterator.next();
		}
	}

	public Date getDateEarlierAboutOneWeek() {
		int dateIndex = dateIterator.previousIndex();
		dateIndex -= 4;
		if (dateIndex >= 0) {
			return dates.get(dateIndex);
		}
		return dates.get(0);
	}

	public boolean isNotLastDate() {
		return dateIterator.hasNext();
	}

}
