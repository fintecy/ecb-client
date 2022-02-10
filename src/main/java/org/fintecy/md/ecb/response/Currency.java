package org.fintecy.md.ecb.response;

/**
 * @author batiaev
 */
public class Currency extends MicroType<String> implements Comparable<Currency> {

    /**
     *
     * @param code iso currency code
     */
    public Currency(String code) {
        super(code);
    }

    /**
     * @return currency
     * @param code iso currency code
     */
    public static Currency currency(String code) {
        return new Currency(code);
    }

    /**
     * Build currency from java Currency class
     * @param currency java currency
     * @return fintecy currency
     */
    public static Currency fromJavaCurrency(java.util.Currency currency) {
        return currency(currency.getCurrencyCode());
    }

    /**
     * @return java currency object
     */
    public java.util.Currency toJavaCurrency() {
        return java.util.Currency.getInstance(value);
    }

    /**
     * @return currency code
     */
    public String getCode() {
        return value;
    }

    @Override
    public int compareTo(Currency o) {
        return value.compareTo(o.value);
    }
}
