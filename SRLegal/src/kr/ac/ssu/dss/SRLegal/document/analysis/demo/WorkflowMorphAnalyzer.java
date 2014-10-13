/*  Copyright 2010, 2011 Semantic Web Research Center, KAIST

This file is part of JHanNanum.

JHanNanum is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

JHanNanum is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with JHanNanum.  If not, see <http://www.gnu.org/licenses/>   */

package kr.ac.ssu.dss.SRLegal.document.analysis.demo;

import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.hannanum.WorkflowFactory;

/**
 * This is a demo program of HanNanum that helps users to utilize the HanNanum library easily.
 * It uses a predefined work flow for morphological analysis, which generates all possible
 * morphological analysis results per given eojeol.<br>
 * <br>
 * It performs morphological analysis for a Korean document with the following procedure:<br>
 * 		1. Create a predefined work flow for morphological analysis.<br>
 * 		2. Activate the work flow in multi-thread mode.<br>
 * 		3. Analyze a document that consists of several sentences.<br>
 * 		4. Print the result on the console.<br>
 * 		5. Repeats the procedure 3~4 with activated work flow.<br>
 * 		6. Close the work flow.<br>
 * 
 * @author Sangwon Park (hudoni@world.kaist.ac.kr), CILab, SWRC, KAIST
 */
public class WorkflowMorphAnalyzer {

	public static void main(String[] args) {
		Workflow workflow = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_MORPH_ANALYZER);
		
		try {
			/* Activate the work flow in the thread mode */
			workflow.activateWorkflow(true);
			
			/* Analysis using the work flow */
			//String document = "1.cable 꽃의 종류\n" 
			//	+	"프로젝트 전체 회의.\n"
			//	+ "회의 일정은 다음과 같습니다.\n";
			String document = "선희는 현우를 일으켜 세워 옷의 먼지를 털어 주었다.";
			workflow.analyze(document);
			System.out.println(workflow.getResultOfDocument());
			
			/* Once a work flow is activated, it can be used repeatedly. */
			document = "日時: 2010년 7월 30일 오후 1시\n"
				+ "場所: Coex Conference Room\n";
			
			workflow.analyze(document);
			System.out.println(workflow.getResultOfDocument());
			
			workflow.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		/* Shutdown the work flow */
		workflow.close();  	
	}
}