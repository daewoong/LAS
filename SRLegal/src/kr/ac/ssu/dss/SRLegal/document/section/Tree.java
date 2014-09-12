package kr.ac.ssu.dss.SRLegal.document.section;
class Tree {
	private Node root; //level 1
	
	Tree() {
		root = new Node(1);
	}
	
	void addNode(Node node) {

		Node parent = getNodeToBeParent(node.getLevel());
		
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
			
			traverseChildren(child, strBuf);
		}
		
		return strBuf.toString();
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
