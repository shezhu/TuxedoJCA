/**
   Copyright 2013 Matthew Young

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.spring.jca.tuxedo;

import javax.resource.ResourceException;
import javax.resource.cci.InteractionSpec;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jca.cci.core.CciTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.oracle.tuxedo.adapter.cci.TuxedoFML32Record;
import com.oracle.tuxedo.adapter.cci.TuxedoInteractionSpec;

import com.spring.jca.tuxedo.ApplicationConfig;
import weblogic.wtc.jatmi.Ferror;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TypedFML32;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class }, loader = AnnotationConfigContextLoader.class)
public class Sample {
	@Autowired
	CciTemplate template;

	@Test
	public void levereraPerson() throws ResourceException, Ferror {
		try {
			TuxedoInteractionSpec spec = new TuxedoInteractionSpec();
			spec.setFunctionName("levereraPerson");
			spec.setInteractionVerb(InteractionSpec.SYNC_SEND_RECEIVE);

			SampleFldTbl sft = new SampleFldTbl();

			TypedFML32 in = new TypedFML32(sft);

			in.Fchg(sft.name_to_Fldid("RFV_ANVNAMN"), 0, "40042841");
			in.Fchg(sft.name_to_Fldid("RFV_ILOGGAD"), 0, 10);
			in.Fchg(sft.name_to_Fldid("RFV_BATCH"), 0, 1);
			in.Fchg(sft.name_to_Fldid("FML_PERSONNUMMER"), 0, "193801010269");

			TuxedoFML32Record recordIn = new TuxedoFML32Record(in);

			TypedFML32 out = new TypedFML32(sft);
			TuxedoFML32Record recordOut = new TuxedoFML32Record(out);

			template.execute(spec, recordIn, recordOut);

			TypedFML32 returnValue = recordOut.getFML32();
			int kundidID = sft.name_to_Fldid("FML_KUNDID");
			String kundid = (String) returnValue.Fget(kundidID, 0);

			System.out.println(kundid);
		} catch (TPException e) {
			System.out.println(e.getMessage());
		}
	}
}
