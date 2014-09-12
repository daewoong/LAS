package kr.ac.ssu.dss.SRLegal.document.section;

class LineParser {
	
	private static final String SPACE = " ";
	private static final String LEFT_PARENTHESIS = "\u0028";
	private static final String RIGHT_PARENTHESIS = "\u0029";
	
	static String getHead(String lineText) {
		String head = null;
		
		lineText = lineText.trim();

		if (lineText.contains(SPACE))
			head = lineText.substring(0, lineText.indexOf(SPACE));
		else
			head = lineText;
		
		if (head.contains(LEFT_PARENTHESIS) && !head.contains(RIGHT_PARENTHESIS))
			head = lineText.substring(0, lineText.lastIndexOf(RIGHT_PARENTHESIS) + 1);
		
		return head;
	}
	
	static String getBody(String lineText, String head) {
		String body = null;
		
		int beginIndexOfHead = lineText.indexOf(head);
		
		int beginIndexOfBody = beginIndexOfHead + head.length();
		
		body = lineText.substring(beginIndexOfBody).trim();//System.out.println("Body[" + body + "]");
		
		return body;
	}
}
