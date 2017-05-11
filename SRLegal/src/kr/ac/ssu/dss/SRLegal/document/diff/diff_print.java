package kr.ac.ssu.dss.SRLegal.document.diff;
import java.io.IOException;


public class diff_print {
	private diff_control dif=null;
	diff_print(String from_file, String to_file) throws IOException{
		dif = new diff_control(from_file,to_file);
	}
	public void diff_print_barsket(){
		int num = dif.getNumberofDiff();
		int i;
		for(i = 0 ; i <num;i++){
			//System.out.printf("<ffl: %d~%d tfl: %d~%d %s>\n%s\n",test.get_form_file_linenum_start(i),test.get_form_file_linenum_end(i),test.get_to_file_linenum_start(i),test.get_to_file_linenum_end(i),test.getDiffStringStatus(i),test.getDiffString(i));
			if(dif.getDiffStringStatus(i).compareTo("EQUAL")==0){
				System.out.printf("%s",dif.getDiffString(i));
			}
			else if(dif.getDiffStringStatus(i).compareTo("INSERT")==0){
				System.out.printf("[[%s]]",dif.getDiffString(i));
			}
			else if(dif.getDiffStringStatus(i).compareTo("DELETE")==0){
				System.out.printf("<<%s>>",dif.getDiffString(i));
			}				
		}
	}
	public void diff_print_linenum(){
		int num = dif.getNumberofDiff();
		int i;
		for(i = 0 ; i <num;i++){
			System.out.printf("<from file line num : %d~%d to file line num : %d~%d %s>\n%s\n",dif.get_form_file_linenum_start(i),dif.get_form_file_linenum_end(i),dif.get_to_file_linenum_start(i),dif.get_to_file_linenum_end(i),dif.getDiffStringStatus(i),dif.getDiffString(i));
		}
	}
	public void diff_print_0(){
		int num = dif.getNumberofDiff();
		int i;
		for(i = 0 ; i <num;i++){
			System.out.printf("Diff(%s,\"%s\")\n",dif.getDiffStringStatus(i),dif.getDiffString(i));
		}
	}
	public void diff_control_close() throws IOException{
		dif.closeFile();
	}
	
	public void diff_print_summary(){
		int count_delete=dif.get_delete_count();
		int count_insert=dif.get_insert_count();
		
		System.out.printf("\nDiff Summary\n");
		System.out.printf("Insert : %d\n",count_insert);
		System.out.printf("Delete : %d\n",count_delete);

		
		
	}
	
	public void diff_print_delete_list(){
		int count_delete=dif.get_delete_count();	
		
		System.out.printf("\nDelete List\n");		
		for(int i = 0; i< count_delete;i++){
			System.out.printf("[%d]: %s\n", i+1,dif.get_delete_string(i));
		}
	}
	public void diff_print_insert_list(){
		int count_insert=dif.get_insert_count();
		System.out.printf("\nInsert List\n");
		
		for(int i = 0; i< count_insert;i++){
			System.out.printf("[%d]: %s\n", i+1,dif.get_insert_string(i));
		}
	}
	
	public void diff_print_summary_and_list(){
		diff_print_summary();
		diff_print_insert_list();
		diff_print_delete_list();
	}
	
	
	
}
