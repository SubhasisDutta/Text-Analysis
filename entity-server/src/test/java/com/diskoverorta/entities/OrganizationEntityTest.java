package com.diskoverorta.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class OrganizationEntityTest {

	@Test
	public void testGetEntities() {
		OrganizationEntity temp = new OrganizationEntity();
        System.out.println(temp.getEntities("Barack Obama is the president of USA"));
        assertEquals("", "");
	}

}
