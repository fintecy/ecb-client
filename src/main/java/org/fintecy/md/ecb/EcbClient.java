package org.fintecy.md.ecb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.failsafe.Failsafe;
import dev.failsafe.Policy;
import org.fintecy.md.ecb.response.Currency;
import org.fintecy.md.ecb.response.EcbResponse;
import org.fintecy.md.ecb.response.ExchangeRate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.fintecy.md.ecb.response.Currency.currency;

public class EcbClient implements EcbApi {
    private final static String DAILY_PATH = "eurofxref-daily.xml";
    private final static String FULL_HISTORY_PATH = "eurofxref-hist.xml";
    private final static String NINETY_DAYS_HISTORY_PATH = "eurofxref-hist-90d.xml";
    private static final ZoneId zone = ZoneId.of("CET");
    private static final LocalTime cutoffTime = LocalTime.of(14, 15);
    private static final Currency baseCurrency = Currency.currency("EUR");

    private final String rootPath;
    private final HttpClient client;
    private final ObjectMapper mapper;
    private final List<Policy<Object>> policies;

    protected EcbClient(String rootPath, HttpClient httpClient, ObjectMapper mapper, List<Policy<Object>> policies) {
        this.client = checkRequired(httpClient, "Http client required for ECB client");
        this.rootPath = checkRequired(rootPath, "root path cannot be empty");
        this.policies = ofNullable(policies).orElse(List.of());
        this.mapper = checkRequired(mapper, "Xml mapper required");
    }

    public static EcbApi api() {
        return ecbClient().build();
    }

    public static EcbClientBuilder ecbClient() {
        return new EcbClientBuilder();
    }

    public static <T> T checkRequired(T v, String msg) {
        return ofNullable(v)
                .orElseThrow(() -> new IllegalArgumentException(msg));
    }

    @Override
    public List<ExchangeRate> rates() {
        var data = convert(processRequest(DAILY_PATH, EcbResponse.class));
        var max = data.keySet().stream().max(LocalDate::compareTo).orElseThrow();
        return data.get(max);
    }

    @Override
    public Map<LocalDate, List<ExchangeRate>> historicalRates() {
        return convert(processRequest(FULL_HISTORY_PATH, EcbResponse.class));
    }

    @Override
    public Map<LocalDate, List<ExchangeRate>> rates90d() {
        return convert(processRequest(NINETY_DAYS_HISTORY_PATH, EcbResponse.class));
    }

    @Override
    public List<ExchangeRate> rates(LocalDate date) {
        return convert(processRequest(DAILY_PATH, EcbResponse.class))
                .get(date);
    }

    @Override
    public List<Currency> supportedCurrencies() {
        throw new IllegalStateException("not implemented");
    }

    private <T> T processRequest(String url, Class<T> responseType) {
        final var request = HttpRequest.newBuilder(URI.create(rootPath + "/" + url)).build();
        var failsafeExecutor = policies.isEmpty() ? Failsafe.none() : Failsafe.with(policies);
        if (client.executor().isPresent()) {
            failsafeExecutor = failsafeExecutor.with(client.executor().get());
        }
        return parseResponse(failsafeExecutor.get(() -> client.send(request, ofString()))
                .body(), responseType);
    }

    private <T> T parseResponse(String body, Class<T> modelClass) {
        try {
            return (T) mapper.readValue(body, modelClass);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Can parse response", e);
        }
    }

    private Map<LocalDate, List<ExchangeRate>> convert(EcbResponse processRequest) {
        return processRequest.getTimePoints()
                .entrySet()
                .stream()
                .map(q -> new AbstractMap.SimpleEntry<>(q.getKey(),
                        q.getValue().getRates()
                                .stream()
                                .map(r -> new ExchangeRate(currency("EUR"), currency(r.getCurrency()),
                                        q.getKey(), r.getRate()))
                                .collect(toList())))
                .collect(toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }
}
