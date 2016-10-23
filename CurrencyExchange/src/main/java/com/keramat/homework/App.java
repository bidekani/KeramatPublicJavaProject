package com.keramat.homework;

import java.math.BigDecimal;

import org.apache.log4j.Logger;


/**
 * Hello world!
 *
 */
public class App {
	final static Logger logger = Logger.getLogger(App.class);
    public static void main( String[] args ){
    	
    	
    	String testNumber = "15633.56";
    	BigDecimal number = new BigDecimal(testNumber);	
		DeltekHomework  n1 = new Convertor();
		String res1 = String.format("the words of number %s is : \n %s", testNumber,n1.toWords(number));
		System.out.println(res1);
		logger.info(res1);
    	
		System.out.format("\n*******************\n");
		
		String testAmount = "200.256";
		String sourceCurrency="EUR";
		String destinationCurrency="USD";
		
		BigDecimal rate, amount = new BigDecimal(testAmount);			
		DeltekHomework  n2 = new Convertor();
		rate=n2.convert(amount,sourceCurrency ,destinationCurrency ).setScale(3, BigDecimal.ROUND_HALF_UP);
		String res2=String.format("the exchanger rate of %s  %s is :  %s  %s ",testAmount,sourceCurrency,rate.toString(),destinationCurrency);
	 
		
		System.out.println( res2);
		logger.info(res2);

    }
}
