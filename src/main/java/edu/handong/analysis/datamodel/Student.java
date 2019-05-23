package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

public class Student {
	private int i=0;
	private String studentId;
	private ArrayList<Course> coursesTaken; // 학생이 들은 수업 목록
	private HashMap<String,Integer> semestersByYearAndSemester; 
	                                                         // key: Yearprivate Semester
	                                                         // e.g., 2003-1
	public Student(String studentId) {
		this.studentId=studentId;
		
	}
	public void addCourse(Course newRecord) {
		if(this.coursesTaken==null) coursesTaken=new ArrayList<Course>();
		i++;
		this.coursesTaken.add(newRecord);
	}
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		if(semestersByYearAndSemester==null) semestersByYearAndSemester=new HashMap();
		
		Iterator it = coursesTaken.listIterator();
		
		String temp;
		int prevyear=0;
		int nowyear;
		int prevSem=0;
		int nowSem;
		int i=1;
		while(it.hasNext()){
			Course temp1=(Course) it.next();
			nowyear=temp1.getyear();
			nowSem=temp1.getsemester();
			if(prevyear!=nowyear||prevSem!=nowSem) {
				temp=nowyear+"-"+nowSem;
				semestersByYearAndSemester.put(temp,i++);
			}
			 prevyear=nowyear;
			 prevSem=nowSem;
		}
		return semestersByYearAndSemester;
	}
	
	public int getNumCourseInNthSementer(int semester) {
		int num=0;
		String temp= (String) getKeyFromValue(semestersByYearAndSemester,semester);
		int year=Integer.parseInt(temp.substring(0,temp.indexOf("-")));
		int sem=Integer.parseInt(temp.substring(temp.indexOf("-")+1));
		
		for(Course object : coursesTaken) {
			if(object.getsemester()==sem&&object.getyear()==year) num++;
			
		}
		return num;
	}
	
	public static Object getKeyFromValue(HashMap<String,Integer> hm, Object value) {
		  for (Object o : hm.keySet()) {
			   if (hm.get(o).equals(value)) {
				   return o;
			   }
		  }
		  return null;
}
	/* field에 대한 getter setter 필요에 따라 추가 */
	public String getID() {
		return this.studentId;
	}
	
	public int getTotalSem() {
		int ans=0;
		for( String key : semestersByYearAndSemester.keySet() ){ 
			ans++;
		}
		return ans;
	}


}

