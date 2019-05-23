package edu.handong.analysis.utils;



public class NotEnoughArgumentException extends Exception{
	public NotEnoughArgumentException() {
		super("No CLI argument Exception! Please put a file path.");
	}
	public NotEnoughArgumentException(String message) {
		super("The file path does not exist. Please check your CLI argument!");
		
	}
	
}