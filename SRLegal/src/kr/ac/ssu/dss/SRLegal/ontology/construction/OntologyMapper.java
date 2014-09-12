package kr.ac.ssu.dss.SRLegal.ontology.construction;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

public class OntologyMapper {
	
	String IRIString = "http://example.com/owlapi/law";
	
	OWLOntologyManager manager;
	IRI ontologyIRI;
	OWLOntology ontology;
	OWLDataFactory factory;


	public OntologyMapper(){
		System.out.println("Ontology Mapper");
	}

	public void createOntology(){
		
		this.manager = OWLManager.createOWLOntologyManager();
		
	    this.ontologyIRI = IRI.create(this.IRIString);  
	    this.factory = manager.getOWLDataFactory();
	    
	    try {
			this.ontology = manager.createOntology(ontologyIRI);			
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}	    	    
	    
	}
	
	//Set IRI
	public void setIRIString(String IRIString){
		this.IRIString = IRIString;
	}
	

	private void printOntology(){
		
		System.out.println("RDF/XML: \n");
		 
		try {
			this.manager.saveOntology(this.ontology, new StreamDocumentTarget(System.out));
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}
	     
	}

	private void saveFile(){
		
		//Create a file for the new format
		File fileformated = new File("legalOntology.owl");
		//Save the ontology in a different format
		OWLOntologyFormat format = manager.getOntologyFormat(ontology);
//		OWLXMLOntologyFormat owlxmlFormat = new OWLXMLOntologyFormat();
		RDFXMLOntologyFormat rdfxmlFormat = new RDFXMLOntologyFormat();
		
		if (format.isPrefixOWLOntologyFormat()) { 
			rdfxmlFormat.copyPrefixesFrom(format.asPrefixOWLOntologyFormat()); 
		}
		try {
			manager.saveOntology(ontology, rdfxmlFormat, IRI.create(fileformated.toURI()));
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setNamedClass(String namedClass){
		
		OWLClass owlClass = this.factory.getOWLClass(IRI
				.create(this.ontologyIRI+ "#" + namedClass));		        	
		
	}
	
	public void setSubClassOf(String parentClass, String childClass){
		
		OWLClass cParent = this.factory.getOWLClass(IRI
				.create(this.ontologyIRI+ "#" + parentClass));	
		
		OWLClass cChild = this.factory.getOWLClass(IRI
				.create(this.ontologyIRI+ "#" + childClass));	
		
		OWLAxiom axiom = factory.getOWLSubClassOfAxiom(cChild, cParent);
        AddAxiom addAxiom = new AddAxiom(this.ontology, axiom);
       
        this.manager.applyChange(addAxiom);
        
        for (OWLClass cls : ontology.getClassesInSignature()) {
            System.out.println("Referenced class: " + cls);
        }
        
//        Set<OWLClassExpression> superClasses = cParent.getSuperClasses(ontology);
//        System.out.println("Asserted superclasses of " + cParent + ":");
//        for (OWLClassExpression desc : superClasses) {
//            System.out.println(desc);
//        }
	}
	
	public void setObjectProperty(String objectProperty){
		
		OWLObjectProperty hasOProperty = this.factory.getOWLObjectProperty(IRI
	            .create(this.ontologyIRI + "#" + objectProperty));
	}
	
	
	public void setDatatypeProperty(String dataTypePorperty){
		
		OWLDataProperty hasDProperty = this.factory.getOWLDataProperty(IRI
		        .create(this.ontologyIRI + "#" + dataTypePorperty));
	}
	
	public void setIndividual(String individualString){
		
		  OWLIndividual individual = this.factory.getOWLNamedIndividual(IRI
	                .create(this.ontologyIRI + "#" + individualString));
		  
	}

	public void setClassAxiom(String hasIndividualClass, String individualString){
		
		  OWLClass iClass = this.factory.getOWLClass(IRI
				.create(this.ontologyIRI+ "#" + hasIndividualClass));	
		
		  OWLIndividual individual = this.factory.getOWLNamedIndividual(IRI
	                .create(this.ontologyIRI + "#" + individualString));
		  
		  OWLClassAssertionAxiom classAssertion = this.factory
	                .getOWLClassAssertionAxiom(iClass, individual);
		  
		  this.manager.addAxiom(this.ontology, classAssertion);
		  
		  for (OWLNamedIndividual ind : ontology.getIndividualsInSignature()) {
	            System.out.println("Referenced Individual: " + ind);
	        }		  
		  
	}
	
	public void testClass() throws OWLOntologyCreationException, OWLOntologyStorageException{
		
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	        OWLDataFactory dataFactory = manager.getOWLDataFactory();
	        // The IRIs used here are taken from the OWL 2 Primer
	        String base = "http://example.com/owl/families/";
	        PrefixManager pm = new DefaultPrefixManager(base);
	        // Get the reference to the :Person class (the full IRI will be
	        // <http://example.com/owl/families/Person>)
	        OWLClass person = dataFactory.getOWLClass(":Person", pm);
	        // Get the reference to the :Mary class (the full IRI will be
	        // <http://example.com/owl/families/Mary>)
	        OWLNamedIndividual mary = dataFactory
	                .getOWLNamedIndividual(":Mary", pm);
	        // Now create a ClassAssertion to specify that :Mary is an instance of
	        // :Person
	        OWLClassAssertionAxiom classAssertion = dataFactory
	                .getOWLClassAssertionAxiom(person, mary);
	        // We need to add the class assertion to the ontology that we want
	        // specify that :Mary is a :Person
	        OWLOntology ontology = manager.createOntology(IRI.create(base));
	        // Add the class assertion
	      //  manager.addAxiom(ontology, classAssertion);
	        // Dump the ontology to stdout
	        manager.saveOntology(ontology, new StreamDocumentTarget(
	        		System.out));
	        
	      
	}
	
	public static void main(String args[]){
		
		OntologyMapper OM = new OntologyMapper();
		OM.createOntology();
		OM.setSubClassOf("parent", "tom");
		OM.setSubClassOf("parent", "mary");
		OM.setClassAxiom("parent", "gerrard");
		OM.setClassAxiom("parent", "lampard");
		OM.printOntology();
		OM.saveFile();
		
	}
}
