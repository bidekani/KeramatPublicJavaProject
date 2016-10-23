package com.keramat.homework;

	import org.junit.Test;
	import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Assert;

	public class TestJunit {
	
	private	BigDecimal number=new BigDecimal(-100);
	Convertor con=new Convertor();
	private	BigDecimal res=con.convert(new BigDecimal(100), "USD", "EUR");	
	   @Test
		public void testMinuesNumber() {
	 
			Assert.assertNotNull(res);	
		}
	   
	   @Test
		public void testConvertNumber() {

		
			Assert.assertTrue(res.intValue()<100);	
			Assert.assertTrue(res.intValue()>0);
		}
	   
	}

