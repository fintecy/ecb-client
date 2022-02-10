package org.fintecy.md.ecb.response;

import java.time.LocalDate;
import java.util.List;

public class TimePoint {
    private final LocalDate date;
    private final List<Rate> rates;

    public TimePoint(LocalDate date, List<Rate> rates) {
        this.date = date;
        this.rates = rates;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Rate> getRates() {
        return rates;
    }
}
