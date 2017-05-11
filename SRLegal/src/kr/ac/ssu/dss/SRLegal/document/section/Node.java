package kr.ac.ssu.dss.SRLegal.document.section;
import java.util.ArrayList;
import java.util.List;


class Node {
	private String lineText;
	private int lineNumber;
	
	private List<Node> children = new ArrayList<Node>();
	
	private int level;
	private String head;
	private String body;
	
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
		this.head = LineParser.getHead(lineText);
		
		beginIndexOfHead = lineText.indexOf(head);
		endIndexOfHead = beginIndexOfHead + head.length() - 1;
		
		this.body = LineParser.getBody(lineText, head);
		
		beginIndexOfBody = lineText.indexOf(body, endIndexOfHead + 1);
		endIndexOfBody = beginIndexOfBody + body.length() - 1;
		
		//System.out.println(beginIndexOfHead + "~" + endIndexOfHead + " , " + beginIndexOfBody + "~" + endIndexOfBody);
		
		//System.out.println(lineText.substring(beginIndexOfBody, endIndexOfBody + 1));
	}
	
	String getLineText() {
		return lineText;
	}
	
	String getHead(){
		return this.head;
	}
	int getbeginIndexOfBody(){
		return beginIndexOfBody;
	}
	int getendIndexOfBody(){
		return endIndexOfBody;
	}
	String getBody(){
		return this.body;
	}
	
	int getLineNumber() {
		return lineNumber;
	}
	List getChildren(){
		return children;
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
