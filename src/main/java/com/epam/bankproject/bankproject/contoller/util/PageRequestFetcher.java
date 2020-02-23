package com.epam.bankproject.bankproject.contoller.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PageRequestFetcher {
    private static final int DEFAULT_PAGE = 0;

    public int fetchPageParameters(String page, long maxPage) {
        int numericPage;
        try {

            numericPage = Integer.parseInt(page);
            isPageNotNegativeAndNotGreaterThenThreshold(numericPage, maxPage);
        } catch (IllegalArgumentException ex) {
            numericPage = DEFAULT_PAGE;
        }
        return numericPage;
    }

    public void isPageNotNegativeAndNotGreaterThenThreshold(int numericPage, long maxPage) {
        if (numericPage < 0 || numericPage > maxPage) {
            throw new IllegalArgumentException();
        }
    }

}
