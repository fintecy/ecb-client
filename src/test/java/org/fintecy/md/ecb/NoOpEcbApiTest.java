package org.fintecy.md.ecb;

import org.fintecy.md.ecb.response.Currency;
import org.fintecy.md.ecb.response.ExchangeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoOpEcbApiTest {
    private EcbApi noOpEcbApi;

    @BeforeEach
    void setUp() {
        noOpEcbApi = new NoOpEcbApi();
    }

    @Test
    void should_test_supported_currencies() {
        //given
        List<Currency> expected = List.of();
        //when
        List<Currency> actual = noOpEcbApi.supportedCurrencies();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_test_historical_rates() {
        //given
        List<ExchangeRate> expected = List.of();
        var asOf = LocalDate.now().minusWeeks(1);
        //when
        var actual = noOpEcbApi.rates(asOf);
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_test_latest_rates() {
        //given
        List<ExchangeRate> expected = List.of();
        //when
        var actual = noOpEcbApi.rates();
        //then
        assertEquals(expected, actual);
    }
}