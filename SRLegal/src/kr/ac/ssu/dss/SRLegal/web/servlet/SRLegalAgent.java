package kr.ac.ssu.dss.SRLegal.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.ssu.dss.SRLegal.ontology.parser.SRLegalParserGateway;
import kr.ac.ssu.dss.SRLegal.ontology.parser.SRLegalStatements;
import kr.ac.ssu.dss.SRLegal.ontology.query.SRLegalQuery;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Servlet implementation class SNavAgent
 */
public class SRLegalAgent extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Vector<String> statement;
    private Vector<String> subject;
    private Vector<String> predicate;
    private Vector<String> object;  
    
    private String keyword;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SRLegalAgent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache"); 
		response.setContentType("text/html;charset=UTF-8"); 
		
		keyword = request.getParameter("search");			
		String callBack = request.getParameter("callback");		
		
		System.out.println("receive keyword: ===" + keyword + "===");
			    
	    //Query process.
	    if(keyword != null){
	    	
	    	keyword = URLDecoder.decode(keyword, "UTF-8");
	    	System.out.println("\nquery keyword : ===" + keyword + "===\n");
	    	
	    	SRLegalQuery query = new SRLegalQuery(keyword); 	    	
			this.subject = query.getvSubject();
			this.predicate = query.getvPredicate();
			this.object = query.getvObject();
			
	        createJSon(response,keyword,callBack);		        
			
	    }else{
	    		    	
	    	System.out.println("\ndefault keyword : ===" + keyword + "===\n");
	    	
			//call to rdf resources.
	    	SRLegalParserGateway parserGateway = new SRLegalParserGateway();
			parserGateway.processingPaser();
			SRLegalStatements sStmt = parserGateway.getStatements();
		    
		    this.statement = sStmt.getvStatement();
		    this.subject = sStmt.getvSubject();
		    this.predicate = sStmt.getvPredicate();
		    this.object = sStmt.getvObject();
		    
	    	createJSon(response,callBack);
	    }
	    
	}
	
	private void createJSon(HttpServletResponse response, String callBack){
		
		//created json type   
		JSONObject obj = new JSONObject();
		
		try {	
		   obj.put("subject", this.subject);
		   obj.put("predicate", this.predicate);
		   obj.put("object", this.object);
		   
		} catch (JSONException e) {
		   e.printStackTrace();
		}	
		
		//write to web browser. 
		PrintWriter out = null;

		try {	
			out = response.getWriter();
			String JSonType = obj.toString();							
			out.print(callBack + "(" + JSonType + ")");
			System.out.println(callBack + "(" + JSonType + ")");
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}finally{
			out.flush();
			out.close();
		}
		
	}
	
	private void createJSon(HttpServletResponse response, String keyword, String callBack){
		
		//created jason type   
		JSONObject obj = new JSONObject();
		
		try {	
		   obj.put("keyword", keyword);
		   obj.put("subject", this.subject);
		   obj.put("predicate", this.predicate);
		   obj.put("object", this.object);
		   obj.put("count", this.subject.size());
		   
		} catch (JSONException e) {
		   e.printStackTrace();
		}	
		
		//write to web browser. 
		PrintWriter out = null;
		
		try {
			out = response.getWriter();			
			String JSonType = obj.toString();							
			out.println(callBack + "(" + JSonType + ")");		
			System.out.println(callBack + "(" + JSonType + ")");
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}finally{
			out.flush();
			out.close();
		}	
	}
}