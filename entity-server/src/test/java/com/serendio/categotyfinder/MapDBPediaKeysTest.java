package com.serendio.categotyfinder;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class MapDBPediaKeysTest {

	@Test
	public void testGetMapedCategory() {
		//fail("Not yet implemented");
		assertEquals("", "");
	}

	@Test
	public void testGetMappingCategories() {
		MapDBPediaKeys m= new MapDBPediaKeys();
		List<String> cats= m.getMappingCategories();
		for(String cat:cats){
			System.out.println(cat);
		}
		assertEquals("","");
	}

}
