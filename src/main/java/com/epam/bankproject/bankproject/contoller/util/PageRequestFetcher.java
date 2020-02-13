package com.epam.bankproject.bankproject.contoller.util;

public class PageRequestFetcher {
    private static final int DEFAULT_PAGE = 0;

    public static int fetchPageParameters(String page, long maxPage){
        int numericPage;
        try {
             numericPage = Integer.parseInt(page);
             isPageNotNegativeAndNotGreaterThenThreshold(numericPage, maxPage);
        }catch (NumberFormatException ex){
            numericPage= DEFAULT_PAGE;
        }
        return numericPage;
    }

    private static void isPageNotNegativeAndNotGreaterThenThreshold(int numericPage, long maxPage){
        if (numericPage < 0 || numericPage > maxPage){
            throw new NumberFormatException();
        }
    }

}
