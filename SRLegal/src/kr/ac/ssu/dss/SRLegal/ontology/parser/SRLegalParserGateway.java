package kr.ac.ssu.dss.SRLegal.ontology.parser;

import com.hp.hpl.jena.rdf.model.Model;

public class SRLegalParserGateway {
	
	
	private String filePath = SRLegalFileList.LKIFCORE;
	private Model model;
	private SRLegalStatements statements;
	
	public SRLegalParserGateway(){
		System.out.println("SRLegalPaserGateway");
		
	}
	
	public void processingPaser(){
		
		SRLegalFileManager fileManager = new SRLegalFileManager();
		fileManager.openFile(this.filePath);
		
		SRLegalReading reading = new SRLegalReading();
		reading.openFile(this.filePath);
		reading.modelRead();
		this.model = reading.getModel();
		
		System.out.println("\n -- statement size: " +  this.model.size() + " -- \n");	
		
		this.statements = new SRLegalStatements();
		this.statements.splitStatement(this.model);
			
	}
		
	public void setFilePath(String filePath){this.filePath = filePath;}
	public Model getModel(){return this.model;}
	public SRLegalStatements getStatements(){return this.statements;}
	
}
