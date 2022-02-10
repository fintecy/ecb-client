package org.fintecy.md.ecb;

import org.fintecy.md.ecb.response.Currency;
import org.fintecy.md.ecb.response.ExchangeRate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author batiaev
 * @see <a href="https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html">docs</a>
 */
public interface EcbApi {
    /**
     * default ECB root path
     */
    String ROOT_PATH = "https://www.ecb.europa.eu/stats/eurofxref";
    /**
     * @return latest daily rates
     * @see <a href="https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml">daily</a>
     */
    List<ExchangeRate> rates();
    /**
     * @return full available ecb history of fx rates
     * @see <a href="https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist.xml">historical</a>
     */
    Map<LocalDate, List<ExchangeRate>> historicalRates();
    /**
     * @return fx rates for last 90 days
     * @see <a href="https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml">90d rates</a>
     */
    Map<LocalDate, List<ExchangeRate>> rates90d();

    /**
     * @param date cob date
     * @return fx rates at specified date
     */
    List<ExchangeRate> rates(LocalDate date);

    /**
     * @return list of supported currencies
     */
    List<Currency> supportedCurrencies();
}
