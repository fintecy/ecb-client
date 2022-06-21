package org.fintecy.md.ecb.response;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class TimePoint {
    private final LocalDate date;
    private final List<ExchangeRate> rates;

    public TimePoint(LocalDate date, List<ExchangeRate> rates) {
        this.date = date;
        this.rates = rates;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<ExchangeRate> getRates() {
        return rates;
    }

    @Override
    public String toString() {
        return "TimePoint{" +
                "date=" + date +
                ", rates=" + rates +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimePoint timePoint = (TimePoint) o;
        return Objects.equals(date, timePoint.date) && Objects.equals(rates, timePoint.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, rates);
    }
}
