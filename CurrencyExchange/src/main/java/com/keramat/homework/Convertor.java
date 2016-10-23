package com.keramat.homework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

public class Convertor implements DeltekHomework {

	final static Logger logger = Logger.getLogger(Convertor.class);
    
	
	private final String[] lOW_NAMES = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight",
			"nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen",
			"nineteen" };

	private final String[] TENS_NAMES = { "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty",
			"ninety" };

	private final String[] BIG_NAMES = { "thousand", "million", "billion" };

	/**
	 * 
	 * @param number
	 * @return
	 */
	private String convertNumberToWords(int number) {
		if (number < 0) {
			return "minus " + convertNumberToWords(-number);
		}
		if (number <= 999) {
			return convert999(number);
		}
		String word = null;
		int counter = 0;
		while (number > 0) {
			if (number % 1000 != 0) {
				String wordTmp = convert999(number % 1000);
				if (counter > 0) {
					wordTmp = wordTmp + " " + BIG_NAMES[counter - 1];
				}
				if (word == null) {
					word = wordTmp;
				} else {
					word = wordTmp + ", " + word;
				}
			}
			number /= 1000;
			counter++;
		}
		return word;
	}

	/**
	 * convert 3 digits into words
	 * @param number
	 * @return
	 */
	private String convert999(int number) {
		String word1 = lOW_NAMES[number / 100] + " hundred";
		String word2 = convert99(number % 100);
		if (number <= 99) {
			return word2;
		} else if (number % 100 == 0) {
			return word1;
		} else {
			return word1 + " " + word2;
		}
	}

	/**
	 * convert 2 digits into words
	 * @param number
	 * @return
	 */
	private String convert99(int number) {
		if (number < 20) {
			return lOW_NAMES[number];
		}
		String word = TENS_NAMES[number / 10 - 2];
		if (number % 10 == 0) {
			return word;
		}
		return word + "-" + lOW_NAMES[number % 10];
	}

	/**
	 * find the decimal part of  number
	 * @param number
	 * @return decimal part of our input number
	 */
	private int decimalNumber(BigDecimal number) {
		BigDecimal decimalDigit = new BigDecimal(100);
		BigDecimal decimalTmp1= number.multiply(decimalDigit);
		BigDecimal decimalTmp2 = decimalTmp1.remainder(decimalDigit);

		int decimalPart = decimalTmp2.intValue();
		return decimalPart;
	}

	/**
	 * 
	 * @param sourceCurrency
	 * @param destinationCurrency
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws ProtocolException
	 */
	private HttpsURLConnection connection(String sourceCurrency, String destinationCurrency)
			throws IOException, MalformedURLException, ProtocolException {
		String url = "https://download.finance.yahoo.com/d/quotes.csv?e=.csv&f=sl1d1t1&s=" + sourceCurrency
				+ destinationCurrency + "=X";
		HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("GET");
		return connection;
	}
	
	public BigDecimal convert(BigDecimal amount, String sourceCurrency, String destinationCurrency) {
		double rate = 0;
				
		 if(amount.intValue()<0) {
			 amount=amount.abs();
			 logger.warn("amount was negative changed by ABS");
		 }
		try {
			HttpsURLConnection connection = connection(sourceCurrency, destinationCurrency);

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = bufferedReader.readLine()) != null) {
				response.append(inputLine);
			}
			bufferedReader.close();

			String[] responseParts = response.toString().split(",");
			rate = Double.parseDouble(responseParts[1]);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error:", e);
		}

		return amount.multiply(new BigDecimal(rate));
	}

	public String toWords(BigDecimal number) {
		int intPart = number.intValue();
		int decimaPart = decimalNumber(number);

		String strIntPart = convertNumberToWords(intPart);

		if (decimaPart == 0)
			return strIntPart;
		else {
			String strDecimalPart = convertNumberToWords(decimaPart);
			String result = strIntPart + "  point   " + strDecimalPart;
			return result;
		}
	}

}
