package com.diskoverorta.entities;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class DateEntityTest {

	@Test
	public void testGetEntities() {
		String exSentence = "It is cold in January.";
        BaseEntity ex = new DateEntity();
        System.out.println("Input Sentence : "+exSentence);
        List<String> names = ex.getEntities(exSentence);
        System.out.println("Date Names : "+ names);
		assertEquals("January", names.get(0));		
	}

}
