package com.capgemini.stockmarket.service;

import java.util.Date;

public interface DateHandler {
	Date getActualDate();
	void moveToNextDate();
	Date getDateEarlierAboutOneWeek();
	boolean isNotLastDate();
}
