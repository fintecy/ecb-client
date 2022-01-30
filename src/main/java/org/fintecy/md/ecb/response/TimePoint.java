package org.fintecy.md.ecb.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimePoint {

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JacksonXmlProperty(localName = "time", isAttribute = true)
    private LocalDate date;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Cube")
    private List<Rate> rates;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
}
