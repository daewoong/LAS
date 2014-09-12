package kr.ac.ssu.dss.SRLegal.ontology.parser;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class SRLegalReading extends SRLegalFileManager{
	
	// create an empty model
	private Model model;
	
	protected SRLegalReading(){
		this.model = ModelFactory.createDefaultModel();
	}
	
	// read the RDF/XML file
	protected void modelRead(){
		this.model.read(in, null);
		System.out.println("\n -- model read finished --\n");
	}
	
	// write it to standard out
	protected void modelWrite(){
		this.model.write(System.out);
		System.out.println("\n -- model write finished --\n");
		
	}
	
	protected Model getModel(){return model;}
	protected void setModel(Model model){this.model = model;}
	
}
