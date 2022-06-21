package org.fintecy.md.ecb.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.fintecy.md.ecb.response.ExchangeRate;
import org.fintecy.md.ecb.response.TimePoint;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.fintecy.md.ecb.response.Currency.currency;

public class TimePointDeserializer extends StdDeserializer<TimePoint> {
    public TimePointDeserializer() {
        super(TimePoint.class);
    }

    @Override
    public TimePoint deserialize(JsonParser jp, DeserializationContext context) throws IOException {

        final JsonNode node = jp.getCodec().readTree(jp);

        var time = LocalDate.parse(node.get("time").asText());
        var rates = new ArrayList<ExchangeRate>();
        node.get("Cube").forEach(n -> {
            BigDecimal rate = getRate(n.get("rate"));
            String currency = n.get("currency").asText();
            rates.add(new ExchangeRate(currency("EUR"), currency(currency), time, rate));
        });
        return new TimePoint(time, rates);
    }

    private BigDecimal getRate(JsonNode rateNode) {
        if (rateNode.isBigDecimal())
            return rateNode.decimalValue();
//        if (rateNode.isTextual())
        return new BigDecimal(rateNode.asText());
    }
}
