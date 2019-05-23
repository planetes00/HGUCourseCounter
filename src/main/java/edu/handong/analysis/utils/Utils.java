package edu.handong.analysis.utils;

import java.util.*;
import java.io.*;
import java.nio.*;
import java.io.FileWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Utils {
	
	public static ArrayList<String> getLines(String file, boolean removeHeader){
		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			ArrayList<String> list=new ArrayList<String>();
	        String line = "";
	        while((line = reader.readLine()) != null){
	            String[] array = line.split("\n");
	            for(String ar: array)
	            	list.add(ar);
	        }
	        if(removeHeader) list.remove(0);
	        return (ArrayList<String>)list;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }  

}
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		
		
		
		try (FileWriter writer = new FileWriter(targetFileName)){
			Iterator iterator = lines.listIterator();
			while (iterator.hasNext()) {
				String str1=(String)iterator.next();
				writer.append(str1);
				writer.append('\n');
						
			}
	        writer.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


}
