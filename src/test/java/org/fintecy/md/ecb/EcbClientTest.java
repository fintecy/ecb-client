package org.fintecy.md.ecb;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.fintecy.md.ecb.response.ExchangeRate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.parse;
import static org.fintecy.md.ecb.EcbClient.ecbClient;
import static org.fintecy.md.ecb.response.Currency.currency;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WireMockTest(httpPort = 7777)
class EcbClientTest {

    @Test
    void should_test_historical_rates() {
        //given
        String api = "eurofxref-hist.xml";
        stubFor(get("/" + api)
                .willReturn(aResponse()
                        .withBodyFile(api)));
        var expected = expectedHistoricalResponse();

        //when
        var actual = ecbClient()
                .rootPath("http://localhost:7777")
                .build()
                .historicalRates();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_test_rates_90d() {
        //given
        String api = "eurofxref-hist-90d.xml";
        stubFor(get("/" + api)
                .willReturn(aResponse()
                        .withBodyFile(api)));
        var expected = expectedHistoricalResponse();

        //when
        var actual = ecbClient()
                .rootPath("http://localhost:7777")
                .build()
                .rates90d();
        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_test_latest_rates() {
        //given
        String api = "eurofxref-daily.xml";
        stubFor(get("/" + api)
                .willReturn(aResponse()
                        .withBodyFile(api)));

        var expected = latestRates();
        //when
        var actual = ecbClient()
                .rootPath("http://localhost:7777")
                .build()
                .rates();
        //then
        assertEquals(expected, actual);
    }

    private Map<LocalDate, List<ExchangeRate>> expectedHistoricalResponse() {
        return Map.of (
                LocalDate.parse("2022-01-27"),
                List.of(
                        new ExchangeRate(currency("EUR"), currency("USD"), parse("2022-01-27"), valueOf(1.116)),
                        new ExchangeRate(currency("EUR"), currency("GBP"), parse("2022-01-27"), valueOf(0.83368)),
                        new ExchangeRate(currency("EUR"), currency("RUB"), parse("2022-01-27"), valueOf(87.139))
                ),
                LocalDate.parse("2022-01-28"),
                latestRates()
        );
    }

    private List<ExchangeRate> latestRates() {
        return List.of(
                new ExchangeRate(currency("EUR"), currency("USD"), parse("2022-01-28"), valueOf(1.1138)),
                new ExchangeRate(currency("EUR"), currency("GBP"), parse("2022-01-28"), valueOf(0.83178)),
                new ExchangeRate(currency("EUR"), currency("RUB"), parse("2022-01-28"), valueOf(86.6113))
        );
    }
}