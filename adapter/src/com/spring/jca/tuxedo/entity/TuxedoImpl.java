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
package com.spring.jca.tuxedo.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.resource.cci.InteractionSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jca.cci.core.CciTemplate;

import com.spring.jca.tuxedo.SampleFldTbl;
import weblogic.wtc.jatmi.Ferror;
import weblogic.wtc.jatmi.FmlKey;
import weblogic.wtc.jatmi.TypedFML32;

import com.oracle.tuxedo.adapter.cci.TuxedoFML32Record;
import com.oracle.tuxedo.adapter.cci.TuxedoInteractionSpec;

public class TuxedoImpl implements Tuxedo {

	@Autowired
	CciTemplate template;

	SampleFldTbl sft = new SampleFldTbl();

	private Map<String, Object> parseRequest(String request) {
		Map<String, Object> results = new HashMap<String, Object>();

		String[] fields = request.split(",");
		for (String field : fields) {
			String[] tuple = field.split("=");

			String key = tuple[0].trim();
			String value = tuple[1].trim();

			if (value.startsWith("int:"))
				results.put(key,
						Integer.parseInt(value.substring(4, value.length())));
			else
				results.put(key, value);
		}

		return results;
	}

	private String getFunctionName(Map<String, Object> fields) {
		return (String) fields.get("SRVCNM");
	}

	private TuxedoFML32Record getInRecord(Map<String, Object> fields)
			throws Ferror {
		TypedFML32 in = new TypedFML32(sft);

		for (Map.Entry<String, Object> entry : fields.entrySet())
			if (!entry.getKey().matches("SRVCNM"))
				in.Fchg(sft.name_to_Fldid(entry.getKey()), 0, entry.getValue());

		return new TuxedoFML32Record(in);
	}

	private String parseOutput(TuxedoFML32Record recordOut) throws Ferror {
		TypedFML32 returnValue = recordOut.getFML32();

		String results = null;

		Iterator<?> fields = returnValue.Fiterator();
		while (fields.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<FmlKey, Object> tuple = (Map.Entry<FmlKey, Object>) fields
					.next();

			String key = sft.Fldid_to_name(tuple.getKey().get_fldid());
			String value = String.valueOf(returnValue.Fget(tuple.getKey()
					.get_fldid(), 0));

			results = results == null ? String.format("%s=%s", key, value)
					: String.format("%s,%s=%s", results, key, value);

		}

		return results;
	}

	public String execute(String request) {
		try {
			Map<String, Object> fields = parseRequest(request);

			TuxedoInteractionSpec spec = new TuxedoInteractionSpec();
			spec.setFunctionName(getFunctionName(fields));
			spec.setInteractionVerb(InteractionSpec.SYNC_SEND_RECEIVE);

			TypedFML32 out = new TypedFML32(sft);
			TuxedoFML32Record recordOut = new TuxedoFML32Record(out);

			template.execute(spec, getInRecord(fields), recordOut);

			String results = parseOutput(recordOut);
			System.out.println(results);

			return results;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
