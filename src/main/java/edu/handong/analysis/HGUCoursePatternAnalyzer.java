package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.lang.String.*;
import java.net.*;
import java.io.*;
import java.util.*;


import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.CourseCode;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	private HashMap<String,CourseCode> courseCode;
	String input;
	String output;
	String analysis;
	String coursecode;
	String startyear;
	String endyear;
	boolean help;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		Options options = createOptions();
		
		
		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}
		}
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		String dataPath = input; // csv file to be analyzed
		String resultPath = output; // the file path where the results are saved.
		ArrayList<String> lines = Utils.getLines(dataPath, true);

		
		if(analysis.contentEquals("1")) {	
			students = loadStudentCourseRecords(lines);
			// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
			Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
			// Generate result lines to be saved.
			ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
			Utils.writeAFile(linesToBeSaved, resultPath);
		}else if(analysis.contentEquals("2")) {
			courseCode = loadCourseCode(lines);
			Map<String, CourseCode> sortedStudents = new TreeMap<String,CourseCode>(courseCode); 
			ArrayList<String> linesToBeSaved = resultForA2(sortedStudents);
			Utils.writeAFile(linesToBeSaved, resultPath);
		}
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		
	}
	
	private HashMap<String,CourseCode> loadCourseCode(ArrayList<String> lines){
		HashMap<String,CourseCode> hm=new HashMap();
		Course ho=null;
		
		Iterator iter = lines.iterator();
        while (iter.hasNext()) {
        	String str1=(String)iter.next();
        	ho=new Course(str1);
        	if(!YearExist(hm,ho.getYear())) {
        		hm.put(ho.getYear(),new CourseCode(coursecode, ho.getYear()));
        		
        	}
        	hm.get(ho.getYear()).addCourse(ho);
        }
		
		return hm;
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
		HashMap<String,Student> hm=new HashMap();
		Course ho=null;
		
		Iterator iter = lines.iterator();
        while (iter.hasNext()) {
        	String str1=(String)iter.next();
        	ho=new Course(str1);
        	if(!studentExist(hm,ho.getID())) {
        		hm.put(ho.getID(),new Student(ho.getID()));
        		
        	}
        	hm.get(ho.getID()).addCourse(ho);
        }
		
		return hm;
	}
	private boolean YearExist(HashMap<String,CourseCode> courses, String year) {
		Collection collection = courses.values();
		Iterator iter = collection.iterator();
		while(iter.hasNext()){
			CourseCode pw=(CourseCode)iter.next();
			if(pw!=null)
				if(year.equals(pw.getYear())) return true;
		}
		
		return false;
	}
	
	private boolean studentExist(HashMap<String,Student> students, String student) {
		Collection collection = students.values();
		Iterator iter = collection.iterator();
		while(iter.hasNext()){
			Student pw=(Student)iter.next();
			if(pw!=null)
				if(student.equals(pw.getID())) return true;
		}
		
		return false;
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		ArrayList<String> ho=new ArrayList<>();
		for( String key : sortedStudents.keySet() ){ 
			Student tempStu=sortedStudents.get(key);
			Map<String,Integer> hm = new TreeMap<>(tempStu.getSemestersByYearAndSemester());
			for( String key1 : hm.keySet() ){ 
				int k=hm.get(key1);
				if(checkYear(Integer.parseInt(key1.substring(0,key1.indexOf("-")))))
					ho.add(key+","+tempStu.getTotalSem()+","+k+","+tempStu.getNumCourseInNthSementer(k));
			}
		}
		ho.add(0,"StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester");
		return ho; // do not forget to return a proper variable.
	}
	
	private ArrayList<String> resultForA2 (Map<String, CourseCode > sortedStudents){
		ArrayList<String> ho=new ArrayList<>();
		for( String key : sortedStudents.keySet() ){
			CourseCode pw=sortedStudents.get(key);
			if(checkYear(pw.getyear())) {
				int all=pw.countAll();
				int that=pw.countThatCourse();
				if(that!=0)
					ho.add(pw.getyear()+","+pw.getsem()+","+coursecode+","+pw.matchName()+","+all+","+that+","+ String.format("%.1f",((double)that / (double) all * 100.0)) + "%");
			
			}
		}
		ho.add(0,"Year,Semester,CouseCode, CourseName,TotalStudents,StudentsTaken,Rate");
		return ho;
	}
	
	//private boolean tu(anal)
	
	private Options createOptions() {
		Options options = new Options();
		
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Input path")
				.hasArg()
				.argName("Set an input file path")
				.required()
				.build());
		
		options.addOption(Option.builder("o").longOpt("output")
				.desc("Output path")
				.hasArg()
				.argName("Set an output file path")
				.required()
				.build());
		
		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("Analysis option")
				.hasArg()
				.argName("1: Count courses per semester, 2: Count per course name and year")
				.required()
				.build());
		
		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("course code")
				.hasArg()
				.argName("Course code for '-a 2' option")
				.required(false)
				.build());
		
		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Start year for analysis")
				.hasArg()
				.argName("Set the start year for analysis e.g., -s 2002")
				.required()
				.build());
		
		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("End year for analysis")
				.hasArg()
				.argName("Set the end year for analysis e.g., -e 2005")
				.required()
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
		        .desc("Help")
		        .build());
		
		return options;
	}
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
			analysis=cmd.getOptionValue("a");
			coursecode=cmd.getOptionValue("c");
			startyear=cmd.getOptionValue("s");
			endyear=cmd.getOptionValue("e");
			help = cmd.hasOption("h");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}
	
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer = "" ; // Leave this empty.

		formatter.printHelp("HGU Course Analyzer", header, options, footer, true);
	}
	
	private boolean checkYear(int i) {
		if(startyear!=null&&endyear!=null)
			if(i>Integer.parseInt(startyear)&&i<Integer.parseInt(endyear)) return true;
		return false;
	}
}

