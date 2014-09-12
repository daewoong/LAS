package kr.ac.ssu.dss.SRLegal.ontology.parser;

import java.util.Vector;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;


public class SRLegalStatements{
	
	private Statement stmt;
	private Resource subject;
	private Property predicate;
	private RDFNode object;
	
	private Vector<String> vStatement;
	private Vector<String> vSubject;
	private Vector<String> vPredicate;
	private Vector<String> vObject;
		
	private String nameSpaceS;
	private String nameSpaceP;
	private String nameSpaceO;
	
	protected SRLegalStatements(){
		
		this.vStatement = new Vector<String>();
		this.vSubject = new Vector<String>();
		this.vPredicate = new Vector<String>();
		this.vObject = new Vector<String>();	
	}
	
	protected void splitStatement(Model model){
					
			String anonynous = "";
			String nonURIValue;
			int anonynousCount = 0;
			int nameSpaceLength = 0;

		    // list the statements in the Model
			StmtIterator iter = model.listStatements();			
			
			// print out the predicate, subject and object of each statement and added vector 
			while (iter.hasNext()) {
			    this.stmt      = iter.nextStatement();  	 // get next statement
			    this.subject   = this.stmt.getSubject();     // get the subject
			    this.predicate = this.stmt.getPredicate();   // get the predicate
			    this.object    = this.stmt.getObject();      // get the object
			    
			    //added statement
			    this.vStatement.add(this.stmt.toString());
			    System.out.println("\nstatement: " + this.stmt.toString());
			    
			    //added subject
			    if(!this.subject.isAnon()){
			    	//System.out.println("subject: " + this.subject.toString());	
			    	this.nameSpaceS = this.subject.getNameSpace();
			    	nameSpaceLength = this.nameSpaceS.length();
			    	nonURIValue = this.subject.getURI().substring(nameSpaceLength);
				    this.vSubject.add(nonURIValue);
				    
				    //System.out.println("subject: " + this.nonURIValue);		   
			    }
			    //subject is a anonynous node
			    else{		    			    	
			    	anonynous = "anonynous" + anonynousCount++;		    	
			    	this.vSubject.add(anonynous);
			    }
			    
			    //predicate
			    //System.out.println("  predicate: " + this.predicate.toString() + " ");
			    
			    //added nonURIPredicate
			    this.nameSpaceP = this.predicate.getNameSpace();			    
			    nameSpaceLength = this.nameSpaceP.length();
			    nonURIValue = this.predicate.getURI().substring(nameSpaceLength);
			    this.vPredicate.add(nonURIValue);
			    //System.out.println("  predicate: " + this.nonURIValue + " ");
			    
			    //added object
			    if (this.object instanceof Resource && !this.object.isAnon()) {
			       
			       //added nonURIObject
			       this.nameSpaceO = this.object.asResource().getNameSpace();
				   nameSpaceLength = this.nameSpaceO.length();
				   nonURIValue = this.object.asResource().getURI().substring(nameSpaceLength); 
				   
			       this.vObject.add(nonURIValue);
			       //System.out.print("  object: " + this.nonURIValue);
			       
			    } else {		    	
			    	// object is a literal
			    	if(this.object.isLiteral()){			   
				    	Literal literal = this.object.asLiteral();			    				        
				        //System.out.println("  object: \"" + literal.getValue() + "\"");
				        String convertLiteral = String.valueOf(literal.getValue());
				        this.vObject.add(convertLiteral);
			    	}   
			    	//object is a anonyous node
				    else{ 			    	
				    	anonynous = "anonyous" + anonynousCount++;	
				    	this.vObject.add(anonynous);
				    	
				    }
			    }	
			} 
	}
	
	/**
	 * @return the vStatement
	 */
	public Vector<String> getvStatement() {
		return vStatement;
	}

	/**
	 * @param vStatement the vStatement to set
	 */
	public void setvStatement(Vector<String> vStatement) {
		this.vStatement = vStatement;
	}

	/**
	 * @return the vSubject
	 */
	public Vector<String> getvSubject() {
		return vSubject;
	}

	/**
	 * @param vSubject the vSubject to set
	 */
	public void setvSubject(Vector<String> vSubject) {
		this.vSubject = vSubject;
	}

	/**
	 * @return the vPredicate
	 */
	public Vector<String> getvPredicate() {
		return vPredicate;
	}

	/**
	 * @param vPredicate the vPredicate to set
	 */
	public void setvPredicate(Vector<String> vPredicate) {
		this.vPredicate = vPredicate;
	}

	/**
	 * @return the vObject
	 */
	public Vector<String> getvObject() {
		return vObject;
	}

	/**
	 * @param vObject the vObject to set
	 */
	public void setvObject(Vector<String> vObject) {
		this.vObject = vObject;
	}		

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public Resource getSubject() {
		return subject;
	}

	public void setSubject(Resource subject) {
		this.subject = subject;
	}

	public Property getPredicate() {
		return predicate;
	}

	public void setPredicate(Property predicate) {
		this.predicate = predicate;
	}

	public RDFNode getObject() {
		return object;
	}

	public void setObject(RDFNode object) {
		this.object = object;
	}
	
	/**
	 * @return the nameSpace
	 */
	public String getNameSpace() {
		return nameSpaceS;
	}

	/**
	 * @param nameSpace the nameSpace to set
	 */
	public void setNameSpace(String nameSpace) {
		this.nameSpaceS = nameSpace;
	}

	/**
	 * @return the nameSpaceP
	 */
	public String getNameSpaceP() {
		return nameSpaceP;
	}

	/**
	 * @param nameSpaceP the nameSpaceP to set
	 */
	public void setNameSpaceP(String nameSpaceP) {
		this.nameSpaceP = nameSpaceP;
	}

	/**
	 * @return the nameSpaceO
	 */
	public String getNameSpaceO() {
		return nameSpaceO;
	}

	/**
	 * @param nameSpaceO the nameSpaceO to set
	 */
	public void setNameSpaceO(String nameSpaceO) {
		this.nameSpaceO = nameSpaceO;
	}
	
}

