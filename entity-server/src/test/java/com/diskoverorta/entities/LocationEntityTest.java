package com.diskoverorta.entities;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class LocationEntityTest {

	@Test
	public void testGetEntities() {
		String exSentence = "Barak Obama is the president of America.";
        BaseEntity ex = new LocationEntity();
        System.out.println("Input Sentence : "+exSentence);
        List<String> names = ex.getEntities(exSentence);
        System.out.println("Location Names : "+ names);
		assertEquals("America", names.get(0));		
	}

}
