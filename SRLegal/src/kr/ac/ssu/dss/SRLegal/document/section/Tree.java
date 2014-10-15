package kr.ac.ssu.dss.SRLegal.document.section;

class Tree {
	
	private static String[] rulePatterns = {"목적","정의","위원회","계획",
											"신고","벌칙","과태료","시행일"};
	
	private Node root; //level 1
	
	Tree() {
		root = new Node(1);
	}
	
	void addNode(Node node) {

		Node parent = getNodeToBeParent(node.getLevel());
		
		//child add 
		parent.addChild(node);
	}
	
	private Node getNodeToBeParent(int levelOfChild) {
		Node parent = root;
		
		int repetition = levelOfChild - 5;
		
		while (repetition > 0) {
			parent = parent.getTheLastChild();
			repetition--;
		}
		
		return parent;
	}
	
	public String toString() {
		StringBuffer strBuf = new StringBuffer();		
		
		for (int i = 0; i < root.getChildCount(); i++) {
			Node child = root.getChildAt(i);
						
			strBuf.append(child.toString() + "\n");
			
			//traverseChildren(child, strBuf);
		}
		
		return strBuf.toString();
	}
	
	
	public void traversingChildren(){
	
		for (int i = 0; i < root.getChildCount(); i++) {
			Node child = root.getChildAt(i);
			//System.out.println("index:" + i + " body:" + child.getBody());
			rulePatternCheck(child, i);
		
		}
	}
	
	private void rulePatternCheck(Node child, int index){
		
		String head = child.getHead();
		
		for(String rPattern : rulePatterns){
			boolean matched = head.matches(".*" + rPattern + ".*");
			if(matched){
				System.out.println("rulePattern: " + rPattern + "(" + matched + ")");				
				int levelOfChild = child.getLevel();				
				System.out.println("Level:" +  levelOfChild);	
				
				int childlenLevel = levelOfChild + 1;				
				System.out.println("index: " + index);
				
				traverseChildren(child, childlenLevel, index);				
				
				break;
			}
		}			
	}
	
	
	private void traverseChildren(Node matchedChild, int childlenLevel, int index) {
		
		for (int i = index; i < root.getChildCount(); i++) {
			Node child = root.getChildAt(i);
			int j = i; 
			j = i+1;
			Node nextChild = root.getChildAt(j);
			
			System.out.println("funcLevel:" + child.getLevel() + " body:" + child.getBody());
			if(matchedChild.getLevel() == child.getLevel() && child.getLevel() == nextChild.getLevel()){
				System.out.println("break");
				break;
			}else{	
				if(child.getLevel() > nextChild.getLevel()){
					break;
				}else{
					int levelOfChild = child.getLevel();
				
					if(levelOfChild == childlenLevel){					
						//System.out.println(child.getBody());	
						LegalRulePattern.rulePatternApplication(child.getBody());
					}
				}
			}		
		}
	}
	
	private void traverseChildren(Node parent, StringBuffer strBuf) {

		for (int i = 0; i < parent.getChildCount(); i++) {
			Node child = parent.getChildAt(i);
			
			int levelOfChild = child.getLevel();						
			int repetition = levelOfChild - 2;
			
			while (repetition > 0) {
				strBuf.append('\t');
				
				repetition--;
			}
			
			strBuf.append(child.toString() + "\n");
			
			traverseChildren(child, strBuf);
		}
	}
}
