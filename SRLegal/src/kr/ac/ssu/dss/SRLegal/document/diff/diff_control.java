package kr.ac.ssu.dss.SRLegal.document.diff;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class diff_control {
	private File in1 = null;
	private FileReader in1R = null;
	private File in2 = null;
	private FileReader in2R = null;
	private String from_file_String = null;
	private String to_file_String = null;

	private diff_match_patch a = null;
	private LinkedList<diff_match_patch.Diff> diff_string = null;
	
	private int form_file_linenum_start[]=null;
	private int form_file_linenum_end[]=null;
	private int to_file_linenum_start[]=null;
	private int to_file_linenum_end[]=null;
	
	private LinkedList<String> insert_string = null;
	private LinkedList<String> delete_string = null;
	

	
	private void set_file(String from_file, String to_file) throws IOException{
		in1 = new File(from_file);
		in1R = new FileReader(in1);

		char[] tmp1 = new char[(int) in1.length()];

		in1R.read(tmp1);

		in2 = new File(to_file);
		in2R = new FileReader(in2);

		char[] tmp2 = new char[(int) in2.length()];

		in2R.read(tmp2);

		from_file_String = new String(tmp1);
		to_file_String = new String(tmp2);

		a = new diff_match_patch();
		
		diff_string = a.diff_main(from_file_String, to_file_String);
		
		
		
		//한글 파일 diff_string 마지막 두개에 \0이 많이 들어감.. 원인 불명..
		//강제로 빼주기
		if((int)diff_string.get(diff_string.size()-2).text.charAt(0) == 0){
			diff_string.removeLast();
			diff_string.removeLast();		
		}
		
		//줄 세기
		cal_line_num();
		
		//요약 하기
		insert_string = new LinkedList<String>();
		delete_string = new LinkedList<String>();
		summary_diff();
		
	}

	
	public diff_control(String from_file, String to_file) throws IOException {
		set_file(from_file,to_file);
	}
	
	
	public void change_diff_file(String from_file, String to_file) throws IOException{
			in1R.close();
			in2R.close();
			set_file(from_file,to_file);
	}	
	
	
	private void cal_line_num(){
		int count_diff = diff_string.size();
		int index_enter;
		diff_match_patch.Operation abcde;
		int from_file_linenum =1;
		int to_file_linenum =1;
		form_file_linenum_start = new int[count_diff];
		form_file_linenum_end=new int[count_diff];
		to_file_linenum_start=new int[count_diff];
		to_file_linenum_end=new int[count_diff];		
		index_enter = 0;
		for(int i=0; i < count_diff ; i++)
		{
			index_enter = diff_string.get(i).text.indexOf('\n', 0);
			form_file_linenum_start[i] = from_file_linenum;
			to_file_linenum_start[i] = to_file_linenum;
			while(index_enter != -1)
			{
				abcde =diff_string.get(i).operation ;
				if(abcde == diff_match_patch.Operation.DELETE){
					from_file_linenum++;
				}
				else if(abcde== diff_match_patch.Operation.EQUAL){
					from_file_linenum++;
					to_file_linenum++;
				}
				else{
					to_file_linenum++;
				}
				index_enter = diff_string.get(i).text.indexOf('\n', index_enter+1);
			}
			
			form_file_linenum_end[i] = from_file_linenum;
			to_file_linenum_end[i] = to_file_linenum;			
			
			
		}
		
	}
	
	public int get_form_file_linenum_start(int i){
		if(i< diff_string.size())
			return form_file_linenum_start[i];
		else
			return -1;
	}
	
	public int get_form_file_linenum_end(int i){
		if(i< diff_string.size())
			return form_file_linenum_end[i];
		else
			return -1;
	}
	public int get_to_file_linenum_start(int i){
		
		if(i< diff_string.size())
			return to_file_linenum_start[i];
		else
			return -1;
	}
	
	public int get_to_file_linenum_end(int i){
		if(i< diff_string.size())
			return to_file_linenum_end[i];
		else
			return -1;
	}
	public int getNumberofDiff(){
		return diff_string.size();		
	}
	
	public String getDiffString(int index){
		if(index < diff_string.size())
			return diff_string.get(index).text;//.replace('\n', '┘');
		else
			return new String("Wrong index");
	}
	
	public String getDiffStringStatus(int index){
		if(index < diff_string.size()){
			diff_match_patch.Operation abcde =diff_string.get(index).operation ;
			if(abcde == diff_match_patch.Operation.DELETE){
				return new String("DELETE");
			}
			else if(abcde== diff_match_patch.Operation.EQUAL){
				return new String("EQUAL");
			}
			else{
				return new String("INSERT");		
			}
		}
		else{
			return new String("Wrong index");	
		}
		
	}
	
	public void closeFile() throws IOException{
		in1R.close();
		in2R.close();
	}
	
	private void summary_diff(){
		int count_diff = diff_string.size();
		diff_match_patch.Operation abcde;
		
		for(int i=0; i < count_diff ; i++)
		{
			abcde =diff_string.get(i).operation ;
			if(abcde == diff_match_patch.Operation.DELETE ){
				delete_string.add(diff_string.get(i).text);
			}
			else if(abcde == diff_match_patch.Operation.INSERT){
				insert_string.add(diff_string.get(i).text);
			}
		}		
	}
	
	public int get_delete_count(){
		return delete_string.size();
	}
	
	public int get_insert_count(){
		return insert_string.size();
	}
	
	public String get_delete_string(int i){
		return delete_string.get(i);
	}
	
	public String get_insert_string(int i){
		return insert_string.get(i);
	}
	
}
