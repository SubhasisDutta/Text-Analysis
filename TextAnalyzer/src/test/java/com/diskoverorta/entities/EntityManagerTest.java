package com.diskoverorta.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class EntityManagerTest {

	@Test
	public void testGetALLDocumentEntitiesINJSON() {
		String str = "Indiaâ€™s indigenously developed nuclear capable sub-sonic cruise missile â€˜Nirbhayâ€™, which can strike targets more than 700 km away, was on Friday test-fired from a test range at Chandipur in Odisha.â€œThe missile was test-fired from a mobile launcher positioned at launch pad 3 of the Integrated Test Range at about 10.03 hours,â€� said an official soon after the flight took off from the launch ground.â€œFlight details will be available after data retrieved from radars and telemetry points, monitoring the trajectories, are analysed,â€� the official said. It is the second test of the sub-sonic long range cruise missile â€˜Nirbhayâ€™ from the ITR. The maiden flight, conducted on March 12, 2013 could not achieve all the desired parameters as â€œthe flight had to be terminated mid-way when deviations were observed from its intended course,â€� sources said. India has in its arsenal the 290-km range supersonic â€œBrahMosâ€� cruise missile which is jointly developed by India and Russia. But â€˜Nirbhayâ€™ with long range capability is a different kind of missile being developed by the Defence Research and Development Organisation (DRDO).";
	    EntityManager temp = new EntityManager();
	    System.out.println(temp.getALLDocumentEntitiesINJSON(str));
		assertEquals("", "");
	}

	@Test
	public void testGetALLEntitiesForDocument() {
		
	}

	@Test
	public void testGetSelectedEntitiesForSentence() {
		
	}

}
