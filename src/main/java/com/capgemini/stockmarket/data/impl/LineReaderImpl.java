package com.capgemini.stockmarket.data.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.stockmarket.data.FilePathProvider;
import com.capgemini.stockmarket.data.LineReader;

public class LineReaderImpl implements LineReader {
	private FilePathProvider filePathProvider;
	
	public LineReaderImpl(String path){
		filePathProvider = new CsvFilePathProvider(path);
	}

	public List<String> getAllLinesFromFile() {
		String pathToCsvFile = filePathProvider.getPath();
		if (pathToCsvFile == null) {
			return null;
		}

		List<String> lines = readDataFrom(pathToCsvFile);
		if (lines == null) {
			return null;
		}

		return lines;
	}

	private List<String> readDataFrom(String pathToCsvFile) {
		
		BufferedReader bufferedReader = getBufferedReader(pathToCsvFile);
		if (bufferedReader == null) {
			return null;
		}
		
		List<String> lines = readAllLinesFrom(bufferedReader);
		try {
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}

	private BufferedReader getBufferedReader(String pathToCsvFile) {
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = new BufferedReader(new FileReader(pathToCsvFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		return bufferedReader;
	}

	private List<String> readAllLinesFrom(BufferedReader bufferedReader) {
		List<String> lines = new ArrayList<String>();

		try {
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return lines;
	}
}
