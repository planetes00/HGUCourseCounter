package edu.handong.analysis.utils;

import java.util.ArrayList;
import java.io.*;

public class Utils {
	
	public static ArrayList<String[]> getLines(String file, boolean removeHeader){
		ArrayList<String[]> locationList = new ArrayList<String[]>();
		
		String line = null;

		File locationFile = new File(file);
		BufferedReader in=null;

		try {

			in = new BufferedReader(new FileReader(locationFile));
			
			while( (line = in.readLine()) != null) {

				String[] array = line.split(",");

				locationList.add(array);
				
				

			}
		}catch (Exception e) {

			e.printStackTrace();

		}finally{
			try{
				if(in != null)
					in.close();
			}catch(Exception ex){}
		}
		return locationList;

}
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		
	}


}
