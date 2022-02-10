package org.fintecy.md.ecb.response;

import java.math.BigDecimal;
import java.util.Objects;

public class Rate {
    private final String currency;
    private final BigDecimal rate;

    public Rate(String currency, BigDecimal rate) {
        this.currency = currency;
        this.rate = rate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate1 = (Rate) o;
        return Objects.equals(rate, rate1.rate) && Objects.equals(currency, rate1.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate, currency);
    }

    @Override
    public String toString() {
        return "Rate{" +
                "rate=" + rate +
                ", currency='" + currency + '\'' +
                '}';
    }
}
