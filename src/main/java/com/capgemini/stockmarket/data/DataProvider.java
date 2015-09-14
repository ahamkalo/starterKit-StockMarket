package com.capgemini.stockmarket.data;

import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import com.capgemini.stockmarket.objects.Stock;

public interface DataProvider {
	SortedMap<Date, List<Stock>> getStocksMap();
}
