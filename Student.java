package gui_basic;

import java.io.Serializable;

//객체직렬화를 위해서는 Serializable 인터페이스를 구현해주어야 한다.
public class Student implements Serializable {
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", score=" + score + "]";
	}
	private String id;			//학생 id
	private String name;		//학생 이름
	private int score;			//학생 성적
	public Student(String id, String name, int score) {
		this.id = id;
		this.name = name;
		this.score = score;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public boolean equals(Object obj) {
		boolean result = true;
		if(obj instanceof Student) {
			Student stu = (Student)obj;
			return id.equals(stu.id);
		}
		return result;
	}
}
