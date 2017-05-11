package kr.ac.ssu.dss.SRLegal.ontology.parser;

import java.io.InputStream;

import org.junit.Test;

import com.hp.hpl.jena.util.FileManager;

public class SRLegalFileManager {
	
	protected InputStream in;	
	
	protected SRLegalFileManager(){
		System.out.println("\n -- file manager -- \n");
	}
	
	protected void openFile(String inputFileName){
		// use the FileManager to find the input file
		this.in = FileManager.get().open( inputFileName );
		if (this.in == null) {
		    throw new IllegalArgumentException(
		       "File: " + inputFileName + " not found");
		}
	}
}
