package edu.handong.analysis.datamodel;

import java.util.*;


public class CourseCode {
	private int year;
	private int semester;
	private String Code;
	private String Name;
	private int thatYearPeo=0;
	private int coursePeo=0;
	
	private int i=0;
	private ArrayList<Course> coursesTaken; // 이 년도에 개설된 강의 목록 
	private HashMap<String,Integer> peopleResister; 
	
	public CourseCode(String code, String year) {
		this.year=Integer.parseInt(year.substring(0,year.indexOf("-")));
		this.semester=Integer.parseInt(year.substring(year.indexOf("-")+1));
		this.Code=code;
	}
	
	public void addCourse(Course newRecord) {
		if(this.coursesTaken==null) coursesTaken=new ArrayList<Course>();
		i++;
		this.coursesTaken.add(newRecord);
	}

	
	public int countAll() {
		if(peopleResister==null) peopleResister=new HashMap();
		
		Iterator it = coursesTaken.listIterator();
		
		String temp;
		
		while(it.hasNext()){
			Course temp1=(Course) it.next();
			if(!peopleResister.containsKey(temp1.getID())) {
				peopleResister.put(temp1.getID(),thatYearPeo);
				thatYearPeo++;
			}
		}
		return thatYearPeo;
	}
	public int countThatCourse() {
		
		Iterator it = coursesTaken.listIterator();
		
		String temp;
		
		while(it.hasNext()){
			Course temp1=(Course) it.next();
			if(temp1.getCode().trim().equals(this.Code.trim())) {
				//System.out.println(temp1.getCode());
				this.coursePeo++;
			}
		}
		return this.coursePeo;
	}
	public String matchName() {
		for(Course object : coursesTaken) {
			if(object.getCode().trim().equals(Code)) Name=object.getName();
		}
		return this.Name;
	}
	/* field에 대한 getter setter 필요에 따라 추가 */
	public String getYear() {
		return this.year+"-"+this.semester;
	}
	
	public int getyear() {
		return this.year;
	}

	public int getsem() {
		return this.semester;
	}
}
