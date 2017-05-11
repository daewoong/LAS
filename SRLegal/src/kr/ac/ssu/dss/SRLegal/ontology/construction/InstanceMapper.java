package kr.ac.ssu.dss.SRLegal.ontology.construction;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class InstanceMapper {

	
	String IRIString = "http://dss.ssu.ac.kr/complete/ontologies/2014/8/Korea_Decree/";
	public String FilePath = "doc/ontology/owl/Korea_Decree.owl";
	
	OWLOntologyManager manager;
	IRI ontologyIRI;
	IRI defaultIRI;
	OWLOntology ontology;
	OWLDataFactory factory;


	public InstanceMapper(){
		System.out.println("Instance Mapper");
		this.manager = OWLManager.createOWLOntologyManager();
		this.factory = this.manager.getOWLDataFactory();
	}
	
	public void loadingOntology(String FilePath){
		
		File file = new File(FilePath);	
				
		try {
			this.ontology = manager.loadOntologyFromOntologyDocument(file);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
		System.out.println("Loaded ontology: " + this.ontology);
		        
	    this.defaultIRI = manager.getOntologyDocumentIRI(this.ontology);
	    System.out.println("    from: " + this.defaultIRI);			  	
	} 
	        
	       
	public void createURI(String LawName){
		
	    this.ontologyIRI = IRI.create(this.IRIString + LawName);  	 	    
	}
	
	public void printClassList(){
		
		int classNum = 0;
		
		for (OWLClass cls : this.ontology.getClassesInSignature()){
            System.out.println(cls);  
            classNum++;
        }	
		
		System.out.println("=======");	
		System.out.println("Number of Class : " + classNum);		
	    System.out.println("Number of Individuals: "
	                + this.ontology.getIndividualsInSignature().size());
	}
	
	public void createInstance(String instance){
		
		createURI("super");
        OWLIndividual ins = factory.getOWLNamedIndividual(IRI
                .create(this.ontologyIRI + "#" + instance));  
        
        //mapping instance
        instanceMapping(ins);
        
	}
	
	private void instanceMapping(OWLIndividual ins){
	
        //Act (법률)
        OWLClass act = this.factory.getOWLClass(IRI
				.create(this.defaultIRI + "#Act"));
        
        OWLClassAssertionAxiom classAssertionAx = this.factory
                .getOWLClassAssertionAxiom(act, ins);
               
        this.manager.addAxiom(this.ontology, classAssertionAx);   
        
	}
	    
	public void printOntologyRDFXML(){
	    try {
			this.manager.saveOntology(this.ontology, new StreamDocumentTarget(
					System.out));
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InstanceMapper IM = new InstanceMapper();
		IM.loadingOntology(IM.FilePath);
		
		IM.printClassList();
		IM.createInstance("SuperComputing");
		
		IM.printOntologyRDFXML();
		
	}

}
