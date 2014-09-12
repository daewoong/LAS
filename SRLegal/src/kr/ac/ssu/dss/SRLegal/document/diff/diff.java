package kr.ac.ssu.dss.SRLegal.document.diff;
import java.io.IOException;

public class diff {
	public static void main(String[] args){
		try {
			
			diff_print test = new diff_print(diff_file.from_file5,diff_file.to_file5);
			
			test.diff_print_barsket(); //수정된 모드 (괄호로 표시) insert [[ ]] delete << >>
			
			//test.diff_print_linenum(); //라인번호 출력
			//test.diff_print_0(); //초기모드
			
			test.diff_print_summary_and_list();
			
			test.diff_control_close(); //파일 닫기	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
