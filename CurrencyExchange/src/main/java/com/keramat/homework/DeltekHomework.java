/**
 * 
 */
package com.keramat.homework;

import java.math.BigDecimal;

public interface DeltekHomework {

	/**
     * Function converting amount in source currency into corresponding amount in destination currency
     *
     * Example: convert(BigDecimal.TEN, "GBP", "USD"); returns 15.34 (varies depending on the actual exchange rate)
     *
     * @param amount Amount in source currency
     * @param sourceCurrency ISO 4217 source currency code
     * @param destinationCurrency ISO 4217 destination currency code
     * @return Amount in destination currency based on the actual exchange rate
     */
    public BigDecimal convert(BigDecimal amount, String sourceCurrency, String destinationCurrency);

    /**
     * Function converting number to english words
     *
     * Example: toWords(BigDecimal.valueOf(1000201)); returns "one million, two hundred one"
     *
     * @param number Source number
     * @return Word representation of the input
     */
    public String toWords(BigDecimal number);
    
}
