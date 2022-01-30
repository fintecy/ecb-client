package org.fintecy.md.ecb;

import org.fintecy.md.ecb.response.Currency;
import org.fintecy.md.ecb.response.ExchangeRate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class NoOpEcbApi implements EcbApi {
    @Override
    public List<ExchangeRate> rates() {
        return List.of();
    }

    @Override
    public Map<LocalDate, List<ExchangeRate>> historicalRates() {
        return Map.of();
    }

    @Override
    public Map<LocalDate, List<ExchangeRate>> rates90d() {
        return Map.of();
    }

    @Override
    public List<ExchangeRate> rates(LocalDate date) {
        return List.of();
    }

    @Override
    public List<Currency> supportedCurrencies() {
        return List.of();
    }
}
