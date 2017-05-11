package kr.ac.ssu.dss.SRLegal.document.converter;


import java.net.FileNameMap;
import java.net.URLConnection;
import javax.activation.MimetypesFileTypeMap;
import java.io.File;


public class FileParsing {

	public static void getMimeType(String fileUrl) throws java.io.IOException {
	    FileNameMap fileNameMap = URLConnection.getFileNameMap();
	    String type = fileNameMap.getContentTypeFor(fileUrl);
	    System.out.println(type);
	 	   	  
	}
	
	public static void main(String args[]) throws Exception {
		
	    FileParsing.getMimeType("doc/create.pdf");
	    File f = new File("doc/create.pdf");
	    System.out.println(f.getName());

	}
}