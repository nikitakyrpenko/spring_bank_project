package com.epam.bankproject.bankproject.util;

import com.epam.bankproject.bankproject.contoller.util.PageRequestFetcher;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PageRequestFetcherTest {

    @Test
    public void whenFetchPageParameterIsCorrect_thenReturnNumericValue() {
        assertEquals(1, PageRequestFetcher.fetchPageParameters("1", 2));
    }

    @Test
    public void whenFetchPageParameterIsNotParsable_thenReturnDefaultValue() {
        assertEquals(0, PageRequestFetcher.fetchPageParameters("abc", 2));
    }

    @Test
    public void whenFetchPageParameterIsLessThenZero_thanReturnDefaultValue() {
        assertEquals(0, PageRequestFetcher.fetchPageParameters("-1", 2));
    }
}
