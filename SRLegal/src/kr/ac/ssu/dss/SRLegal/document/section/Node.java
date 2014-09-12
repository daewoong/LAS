package kr.ac.ssu.dss.SRLegal.document.section;
import java.util.ArrayList;
import java.util.List;


class Node {
	private String lineText;
	private int lineNumber;
	
	private List<Node> children = new ArrayList<Node>();
	
	private int level;
	
	private int beginIndexOfHead;
	private int endIndexOfHead;
	private int beginIndexOfBody;
	private int endIndexOfBody;
	
	Node(int level) {
		this.level = level;
	}
	
	Node(String lineText, int lineNumber, int level) {
		this(level);
		this.lineText = lineText;
		this.lineNumber = lineNumber;

		initIndices(lineText);
	}
	
	private void initIndices(String lineText) {
		String head = LineParser.getHead(lineText);
		
		beginIndexOfHead = lineText.indexOf(head);
		endIndexOfHead = beginIndexOfHead + head.length() - 1;
		
		String body = LineParser.getBody(lineText, head);
		
		beginIndexOfBody = lineText.indexOf(body, endIndexOfHead + 1);
		endIndexOfBody = beginIndexOfBody + body.length() - 1;
		
		//System.out.println(beginIndexOfHead + "~" + endIndexOfHead + " , " + beginIndexOfBody + "~" + endIndexOfBody);
		
		//System.out.println(lineText.substring(beginIndexOfBody, endIndexOfBody + 1));
	}
	
	String getLineText() {
		return lineText;
	}
	
	int getLineNumber() {
		return lineNumber;
	}
	
	boolean addChild(Node child) {
		return children.add(child);
	}
	
	Node getTheLastChild() {	
		return children.get(children.size()-1);	
	}
		
	int getChildCount() {
		return children.size();
	}
	
	Node getChildAt(int index) {
		return children.get(index);
	}
	
	public String toString() {
		return lineText;
	}
	
	int getLevel() {
		return level;
	}
}
