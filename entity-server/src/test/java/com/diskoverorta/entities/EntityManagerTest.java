package com.diskoverorta.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class EntityManagerTest {

	@Test
	public void testGetALLDocumentEntitiesINJSON() {
		String str = "Indiaâ€™s indigenously developed nuclear capable sub-sonic cruise missile â€˜Nirbhayâ€™, "
				+ "which can strike targets more than 700 km away, was on Friday test-fired from a test range at "
				+ "Chandipur in Odisha.â€œThe missile was test-fired from a mobile launcher positioned at launch "
				+ "pad 3 of the Integrated Test Range at about 10.03 hours,â€� said an official soon after the "
				+ "flight took off from the launch ground.â€œFlight details will be available after data retrieved "
				+ "from radars and telemetry points, monitoring the trajectories, are analysed,â€� the official said. "
				+ "It is the second test of the sub-sonic long range cruise missile â€˜Nirbhayâ€™ from the ITR. "
				+ "The maiden flight, conducted on March 12, 2013 could not achieve all the desired parameters as "
				+ "â€œthe flight had to be terminated mid-way when deviations were observed from its intended course,"
				+ "â€� sources said. India has in its arsenal the 290-km range supersonic â€œBrahMosâ€� cruise missile "
				+ "which is jointly developed by India and Russia. But â€˜Nirbhayâ€™ with long range capability is a "
				+ "ifferent kind of missile being developed by the "
				+ "Defence Research and Development Organisation (DRDO).American Special Operations forces in eastern Syria killed a top "
				+ "Islamic State commander this week, Pentagon officials said Friday, part of a monthslong campaign the Obama administration "
				+ "boasts is eviscerating the Islamic State even as the group"
				+ " continues to demonstrate the power to sow violence in Western Europe.Senator Ted Cruz may have urged "
				+ "Donald J. Trump to leave his wife, Heidi, “the hell alone,” but there is one group that is quietly hoping"
				+ " Mr. Trump’s attacks on his rival’s spouse and other women will continue indefinitely: Democrats."
				+ "As Hillary Clinton turns her attention to a general election campaign, Mr. Trump’s nasty skirmish with "
				+ "Mr. Cruz, including his warning to “spill the beans” about Mrs. Cruz, without offering specifics, "
				+ "and his reposting of a message that mocked her looks, have played into a crucial Democratic strategy "
				+ "to defeat Mr. Trump in November: to portray him as an unabashed sexist. Mrs. Clinton’s allies hope to "
				+ "sway suburban and independent women, who will play an outsize role in deciding the fall election, to "
				+ "support her candidacy by pushing this theme. These Democrats say the matchup would be historic: "
				+ "one pitting the first female nominee of a major political party against a rival who has repeatedly "
				+ "dismissed and disparaged women and their looks. I want Donald Trump to talk every single day for the"
				+ "rest of this election,” said Representative Debbie Wasserman Schultz of Florida, the chairwoman of the"
				+ " Democratic National Committee. “He just needs to keep spewing what he has been spewing. Although "
				+ "Mrs. Clinton will present herself as a protector of women, the political strategy is more about math "
				+ "than morality. Mr. Trump has shown a particular weakness among female voters, who favored "
				+ "Mrs. Clinton 55 percent to 35 percent in a New York Times/CBS News poll released this week, "
				+ "twice the gender gap of the 2012 presidential election, when President Obama defeated Mitt Romney."
				+ " And 31 percent of Republican women said they would be upset if Mr. Trump were the party’s nominee,"
				+ "according to the most recent CNN/ORC poll.";
	    EntityManager temp = new EntityManager();
	    System.out.println(temp.getALLDocumentEntitiesINJSON(str));
		assertEquals("", "");
	}

	@Test
	public void testGetALLEntitiesForDocument() {
		assertEquals("", "");
	}

	@Test
	public void testGetSelectedEntitiesForSentence() {
		assertEquals("", "");
	}

}
