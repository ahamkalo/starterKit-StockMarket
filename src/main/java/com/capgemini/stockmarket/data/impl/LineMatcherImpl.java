package com.capgemini.stockmarket.data.impl;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.capgemini.stockmarket.data.LineMatcher;

public class LineMatcherImpl implements LineMatcher {

	public boolean matchTheStockDataPattern(String line) {
		if (!line.matches("^\\w*,20\\d\\d[0-1]\\d[0-3]\\d,[1-9]\\d*\\W\\d*$.*")
				&& !line.matches("^\\w*,20\\d\\d[0-1]\\d[0-3]\\d,[1-9]\\d*$.*")) {
			return false;
		}

		String patternString = "20\\d\\d[0-1]\\d[0-3]\\d";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(line);
		matcher.find();
		String dateString = line.substring(matcher.start() + 4, matcher.end());

		if (dateMatchInvalidPattern(dateString)) {
			return false;
		}

		return true;
	}

	private boolean dateMatchInvalidPattern(String dateString) {
		List<String> invalidPatterns = Arrays.asList("^02[3-9]", "^0229", "^1[3-9]", "^00", "^..00",
				"(01|03|05|07|08|10|12)3[2-9]", "(04|06|09|11)3[1-9]");
		return !invalidPatterns.stream().filter(p -> Pattern.compile(p).matcher(dateString).find())
				.collect(Collectors.toList()).isEmpty();
	}
}
