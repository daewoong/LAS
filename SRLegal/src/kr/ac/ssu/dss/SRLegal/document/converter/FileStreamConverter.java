package kr.ac.ssu.dss.SRLegal.document.converter;
/* 
 * Copyright (c) 1995-1998 Sun Microsystems, Inc. All Rights Reserved. 
 * 
 * Permission to use, copy, modify, and distribute this software 
 * and its documentation for NON-COMMERCIAL purposes and without 
 * fee is hereby granted provided that this copyright notice 
 * appears in all copies. Please refer to the file "copyright.html" 
 * for further important copyright and licensing information. 
 * 
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF 
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR 
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR 
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. 
 */  
   
import java.io.*;  
import java.util.*;  
   
public class FileStreamConverter {  
   
   static void writeOutput(String str) {  
   
       try {  
           FileOutputStream fos = new FileOutputStream("doc/documents/testStream.txt");  
           Writer out = new OutputStreamWriter(fos, "ASCII");  
           out.write(str);  
           out.close();  
       } catch (IOException e) {  
           e.printStackTrace();  
       }  
   }  
   
   static String readInput() {  
   
      StringBuffer buffer = new StringBuffer();  
      try {  
          FileInputStream fis = new FileInputStream("doc/documents/RDF.docx");  
          InputStreamReader isr = new InputStreamReader(fis, "ASCII");  
          Reader in = new BufferedReader(isr);  
          int ch;  
          while ((ch = in.read()) > -1) {  
             buffer.append((char)ch);  
          }  
          in.close();  
          return buffer.toString();  
      } catch (IOException e) {  
          e.printStackTrace();  
          return null;  
      }  
   }  
   
   public static void main(String[] args) {  
   
      String inputString = readInput();  
   writeOutput(inputString);  
       
   }  
   
}  