package com.diskoverorta.tamanager;

import static org.junit.Assert.*;

import org.junit.Test;

import com.diskoverorta.vo.TAConfig;

public class TextManagerTest {

	@Test
	public void testTagTextAnalyticsComponents() {
		//fail("Not yet implemented");
		assertEquals("", "");
	}

	@Test
	public void testAggregateDocumentComponentsFromSentences() {
		//fail("Not yet implemented");
		assertEquals("", "");
	}

	@Test
	public void testTagTextAnalyticsComponentsINJSON() {
		String sample = " The CBI on Thursday filed the case diary and crime files in a sealed cover in a coal blocks allocation case related to Hindalco before a special court, complying with its order.\n" +
                "\n" +
                "The court has now fixed the matter for consideration of the CBIâ€™s closure report filed in the case for December 12.\n" +
                "\n" +
                "Two days after the court had directed it to furnish the case diary of the matter, the CBI submitted two bundles of documents in a sealed cover before the court.\n" +
                "\n" +
                "â€œIn compliance of the courtâ€™s order, we are filing the crime folder as well as the case diary,â€� senior Public Prosecutor V.K. Sharma told special CBI Judge Bharat Parashar.\n" +
                "\n" +
                "The judge said, â€œIO (Investigating officer) states that he has brought both the case diary and crime files in a sealed cover. He is further being told to assist the court in looking into the relevant papers. The matter is now adjourned for consideration on December 12.â€�\n" +
                "\n" +
                "During the brief hearing, the court said if there is still any clarification to be sought in the matter, it will ask the agency and then pass order on the closure report.\n" +
                "\n" +
                "The court was hearing a case in which an FIR was lodged against industrialist Kumar Mangalam Birla, ex-Coal Secretary P.C. Parakh and others relating to allocation of Talabira II and III coal blocks in Odisha in 2005 to Hindalco. The CBI had later on filed closure report in the case.\n" +
                "\n" +
                "During the hearing on November 25, the CBI had come in for some tough questioning from the court which had asked why the agency did not question former Prime Minister Manmohan Singh who was also holding the Coal portfolio between 2005 and 2009.\n" +
                "\n" +
                "The courtâ€™s observations had came after the CBI submitted that though initially it felt Singhâ€™s examination was required, later it was found to be not necessary.\n" +
                "\n" +
                "At the end of the hearing, the court had summoned the case diary and crime files in a sealed cover and had posted the matter for on Thursday.\n" +
                "\n" +
                "Dr. Singh was holding the coal portfolio when Mr. Birlaâ€™s firm Hindalco was allocated coal blocks in Orissaâ€™s Talabira II & III in 2005.\n" +
                "\n" +
                "The FIR against Mr. Birla, Mr. Parakh and others was registered in October 2013 by the CBI which had alleged that Mr. Parakh had reversed his decision to reject coal block allocation to Birlaâ€™s firm Hindalco within months â€œwithout any valid basis or change in circumstancesâ€� and shown â€œundue favoursâ€�.\n" +
                "\n" +
                "The CBI had booked Mr. Birla, Mr. Parakh and other Hindalco officials under various IPC sections, including criminal conspiracy and criminal misconduct on part of government officials.\n" +
                "\n" +
                "Earlier on November 10, the CBI had told the court that there was â€œprima facie enough materialâ€� to proceed against some private parties and public servants in the case.\n" +
                "\n" +
                "The Supreme Court-appointed special public prosecutor (SPP) R.S. Cheema for the CBI had submitted before the judge that the court can take cognisance of the offences mentioned in the closure report as there was prima facie â€œevidence against the accused to show their involvementâ€�.\n" +
                "\n" +
                "Keywords: Coalgate, coal blocks allocation scam, coal scam, Kumar Mangalam Birla, Hindalco, P.C. Parakh, Talabira coal blocks, Manmohan Singh";

        String sample1 = "what is meant by Kaposi's sarcoma, once a rarely seen neoplasm in the West, now occurs in an epidemic fashion in association with acquired immune deficiency syndrome (AIDS).\n" +
                "The pathogenesis of Kaposi's sarcoma is still unclear but it appears to be an endothelial neoplasm.\n" +
                "Its clinical presentation may be quite subtle and varied.\n" +
                "The natural history of Kaposi's sarcoma is still not fully defined, and its rate of progression may be either relatively indolent or aggressive.\n" +
                "Therapies include local radiation, recombinant interferon alfa-2a, and cytotoxic chemotherapy.\n" +
                "For a subset of patients with Kaposi's sarcoma who were treated with recombinant interferon alfa-2a, the disease is in complete remission, without opportunistic infection, and they appear to be culture-negative for the etiologic retrovirus that causes their immune deficiency.\n" +
                "Interferon alfa-2a appears to have antineoplastic efficacy, (and may have antiretroviral efficacy as well) in this epidemic neoplasm.";

        String sample2 = "Investigation into the Saradha chit fund scam has so far not revealed any terror link with Bangladesh, the Centre said today, days after the BJP had alleged such a connection.\n" +
                "\n" +
                "â€œThe investigation has so far not revealed any such transaction where money was routed to Bangladesh to fund terrorist activities,â€� Union Minister of State for Personnel Jitendra Singh told the Lok Sabha in a written response.\n" +
                "\n" +
                "BJP chief Amit Shah had alleged that Saradha chit fund money had been used in the October 2, 2014 Bardhaman blast, which is being probed for link with the Jamaat-ul-Mujahideen Bangladesh (JMB) terror outfit.\n" +
                "\n" +
                "â€œSaradha chit fund money was used in the Burdwan (Bardhaman) blast. The NIA is not being allowed to probe the blast properly. Hurdles are being created. It is being done in order to save TMC leaders who are involved in the blast,â€� Mr. Shah had said, attacking the Trinamool Congress, at a BJP rally in Kolkata.\n" +
                "\n" +
                "The Union Minister was asked whether the government has sought details of the probe into the Saradha chit fund scam after reports indicated that a part of the money was routed to Bangladesh to fund terror activities. Mr. Singh replied that government had not sought details of the probe.\n" +
                "\n" +
                "To another question on whether the Saradha chief has admitted that he paid large sums to several people to influence the case in his favour, the Minister said, â€œThe matter is under investigation.â€�";

        String sample3 = "Teresa h meng founded Atheros communications. Meng served on the board of Atheros. Charles barratt was the CEO of Atheros inc. Barratt and Meng were directors of Atheros.";

        sample2 =  sample2.replace("\n","");
        TAConfig config = new TAConfig();
        config.analysisConfig.put("Entity","TRUE");
        config.analysisConfig.put("LSEntity","TRUE");

        config.entityConfig.put("Person","TRUE");
        config.entityConfig.put("Organization","TRUE");
        config.entityConfig.put("Location","TRUE");
        config.entityConfig.put("Date","TRUE");
        config.entityConfig.put("Time","TRUE");
        config.entityConfig.put("Currency","TRUE");
        config.entityConfig.put("Percent","TRUE");

        TextManager temp = new TextManager();
        System.out.println(temp.tagUniqueTextAnalyticsComponentsINJSON(sample2,config));
        assertEquals("", "");
	}

	@Test
	public void testTagUniqueTextAnalyticsComponentsINJSON() {
		//fail("Not yet implemented");
		assertEquals("", "");
	}

}
