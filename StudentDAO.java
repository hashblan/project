package gui_basic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class StudentDAO {
	private Scanner scan;
	private ArrayList<Student> al;		//ArrayList에 무한 데이터 입력
	
	private String id;					//학생 ID
	private String name;				//등록할 학생 이름
	private int score;					//등록할 학생 성적
	public StudentDAO() {
		scan = new Scanner(System.in);
		al = new ArrayList<Student>();
	}
//	등록 시 점수는 0-100 사이의 값이 등록되고
//	이 외의 경우 예외처리
//	저장하기
//	불러오기
	public boolean checkIdExists(String id_T) {
	    for (int i = 0; i < al.size(); i++) {
	        Student student = al.get(i);
	        if (id_T.equals(student.getId())) {
	            return true; // ID가 등록되어 있는 경우 true 반환
	        }
	    }
	    return false; // ID가 등록되어 있지 않은 경우 false 반환
	}
	public boolean check() {
		if(al.size() >= 2) {
			return true;
		}
		return false;
	}
//	1. 등록
	public boolean create(String id, String name, int score) {	
		if(id.length()!=7)
			return false;
		else if (score > 100 || score < 0) {
	        return false;
	    }
		else if(id.matches("[a-zA-Z가-힣]+")) {
			return false;
		}
		else if(!name.matches("[a-zA-Z가-힣]+")) {
			return false;
		}
		for(int i=0;i<al.size();i++) {
			Student s = al.get(i);
			if(id.equals(s.getId())) {
				return false;
			}
		}
		Student stu = new Student(id, name, score);
		al.add(stu);
		return true;
	}
//	2. 조회
	public String read() {
		String str="";
		for(int i=0;i<al.size();i++) {
			str = str + "ID : " + al.get(i).getId() + " 이름 : " + al.get(i).getName() + " 성적 : " + al.get(i).getScore() + "\n";
		}
		return str;
	}
//	3. 수정
	public Student getStudentById(String id2) {
		for (Student student : al) {
	        if (student.getId().equals(id2)) {
	            return student;
	        }
	    }
	    return null; // 해당하는 ID의 학생이 없는 경우
	}
//	public void update(String id) {
////		 for(int i=0;i<al.size();i++) {
////			 Student stu = al.get(i);
////			 if(id.equals(stu.getId())) {
////				 al.remove(i);
//////				 create(id, name, score);
////			 }
////			 else {
////				System.out.println("수정할 ID가 없습니다.");
////				return;
////			}
////		 }
////		 System.out.println("수정할 내용을 다시 입력하세요.");
////		 create(id, name, score);
//	}
//	4. 삭제
	public void delete(String id) {
		 boolean found = false;
		 int tempIndex = 0;
		 for(int i=0;i<al.size();i++) {
			 Student stu = al.get(i);
			 if(id.equals(stu.getId())) {
				 found = true;
				 tempIndex = i;
			 }
		 }
		 if(found) {
			 al.remove(tempIndex);}
		return;
	}
//	public boolean checkIdExists(String id_T) {
//	    for (int i = 0; i < al.size(); i++) {
//	        Student student = al.get(i);
//	        if (id_T.equals(student.getId())) {
//	            return true; // ID가 등록되어 있는 경우 true 반환
//	        }
//	    }
//	    return false; // ID가 등록되어 있지 않은 경우 false 반환
//	}
	
//	6. 정렬
	class asc_id_compare implements Comparator<Student> {
        @Override
        public int compare(Student stu1, Student stu2) {
            return Integer.compare(Integer.parseInt(stu1.getId()),Integer.parseInt(stu2.getId()));
        }
    }
//	ID(학번) 내림차순 정렬
    class desc_id_compare implements Comparator<Student>{

        @Override
        public int compare(Student stu1, Student stu2) {
            return Integer.compare(Integer.parseInt(stu2.getId()),Integer.parseInt(stu1.getId()));
        }
    }

    class asc_score_compare implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return Integer.compare(o1.getScore(),o2.getScore());
        }
    }
    class desc_score_compare implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            return Integer.compare(o2.getScore(),o1.getScore());
        }
    }
    class asc_name_compare implements Comparator<Student>{

        @Override
        public int compare(Student o1, Student o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
    class desc_name_compare implements Comparator<Student>{

        @Override
        public int compare(Student o1, Student o2) {
            return o2.getName().compareTo(o1.getName());
        }
    }
//  정렬
    public boolean sortAsc_ID(){
        Collections.sort(al,new asc_id_compare());
        return true;
    }
    public boolean sortDesc_ID(){
        Collections.sort(al,new desc_id_compare());
        return true;
    }
    public boolean sortAsc_score(){
        Collections.sort(al,new asc_score_compare());
        return true;
    }
    public boolean sortDesc_score(){
        Collections.sort(al,new desc_score_compare());
        return true;
    }
    public boolean sortAsc_name(){
        Collections.sort(al,new asc_name_compare());
        return true;
    }

    public boolean sortDesc_name(){
        Collections.sort(al,new desc_name_compare());
        return true;
    }
}
