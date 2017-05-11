package kr.ac.ssu.dss.SRLegal.demo;

import kr.ac.ssu.dss.SRLegal.ontology.parser.SRLegalParserGateway;

public class SRLegalMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SRLegalParserGateway parserGateway = new SRLegalParserGateway();
		parserGateway.processingPaser();		
	}
}
