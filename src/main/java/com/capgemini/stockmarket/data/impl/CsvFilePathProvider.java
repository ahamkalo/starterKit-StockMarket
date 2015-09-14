package com.capgemini.stockmarket.data.impl;

import java.net.URL;

import com.capgemini.stockmarket.data.FilePathProvider;
import com.capgemini.stockmarket.exceptions.InvalidPathException;

public class CsvFilePathProvider implements FilePathProvider {
	String resurcePath;

	public CsvFilePathProvider(String path) {
		resurcePath = path;
	}

	public String getPath() {
		String pathToCsvFile;

		try {
			URL url = getClass().getResource(resurcePath);
			checkUrlIsNotNull(url);
			pathToCsvFile = url.getPath();
			checkPathEndsWithCsv(pathToCsvFile);
		} catch (InvalidPathException e) {
			return null;
		}

		return pathToCsvFile;
	}

	private void checkPathEndsWithCsv(String pathToCsvFile) throws InvalidPathException {
		if (!pathToCsvFile.endsWith(".csv")) {
			throw new InvalidPathException();
		}
	}

	private void checkUrlIsNotNull(URL url) throws InvalidPathException {
		if (url == null) {
			throw new InvalidPathException();
		}
	}
}
