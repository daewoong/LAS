package kr.ac.ssu.dss.SRLegal.document.section;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class DocHierarchyExtractor {
	private static String[] patterns = {
										"제\\d+장",
										"제\\d+조.+",
										"[①-⑮]", 
										"\\d+\\.",   // 1. or 10. 
										"[가-하]\\." // 가. to 하.
										};
	
	private static List<String> priorities = new ArrayList<String>();
	private static Tree tree = new Tree(); 
				
	private static LineNumberReader openFile(String pathname) {
		LineNumberReader lnr = null;
		
		try {
			FileInputStream fis = new FileInputStream(pathname);
			byte[] BOM = new byte[4];
			fis.read(BOM, 0, 4);
			
			String charsetName = determineCharsetName(BOM);
			lnr = new LineNumberReader(new InputStreamReader(fis, charsetName));

		} catch (IOException e) {
			e.printStackTrace();
		}

		return lnr;
	}
	
	private static void extractHierarchy(LineNumberReader reader) {
		int lineNumber = 0;
		String lineText = null;

		try {
			for (;;) {
				lineNumber = reader.getLineNumber();
				lineText = reader.readLine();//System.out.println(lineText);

				if(lineText == null) 
					break;
				
				if (lineText.trim().length() == 0)
					continue;
				
				analyzeLine(lineText, lineNumber);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void analyzeLine(String lineText, int lineNumber) {
		String head = LineParser.getHead(lineText);//System.out.println("1st: " + head);
		String body = LineParser.getBody(lineText, head);
		
		if (head == null)
			return;
		
	
		for (String aPattern : patterns) {
			boolean matched = Pattern.matches(aPattern, head);
			
			if (matched) {//System.out.println(head);
				addToPriorities(aPattern);
					
				System.out.println("pattern: "+aPattern);
				
				int level = getPriorityIndex(aPattern) + 2;
				
				System.out.println("head: "+ head);
				System.out.println("body: "+ body);
				//System.out.println(lineText +" : "+ lineNumber + " : " + level);
				addToTree(lineText, lineNumber, level);
				
				break;
			}
		}
	}
	
	private static void addToPriorities(String newPattern) {
		if (!priorities.contains(newPattern))
			priorities.add(newPattern);
	}
	
	private static void printHierarchy() {
		System.out.println(tree.toString());
	}
	
	private static void addToTree(String lineText, int lineNumber, int level) {
		Node node = new Node(lineText, lineNumber, level);		
		tree.addNode(node);
	
	}
	
	// An index begins at 0.
	private static int getPriorityIndex(String aPattern) {
		return priorities.indexOf(aPattern);
	}
	
	private static String determineCharsetName(byte[] BOM) {
		String charsetName = null;
		
		if ((BOM[0] & 0xFF) == 0xEF && (BOM[1] & 0xFF) == 0xBB && (BOM[2] & 0xFF) == 0xBF)
			charsetName = "UTF-8";
		else if ((BOM[0] & 0xFF) == 0xFE && (BOM[1] & 0xFF) == 0xFF)
			charsetName = "UTF-16BE";
		else if((BOM[0] & 0xFF) == 0xFF && (BOM[1] & 0xFF) == 0xFE)
			charsetName = "UTF-16LE";
		else if((BOM[0] & 0xFF) == 0x00 && (BOM[1] & 0xFF) == 0x00 && (BOM[0] & 0xFF) == 0xFE && (BOM[1] & 0xFF) == 0xFF)
			charsetName = "UTF-32BE";
		else if((BOM[0] & 0xFF) == 0xFF && (BOM[1] & 0xFF) == 0xFE && (BOM[0] & 0xFF) == 0x00 && (BOM[1] & 0xFF) == 0x00)
			charsetName = "UTF-32LE";
		else
			charsetName = "EUC-KR";
		
		return charsetName;
	}
	
	public static void main(String[] args) {
		
		LineNumberReader reader = openFile("doc/Law/law1.txt");
		extractHierarchy(reader);
		printHierarchy();
	}
}
