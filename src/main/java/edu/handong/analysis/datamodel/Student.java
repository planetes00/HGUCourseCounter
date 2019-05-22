package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	
	private String studentId;
	private ArrayList<Course> coursesTaken; // 학생이 들은 수업 목록
	private HashMap<String,Integer> semestersByYearAndSemester; 
	                                                         // key: Yearprivate Semester
	                                                         // e.g., 2003private 1, 
	public Student(String studentId) {
		// constructor
	}
	public void addCourse(Course newRecord) {
		
	}
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		
	}
	public int getNumCourseInNthSementer(int semester) {
		
	}
	/* field에 대한 getter setter 필요에 따라 추가 */

}
