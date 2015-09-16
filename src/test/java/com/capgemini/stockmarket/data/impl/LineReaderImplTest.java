package com.capgemini.stockmarket.data.impl;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;

import com.capgemini.stockmarket.data.LineReader;

public class LineReaderImplTest {

	@Test
	public final void testGetAllLinesFromFile() {
		LineReader lineReader = new LineReaderImpl("random invalid resource path");
		List<String> lines = lineReader.getAllLinesFromFile();
		
		assertNull(lines);
	}

}
