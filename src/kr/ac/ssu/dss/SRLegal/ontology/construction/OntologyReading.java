package kr.ac.ssu.dss.SRLegal.ontology.construction;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OntologyReading {

	
	
	public OntologyReading(){

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
 
        File file = new File("doc/ontology/owl/Korea_Decree.owl");
        
        // Now load the local copy
        OWLOntology localOntology;
		try {
			localOntology = manager.loadOntologyFromOntologyDocument(file);
			System.out.println("Loaded ontology: " + localOntology);
		        
	        IRI documentIRI = manager.getOntologyDocumentIRI(localOntology);
	        System.out.println("    from: " + documentIRI);
	        
	        for (OWLClass cls : localOntology.getClassesInSignature()) {
	            System.out.println(cls);
	        }

	        
	        //create individual
	        OWLDataFactory factory = manager.getOWLDataFactory();
	        
	        OWLIndividual john = factory.getOWLNamedIndividual(IRI
	                .create(documentIRI + "#John"));
	        OWLIndividual mary = factory.getOWLNamedIndividual(IRI
	                .create(documentIRI + "#Mary"));
	        
	        OWLClass person = factory.getOWLClass(IRI.create(documentIRI
	                + "#Person"));
	        
	        OWLClassAssertionAxiom classAssertionAx = factory
	                .getOWLClassAssertionAxiom(person, john);
	        
	        OWLClassAssertionAxiom classAssertionAx1 = factory
	                .getOWLClassAssertionAxiom(person, mary);
	        
	        manager.addAxiom(localOntology, classAssertionAx);
	        manager.addAxiom(localOntology, classAssertionAx1);
	        
	        
	        System.out.println("=======\n");
	        System.out.println("Number of individuals: "
	                + localOntology.getIndividualsInSignature().size());
	       
	        for (OWLNamedIndividual ind : localOntology.getIndividualsInSignature()) {
	            System.out.println(ind);
	        }
	        
	        
	      
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
 
       
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new OntologyReading();
	}

}
