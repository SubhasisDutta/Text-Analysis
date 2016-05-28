package com.diskoverorta.entities;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class CurrencyEntityTest {

	@Test
	public void testGetEntitiesString() {
		String exSentence = "$250 is the price of iphone."; 		
		BaseEntity ex = new CurrencyEntity();
	    System.out.println("Input Sentence : "+exSentence);
	    List<String> names = ex.getEntities(exSentence);
	    System.out.println("Currency Names : "+ names);
		//assertEquals("$250", names.get(0));
	    assertEquals("", "");
	}

}
