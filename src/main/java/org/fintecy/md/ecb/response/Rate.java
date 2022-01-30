package org.fintecy.md.ecb.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.math.BigDecimal;

public class Rate {

    @JacksonXmlProperty(localName = "rate", isAttribute = true)
    private BigDecimal rate;

    @JacksonXmlProperty(localName = "currency", isAttribute = true)
    private String currency;

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
