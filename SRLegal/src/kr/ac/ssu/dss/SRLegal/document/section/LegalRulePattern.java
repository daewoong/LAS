package kr.ac.ssu.dss.SRLegal.document.section;

import java.util.Vector;

import kr.ac.ssu.dss.SRLegal.ontology.construction.InstanceMapper;

public class LegalRulePattern {

	private static String[] definePattern = {"이란","란","은","는"};
	private static String[] planPattern = {"장관","년마다","계획"};
	private static Vector<String> vSubject = new Vector<String>();
	private static Vector<String> vObject = new Vector<String>();
	
	public LegalRulePattern(){
		
	}
	
	static void rulePatternApplication(String body){
		
		System.out.println(body);
		String[] subjectObject = null;
		int i=0; 
		
		for(String dPattern : definePattern){
			boolean mached = body.matches(".*" + dPattern + ".*");
			if(mached){
				System.out.println("definePattern: " + dPattern + "(" + mached + ")");
				int index = body.indexOf(dPattern);	
				int dPatternLength = dPattern.length();
				
				String subject = body.substring(0,index);
				String object = body.substring(index+dPatternLength, body.length()-1);				
				subject = subject.replaceAll("\"", "");
				
				System.out.println("Subject: " + subject);
				System.out.println("Object: " + object);
				
				vSubject.add(subject);
				vObject.add(object);				
				//subjectObject = body.split(dPattern, 2);						
				break;
			}
		}
		
		for(String pPattern : planPattern){
			boolean mached = body.matches(".*" + pPattern + ".*");
			if(mached){
				System.out.println("planPattern: " + pPattern + "(" + mached + ")");
				int index = body.indexOf(pPattern);	
				
				String str = body.substring(0,index);
				
				System.out.println("plan : " + str);
					
				break;
			}
		}

//		for(int j = 0; j<=vSubject.size(); j++){
//			System.out.println("Subject List: "+ vSubject.get(j));
//		}
		
//		int i = 0;		
//		for(String result: subjectObject){
//			result = result.replaceAll("\"", "");						
//			System.out.println(result);			
//			
//		}
	}
	
	
	
}
