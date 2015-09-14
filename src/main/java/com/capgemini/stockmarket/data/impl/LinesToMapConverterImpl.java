package com.capgemini.stockmarket.data.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.capgemini.stockmarket.data.LineMatcher;
import com.capgemini.stockmarket.data.LinesToMapConverter;
import com.capgemini.stockmarket.objects.Stock;

public class LinesToMapConverterImpl implements LinesToMapConverter {
	private SortedMap<Date, List<Stock>> stocks;
	private LineMatcher lineMatcher;
	
	public LinesToMapConverterImpl() {
		stocks = new TreeMap<Date, List<Stock>>();
		lineMatcher = new LineMatcherImpl();
	}
	
	public SortedMap<Date, List<Stock>> convert(List<String> lines) {
		String csvSplitBy = ",";
		
		for(String line : lines){
			if(!lineMatcher.matchTheStockDataPattern(line)){
				continue;
			}

			String[] words = line.split(csvSplitBy);

			String company = words[0];
			String dateInString = words[1];
			String value = words[2];
			
			Date date;
			try {
				date = convertUsingDefinedFormat(dateInString);
			} catch (ParseException e) {
				continue;
			}
			
			Stock stock = new Stock(company, new BigDecimal(value));
			addToMap(date, stock);	
		}
		
		return stocks;
	}

	private void addToMap(Date date, Stock stock) {
		if (stocks.containsKey(date)) {
			stocks.get(date).add(stock);
		} else {
			List<Stock> stocksList = new ArrayList<Stock>();
			stocksList.add(stock);
			stocks.put(date, stocksList);
		}
	}

	private Date convertUsingDefinedFormat(String dateInString) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = dateFormat.parse(dateInString);
		
		return date;
	}
}
