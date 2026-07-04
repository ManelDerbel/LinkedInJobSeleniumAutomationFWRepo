package com.qa.LinkedInJob.utils;

import java.util.List;

public class StringUtils {

	public static Boolean containsAnyKeywordFromList(List<String> KeywordsList, String text) {
		text = text.toLowerCase();

		for (String keyword : KeywordsList) {
			if (text.toLowerCase().contains(keyword.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static Boolean isLastStringEqual(List<String> list, String expectedValue) {
		if (!list.isEmpty() && expectedValue.equals(list.get(list.size() - 1))) {
			return true;
		}
		return false;
	}

	public static int getIndexOfString(List<String> list, String expectedValue) {
		if (list.isEmpty()) {
			return -1;
		}
		for (int index = list.size() - 1; index >= 0; index--) {
			if (expectedValue.equals(list.get(index))) {
				return index+1;
			}
		}
		return -1;
	}

}
