package com.capgemini.stockmarket.data.impl;

import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import com.capgemini.stockmarket.data.DataProvider;
import com.capgemini.stockmarket.data.LineReader;
import com.capgemini.stockmarket.data.LinesToMapConverter;
import com.capgemini.stockmarket.objects.Stock;

public class DataProviderImpl implements DataProvider {
	private LineReader lineReader;
	private LinesToMapConverter linesToMapConverter;
	String path;

	public DataProviderImpl(String path) {
		lineReader = new LineReaderImpl(path);
		linesToMapConverter = new LinesToMapConverterImpl();
		this.path = path;
	}

	@Override
	public SortedMap<Date, List<Stock>> getStocksMap() {
		List<String> lines = lineReader.getAllLinesFromFile();

		return linesToMapConverter.convert(lines);
	}
}
