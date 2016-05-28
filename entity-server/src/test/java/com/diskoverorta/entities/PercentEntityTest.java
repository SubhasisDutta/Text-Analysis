package com.diskoverorta.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class PercentEntityTest {

	@Test
	public void testGetEntities() {
		PercentEntity temp = new PercentEntity();
        System.out.println(temp.getEntities("Barack Obama is the president of USA"));
		//fail("Not yet implemented");
        assertEquals("", "");
	}

}
