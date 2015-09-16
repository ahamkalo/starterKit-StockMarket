package com.capgemini.stockmarket.data.impl;

import static org.junit.Assert.*;
import org.junit.Test;
import com.capgemini.stockmarket.data.FilePathProvider;

public class CsvFilePathProviderTest {

	@Test
	public final void shouldReturnNullForInvalidPath() {
		FilePathProvider pathProvider = new CsvFilePathProvider("random invalid resource path");
		String path = pathProvider.getPath();
		
		assertNull(path);
	}
}
