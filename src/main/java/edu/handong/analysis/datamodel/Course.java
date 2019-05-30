package edu.handong.analysis.datamodel;

public class Course {
	
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;

	public Course(String line){
		String[] array = line.split(",");
		this.studentId=array[0];
		this.yearMonthGraduated=array[1];
		this.firstMajor=array[2];
		this.secondMajor=array[3];
		this.courseCode=array[4];
		this.courseName=array[5];
		this.courseCredit=array[6];
		this.yearTaken= Integer.parseInt(array[7].trim());
		this.semesterCourseTaken= Integer.parseInt(array[8].trim());
	}
	public String getID() {return this.studentId;}
	
	public int getyear() {
		return this.yearTaken;
	}
	public int getsemester() {
		return this.semesterCourseTaken;
	}
	public String getYear() {
		return this.yearTaken+"-"+this.semesterCourseTaken;
	}
	public String getCode() {
		return this.courseCode;
	}
	public String getName() {
		return this.courseName;
	}
	
}
