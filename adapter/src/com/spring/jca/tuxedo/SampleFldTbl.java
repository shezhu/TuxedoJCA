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

import java.util.Hashtable;

public final class SampleFldTbl implements weblogic.wtc.jatmi.FldTbl {
	Hashtable<String, Integer> nametofieldHashTable;
	Hashtable<Integer, String> fieldtonameHashTable;

	public final static int RFV_ILOGGAD = 33555932;
	/** number: 1501 type: long */
	public final static int RFV_BATCH = 33555933;
	/** number: 1504  type: long */
	public final static int RFV_STATUS = 33555936;
	/** number: 1502 type: string */
	public final static int RFV_ANVNAMN = 167773662;

	/** number: 1006 type: string */
	public final static int FML_PERSONNUMMER = 167773166;
	
	/** number: 1158  type: short */
	public final static int FML_SRSKLDPRVN = 1158;
	
	/** number: 1003  type: string */
	public final static int FML_KUNDID = 167773163;
	
	/** number: 1157  type: string */
	public final static int FML_NAMN = 167773317;
	
	/** number: 1146  type: string */
	public final static int FML_LKNUMMER1 = 167773306;
	
	/** number: 1186  type: string */
	public final static int FML_AVLIDENDATUM = 167773346;
	
	/** number: 1505  type: string */
	public final static int RFV_MESSAGE = 167773665;
	

	public String Fldid_to_name(int fldid) {
		if (fieldtonameHashTable == null) {
			fieldtonameHashTable = new Hashtable<Integer,String>();
			
			fieldtonameHashTable.put(new Integer(RFV_ILOGGAD),
					"RFV_ILOGGAD");
			fieldtonameHashTable.put(new Integer(RFV_BATCH),
					"RFV_BATCH");
			fieldtonameHashTable.put(new Integer(RFV_STATUS),
					"RFV_STATUS");
			fieldtonameHashTable.put(new Integer(RFV_ANVNAMN),
					"RFV_ANVNAMN");
			fieldtonameHashTable.put(new Integer(FML_PERSONNUMMER),
					"FML_PERSONNUMMER");
			fieldtonameHashTable.put(new Integer(FML_SRSKLDPRVN),
					"FML_SRSKLDPRVN");
			fieldtonameHashTable.put(new Integer(FML_KUNDID),
					"FML_KUNDID");
			fieldtonameHashTable.put(new Integer(FML_NAMN),
					"FML_NAMN");
			fieldtonameHashTable.put(new Integer(FML_LKNUMMER1),
					"FML_LKNUMMER1");
			fieldtonameHashTable.put(new Integer(FML_AVLIDENDATUM),
					"FML_AVLIDENDATUM");
			fieldtonameHashTable.put(new Integer(RFV_MESSAGE),
					"RFV_MESSAGE");
		}

		return ((String) fieldtonameHashTable.get(new Integer(fldid)));
	}

	public String[] getFldNames() {
		String retval[] = new String[10];
		retval[0] = new String("RFV_ILOGGAD");
		retval[1] = new String("RFV_BATCH");
		retval[2] = new String("RFV_STATUS");
		retval[3] = new String("RFV_ANVNAMN");
		retval[4] = new String("FML_PERSONNUMMER");
		retval[5] = new String("FML_SRSKLDPRVN");
		retval[6] = new String("FML_KUNDID");
		retval[6] = new String("FML_NAMN");
		retval[7] = new String("FML_LKNUMMER1");
		retval[8] = new String("FML_AVLIDENDATUM");
		retval[9] = new String("RFV_MESSAGE");

		return retval;
	}

	@Override
	public int name_to_Fldid(String name) {
		if (nametofieldHashTable == null) {
			nametofieldHashTable = new Hashtable<String, Integer>();
			
			nametofieldHashTable.put("RFV_ILOGGAD", new Integer(
					RFV_ILOGGAD));
			nametofieldHashTable.put("RFV_BATCH", new Integer(
					RFV_BATCH));
			nametofieldHashTable.put("RFV_STATUS", new Integer(
					RFV_STATUS));
			nametofieldHashTable.put("RFV_ANVNAMN", new Integer(
					RFV_ANVNAMN));
			nametofieldHashTable.put("FML_PERSONNUMMER", new Integer(
					FML_PERSONNUMMER));
			nametofieldHashTable.put("FML_SRSKLDPRVN", new Integer(
					FML_SRSKLDPRVN));
			nametofieldHashTable.put("FML_KUNDID", new Integer(
					FML_KUNDID));
			nametofieldHashTable.put("FML_NAMN", new Integer(
					FML_NAMN));
			nametofieldHashTable.put("FML_LKNUMMER1", new Integer(
					FML_LKNUMMER1));
			nametofieldHashTable.put("FML_AVLIDENDATUM", new Integer(
					FML_AVLIDENDATUM));
			nametofieldHashTable.put("RFV_MESSAGE", new Integer(
					RFV_MESSAGE));
		}

		Integer fld = (Integer) nametofieldHashTable.get(name);
		if (fld == null) {
			return (-1);
		} else {
			return (fld.intValue());
		}
	}
}
