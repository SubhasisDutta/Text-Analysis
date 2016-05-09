package com.diskoverorta.entities;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class PersonEntityTest {

	@Test
	public void testGetEntitiesString() {
		String exSentence = "Barak Obama is the president of America.";
        BaseEntity ex = new PersonEntity();
        System.out.println("Input Sentence : "+exSentence);
        List<String> names = ex.getEntities(exSentence);
        System.out.println("Person Names : "+ names);
		assertEquals("Barak Obama", names.get(0));		
	}
}
