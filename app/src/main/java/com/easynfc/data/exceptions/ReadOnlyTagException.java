package com.easynfc.data.exceptions;

public class ReadOnlyTagException extends Exception {

	public ReadOnlyTagException(){
		super("Tag is read only.");
	}
	
}
